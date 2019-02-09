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

import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1712InventoryTargetController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "SystemService")
    private SystemService systemService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1712InventoryTargetController.class);

    @RequestMapping(value="/kp1700/kp1712.do")
	public String kp1712(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//화면표시관리 (자산목록)
		cmap.put("dispType", "ASSET_LIST");
		CommonList dispAssetList = systemService.getDispMngList(cmap);
		model.addAttribute("dispAssetList", dispAssetList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1712";
	}

    @RequestMapping(value="/kp1700/kp1712Ajax.do")
	public String kp1712Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "11");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = assetService.getAssetTargetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    /** 재물조사대상 설정 저장 */
    @RequestMapping(value="/kp1700/kp1712Proc.do")
	public String Kp1712Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
	    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
	    	cmap.put("invYear", cmap.getString("invYear"));
	    	cmap.put("invNo", cmap.getString("invNo"));
	    	cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS").replaceAll("\\D", ""));
	    	cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE").replaceAll("\\D", ""));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
	    	
	    	//복수검색 설정
	    	assetService.setSearchArr(cmap);

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("invYear"))
	    			|| "".equals(cmap.getString("invNo"))
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
		    	if (!"".equals(param.getString("assetSeq"))
		    			) {
		    		paramList.add(param);
		    	}
	    	}

	    	//저장
	    	if (paramList.size() > 0) {
	    		resultCnt = inventoryService.updateTargetYnChk(cmap, paramList);
	    	} else {
	    		resultCnt = inventoryService.updateTargetYnAll(cmap);
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

}
