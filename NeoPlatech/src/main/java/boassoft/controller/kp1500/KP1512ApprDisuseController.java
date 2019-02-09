package boassoft.controller.kp1500;

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
import boassoft.service.ApprDisuseService;
import boassoft.service.CommonCodeService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.StringUtil;


@Controller
public class KP1512ApprDisuseController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "ApprDisuseService")
    private ApprDisuseService apprDisuseService;

	@Resource(name = "ApprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "CommonCodeService")
    private CommonCodeService commonCodeService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1512ApprDisuseController.class);

    @RequestMapping(value="/kp1500/kp1512.do")
	public String kp1512(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//상세
    	CommonMap viewData = apprDisuseService.getApprDisuseView(cmap);
    	model.addAttribute("viewData", viewData);

    	//공통코드
    	model.addAttribute("com010Str", commonCodeService.getCommonCodeJqGridStr("COM010")); //불용처리상태

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1500/kp1512";
	}

    @RequestMapping(value="/kp1500/kp1512Ajax.do")
	public String kp1512Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = apprAssetService.getApprAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1500/kp1512Proc.do")
	public String Kp1512Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("rqstno"))
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
	    		param.put("rqstno", obj.get("rqstno"));
	    		param.put("assetSeq", obj.get("assetSeq"));
	    		param.put("disuseProcDt", obj.get("disuseProcDt"));
	    		param.put("disuseProcTypeCd", obj.get("disuseProcTypeCd"));
	    		param.put("disuseProcAmt", obj.get("disuseProcAmt"));
	    		param.put("disuseProcCont", obj.get("disuseProcCont"));

	    		//숫자형 체크
	    		param.put("disuseProcAmt", StringUtil.nvl(param.getString("disuseProcAmt").replaceAll("\\D",""), null));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("rqstno"))
		    			|| "".equals(param.getString("assetSeq"))
		    			|| "".equals(param.getString("disuseProcDt"))
		    			|| "".equals(param.getString("disuseProcTypeCd"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	} else {
		    		paramList.add(param);
		    	}
	    	}

	    	//저장
	    	resultCnt = apprAssetService.updateApprAssetAll(cmap, paramList);

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
