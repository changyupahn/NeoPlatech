package boassoft.controller.kp2200;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import boassoft.controller.kp2100.KP2111GoodsReceiptDtlController;
import boassoft.service.GoodsReceiptService;
import boassoft.service.PackingReceiptService;
import boassoft.service.SubsiDiaryReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

@Controller
public class kp2210GoodsInvoiceController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;

	@Resource(name = "goodsReceiptService")
	private GoodsReceiptService goodsReceiptService;
	
	@Resource(name = "subsiDiaryReceiptService")
	private SubsiDiaryReceiptService subsiDiaryReceiptService;

	@Resource(name = "packingReceiptService")
	private PackingReceiptService packingReceiptService;
	
	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "systemService")
	private SystemService systemService;

	/** log */
	protected static final Log LOG = LogFactory
			.getLog(KP2111GoodsReceiptDtlController.class);

	@RequestMapping(value = "/kp2200/kp2210.do")
	public String kp2110(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));
		cmap.put("pageLimit", pageLimit);

		// 화면표시관리 (부자재입고목록)
		cmap.put("dispType", "GOODS_RECEIPT_DETAIL_LIST");
		CommonList goodsReceiptDetailList = systemService.getDispMngList(cmap);
		model.addAttribute("goodsReceiptDetailList", goodsReceiptDetailList);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp2200/kp2210";
	}

	@RequestMapping(value = "/kp2200/kp2210Ajax.do")
	public String kp2210GoodsInvoiceAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
		cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd", ""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
		cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS", ""));
		cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE", ""));

		System.out.println(" dataOrder " + "  : "
				+ cmap.getString("dataOrder", ""));
		System.out.println(" dataOrderArrow " + "  : "
				+ cmap.getString("dataOrderArrow", ""));
		System.out.println(" pageLimit " + "  : "
				+ cmap.getString("pageLimit", ""));
		System.out.println(" sRqstVendorCd " + "  : "
				+ cmap.getString("sRqstVendorCd", ""));
		System.out.println(" sRqstItemCd " + "  : "
				+ cmap.getString("sRqstItemCd", ""));
		System.out.println(" sRqstPNoCd " + "  : "
				+ cmap.getString("sRqstPNoCd", ""));
		System.out.println(" searchDtKeywordS " + "  : "
				+ cmap.getString("searchDtKeywordS", ""));
		System.out.println(" searchDtKeywordE " + "  : "
				+ cmap.getString("searchDtKeywordE", ""));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);

		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		CommonList resultList = goodsReceiptService
				.getGoodsReceiptDetailList(cmap);

		CommonMap result = new CommonMap();
		result.put("resultList", resultList);
		result.put("totalRow", resultList.totalRow);
		model.addAttribute("printString", result.toJsonString());

		return "common/commonString";
	}

	@RequestMapping(value = "/kp2200/kp2210Search.do")
	public String kp2210Search(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp2200/kp2210Search";
	}

	@RequestMapping(value = "/kp2200/kp2210Excel.do")
	public String kp2110Excel(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", "999999");
		cmap.put("pageLimit", "1");
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		// 사용자 기본 파라미터 설정
		if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
				&& "USR".equals(cmap.getString("searchDiv"))) {
			cmap.put("sUserNo",
					cmap.getString("sUserNo", SessionUtil.getString("userNo")));
			cmap.put(
					"sUserName",
					cmap.getString("sUserName",
							SessionUtil.getString("userName")));
			cmap.put("sDeptNo",
					cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
			cmap.put(
					"sDeptName",
					cmap.getString("sDeptName",
							SessionUtil.getString("deptName")));
		}

		CommonList resultList = goodsReceiptService.getGoodsReceiptList(cmap);
		
		// 화면표시관리 (자산목록)
		cmap.put("dispType", "GOODS_RECEIPT_LIST_EXCEL");
		CommonList dispMngList = systemService.getDispMngList(cmap);
		
		int headerSize = dispMngList.size();
		String[] headerListLgc1 = new String[headerSize];
		String[] headerListLgc2 = null;
		String[] headerListPhc = new String[headerSize];
		String[] headerListTyp = new String[headerSize];
		String[] headerListWidth = new String[headerSize];
		String[][] mergedRegion = null;
		int idx = 0;
		
		while (idx < dispMngList.size()) {
			CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = ""
					+ Math.round(dispMng.getInt("default_width", 100) / 10);
			idx++;
		}
		
		ExcelUtil.write2(request, response, resultList, "부자재입고목록",
				headerListLgc1, headerListLgc2, headerListPhc, headerListTyp,
				mergedRegion, headerListWidth, 20);
		
		return null;

	}
	
		@RequestMapping(value = "/kp2200/kp2210DetailAjax.do")
	public ModelAndView kp2210DetailAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		CommonMap cmap = new CommonMap(request);		
		
		System.out.println("  cmap " + " : " + cmap.toString());
		
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
    	
		
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd", ""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
		cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS", ""));
		cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE", ""));
    	cmap.put("odId", cmap.getString("odId",""));
    	cmap.put("demandId", cmap.getString("demandId",""));
		
    	System.out.println(" dataOrder " + "  : "  +cmap.getString("dataOrder", "") );
		System.out.println(" dataOrderArrow " + "  : "  +cmap.getString("dataOrderArrow", "") );
		System.out.println(" pageLimit " + "  : "  +cmap.getString("pageLimit", "") );
		System.out.println(" sRqstVendorCd " + "  : "  +cmap.getString("sRqstVendorCd", "") );
		System.out.println(" sRqstItemCd " + "  : "  +cmap.getString("sRqstItemCd", "") );
		System.out.println(" sRqstPNoCd " + "  : "  +cmap.getString("sRqstPNoCd", "") );
		System.out.println(" searchDtKeywordS " + "  : "  +cmap.getString("searchDtKeywordS", "") );
		System.out.println(" searchDtKeywordE " + "  : "  +cmap.getString("searchDtKeywordE", "") );		
		System.out.println(" odId " + "  : "  +cmap.getString("odId", "") );
		System.out.println(" demandId " + "  : "  +cmap.getString("demandId", "") );
		CommonList commonCodeList = new CommonList();
		try {
			 commonCodeList = subsiDiaryReceiptService.getSubsiDiaryReceiptList(cmap);
			  System.out.println(" commonCodeList " + " : " + commonCodeList.size() ); 
			 
		    	/*if(commonCodeList.size() > 0){
		    		for(int i = 0; i <  commonCodeList.size(); i++){
		    		CommonMap map = (CommonMap)commonCodeList.get(i);
		    		System.out.println(" OD_ID " + " : " + map.getString("odId"));
		    		System.out.println(" DEMAND_ID " + " : " + map.getString("demandId"));
		    		System.out.println(" LGE_DATE " + " : " + map.getString("legDate"));
		    		System.out.println(" NEO_DATE " + " : " + map.getString("neoDate"));
		    		}
		    		
		    	}*/
		} catch (Exception e) {
			LOG.error("[" + this.getClass().getName()
					+ ".kp2210DetailAjax().Exception()]" + e.getMessage());
		}
		HashMap<String, CommonList> resultMap = new HashMap<String, CommonList>();

		resultMap.put("resultList", commonCodeList);
		System.out.println(" resultMap " + " : " + resultMap.toString() ); 
		modelAndView.addAllObjects(resultMap);
		modelAndView.setViewName("jsonView");

		return modelAndView;
	}
	
	@RequestMapping(value = "/kp2200/kp2210Stock.do")
	public String kp2210Stock(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		
		String od_id = "";
		String qtyinvoiced = "";
		String result = "2";

		
		int resultCnt = 0;
		try {
			//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}
    		
    		//파라미터    			    		    	
	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString(
					"saveJsonArray", "[]", false));
			CommonList paramList = new CommonList();

			for (int i = 0; i < saveJsonArray.size(); i++) {
				JSONObject obj = saveJsonArray.getJSONObject(i);
				CommonMap param = new CommonMap();
				param.put("odId", obj.get("odId"));			
				param.put("sReceiptCnt", obj.get("sReceiptCnt"));
				param.put("sRqstVendorCd", obj.get("sRqstVendorCd"));
				param.put("sRqstItemCd", obj.get("sRqstItemCd"));
				param.put("sRqstPNoCd", obj.get("sRqstPNoCd"));

				// 필수 파라미터 체크
				if ("".equals(param.getString("odId"))) {
					resultMap.put("ret", "ERR");
					resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
					return resultMap.toJsonString(model);
				}

				paramList.add(param);
			}
	    	
			// 파라미터
			cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
			cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
			cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",
					((CommonMap) paramList.get(0)).getString("sRqstVendorCd")));
			cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd",
					((CommonMap) paramList.get(0)).getString("sRqstItemCd")));
			cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd",
					((CommonMap) paramList.get(0)).getString("sRqstPNoCd")));
			cmap.put(
					"odId",
					cmap.getString("odId",
							((CommonMap) paramList.get(0)).getString("odId")));			
			cmap.put("sReceiptCnt", cmap.getInt("sReceiptCnt",
					((CommonMap) paramList.get(0)).getInt("sReceiptCnt")));
			
			
	    	System.out.println(" odId " + " : " + cmap.getString("odId", ""));
	    	System.out.println(" sReceiptCnt " + " : " + cmap.getString("sReceiptCnt", ""));
	    	System.out.println(" sRqstVendorCd " + " : " + cmap.getString("sRqstVendorCd", ""));
	    	System.out.println(" sRqstItemCd " + " : " + cmap.getString("sRqstItemCd", ""));
	    	System.out.println(" sRqstPNoCd " + " : " + cmap.getString("sRqstPNoCd", ""));
	    	System.out.println(" frstRegisterId " + " : " + cmap.getString("frstRegisterId", ""));
	    	System.out.println(" lastUpdusrId " + " : " + cmap.getString("frstRegisterId", ""));
	    	
	    	//필수 파라미터 체크
        	if ("".equals(cmap.getString("odId"))
        			|| "".equals(cmap.getString("sReceiptCnt"))
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}
	    	
        	CommonList resultList = goodsReceiptService
					.getGoodsReceiptDetailList(cmap);
        	System.out.println(" resultList.size() " + " : "
					+ resultList.size());
        	if (resultList.size() > 0) {        	
        	  double maxCnt = cmap.getDouble("sReceiptCnt", 0);
        	  double disCnt = 0;
        	  System.out.println(" maxCnt " + " : " + maxCnt);
        	  for (int i = 0; i < resultList.size(); i++) {
        		  CommonMap gmap = (CommonMap) resultList.get(i);
        		  double sumQty = Double.parseDouble(gmap
							.getString("sumQty"));
        		  
        		  if (maxCnt > 0) {
        			  System.out.println(" receipt_cnt 444 " + " : "
								+ gmap.toString());
        				od_id = gmap.getString("odId");
						result = gmap.getString("result");  

					System.out.println("000 maxCnt " + " : " + maxCnt);
					// maxCnt 300 sumQty 150 입고량 소요량 maxCnt 5 sumQty 0
					if (maxCnt == sumQty) {
						gmap.put("qtyOnHand", 0);
						gmap.put("preQtyOnHand", maxCnt);
						gmap.put("qtyinvoiced", maxCnt);
						qtyinvoiced = gmap.getString("qtyinvoiced");
						resultCnt = goodsReceiptService
								.updateQtyOnHand(gmap);
						// 양품창고로 보낸다. 
						goodsReceiptService.insertRfidCInOrder(gmap);
						goodsReceiptService.insertRfidCInOrderLine(gmap);
						
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
						
						resultCnt = goodsReceiptService
								.updateQtyOnHand(gmap);
						// 양품창고로 보낸다. 
						goodsReceiptService.insertRfidCInOrder(gmap);
						goodsReceiptService.insertRfidCInOrderLine(gmap);
						
						System.out.println(" receipt_cnt 2222 " + " : "
								+ gmap.toString());
						
						break;
					} else { // 소요량이 입고량보다 많으면 남는다.
						disCnt = sumQty - maxCnt; // 150 부족 1개
						gmap.put("qtyOnHand", 0);
						gmap.put("preQtyOnHand", sumQty);
						gmap.put("qtyinvoiced", disCnt);
						qtyinvoiced = gmap.getString("qtyinvoiced");
						
						resultCnt = goodsReceiptService
								.updateQtyOnHand(gmap);
						// 양품창고로 보낸다. 
						goodsReceiptService.insertRfidCInOrder(gmap);
						goodsReceiptService.insertRfidCInOrderLine(gmap);
						
						System.out.println(" receipt_cnt 333 " + " : "
								+ gmap.toString());
						break;
					}
        		  }
        		  
        	  }
        	} 
        } catch (Exception e) {
    		e.printStackTrace();
    	}	  
        	
		if (resultCnt > 0) {
			resultMap.put("ret", "OK");
			resultMap.put("retmsg", "성공");
		} else {
			resultMap.put("ret", "ERR");
			resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
		}
		
		return resultMap.toJsonString(model);
			
	}
	
	
	@RequestMapping(value = "/kp2200/kp2210Recall.do")
	public String Kp2111Recall(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		CommonMap resultMap = new CommonMap();
		
		String od_id = "";
		String qtyinvoiced = "";
		String result = "1";

		System.out.println(" result " + " : " + result);
		int resultCnt = 0;
		try {
			//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}
    		
    		//파라미터    			    		    	
	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString(
					"saveJsonArray", "[]", false));
	    	System.out.println(" saveJsonArray " + " : " + saveJsonArray.toString());	
	    	
			CommonList paramList = new CommonList();

			for (int i = 0; i < saveJsonArray.size(); i++) {
				JSONObject obj = saveJsonArray.getJSONObject(i);
				System.out.println(" JSONObject " + " : " + obj.toString());
				CommonMap param = new CommonMap();
				System.out.println(" JSONObject odId " + " : " + obj.get("odId"));
				System.out.println(" JSONObject sReceiptCnt " + " : " + obj.get("sReceiptCnt"));
				System.out.println(" JSONObject sRqstVendorCd " + " : " + obj.get("sRqstVendorCd"));
				System.out.println(" JSONObject sRqstItemCd " + " : " + obj.get("sRqstItemCd"));
				System.out.println(" JSONObject sRqstPNoCd " + " : " + obj.get("sRqstPNoCd"));
				
				param.put("odId", obj.get("odId"));			
				param.put("sReceiptCnt", obj.get("sReceiptCnt"));
				param.put("sRqstVendorCd", obj.get("sRqstVendorCd"));
				param.put("sRqstItemCd", obj.get("sRqstItemCd"));
				param.put("sRqstPNoCd", obj.get("sRqstPNoCd"));

				// 필수 파라미터 체크
				if ("".equals(param.getString("odId"))) {
					resultMap.put("ret", "ERR");
					resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
					return resultMap.toJsonString(model);
				}

				paramList.add(param);
				System.out.println(" paramList.size() " + " : " + paramList.size());
			}
			System.out.println(" cmap " + " : " + cmap.toString());
			
			if(paramList != null){
			  if(paramList.size() > 0){
				  for(int j = 0; j < paramList.size(); j++){
				  CommonMap gmap = (CommonMap)paramList.get(j);
				  String sRqstVendorCd = (String)gmap.get("sRqstVendorCd");
				  String sRqstItemCd = (String)gmap.get("sRqstItemCd");
				  String sRqstPNoCd = (String)gmap.get("sRqstPNoCd");
			      int odId = ((Integer)gmap.get("odId")).intValue();
				  String sReceiptCnt = (String)gmap.get("sReceiptCnt");
				  
				  System.out.println("  sRqstVendorCd " + " : " + sRqstVendorCd);
				  System.out.println("  sRqstItemCd " + " : " + sRqstItemCd);
				  System.out.println("  sRqstPNoCd " + " : " + sRqstPNoCd);
				  System.out.println("  odId " + " : " + odId);
				  System.out.println("  sReceiptCnt " + " : " + sReceiptCnt);
				  }
			  }
			}
			// 파라미터
			cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
			cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
			cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",
					((CommonMap) paramList.get(0)).getString("sRqstVendorCd")));
			cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd",
					((CommonMap) paramList.get(0)).getString("sRqstItemCd")));
			cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd",
					((CommonMap) paramList.get(0)).getString("sRqstPNoCd")));
			cmap.put(
					"odId",
					cmap.getString("odId",
							((CommonMap) paramList.get(0)).getString("odId")));			
			cmap.put("sReceiptCnt", cmap.getInt("sReceiptCnt",
					((CommonMap) paramList.get(0)).getInt("sReceiptCnt")));
			
			
	    	System.out.println(" odId " + " : " + cmap.getString("odId", ""));
	    	System.out.println(" sReceiptCnt " + " : " + cmap.getString("sReceiptCnt", ""));
	    	System.out.println(" sRqstVendorCd " + " : " + cmap.getString("sRqstVendorCd", ""));
	    	System.out.println(" sRqstItemCd " + " : " + cmap.getString("sRqstItemCd", ""));
	    	System.out.println(" sRqstPNoCd " + " : " + cmap.getString("sRqstPNoCd", ""));
	    	System.out.println(" frstRegisterId " + " : " + cmap.getString("frstRegisterId", ""));
	    	System.out.println(" lastUpdusrId " + " : " + cmap.getString("frstRegisterId", ""));
	    	
	    	//필수 파라미터 체크
        	if ("".equals(cmap.getString("odId"))
        			|| "".equals(cmap.getString("sReceiptCnt"))
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}
	    	
    		
    		CommonList resultList = goodsReceiptService
					.getGoodsReceiptDetailList(cmap);
        	System.out.println(" resultList.size() " + " : "
					+ resultList.size());

        	if (resultList.size() > 0) {        	
          	  double maxCnt = cmap.getDouble("sReceiptCnt", 0);
          	  double disCnt = 0;
          	  
          	  for (int i = 0; i < resultList.size(); i++) {
          		
          	     CommonMap gmap = (CommonMap) resultList.get(i);
    		     double sumQty = Double.parseDouble(gmap
						.getString("sumQty"));
    		     
    		     if (maxCnt > 0) {
       			  System.out.println(" receipt_cnt 444 " + " : "
								+ gmap.toString());
       				od_id = gmap.getString("odId");
					result = gmap.getString("result");  
						
					System.out.println("000 maxCnt " + " : " + maxCnt);
					// maxCnt 300 sumQty 150 입고량 소요량 maxCnt 5 sumQty 0
					if (maxCnt == sumQty) {
						gmap.put("qtyOnHand", 0);
						gmap.put("preQtyOnHand", maxCnt);
						gmap.put("qtyinvoiced", maxCnt);
						qtyinvoiced = gmap.getString("qtyinvoiced");
						resultCnt = goodsReceiptService
								.updateQtyOnHand(gmap);
						
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
        	}  
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if (resultCnt > 0) {
			resultMap.put("ret", "OK");
			resultMap.put("retmsg", "성공");
		} else {
			resultMap.put("ret", "ERR");
			resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
		}

		return resultMap.toJsonString(model);
		
		
	}
	
	
	
	@RequestMapping(value="/kp2210/kp2210KeyPad.do")
    public String kp2210KeyPad(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	return "common/keyPad";
	}

	
	@RequestMapping(value="/kp2210/kp2211KeyPad.do")
	public String kp2211KeyPad(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	return "common/keyPad2";
	}
}
