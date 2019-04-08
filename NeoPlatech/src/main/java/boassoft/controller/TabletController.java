package boassoft.controller;

import java.io.File;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.common.CommonXmlList;
import boassoft.common.CommonXmlManage;
import boassoft.common.GoodsXmlList;
import boassoft.common.GoodsXmlManage;
import boassoft.service.BatchMssqlService;
import boassoft.service.DeviceLogService;
import boassoft.service.DeviceService;
import boassoft.service.GoodsReceiptService;
import boassoft.service.GoodsShipmentOutService;
import boassoft.service.PackingReceiptService;
import boassoft.service.PackingShipmentOutService;
import boassoft.service.TabService;
import boassoft.service.TabletService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class TabletController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;

	@Resource(name = "tabletService")
	private TabletService tabletService;

	@Resource(name = "tabService")
	private TabService tabService;

	@Resource(name = "deviceService")
	private DeviceService deviceService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "commonXmlManage")
	private CommonXmlManage commonXmlManage;

	@Resource(name = "goodsXmlManage")
	private GoodsXmlManage goodsXmlManage;

	@Resource(name = "deviceLogService")
	private DeviceLogService deviceLogService;

	@Resource(name = "deviceLogSeqIdGnrService")
	private EgovIdGnrService deviceLogSeqIdGnrService;

	@Resource(name = "goodsReceiptService")
	private GoodsReceiptService goodsReceiptService;

	@Resource(name = "packingReceiptService")
	private PackingReceiptService packingReceiptService;

	@Resource(name = "packingShipmentOutService")
	private PackingShipmentOutService packingShipmentOutService;
	
	@Resource(name = "goodsShipmentOutService")
	private GoodsShipmentOutService goodsShipmentOutService;
	
	@Resource(name = "batchMssqlService")
	private BatchMssqlService batchMssqlService;

	/** log */
	protected static final Log LOG = LogFactory.getLog(TabletController.class);

	@RequestMapping(value = "/goods/login.do")
	public String rfidLogin(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/login.do" + " - " + cmap);

		cmap.put("user_id", cmap.getString("user_id", "").trim());
		cmap.put("user_pw", cmap.getString("user_pw", "").trim());
		cmap.put("deviceno", cmap.getString("deviceno", "").trim());

		// 사용자ID/비밀번호 대문자 변환
		cmap.put("user_id", cmap.getString("user_id").toUpperCase());
		cmap.put("user_pw", cmap.getString("user_pw").toUpperCase());

		String xmlString = "";

		try {
			// 디바이스 접근 로그 기록
			cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId()
					.replaceAll("^[0]+", ""));
			cmap.put("accessIp", request.getRemoteAddr());
			deviceLogService.insertDeviceLog(cmap);

			// 디바이스번호 확인
			CommonMap deviceView = deviceService.getDeviceView(cmap);

			if (deviceView.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
				model.addAttribute("xmlString", xmlString);
				return "common/commonXml";

			}

			if (!"".equals(cmap.getString("user_pw"))) {
				cmap.put("user_pw", EgovFileScrty.encryptPassword(cmap
						.getString("user_pw")));
				// System.out.println("user_pw : " + cmap.getString("user_pw"));
			}

			CommonMap view = tabService.getRfidLoginView(cmap);

			if (view == null || view.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>[로그인실패] 아이디 또는 비밀번호가 맞지 않습니다.</retmsg></data>";
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>OK</ret><retmsg>로그인 성공</retmsg><user_key>"
						+ view.getString("userKey") + "</user_key></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			LOG.debug(e.toString());
			// e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/selectInventoryListXml.do")
	public String inventoryDetailSelectListXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/inventory/detail/selectListXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));
		cmap.put("inv_year", cmap.getString("inv_year", ""));
		cmap.put("inv_no", cmap.getString("inv_no", ""));
		cmap.put("dataOrder", cmap.getString("sort_option", ""));
		cmap.put("dataOrderArrow", cmap.getString("order", ""));
		cmap.put("user_key", cmap.getString("user_key", ""));

		CommonXmlList commonXmlList = new CommonXmlList();
		String xmlString = "";

		try {

			// 디바이스 접근 로그 기록
			cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId()
					.replaceAll("^[0]+", ""));
			cmap.put("accessIp", request.getRemoteAddr());
			deviceLogService.insertDeviceLog(cmap);

			// 디바이스번호 확인
			CommonMap deviceView = deviceService.getDeviceView(cmap);
			if (deviceView.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
				model.addAttribute("xmlString", xmlString);
				return "common/commonXml";
			}

			// 사용자 확인
			CommonMap userView = userService.getUserKeyLoginView(cmap);
			// 사용자 확인
			if (userView.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 사용자 접근입니다.</retmsg></data>";
				model.addAttribute("xmlString", xmlString);
				return "common/commonXml";
			}

			if (!"MGR".equals(userView.getString("grantNo"))) {
				// 사용자 부서 권한 확인
				CommonList userDeptList = tabService.getUserDeptList(userView);

				if (userDeptList.isEmpty()) {
					xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>부서 권한이 없습니다. (관리자 권한 작업이 필요합니다.)</retmsg></data>";
					model.addAttribute("xmlString", xmlString);
					return "common/commonXml";

				} else {
					String deptNameArr = "";
					for (int k = 0; k < userDeptList.size(); k++) {
						CommonMap userDept = userDeptList.getMap(k);
						deptNameArr += "," + userDept.getString("deptName");
					}
					deptNameArr = deptNameArr.replaceAll("^,", "");
					cmap.put("deptNameArr", deptNameArr.split(","));

				}

			}
			// 최신 차수 조회하기
			if ("".equals(cmap.getString("inv_year"))
					|| "".equals(cmap.getString("inv_no"))) {
				CommonMap invLast = tabService.getInventoryLast(cmap);
				cmap.put("inv_year", invLast.getString("inv_year"));
				cmap.put("inv_no", invLast.getString("inv_no"));
			}

			commonXmlList = tabService.getInventoryDetailListXml(cmap);

			if (commonXmlList.size() > 0) {
				xmlString = commonXmlManage.writeXmlString(commonXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/inventorUypload.do")
	public String inventorySyncUpload(
			final MultipartHttpServletRequest multiRequest,
			HttpServletRequest request, ModelMap model) throws Exception {
		String xmlString = "";

		try {

			CommonMap cmap = commonMap.setMultipartXml(multiRequest, "",
					"Globals.Asset.Xml.fileStorePath", "");
			System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
					+ " - " + "/goods/sync/inventorUypload.do" + " - " + cmap);

			cmap.put("deviceno", cmap.getString("deviceno", "").trim());

			// 디바이스 접근 로그 기록
			cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId()
					.replaceAll("^[0]+", ""));
			cmap.put("accessIp", request.getRemoteAddr());
			deviceLogService.insertDeviceLog(cmap);

			// 디바이스번호 확인
			CommonMap deviceView = deviceService.getDeviceView(cmap);
			if (deviceView.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
				model.addAttribute("xmlString", xmlString);
				return "common/commonXml";
			}

			// 디바이스번호로 조사자명 가져오기
			cmap.put("tagInspName", deviceView.getString("devicenm"));

			if (!"".equals(cmap.getString("fileStreFileNm"))) {
				String filePath = cmap.getString("fileFileStreCours");
				String fileNm = cmap.getString("fileStreFileNm");
				// String fileExt = cmap.getString("fileFileExtsn");
				String xmlPath = filePath + File.separatorChar + fileNm;
				CommonList xmlList = commonXmlManage.getXmlList(xmlPath);
				System.out.println("xmlList.size() : " + xmlList.size());

				if (xmlList != null) {
					// 재물조사 업데이트
					for (int i = 0; i < xmlList.size(); i++) {
						CommonMap inv = xmlList.getMap(i);
						inv.put("tagInspName",
								cmap.getString("tagInspName",
										cmap.getString("deviceno")));
						tabService.updateInventoryDetail(inv);
					}
				}

			}

			xmlString = "1";

		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/selecGoodsShipmentOutListXml.do")
	public String goodsselecGoodsShipmentOutListXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/selecGoodsShipmentOutListXml.do" + " - "
				+ cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		CommonXmlList commonXmlList = new CommonXmlList();
		String xmlString = "";

		try {
			commonXmlList = tabletService.getSubsiDiaryReceiptListXml(cmap);
			if (commonXmlList.size() > 0) {
				xmlString = goodsXmlManage.writeXmlString(commonXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}

		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/uploadGoodsShipmentOut.do")
	public String goodsShipmentSyncUpload(
			final MultipartHttpServletRequest multiRequest,
			HttpServletRequest request, ModelMap model) throws Exception {
		String xmlString = "";

		try {

			CommonMap cmap = commonMap.setMultipartXml(multiRequest, "",
					"Globals.Asset.Xml.fileStorePath", "");
			System.out
					.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
							+ " - " + "/goods/uploadGoodsShipmentOut.do"
							+ " - " + cmap);

			cmap.put("deviceno", cmap.getString("deviceno", "").trim());

			// 디바이스 접근 로그 기록
			cmap.put("deviceLogSeq", deviceLogSeqIdGnrService.getNextStringId()
					.replaceAll("^[0]+", ""));
			cmap.put("accessIp", request.getRemoteAddr());
			deviceLogService.insertDeviceLog(cmap);

			// 디바이스번호 확인
			CommonMap deviceView = deviceService.getDeviceView(cmap);

			if (deviceView.isEmpty()) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>인증되지 않은 디바이스 접근입니다. (관리자 승인이 필요합니다.)</retmsg></data>";
				model.addAttribute("xmlString", xmlString);
				return "common/commonXml";
			}

			// 디바이스번호로 조사자명 가져오기
			cmap.put("tagInspName", deviceView.getString("devicenm"));

			if (!"".equals(cmap.getString("fileStreFileNm"))) {

				String filePath = cmap.getString("fileFileStreCours");
				String fileNm = cmap.getString("fileStreFileNm");
				// String fileExt = cmap.getString("fileFileExtsn");
				String xmlPath = filePath + File.separatorChar + fileNm;
				CommonList xmlList = commonXmlManage.getXmlList(xmlPath);
				System.out.println("xmlList.size() : " + xmlList.size());

				if (xmlList != null) {

					// 재물조사 업데이트
					for (int i = 0; i < xmlList.size(); i++) {

						CommonMap inv = xmlList.getMap(i);
						inv.put("tagInspName",
								cmap.getString("tagInspName",
										cmap.getString("deviceno")));
						// tabService.updateInventoryDetail(inv);
						tabletService.updateGoodsShipment(inv);
					}
				}
			}

			xmlString = "1";

		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}

	
	@RequestMapping(value = "/packing/recallPackingReceiptXml.do")
	public String recallPackingReceiptXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		int resultCnt = 0;
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/packing/recallPackingReceiptXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("odId", cmap.getString("od_id", ""));
		cmap.put("receiptCnt", cmap.getString("receipt_cnt", ""));
		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "
				+ cmap.getString("pageSize", ""));
		System.out.println(" odId " + " : " + cmap.getString("odId", ""));
		System.out.println(" receiptCnt " + " : "
				+ cmap.getString("receiptCnt", ""));

		CommonList goodsXmlList = new CommonList();

		String xmlString = "";
		String part_number = "";
		String lg_part_no = "";
		String neo_od_day = "";
		String neo_od_qty = "";
		String od_id = "";
		String qtyinvoiced = "";		
		String result = "0";

		try {
			goodsXmlList = tabletService.getPackingShipmentOutListXml(cmap);
			System.out.println(" goodsXmlList.size() " + " : "
					+ goodsXmlList.size());
			if (goodsXmlList.size() > 0) {
				double maxCnt = cmap.getDouble("receiptCnt", 0);
				double disCnt = 0;
				System.out.println(" maxCnt " + " : " + maxCnt);
				for (int i = 0; i < goodsXmlList.size(); i++) {
					CommonMap gmap = (CommonMap) goodsXmlList.get(i);
					double sumQty = Double.parseDouble(gmap
							.getString("neoOdQty"));

					if (maxCnt > 0) {

						System.out.println(" receipt_cnt 444 " + " : "
								+ gmap.toString());
						part_number = gmap.getString("partNumber");
						lg_part_no = gmap.getString("lgPartNo");
						neo_od_day = gmap.getString("neoOdDay");
						neo_od_qty = gmap.getString("neoOdQty");
						od_id = gmap.getString("od_id");
						result = gmap.getString("result");
						System.out.println(" part_number 444 " + " : "
								+ part_number);
						System.out.println(" lg_part_no 444 " + " : "
								+ lg_part_no);
						System.out.println(" neo_od_day 444 " + " : "
								+ neo_od_day);
						System.out.println(" neo_od_qty 444 " + " : "
								+ neo_od_qty);
						System.out.println(" od_id 444 " + " : "
								+ od_id);
						System.out.println(" result 444 " + " : " + result);

						System.out.println("000 maxCnt " + " : " + maxCnt);
						// maxCnt 300 sumQty 150 입고량 소요량 maxCnt 5 sumQty 0
						if (maxCnt == sumQty) {
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", maxCnt);
							gmap.put("qtyinvoiced", maxCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 불량창고로 보낸다.
							packingReceiptService.insertCRecall(gmap);
							packingReceiptService.insertCRecallLine(gmap);
							System.out.println(" receipt_cnt 1111 " + " : "
									+ gmap.toString());

							break;
						} else if (maxCnt > sumQty) { // 입고량이 소요량보다 많으면
							disCnt = maxCnt - sumQty; // 150 부족 1개
							System.out.println("111 disCnt " + " : " + disCnt);
							resultMap.put("qtyOnHand", 0);
							resultMap.put("preQtyOnHand", disCnt);
							gmap.put("qtyinvoiced", sumQty);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 불량창고로 보낸다.
							packingReceiptService.insertCRecall(gmap);
							packingReceiptService.insertCRecallLine(gmap);
							System.out.println(" receipt_cnt 2222 " + " : "
									+ gmap.toString());

							break;

						} else { // 소요량이 입고량보다 많으면
							disCnt = sumQty - maxCnt; // 150 부족 1개
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", sumQty);
							gmap.put("qtyinvoiced", disCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 불량창고로 보낸다.
							packingReceiptService.insertCRecall(gmap);
							packingReceiptService.insertCRecallLine(gmap);
							System.out.println(" receipt_cnt 333 " + " : "
									+ gmap.toString());
							break;
						}
						
						
					}
				}
				
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>"
						+ "<od_id><![CDATA[" + od_id
						+ "]]></od_id>" + "<neo_od_qty><![CDATA["
						+ neo_od_qty + "]]></neo_od_qty>" + "<result><![CDATA["
						+ result + "]]></result>" + "</data>";
				System.out.println(" xmlString 555 " + " : " + xmlString);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
				System.out.println(" xmlString 666 " + " : " + xmlString);
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			System.out.println(" xmlString 777 " + " : " + xmlString);
			e.printStackTrace();
		}
		System.out.println(" xmlString 888 " + " : " + xmlString);
		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/packing/packingShipmentXml.do")
	public String packingShipmentXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		int resultCnt = 0;
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/packing/packingShipmentXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("odId", cmap.getString("od_id", ""));
		cmap.put("receiptCnt", cmap.getString("receipt_cnt", ""));
		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "
				+ cmap.getString("pageSize", ""));
		System.out.println(" odId " + " : " + cmap.getString("odId", ""));
		System.out.println(" receiptCnt " + " : "
				+ cmap.getString("receiptCnt", ""));

		CommonList goodsXmlList = new CommonList();

		String xmlString = "";
		String part_number = "";
		String lg_part_no = "";
		String neo_od_day = "";
		String neo_od_qty = "";
		String od_id = "";
		String qtyinvoiced = "";
		String result = "0";

		try {
			goodsXmlList = tabletService.getPackingShipmentOutListXml(cmap);
			System.out.println(" goodsXmlList.size() " + " : "
					+ goodsXmlList.size());
			if (goodsXmlList.size() > 0) {
				double maxCnt = cmap.getDouble("receiptCnt", 0);
				double disCnt = 0;

				for (int i = 0; i < goodsXmlList.size(); i++) {
					CommonMap gmap = (CommonMap) goodsXmlList.get(i);
					double sumQty = Double.parseDouble(gmap
							.getString("neoOdQty"));

					if (maxCnt > 0) {

						System.out.println(" receipt_cnt 444 " + " : "
								+ gmap.toString());
						part_number = gmap.getString("partNumber");
						lg_part_no = gmap.getString("lgPartNo");
						neo_od_day = gmap.getString("neoOdDay");
						neo_od_qty = gmap.getString("neoOdQty");	
						od_id = gmap.getString("odId");
						result = gmap.getString("result");
						
						System.out.println(" part_number 444 " + " : "
								+ part_number);
						System.out.println(" lg_part_no 444 " + " : "
								+ lg_part_no);
						System.out.println(" neo_od_day 444 " + " : "
								+ neo_od_day);
						System.out.println(" neo_od_qty 444 " + " : "
								+ neo_od_qty);
						System.out.println(" od_id 444 " + " : "
								+ od_id);
						System.out.println(" result 444 " + " : " + result);

						System.out.println("000 maxCnt " + " : " + maxCnt);
						// maxCnt 300 sumQty 150 입고량 소요량 maxCnt 5 sumQty 0
						if (maxCnt == sumQty) {
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", maxCnt);
							gmap.put("qtyinvoiced", maxCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 출고지시를 한다.
							packingShipmentOutService.insertMOutOrder(gmap);
							packingShipmentOutService.insertMOutOrderLine(gmap);
							System.out.println(" receipt_cnt 1111 " + " : "
									+ gmap.toString());

							break;
						} else if (maxCnt > sumQty) { // 입고량이 소요량보다 많으면
							disCnt = maxCnt - sumQty; // 150 부족 1개
							System.out.println("111 disCnt " + " : " + disCnt);
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", disCnt);
							gmap.put("qtyinvoiced", sumQty);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 출고지시를 한다.
							packingShipmentOutService.insertMOutOrder(gmap);
							packingShipmentOutService.insertMOutOrderLine(gmap);
							System.out.println(" receipt_cnt 2222 " + " : "
									+ gmap.toString());
							break;

						} else { // 소요량이 입고량보다 많으면
							disCnt = sumQty - maxCnt; // 150 부족 1개
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", sumQty);
							gmap.put("qtyinvoiced", disCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 출고지시를 한다.
							packingShipmentOutService.insertMOutOrder(gmap);
							packingShipmentOutService.insertMOutOrderLine(gmap);
							System.out.println(" receipt_cnt 333 " + " : "
									+ gmap.toString());
							break;
						}
					}

				}

				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>"
						+ "<od_id><![CDATA[" + od_id
						+ "]]></od_id>" + "<neo_od_qty><![CDATA["
						+ neo_od_qty + "]]></neo_od_qty>" + "<result><![CDATA["
						+ result + "]]></result>" + "</data>";
				System.out.println(" xmlString 555 " + " : " + xmlString);

			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
				System.out.println(" xmlString 666 " + " : " + xmlString);
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			System.out.println(" xmlString 777 " + " : " + xmlString);
			e.printStackTrace();
		}
		System.out.println(" xmlString 888 " + " : " + xmlString);
		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/packing/packingShipmentOutXml.do")
	public String packingShipmentOutXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		int resultCnt = 0;
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/packing/packingShipmentOutXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("odId", cmap.getString("od_id", ""));
		cmap.put("receiptCnt", cmap.getString("receipt_cnt", ""));
		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "
				+ cmap.getString("pageSize", ""));
		System.out.println(" odId " + " : " + cmap.getString("odId", ""));
		System.out.println(" receiptCnt " + " : "
				+ cmap.getString("receiptCnt", ""));

		CommonList goodsXmlList = new CommonList();

		String xmlString = "";
		String part_number = "";
		String lg_part_no = "";
		String neo_od_day = "";
		String neo_od_qty = "";
		String od_id = "";
		String qtyinvoiced = "";
		String result = "2";

		try {
			goodsXmlList = tabletService.getPackingShipmentOutListXml(cmap);
			System.out.println(" goodsXmlList.size() " + " : "
					+ goodsXmlList.size());
			if (goodsXmlList.size() > 0) {
				double maxCnt = cmap.getDouble("receiptCnt", 0);
				double disCnt = 0;
				System.out.println(" maxCnt " + " : " + maxCnt);
				for (int i = 0; i < goodsXmlList.size(); i++) {
					CommonMap gmap = (CommonMap) goodsXmlList.get(i);
					double sumQty = Double.parseDouble(gmap
							.getString("neoOdQty"));

					if (maxCnt > 0) {

						System.out.println(" receipt_cnt 444 " + " : "
								+ gmap.toString());
						part_number = gmap.getString("partNumber");
						lg_part_no = gmap.getString("lgPartNo");
						neo_od_day = gmap.getString("neoOdDay");
						neo_od_qty = gmap.getString("neoOdQty");
						od_id = gmap.getString("odId");
						result = gmap.getString("result");
						System.out.println(" part_number 444 " + " : "
								+ part_number);
						System.out.println(" lg_part_no 444 " + " : "
								+ lg_part_no);
						System.out.println(" neo_od_day 444 " + " : "
								+ neo_od_day);
						System.out.println(" neo_od_qty 444 " + " : "
								+ neo_od_qty);
						System.out.println(" result 444 " + " : " + result);

						System.out.println("000 maxCnt " + " : " + maxCnt);
						// maxCnt 300 sumQty 150 입고량 소요량 maxCnt 5 sumQty 0
						if (maxCnt == sumQty) {
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", maxCnt);
							gmap.put("qtyinvoiced", maxCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 매입지시를 한다.
							packingShipmentOutService.insertCInvoicePo(gmap);
							packingShipmentOutService
									.insertCInvoicePoLine(gmap);
							System.out.println(" receipt_cnt 1111 " + " : "
									+ gmap.toString());

							break;
						} else if (maxCnt > sumQty) { // 입고량이 소요량보다 많으면
							disCnt = maxCnt - sumQty; // 150 부족 1개
							System.out.println("111 disCnt " + " : " + disCnt);
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", disCnt);
							gmap.put("qtyinvoiced", sumQty);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 매입지시를 한다.
							packingShipmentOutService.insertCInvoicePo(gmap);
							packingShipmentOutService
									.insertCInvoicePoLine(gmap);

							System.out.println(" receipt_cnt 2222 " + " : "
									+ gmap.toString());
							break;

						} else { // 소요량이 입고량보다 많으면 남는다.
							disCnt = sumQty - maxCnt; // 150 부족 1개
							gmap.put("qtyOnHand", 0);
							gmap.put("preQtyOnHand", sumQty);
							gmap.put("qtyinvoiced", disCnt);
							qtyinvoiced = gmap.getString("qtyinvoiced");
							resultCnt = packingReceiptService
									.updateQtyOnHand(gmap);
							// 매입지시를 한다..
							packingShipmentOutService.insertCInvoicePo(gmap);
							packingShipmentOutService
									.insertCInvoicePoLine(gmap);

							System.out.println(" receipt_cnt 333 " + " : "
									+ gmap.toString());
							break;
						}

					}

				}

				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>"
						+ "<od_id><![CDATA[" + od_id
						+ "]]></od_id>" + "<neo_od_qty><![CDATA["
						+ neo_od_qty + "]]></neo_od_qty>" + "<result><![CDATA["
						+ result + "]]></result>" + "</data>";
				System.out.println(" xmlString 555 " + " : " + xmlString);

			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
				System.out.println(" xmlString 666 " + " : " + xmlString);
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			System.out.println(" xmlString 777 " + " : " + xmlString);
			e.printStackTrace();
		}
		System.out.println(" xmlString 888 " + " : " + xmlString);
		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/goodsShipmentDetailKitItemOutXml.do")
	public String goodsShipmentDetailKitItemOutXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/goodsShipmentDetailKitItemOutXml.do " + " - "
				+ cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("pt_od_id", cmap.getString("pt_od_id", "").trim());

		GoodsXmlList goodsXmlList = new GoodsXmlList();
		String xmlString = "";

		try {
			goodsXmlList = tabletService.goodsShipmentDetailKitItemOutXml(cmap);
			if (goodsXmlList != null) {
				xmlString = goodsXmlManage.writeXmlString(goodsXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/goodsShipmentDetailRefItemOutXml.do")
	public String goodsShipmentDetailRefItemOutXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/goodsShipmentDetailRefItemOutXml.do" + " - "
				+ cmap);

		cmap.put("deviceno", cmap.getString("deviceno", ""));
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("pt_od_id", cmap.getString("pt_od_id", "").trim());

		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" page_idx " + " : "
				+ cmap.getString("page_idx", ""));
		System.out.println(" page_size " + " : "
				+ cmap.getString("page_size", ""));
		System.out.println(" pt_od_id " + " : "
				+ cmap.getString("pt_od_id", ""));

		GoodsXmlList goodsXmlList = new GoodsXmlList();
		String xmlString = "";

		try {
			goodsXmlList = tabletService.goodsShipmentDetailRefItemOutXml(cmap);
			if (goodsXmlList != null) {
				xmlString = goodsXmlManage.writeXmlString(goodsXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/packing/optionVendorListXml.do")
	public String optionVendorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/packing/optionVendorListXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", ""));
		cmap.put("neoOdDay", cmap.getString("neo_od_day", ""));
		cmap.put("lgPartNo", cmap.getString("lg_part_no", ""));

		GoodsXmlList goodsXmlList = new GoodsXmlList();
		String xmlString = "";

		try {
			goodsXmlList = tabletService.optionVendorListXml(cmap);
			System.out.println(" goodsXmlList.size() " + goodsXmlList.size());

			if (goodsXmlList != null) {
				xmlString = goodsXmlManage.writeXmlString(goodsXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}

		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/packing/optionPartNoListXml.do")
	public String optionPartNoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/packing/optionPartNoListXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", ""));
		cmap.put("neoOdDay", cmap.getString("neo_od_day", ""));
		cmap.put("finalVendor", URLEncoder.encode(cmap.getString("final_vendor", ""), "UTF-8"));		
		

		System.out.println("deviceno  " + " : " + cmap.getString("deviceno", ""));
		System.out.println("neoOdDay  " + " : " + cmap.getString("neoOdDay", ""));
		System.out.println("finalVendor  " + " : " + cmap.getString("finalVendor", ""));
		
		GoodsXmlList goodsXmlList = new GoodsXmlList();
		String xmlString = "";
		try {
			goodsXmlList = tabletService.optionPartNoListXml(cmap);
			System.out.println(" goodsXmlList.size() " + goodsXmlList.size());

			if (goodsXmlList != null) {

			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/receiptStockDReceiptXml.do")
	public String receiptStockReceiptXml(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		int resultCnt = 0;
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/receiptStockReceiptXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("ptOdId", cmap.getString("pt_od_id", ""));
		cmap.put("subPtOdId", cmap.getString("sub_pt_od_id", ""));
		cmap.put("receiptCnt", cmap.getString("receipt_cnt", ""));

		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "
				+ cmap.getString("pageSize", ""));
		System.out.println(" ptOdId " + " : " + cmap.getString("ptOdId", ""));
		System.out.println(" subPtOdId " + " : "
				+ cmap.getString("subPtOdId", ""));

		CommonList goodsXmlList = new CommonList();

		String xmlString = "";
		String part_number = "";
		String go_with = "";
		String vendor = "";
		String sub_sum_qty = "";
		String bom_qty = ""; 
		String sub_unit = "";
		String qtyinvoiced = "";
		String od_id = "";
		String result = "0";

		try {
			goodsXmlList = tabletService.getGoodsShipmentOutDetailListXml(cmap);
			System.out.println(" goodsXmlList.size() " + " : "
					+ goodsXmlList.size());
			if (goodsXmlList.size() > 0) {
				double maxCnt = cmap.getDouble("receiptCnt", 0);
				double disCnt = 0;
				System.out.println(" maxCnt " + " : " + maxCnt);  
				for(int i = 0; i < goodsXmlList.size(); i++){
					  CommonMap gmap = (CommonMap)goodsXmlList.get(i);
					  double sumQty = Double.parseDouble(gmap.getString("subSumQty"));
					  
					  if (maxCnt > 0) {
						  System.out.println(" receipt_cnt 444 " + " : " + gmap.toString());	
						  
						  part_number = gmap.getString("partNumber");
						  go_with = gmap.getString("goWith"); 
						  vendor = gmap.getString("vendor");
						  sub_sum_qty = gmap.getString("subSumQty");
						  sub_unit = gmap.getString("subUnit");
						  qtyinvoiced = gmap.getString("qtyinvoiced");
						  bom_qty = gmap.getString("bom_qty");
						  od_id = gmap.getString("odId");						  
						  result = gmap.getString("result");
						  System.out.println(" part_number 444 " + " : " + part_number);
						  System.out.println(" go_with 444 " + " : " + go_with);
						  System.out.println(" vendor 444 " + " : " + vendor);
						  System.out.println(" sub_sum_qty 444 " + " : " + sub_sum_qty);
						  System.out.println(" sub_unit 444 " + " : " + sub_unit);
						  System.out.println(" qtyinvoiced 444 " + " : " + qtyinvoiced);
						  System.out.println(" bom_qty 444 " + " : " + bom_qty);
						  System.out.println(" result 444 " + " : " + result);  
						  
						  System.out.println("000 maxCnt " + " : " + maxCnt);
	  					  // maxCnt 300 sumQty 150 출고량 소요량 maxCnt 5 sumQty 0
	  					  if(maxCnt == sumQty){
	  						  gmap.put("qtyOnHand",0);
  							  gmap.put("preQtyOnHand", maxCnt);  	
  							  gmap.put("qtyinvoiced", maxCnt);
  							  
  							resultCnt = goodsReceiptService.updateQtyInvoiced(gmap);
  							
  						  // 출고를 한다.
  							goodsShipmentOutService.insertRridMOut(gmap);
  							goodsShipmentOutService.insertRridMOutLine(gmap);
  							System.out.println(" receipt_cnt 1111 " + " : " + gmap.toString());
  							
  							break;
	  					  }else if(maxCnt > sumQty) { // 출고량이 소요량보다 많으면
	  						disCnt = maxCnt - sumQty; // 150  부족 1개 
	  						System.out.println("111 disCnt " + " : " + disCnt);
	  						gmap.put("qtyOnHand",0);
  							gmap.put("preQtyOnHand", disCnt);
  							gmap.put("qtyinvoiced", sumQty);
  							resultCnt = goodsReceiptService.updateQtyInvoiced(gmap);
  							
  						    // 출고를 한다.
  							goodsShipmentOutService.insertRridMOut(gmap);
  							goodsShipmentOutService.insertRridMOutLine(gmap);  						
  							
  							System.out.println(" receipt_cnt 2222 " + " : " + gmap.toString());
  							break;
	  					}else{ // 소요량이 출고량보다 많으면 남는다.
	  						disCnt = sumQty - maxCnt; // 150  부족 1개 
	  						gmap.put("qtyOnHand",0);
  							gmap.put("preQtyOnHand", sumQty);
  							gmap.put("qtyinvoiced", disCnt);
  							resultCnt = goodsReceiptService.updateQtyInvoiced(gmap);
  							
  							goodsShipmentOutService.insertRridMOut(gmap);
  							goodsShipmentOutService.insertRridMOutLine(gmap);
  							
  							System.out.println(" receipt_cnt 333 " + " : " + gmap.toString());
  							break;
	  					}
					  }
					  
				}
				
				 xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>"
	    					+ "<part_number><![CDATA["+part_number+"]]></part_number>"
	    					+ "<go_with><![CDATA["+go_with+"]]></go_with>"
	    					+ "<vendor><![CDATA["+vendor+"]]></vendor>"
	    					+ "<sub_sum_qty><![CDATA["+sub_sum_qty+"]]></sub_sum_qty>"
	    					+ "<sub_unit><![CDATA["+sub_unit+"]]></sub_unit>"
	    					+ "<bom_qty><![CDATA["+bom_qty+"]]></bom_qty>"
	    					+ "<result><![CDATA["+result+"]]></result>"
	    					+ "</data>";  	
				System.out.println(" xmlString 555 " + " : " + xmlString);
			}else{
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
    			System.out.println(" xmlString 666 " + " : " + xmlString);
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
    		System.out.println(" xmlString 777 " + " : " + xmlString);
    		e.printStackTrace();
		}
		System.out.println(" xmlString 888 " + " : " + xmlString);	
		model.addAttribute("xmlString", xmlString);

		return "common/commonXml";
	}
	
	@RequestMapping(value = "/packing/packingReceiptDetailXml.do")
	public String getPackingReceiptListXml(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception {
		System.out.println(" packingReceiptDetailXml " + " : " + "start");
		CommonMap cmap = new CommonMap(request);
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")+ " - " + "/packingReceiptDetailXml.do" + " - "+ cmap);

		cmap.put("deviceno", cmap.getString("deviceno", ""));
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("odDay", cmap.getString("od_day", ""));
		cmap.put("finalVendor", cmap.getString("final_vendor", ""));
		cmap.put("pkgPoNO", cmap.getString("pkg_po_no", ""));

		System.out.println(" deviceno " + " : "		+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "		+ cmap.getString("pageSize", ""));
		System.out.println(" odDay " + " : " + cmap.getString("neoOdDay", ""));
		System.out.println(" finalVendor " + " : "+ cmap.getString("finalVendor", ""));
		System.out.println(" pkgPoNO " + " : " + cmap.getString("pkgPoNO", ""));

		GoodsXmlList goodsXmlList = new GoodsXmlList();
		String xmlString = "";

		try {
			goodsXmlList = tabletService.getPackingReceptListXml(cmap);
			if (goodsXmlList != null) {
				xmlString = goodsXmlManage.writeXmlString(goodsXmlList);
			} else {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
			}
		} catch (Exception e) {
			xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
			e.printStackTrace();
		}

		model.addAttribute("xmlString", xmlString);
		
		return "common/commonXml";

	}

	@RequestMapping(value = "/goods/goodsDistributionOrderListXml.do")
	public String DistributionOrderListXml(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		int resultCnt = 0;
		System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss")
				+ " - " + "/goods/goodsDistributionOrderListXml.do" + " - " + cmap);

		cmap.put("deviceno", cmap.getString("deviceno", "").trim());
		cmap.put("pageIdx", cmap.getString("page_idx", "1"));
		cmap.put("pageSize", cmap.getString("page_size", "10"));

		cmap.put("ptOdId", cmap.getString("pt_od_id", ""));
		cmap.put("subPtOdId", cmap.getString("sub_pt_od_id", ""));
		cmap.put("receiptCnt", cmap.getString("receipt_cnt", ""));
		cmap.put("orderType", cmap.getString("order_type", ""));

		System.out.println(" deviceno " + " : "
				+ cmap.getString("deviceno", ""));
		System.out.println(" pageIdx " + " : " + cmap.getString("pageIdx", ""));
		System.out.println(" pageSize " + " : "
				+ cmap.getString("pageSize", ""));
		System.out.println(" ptOdId " + " : " + cmap.getString("ptOdId", ""));
		System.out.println(" subPtOdId " + " : "
				+ cmap.getString("subPtOdId", ""));
		System.out.println(" orderType " + " : "
				+ cmap.getString("orderType", ""));
		
		CommonList goodsXmlList = new CommonList();			

		String xmlString = "";
		String part_number = "";
		String go_with = "";
		String vendor = "";
		String sub_sum_qty = "";
		String sub_unit = "";
		String qtyinvoiced = "";
		String bom_qty = "";
		String pt_od_id = cmap.getString("ptOdId", "");
        String order_type = cmap.getString("orderType", "");
        String receiptCnt = cmap.getString("receiptCnt","0");
		String result = "0";
		
		try {
			goodsXmlList = tabletService.getGoodsShipmentOutDetailListXml(cmap);
			
			System.out.println(" goodsXmlList.size() " + " : "+ goodsXmlList.size());
			
			if (goodsXmlList.size() > 0) {
				
				double maxCnt = cmap.getDouble("receiptCnt", 0);
				double disCnt = 0;
				System.out.println(" maxCnt " + " : " + maxCnt);  
				for(int i = 0; i < goodsXmlList.size(); i++){
					  CommonMap gmap = (CommonMap)goodsXmlList.get(i);
					  double sumQty = Double.parseDouble(gmap.getString("subSumQty"));
					  
					  if (maxCnt > 0) {
						
						  System.out.println(" receipt_cnt 444 " + " : " + gmap.toString());
						  
						  part_number = gmap.getString("partNumber");
						  go_with = gmap.getString("goWith"); 
						  vendor = gmap.getString("vendor");
						  sub_sum_qty = gmap.getString("subSumQty");
						  sub_unit = gmap.getString("subUnit");
						  qtyinvoiced = gmap.getString("qtyinvoiced");
						  bom_qty = gmap.getString("bomQty");
						  result = gmap.getString("result");
						  gmap.put("orderType",order_type);
						  System.out.println(" part_number 444 " + " : " + part_number);
						  System.out.println(" go_with 444 " + " : " + go_with);
						  System.out.println(" vendor 444 " + " : " + vendor);
						  System.out.println(" sub_sum_qty 444 " + " : " + sub_sum_qty);
						  System.out.println(" sub_unit 444 " + " : " + sub_unit);
						  System.out.println(" qtyinvoiced 444 " + " : " + qtyinvoiced);
						  System.out.println(" bom_qty 444 " + " : " + bom_qty);
						  System.out.println(" result 444 " + " : " + result);  
						  
						  System.out.println("000 maxCnt " + " : " + maxCnt);
						  
						  
						  
						  resultCnt =  batchMssqlService.insertAssayOrderQty(gmap);
						  
						  System.out.println(" receipt_cnt 1111 " + " : " + gmap.toString());
							
						  break;
					  }
				}		  
					  xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>"
		    					+ "<part_number><![CDATA["+part_number+"]]></part_number>"
		    					+ "<go_with><![CDATA["+go_with+"]]></go_with>"
		    					+ "<vendor><![CDATA["+vendor+"]]></vendor>"
		    					+ "<sub_sum_qty><![CDATA["+sub_sum_qty+"]]></sub_sum_qty>"
		    					+ "<sub_unit><![CDATA["+sub_unit+"]]></sub_unit>"
		    					+ "<bom_qty><![CDATA["+bom_qty+"]]></bom_qty>"
		    					+ "<result><![CDATA["+result+"]]></result>"
		    					+ "</data>";  	
					System.out.println(" xmlString 555 " + " : " + xmlString);
				}else{
					xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data></data>";
	    			System.out.println(" xmlString 666 " + " : " + xmlString);
				}
			} catch (Exception e) {
				xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><data><ret>ERR</ret><retmsg>서버 오류</retmsg></data>";
	    		System.out.println(" xmlString 777 " + " : " + xmlString);
	    		e.printStackTrace();
			}
			System.out.println(" xmlString 888 " + " : " + xmlString);	
			model.addAttribute("xmlString", xmlString);

			return "common/commonXml";
		}	
}