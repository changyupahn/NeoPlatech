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

import boassoft.service.AssetCompntService;
import boassoft.service.AssetService;
import boassoft.service.AssetSubService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.EgovMessageSource;


@Controller
public class KP1312AssetCompntController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "assetSubService")
    private AssetSubService assetSubService;

	@Resource(name = "assetCompntService")
    private AssetCompntService assetCompntService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1312AssetCompntController.class);

    @RequestMapping(value="/kp1300/kp1312.do")
	public String kp1312(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1312";
	}

    @RequestMapping(value="/kp1300/kp1312Ajax.do")
	public String kp1312Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "9999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//부속품으로 등록된 자산이라면
    	if (!"".equals(viewData.getString("topAssetSeq"))
    			&& !viewData.getString("assetSeq").equals(viewData.getString("topAssetSeq"))) {
    		cmap.put("assetSeq", viewData.getString("topAssetSeq"));
    	}

    	//부속품목록 자산
    	cmap.put("sAssetDiv", "10");

    	CommonList resultList = assetService.getAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1300/kp1312Write.do")
	public String kp1312Write(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1312Write";
	}

    @RequestMapping(value="/kp1300/kp1312WriteAjax.do")
	public String kp1312WriteAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

//    	//부서장 권한이더라도 자산 선택은 개인 자산만 보이도록 설정
//    	if ("GRANT_DHD".equals(cmap.getString("ssGrantRead"))) {
//    		cmap.put("ssGrantRead", "GRANT_USR");
//    		cmap.put("ssGrantWrite", "GRANT_USR");
//    	}

    	//부속품대상 자산
    	cmap.put("sAssetDiv", "9");

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = assetService.getAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1300/kp1312WriteSelAjax.do")
	public String kp1312WriteSelAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = new CommonList();
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    /** 부속품 자산선택 저장 */
    @RequestMapping(value="/kp1300/kp1312WriteProc.do")
	public String kp1312WriteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("assetSeq", cmap.getString("assetSeq").replaceAll("\\D", ""));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("assetSeq"))
	    			|| "".equals(cmap.getString("saveJsonArray"))
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
	    		param.put("topAssetSeq", cmap.getString("assetSeq"));
	    		param.put("assetSeq", obj.get("assetSeq"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("topAssetSeq"))
		    			|| "".equals(param.getString("assetSeq"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//저장
	    	resultCnt = assetSubService.updateAssetSubAll(cmap, paramList);

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    		resultMap.put("rqstno", cmap.getString("rqstno"));
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}

    /** 부속품 자산선택 삭제 */
    @RequestMapping(value="kp1300/kp1312DelProc.do")
	public String kp1312DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("assetSeq", cmap.getString("assetSeq").replaceAll("\\D", ""));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("assetSeq"))
	    			|| "".equals(cmap.getString("saveJsonArray"))
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
	    		param.put("topAssetSeq", cmap.getString("assetSeq"));
	    		param.put("assetSeq", obj.get("assetSeq"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("topAssetSeq"))
		    			|| "".equals(param.getString("assetSeq"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//저장
	    	resultCnt = assetSubService.deleteAssetSubAll(cmap, paramList);

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    		resultMap.put("rqstno", cmap.getString("rqstno"));
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}

}
