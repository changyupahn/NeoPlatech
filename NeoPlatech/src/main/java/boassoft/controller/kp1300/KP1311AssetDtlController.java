package boassoft.controller.kp1300;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.service.AppService;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.CommonCodeService;
import boassoft.service.DeptService;
import boassoft.service.InventoryService;
import boassoft.service.SndMisService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.SessionUtil;
import boassoft.util.StringUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1311AssetDtlController {
	
	@Resource(name = "commonMap")
	private CommonMap commonMap;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "AssetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "AppService")
    private AppService appService;

	@Resource(name = "SystemService")
    private SystemService systemService;

	@Resource(name = "assetSeqIdGnrService")
    private EgovIdGnrService assetSeqIdGnrService;

	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "DeptService")
    private DeptService deptService;

	@Resource(name = "SndMisService")
    private SndMisService sndMisService;

	@Resource(name = "CommonCodeService")
    private CommonCodeService commonCodeService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1311AssetDtlController.class);

    @RequestMapping(value="/kp1300/kp1311.do")
	public String kp1311(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap assetDetail = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", assetDetail);

    	if ("".equals(cmap.getString("assetSeq"))) {
    		cmap.put("assetSeq", assetSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("gbn", "I");
    	}

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311";
	}

    /** 자산상세정보 탭구성 */
    @RequestMapping(value="/kp1300/kp1311Detail.do")
	public String kp1311Detail(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	//화면표시관리 (자산목록)
		cmap.put("dispType", "ASSET_LIST");
		CommonList dispAssetList = systemService.getDispMngList(cmap);
		model.addAttribute("dispAssetList", dispAssetList);

    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311Detail";
	}

    /** 자산상세정보 탭구성 (불용정보) */
    @RequestMapping(value="/kp1300/kp1311Disuse.do")
	public String kp1311Disuse(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311Disuse";
	}

    /** 자산상세팝업 탭구성 */
    @RequestMapping(value="/kp1300/kp1311Tab.do")
	public String kp1311Tab(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311Tab";
	}

    /** 자산이미지폼 */
    @RequestMapping(value="/kp1300/kp1311ImageForm.do")
	public String kp1311Image(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));
    	cmap.put("equipId", cmap.getString("equipId"));

    	CommonList imgList = assetService.getAssetImgList(cmap);
    	model.addAttribute("imgList",imgList);

//    	//ZEUS 자산의 이미지 조회
//    	if (!"".equals(cmap.getString("equipId"))) {
//	    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
//			String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s"
//					, cmap.getString("equipId")
//					, key
//					);
//	    	/*//자산번호로 조회
//	    	String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s?division=fixedAsetNo"
//					, "{자산번호}"
//					, key
//					); */
//
//			String resultStr = HttpZeusUtil.get(url);
//			System.out.println(resultStr);
//    	}

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311ImageForm";
	}

    /** 자산이미지목록 */
    @RequestMapping(value="/kp1300/kp1311ImageList.do")
	public String kp1311ImageList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("paramAssetSeq"));

    	CommonList imgList = assetService.getAssetImgList(cmap);
    	model.addAttribute("imgList",imgList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311ImageList";
    }

    /** 자산이미지 업로드 */
    @RequestMapping(value="/kp1300/kp1311ImageUpload.do")
	public String kp1311ImageUpload(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = commonMap.setMultipartNoFile(multiRequest, "", "Globals.Asset.Img.fileStorePath", SessionUtil.getString("orgNo"));
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	try{

    		if (!"".equals(cmap.getString("assetSeq"))
    				&& !"".equals(cmap.getString("fileStreFileNm"))) {

    			cmap = commonMap.setMultipartImage(multiRequest, "", "Globals.Asset.Img.fileStorePath", SessionUtil.getString("orgNo"));

    			cmap.put("orgNo", SessionUtil.getString("orgNo"));
    			cmap.put("filePath", cmap.getString("fileFileStreCours"));
    			cmap.put("webFilePath", "/appimg/" + DateUtil.getFormatDate("yyyy"));
				cmap.put("fileNm", cmap.getString("fileStreFileNm"));
				cmap.put("orignlFileNm", cmap.getString("fileOrignlFileNm"));
				cmap.put("fileExt", cmap.getString("fileFileExtsn"));
				cmap.put("fileDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));

				//자산정보
				CommonMap viewData = assetService.getAssetDetail(cmap);
				cmap.put("assetNo", viewData.getString("assetNo"));

				appService.insertAssetImage(cmap);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	CommonMap cmForward = new CommonMap();
    	cmForward.put("forwardUrl", "/kp1300/kp1311.do?assetSeq=" + cmap.getString("assetSeq"));
    	cmForward.put("forwardMsg", "등록되었습니다.");
    	model.addAttribute("cmForward", cmForward);
    	return "common/commonOk";
	}

    /** 자산이미지 삭제 */
    @RequestMapping(value="/kp1300/kp1311ImageDelete.do")
	public String kp1311ImageDelete(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetNo", cmap.getString("assetNo", ""));
    	cmap.put("fileDt", cmap.getString("fileDt", ""));

		if (!"".equals(cmap.getString("fileDt"))
				&& !"".equals(cmap.getString("assetNo"))
				) {

			appService.deleteAssetImage(cmap);
		}

		CommonMap cmForward = new CommonMap();
    	cmForward.put("assetNo", cmap.getString("assetNo"));
    	cmForward.put("forwardUrl", "/kp1300/kp1311.do?assetSeq=" + cmap.getString("assetSeq"));
    	cmForward.put("forwardMsg", "삭제되었습니다.");
    	model.addAttribute("cmForward", cmForward);
    	return "common/commonOk";
	}

    /** 자산정보 저장 */
    @RequestMapping(value="/kp1300/kp1311Proc.do")
	public String Kp1311Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

	    	//파라미터
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//숫자형 체크
	    	cmap.put("aqusitAmt", StringUtil.nvl(cmap.getString("aqusitAmt").replaceAll("\\D",""), null));
	    	cmap.put("aqusitForeignAmt", StringUtil.nvl(cmap.getString("aqusitForeignAmt").replaceAll("[^.0-9]",""), null));
	    	cmap.put("aqusitUnitAmt", StringUtil.nvl(cmap.getString("aqusitUnitAmt").replaceAll("\\D",""), null));
	    	cmap.put("assetCnt", StringUtil.nvl(cmap.getString("assetCnt").replaceAll("\\D",""), null));
	    	cmap.put("usefulLife", StringUtil.nvl(cmap.getString("usefulLife").replaceAll("\\D",""), null));

	    	//날짜형 체크
	    	cmap.put("aqusitDt", cmap.getString("aqusitDt").replaceAll("\\D", ""));
	    	cmap.put("contDt", cmap.getString("contDt").replaceAll("\\D", ""));
	    	cmap.put("disuseDt", cmap.getString("disuseDt").replaceAll("\\D", ""));

	    	//cmap.put("ssGrantWrite", "GRANT_MGR");
	    	if ("GRANT_MGR".equals(cmap.getString("ssGrantWrite"))) {
		    	//취득방법
		    	if (!"".equals(cmap.getString("aqusitTypeCd"))) {
		    		cmap.put("aqusitTypeName", commonCodeService.getCommonCodeName("COM006", cmap.getString("aqusitTypeCd")));
		    	}

		    	//필수 파라미터 체크
		    	if ("".equals(cmap.getString("assetSeq"))
		    			//|| "".equals(cmap.getString("assetTypeCd"))
		    			//|| "".equals(cmap.getString("itemCd"))
		    			//|| "".equals(cmap.getString("itemName"))
		    			//|| "".equals(cmap.getString("assetName"))
		    			//|| "".equals(cmap.getString("aqusitUnitAmt"))
		    			//|| "".equals(cmap.getString("assetCnt"))
		    			//|| "".equals(cmap.getString("aqusitAmt"))
		    			//|| "".equals(cmap.getString("depreCd"))
		    			//|| "".equals(cmap.getString("usefulLife"))
		    			//|| cmap.getString("aqusitDt").length() != 8
		    			|| "".equals(cmap.getString("deptNo"))
		    			|| "".equals(cmap.getString("deptName"))
		    			|| "".equals(cmap.getString("userNo"))
		    			|| "".equals(cmap.getString("userName"))
		    			|| "".equals(cmap.getString("posNo"))
		    			|| "".equals(cmap.getString("posName"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

//		    	//Validation Check
//		    	if (!cmap.getString("itemAssetTypeCd").equals(cmap.getString("assetTypeCd"))) {
//		    		resultMap.put("ret", "ERR");
//		    		resultMap.put("retmsg", "[오류] 자산구분과 품목코드가 맞지 않습니다.");
//		    		return resultMap.toJsonString(model);
//		    	}
//
//		    	if (cmap.getInt("usefulLife") > 60) {
//		    		resultMap.put("ret", "ERR");
//		    		resultMap.put("retmsg", "[오류] 내용연수는 60년 이하로 설정 가능합니다.");
//		    		return resultMap.toJsonString(model);
//		    	}

//		    	if (cmap.getInt("aqusitAmt") != (cmap.getInt("aqusitUnitAmt") * cmap.getInt("assetCnt"))) {
//		    		resultMap.put("ret", "ERR");
//		    		resultMap.put("retmsg", "[오류] 단가 * 수량은 취득가액과 동일해야 합니다.");
//		    		return resultMap.toJsonString(model);
//		    	}

//		    	//사용자 부서 설정
//		    	if (!"".equals(cmap.getString("userNo"))) {
//		    		CommonMap userMap = userService.getUserView(cmap);
//		    		if (!userMap.isEmpty()) {
//			    		cmap.put("topDeptNo", userMap.getString("topDeptNo"));
//			    		cmap.put("topDeptName", userMap.getString("topDeptName"));
//			    		cmap.put("deptNo", userMap.getString("deptNo"));
//			    		cmap.put("deptName", userMap.getString("deptName"));
//			    		cmap.put("userNo", userMap.getString("userNo"));
//			    		cmap.put("userName", userMap.getString("userName"));
//		    		}
//		    	}

		    	//상위부서 설정
		    	if (!"".equals(cmap.getString("deptNo"))) {
		    		CommonMap deptMap = deptService.getDeptView(cmap);
		    		if (!deptMap.isEmpty()) {
			    		cmap.put("topDeptNo", deptMap.getString("parentDeptNo"));
			    		cmap.put("topDeptName", deptMap.getString("parentDeptName"));
			    		cmap.put("deptNo", deptMap.getString("deptNo"));
			    		cmap.put("deptName", deptMap.getString("deptName"));
		    		}
		    	}

		    	//자산정보조회
		    	if (!"I".equals(cmap.getString("gbn"))) {
			    	CommonMap viewData = assetService.getAssetDetail(cmap);
			    	if (viewData.isEmpty()) {
			    		resultMap.put("ret", "ERR");
			    		resultMap.put("retmsg", "[오류] 키값이 잘못되었습니다.");
			    		return resultMap.toJsonString(model);
			    	}

//			    	//감가상각여부 체크
//			    	if ("Y".equals(viewData.getString("depreYn"))) {
//
//			    		if (!viewData.getString("assetTypeCd").equals(cmap.getString("assetTypeCd"))) {
//			    			resultMap.put("ret", "ERR");
//				    		resultMap.put("retmsg", "[오류] 감가상각 내역이 있는 자산은 [자산구분]을 수정할 수 없습니다.");
//				    		return resultMap.toJsonString(model);
//			    		}
//			    		if (!viewData.getString("depreCd").equals(cmap.getString("depreCd"))) {
//			    			resultMap.put("ret", "ERR");
//				    		resultMap.put("retmsg", "[오류] 감가상각 내역이 있는 자산은 [상각법]을 수정할 수 없습니다.");
//				    		return resultMap.toJsonString(model);
//			    		}
//			    		if (!viewData.getString("usefulLife").equals(cmap.getString("usefulLife"))) {
//			    			resultMap.put("ret", "ERR");
//				    		resultMap.put("retmsg", "[오류] 감가상각 내역이 있는 자산은 [내용연수]를 수정할 수 없습니다.");
//				    		return resultMap.toJsonString(model);
//			    		}
//			    		if (!viewData.getString("aqusitAmt").equals(cmap.getString("aqusitAmt"))) {
//			    			resultMap.put("ret", "ERR");
//				    		resultMap.put("retmsg", "[오류] 감가상각 내역이 있는 자산은 [취득가액]을 수정할 수 없습니다.");
//				    		return resultMap.toJsonString(model);
//			    		}
//			    	}

			    	//저장
			    	resultCnt = assetService.updateAssetMgr(cmap);

			    	//변경 히스토리 등록
			    	assetHistoryService.insertAssetHistory(viewData, cmap);

			    	//변경정보 MIS전송
			    	CommonMap sndMap = new CommonMap();
			    	sndMap.put("sndSeq", sndSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
			    	sndMap.put("sndDiv", "1"); //1:정보변경, 2:불용신청, 3:불용승인, 4:부외자산변경
			    	sndMap.put("assetNo", viewData.getString("etisAssetNo").replaceAll("-",""));
			    	sndMap.put("mgtDeptCd", viewData.getString("posNo"));
			    	sndMap.put("useDeptCd", viewData.getString("deptNo"));
			    	sndMap.put("useEmpNo", viewData.getString("userNo"));
			    	sndMap.put("assetStatusCd", null);
			    	sndMap.put("disuseDt", null);
			    	sndMap.put("disuseApprovalDt", null);
			    	sndMap.put("outAssetYn", null);
			    	sndMap.put("sndYn", "N");
			    	sndMisService.insertSndMis(sndMap);


		    	} else {

		    		//저장
			    	resultCnt = assetService.insertAsset(cmap);

			    	//변경 히스토리 등록
		        	cmap.put("histTypeCd", "1");
		        	cmap.put("histContent", "자산취득");
		        	assetHistoryService.insertAssetHistory(cmap);
		    	}
	    	}
	    	else
	    	{ //일반사용자
	    		CommonMap viewData = assetService.getAssetDetail(cmap);
		    	if (viewData.isEmpty()) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "[오류] 키값이 잘못되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

	    		//파라미터
		    	cmap.put("posNo", cmap.getString("posNo"));
		    	cmap.put("posName", cmap.getString("posName"));
		    	cmap.put("zeusYn", cmap.getString("zeusYn"));
		    	cmap.put("etubeYn", cmap.getString("etubeYn"));

		    	//필수 파라미터 체크
		    	if ("".equals(cmap.getString("assetSeq"))
		    			|| "".equals(cmap.getString("posName"))
		    			|| "".equals(cmap.getString("zeusYn"))
		    			|| "".equals(cmap.getString("etubeYn"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	//사용자 부서 설정
		    	if (!"".equals(cmap.getString("userNo"))) {
		    		CommonMap userMap = userService.getUserView(cmap);
		    		if (!userMap.isEmpty()) {
			    		cmap.put("topDeptNo", userMap.getString("topDeptNo"));
			    		cmap.put("topDeptName", userMap.getString("topDeptName"));
			    		cmap.put("deptNo", userMap.getString("deptNo"));
			    		cmap.put("deptName", userMap.getString("deptName"));
			    		cmap.put("userNo", userMap.getString("userNo"));
			    		cmap.put("userName", userMap.getString("userName"));
		    		}
		    	}

		    	//저장
		    	resultCnt = assetService.updateAssetUsr(cmap);

		    	//변경 히스토리 등록
		    	assetHistoryService.insertAssetHistory(viewData, cmap);

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

    /** 자산정보 삭제 */
    @RequestMapping(value="/kp1300/kp1311DelProc.do")
	public String Kp1311DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

	    	//파라미터
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	if ("GRANT_MGR".equals(cmap.getString("ssGrantWrite"))) {

		    	//필수 파라미터 체크
		    	if ("".equals(cmap.getString("assetSeq"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	//자산정보조회
		    	CommonMap viewData = assetService.getAssetDetail(cmap);
		    	if ("Y".equals(viewData.getString("depreYn"))) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "[삭제오류] 감가상각 내역이 있는 자산은 삭제할 수 없습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	//삭제
		    	resultCnt = assetService.deleteAsset(cmap);
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
    
    /** 일괄수정 */
    @RequestMapping(value="/kp1300/kp1311ModifyAll.do")
	public String kp1311ModifyAll(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1311ModifyAll";
	}

    @RequestMapping(value="/kp1300/kp1311ModifyAllAjax.do")
	public String kp1311ModifyAllAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	//saveJsonArray
    	//cmap.put("sAssetSeqArr", cmap.getArray("sAssetSeqArr"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
    	String sAssetSeqArr = "";
    	for (int i=0; i<saveJsonArray.size(); i++) {
    		JSONObject obj = saveJsonArray.getJSONObject(i);
    		CommonMap param = new CommonMap();
    		param.put("assetSeq", obj.get("assetSeq"));

    		if (!"".equals(param.getString("assetSeq"))) {
    			sAssetSeqArr += obj.get("assetSeq") + ",";
    		}
    	}
    	cmap.put("sAssetSeqArr", sAssetSeqArr.split(","));

    	CommonList resultList = new CommonList();
    	if (!"".equals(cmap.getString("sAssetSeqArr"))) {
    		resultList = assetService.getAssetList(cmap);
    	}
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    /** 일괄수정 처리 */
    @RequestMapping(value="/kp1300/kp1311ModifyAllProc.do")
	public String kp1311ModifyAllProc(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = commonMap.setMultipartNoFile(multiRequest, "", "Globals.Asset.Img.fileStorePath", SessionUtil.getString("orgNo"));
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, multiRequest);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

	    	//파라미터
    		cmap.put("userNo", cmap.getString("userNo"));
    		cmap.put("userName", cmap.getString("userName"));
    		cmap.put("posNo", cmap.getString("posNo"));
    		cmap.put("posName", cmap.getString("posName"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("saveJsonArray"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("assetSeq", obj.get("assetSeq"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("assetSeq"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//저장
	    	resultCnt = assetService.updateAssetAll(cmap, paramList);

	    	CommonMap tempMap = new CommonMap();
	    	for (int k=1; k<=3; k++) {
	    		if (!"".equals(cmap.getString("file"+ k +"StreFileNm"))) {
	    			tempMap.put("file"+ k +"YN", "Y");
	    		}
	    	}
	    	for (int k=1; k<=3; k++) {

		    	if ("Y".equals(tempMap.getString("file"+ k +"YN"))) {

		    		if (k > 1) {
		    			// fileDt 중복되지 않게 하기 위하여 딜레이 주기
		    			Thread.sleep(1000);
		    		}

		    		for (int i=0; i<paramList.size(); i++) {
		    			//자산정보
						CommonMap viewData = assetService.getAssetDetail(paramList.getMap(i));
						if (!viewData.isEmpty()) {
							String assetNo = viewData.getString("assetNo");
							String fileDt = DateUtil.getFormatDate("yyyyMMddHHmmss");
							cmap.put("assetNo", assetNo);
							cmap.put("fileDt", fileDt);

			    			cmap = commonMap.setMultipartImage3(multiRequest
			    					, ""
			    					, "Globals.Asset.Img.fileStorePath"
			    					, SessionUtil.getString("orgNo")
			    					, cmap.getString("assetNo")
			    					, cmap.getString("fileDt")
			    					, ("file"+k)
			    					);

			    			cmap.put("orgNo", SessionUtil.getString("orgNo"));
			    			cmap.put("filePath", cmap.getString("file"+ k +"FileStreCours"));
			    			cmap.put("webFilePath", "/appimg/" + DateUtil.getFormatDate("yyyy"));
							cmap.put("fileNm", cmap.getString("file"+ k +"StreFileNm"));
							cmap.put("orignlFileNm", cmap.getString("file"+ k +"OrignlFileNm"));
							cmap.put("fileExt", cmap.getString("file"+ k +"FileExtsn"));
							cmap.put("assetNo", assetNo);
							cmap.put("fileDt", fileDt);

							appService.insertAssetImage(cmap);
						}
		    		}
	    		}
	    	}

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "저장 되었습니다.");
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	//return resultMap.toJsonString(model);
    	CommonMap cmForward = new CommonMap();
    	cmForward.put("forwardUrl", "none");
    	cmForward.put("customScript", "parent.fnSaveCallback();");
    	cmForward.put("forwardMsg", resultMap.getString("retmsg"));
    	model.addAttribute("cmForward", cmForward);
    	return "common/commonOk";
	}
}
