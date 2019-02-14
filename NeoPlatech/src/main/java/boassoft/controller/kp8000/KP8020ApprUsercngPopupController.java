package boassoft.controller.kp8000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ApprAssetService;
import boassoft.service.ApprUsercngService;
import boassoft.service.AssetService;
import boassoft.service.GrantMenuService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;

@Controller
public class KP8020ApprUsercngPopupController {

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "grantMenuService")
    private GrantMenuService grantMenuService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "apprUsercngService")
    private ApprUsercngService apprUsercngService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP8020ApprUsercngPopupController.class);

    @RequestMapping(value="/kp8000/kp8020.do")
	public String kp8020(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "100"));

    	//신청자 정보
    	CommonMap viewData = SessionUtil.getCommonMap("userLoginView");
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp8000/kp8020";
	}

    @RequestMapping(value="/kp8000/kp8020Ajax.do")
	public String kp8020Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//파라미터
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	CommonList resultList = apprAssetService.getApprAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    /** 인수인계 신청 저장 */
    @RequestMapping(value="/kp8000/kp8020Proc.do")
	public String Kp8020Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

    		CommonMap userLoginView = SessionUtil.getCommonMap("userLoginView");

	    	//파라미터
	    	cmap.put("saveJsonObj", cmap.getString("saveJsonObj", false));
	    	cmap.put("rqstUserNo", userLoginView.getString("userNo"));
	    	cmap.put("rqstUserName", userLoginView.getString("userName"));
	    	cmap.put("rqstDeptNo", userLoginView.getString("deptNo"));
	    	cmap.put("rqstDeptName", userLoginView.getString("deptName"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("rqstno"))
	    			|| "".equals(cmap.getString("rqstRegtime"))
	    			|| "".equals(cmap.getString("reason"))
	    			|| "".equals(cmap.getString("aucUserNo"))
	    			|| "".equals(cmap.getString("aucUserName"))
	    			|| "".equals(cmap.getString("aucDeptNo"))
	    			|| "".equals(cmap.getString("aucDeptName"))
	    			|| "".equals(cmap.getString("rqstUserNo"))
	    			|| "".equals(cmap.getString("rqstUserName"))
	    			|| "".equals(cmap.getString("rqstDeptNo"))
	    			|| "".equals(cmap.getString("rqstDeptName"))

	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//저장
	    	resultCnt = apprUsercngService.insertApprUsercng(cmap);

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
