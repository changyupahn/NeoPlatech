package boassoft.controller.kp8000;

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

import boassoft.service.ApprAssetService;
import boassoft.service.AssetService;
import boassoft.service.GrantMenuService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.MessageUtils;
import boassoft.util.SessionUtil;

@Controller
public class KP8010ApprAssetPopupController {

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "GrantMenuService")
    private GrantMenuService grantMenuService;

	@Resource(name = "ApprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP8010ApprAssetPopupController.class);

    @RequestMapping(value="/kp8000/kp8010.do")
	public String kp8010(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "100"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp8000/kp8010";
	}

    @RequestMapping(value="/kp8000/kp8010Ajax.do")
	public String kp8010Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//부서장 권한이더라도 승인 신청은 개인 자산만 보이도록 설정
    	if ("GRANT_DHD".equals(cmap.getString("ssGrantRead"))) {
    		cmap.put("ssGrantRead", "GRANT_USR");
    		cmap.put("ssGrantWrite", "GRANT_USR");
    	}

    	//파라미터
    	cmap.put("apprType", cmap.getString("apprType"));

    	//승인신청대상 자산 (이미 다른 승인가 신청 중이거나 , 삭제, 불용된 자산등을 제외 시키면 됨)
    	cmap.put("sAssetDiv", "7");
    	if ("1".equals(cmap.getString("apprType"))) { //인수인계
    		cmap.put("sAssetDiv", "6");
    	} else if ("2".equals(cmap.getString("apprType"))) { //반출
    		cmap.put("sAssetDiv", "7");
    	} else if ("3".equals(cmap.getString("apprType"))) { //불용
    		cmap.put("sAssetDiv", "8");
    	}

    	CommonList resultList = assetService.getAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp8000/kp8010SelAjax.do")
	public String kp8010SelAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    /** 자산선택 저장 */
    @RequestMapping(value="/kp8000/kp8010Proc.do")
	public String Kp8010Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("apprType", cmap.getString("apprType").replaceAll("\\D", ""));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if (cmap.getString("apprType").length() != 1
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

	    	//신청번호 생성
	    	cmap.put("rqstno", MessageUtils.getRqstNo());

	    	//저장
	    	resultCnt = apprAssetService.insertApprAssetAll(cmap, paramList);

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
