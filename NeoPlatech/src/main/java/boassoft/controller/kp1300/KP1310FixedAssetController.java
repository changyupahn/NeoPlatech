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

import boassoft.service.AppService;
import boassoft.service.AssetService;
import boassoft.service.BatchService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1310FixedAssetController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "appService")
    private AppService appService;

	@Resource(name = "systemService")
    private SystemService systemService;


	@Resource(name = "assetNoIdGnrService")
    private EgovIdGnrService assetNoIdGnrService;

	@Resource(name = "rfidNoIdGnrService")
    private EgovIdGnrService rfidNoIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "batchService")
    private BatchService batchService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1310FixedAssetController.class);

    @RequestMapping(value="/kp1300/kp1310.do")
	public String kp1310(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);     	
    	cmap.put("sAssetDiv", "1");

    	//화면표시관리 (자산목록)
		cmap.put("dispType", "ASSET_LIST");
		CommonList dispAssetList = systemService.getDispMngList(cmap);
		model.addAttribute("dispAssetList", dispAssetList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1310";
	}

    @RequestMapping(value="/kp1300/kp1310Ajax.do")
	public String kp1310Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	cmap.put("sAssetDiv", "1");
    	
    	System.out.println(" dataOrder " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize " + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit " + "  : " + cmap.getInt("pageLimit"));
    	System.out.println(" sAssetDiv " + "  : " + "1");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	System.out.println(" searchGubun " + "  : " + cmap.getString("searchGubun"));
    	//파라미터
    	if ("1".equals(cmap.getString("searchGubun"))) {
    		cmap.put("searchKeyword", cmap.getString("searchKeyword").toUpperCase());
    	}

    	//사용자 기본 파라미터 설정
//    	if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
//    			&& "USR".equals(cmap.getString("searchDiv"))) {
//   		cmap.put("sUserNo", cmap.getString("sUserNo", SessionUtil.getString("userNo")));
//    		cmap.put("sUserName", cmap.getString("sUserName", SessionUtil.getString("userName")));
//    		cmap.put("sDeptNo", cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
//    		cmap.put("sDeptName", cmap.getString("sDeptName", SessionUtil.getString("deptName")));
//    	}

    	//복수검색 설정
    	assetService.setSearchArr(cmap);
    	System.out.println(" cmap " + "  : " + cmap.toString());
    	CommonList assetList = assetService.getAssetList(cmap);
    	System.out.println(" assetList " + "  : " + assetList.toString());
    	System.out.println(" assetList.size() " + "  : " + assetList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", assetList);
    	result.put("totalRow", assetList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1300/kp1310Search.do")
	public String kp1310Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1310Search";
	}

    @RequestMapping(value="/kp1300/kp1310Excel.do")
	public String kp1310Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "1");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//파라미터
    	if ("1".equals(cmap.getString("searchGubun"))) {
    		cmap.put("searchKeyword", cmap.getString("searchKeyword").toUpperCase());
    	}

    	//사용자 기본 파라미터 설정
    	if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
    			&& "USR".equals(cmap.getString("searchDiv"))) {
    		cmap.put("sUserNo", cmap.getString("sUserNo", SessionUtil.getString("userNo")));
    		cmap.put("sUserName", cmap.getString("sUserName", SessionUtil.getString("userName")));
    		cmap.put("sDeptNo", cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
    		cmap.put("sDeptName", cmap.getString("sDeptName", SessionUtil.getString("deptName")));
    	}

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = assetService.getAssetList(cmap);

    	//화면표시관리 (자산목록)
		cmap.put("dispType", "ASSET_LIST_EXCEL");
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

    	ExcelUtil.write2(request, response, resultList, "고정자산목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}
    
    @RequestMapping(value="/kp1300/kp1310DelProc.do")
	public String kp1310DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));

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
	    	resultCnt = assetService.deleteAsset2(cmap, paramList);

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

    @RequestMapping(value="/kp1300/kp1310TestBatch.do")
	public String kp1310TestBatch(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap(request);

    	try {
	    	batchService.syncUser(cmap);
	    	batchService.syncDept(cmap);
	    	batchService.syncItem(cmap);
	    	batchService.syncAsset(cmap);

	    	resultMap.put("ret", "OK");
			resultMap.put("retmsg", "성공");

    	} catch (Exception e) {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}


}
