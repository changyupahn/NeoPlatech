package boassoft.controller.kp1700;

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

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.SndMisService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.ExcelUtil;

@Controller
public class KP1730InventoryUpdateController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "AssetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "SndMisService")
    private SndMisService sndMisService;

	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;

	@Resource(name = "SystemService")
    private SystemService systemService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1730InventoryUpdateController.class);

    @RequestMapping(value="/kp1700/kp1730.do")
	public String kp1730(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//마지막 재물조사 차수
    	CommonMap invLast = inventoryService.getInventoryLast(cmap);
    	model.addAttribute("invLast",invLast);
    	
    	//화면표시관리 (재물조사목록)
		cmap.put("dispType", "INVENTORY_LIST");
		CommonList dispAssetList = systemService.getDispMngList(cmap);
		model.addAttribute("dispAssetList", dispAssetList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1730";
	}

    @RequestMapping(value="/kp1700/kp1730Ajax.do")
	public String kp1730Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sInventoryDiv", "1");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = inventoryService.getInventoryDetailList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1700/kp1730Excel.do")
	public String kp1730Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sInventoryDiv", "1");
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		CommonMap cmForward = new CommonMap();
        	cmForward.put("forwardUrl", "");
        	cmForward.put("forwardMsg", "조회 권한이 없거나 로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
        	model.addAttribute("cmForward", cmForward);
        	return "common/commonOk";
    	}

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = inventoryService.getInventoryDetailList(cmap);

    	//화면표시관리 (재물조사목록)
		cmap.put("dispType", "INVENTORY_LIST");
		CommonList dispMngList = systemService.getDispMngList(cmap);
		
		int headerSize = dispMngList.size();
    	String[] headerListLgc1 = new String[headerSize];
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = new String[headerSize];
    	String[] headerListTyp = new String[headerSize];
    	String[] headerListWidth = new String[headerSize];
    	String[][] mergedRegion = null;
    	int idx = 0;
    	while (idx<dispMngList.size()) {
    		CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = "" + Math.round(dispMng.getInt("default_width",100) / 10);
			idx++;
    	}

    	ExcelUtil.write2(request, response, resultList, "재물조사결과반영", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1700/kp1730Proc.do")
	public String Kp1730Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("confirmDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("saveJsonArray"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	CommonList oriAssetList = new CommonList();
	    	CommonList newAssetList = new CommonList();

	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("assetSeq", obj.get("assetSeq"));
	    		param.put("invYear", cmap.getString("invYear"));
	    		param.put("invNo", cmap.getString("invNo"));

	    		CommonMap viewData = inventoryService.selectRequest(param);
	    		if (viewData.isEmpty()) {
	    			resultMap.put("ret", "ERR");
	        		resultMap.put("retmsg", "[오류] 자산 정보를 조회할 수 없습니다.");
	        		return resultMap.toJsonString(model);
	    		}

		    	//변경전 값 (히스토리 등록을 위해)
		    	CommonMap oriAsset = new CommonMap();
		    	oriAsset.put("assetSeq", param.getString("assetSeq"));
		    	oriAsset.put("userNo", viewData.getString("userNo"));
		    	oriAsset.put("userName", viewData.getString("userName"));
		    	oriAsset.put("empNo", viewData.getString("empNo"));
		    	oriAsset.put("deptNo", viewData.getString("deptNo"));
		    	oriAsset.put("deptName", viewData.getString("deptName"));
		    	oriAsset.put("topDeptNo", viewData.getString("topDeptNo"));
		    	oriAsset.put("topDeptName", viewData.getString("topDeptName"));
		    	oriAsset.put("posNo", viewData.getString("posNo"));
		    	oriAsset.put("posName", viewData.getString("posName"));
		    	oriAsset.put("assetStatusCd", viewData.getString("cngAssetStatusCd"));		    	
		    	oriAsset.put("disuseDt", viewData.getString("cngDisuseDt"));
		    	oriAsset.put("disuseCont", viewData.getString("cngDisuseCont"));

		    	//변경후 값 (히스토리 등록을 위해)
		    	CommonMap newAsset = new CommonMap();
		    	newAsset.put("assetSeq", param.getString("assetSeq"));
		    	newAsset.put("userNo", viewData.getString("cngUserNo"));
		    	newAsset.put("userName", viewData.getString("cngUserName"));
		    	newAsset.put("empNo", viewData.getString("empNo"));
		    	newAsset.put("deptNo", viewData.getString("cngDeptNo"));
		    	newAsset.put("deptName", viewData.getString("cngDeptName"));
		    	newAsset.put("topDeptNo", viewData.getString("cngTopDeptNo"));
		    	newAsset.put("topDeptName", viewData.getString("cngTopDeptName"));
		    	newAsset.put("posNo", viewData.getString("cngPosNo"));
		    	newAsset.put("posName", viewData.getString("cngPosName"));
		    	newAsset.put("assetStatusCd", viewData.getString("assetStatusCd"));
		    	newAsset.put("disuseDt", viewData.getString("disuseDt"));
		    	newAsset.put("disuseCont", viewData.getString("disuseCont"));

	    		oriAssetList.add(oriAsset);
	    		newAssetList.add(newAsset);
	    	}

	    	for (int i=0; i<newAssetList.size(); i++) {
	    		CommonMap oriAsset = oriAssetList.getMap(i);
	    		CommonMap newAsset = newAssetList.getMap(i);

	    		newAsset.put("frstRegisterId", cmap.getString("ssUserNo"));
	    		newAsset.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    		//재물조사 변경 정보 자산정보에 반영
	    		newAsset.put("confirmDt", cmap.getString("confirmDt"));
	    		newAsset.put("invYear", cmap.getString("invYear"));
	    		newAsset.put("invNo", cmap.getString("invNo"));

	    		if (!"".equals(newAsset.getString("userName"))
		    			|| !"".equals(newAsset.getString("posName"))
		    			|| !"".equals(newAsset.getString("disuseDt"))
		    			|| !"".equals(newAsset.getString("disuseCont"))
		    			|| !"".equals(newAsset.getString("assetStatusCd"))
		    			) {
	    			//실사결과반영 (RFID_ASSET)
	    			inventoryService.updateRequest(newAsset);

			    	//변경 히스토리 등록
	    			assetHistoryService.insertAssetHistoryDetail(oriAsset, newAsset, "ASSET_HIST3", false);

		    		//반영확인일자 UPDATE
		    		resultCnt += inventoryService.updateRequestCon(newAsset);
	    		} else {
	    			resultCnt += inventoryService.updateRequestCon(newAsset);
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

    @RequestMapping(value="/kp1700/kp1730DelProc.do")
	public String Kp1730DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("confirmDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
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
	    		param.put("invYear", cmap.getString("invYear"));
	    		param.put("invNo", cmap.getString("invNo"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("assetSeq"))
		    			|| "".equals(param.getString("invYear"))
		    			|| "".equals(param.getString("invNo"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

	    		paramList.add(param);
	    	}

    		//재물조사결과 삭제
    		resultCnt += inventoryService.deleteRequestCon(cmap, paramList);

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

}
