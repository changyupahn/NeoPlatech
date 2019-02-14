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
import boassoft.service.ApprIoExtService;
import boassoft.service.ApprIoOutService;
import boassoft.service.AssetService;
import boassoft.service.GrantMenuService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.MessageUtils;
import boassoft.util.SessionUtil;

@Controller
public class KP8040ApprIoExtPopupController {

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "grantMenuService")
    private GrantMenuService grantMenuService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "apprIoOutService")
    private ApprIoOutService apprIoOutService;

	@Resource(name = "apprIoExtService")
    private ApprIoExtService apprIoExtService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP8040ApprIoExtPopupController.class);

    @RequestMapping(value="/kp8000/kp8040.do")
	public String kp8040(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "100"));

    	//반출 정보
    	CommonMap viewData = apprIoOutService.getApprIoOutView(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp8000/kp8040";
	}

    @RequestMapping(value="/kp8000/kp8040Ajax.do")
	public String kp8040Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    /** 반입 연장 신청 저장 */
    @RequestMapping(value="/kp8000/kp8040Proc.do")
	public String Kp8040Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

	    	//{rqstno=1511852671227I116986, rqstRegtime=2017-11-28, reason=자산 인수인계, aucUserNo=20229, aucUserName=한태양,
	    	//aucDeptNo=3B010, aucDeptName=지질연구센터, lang=KO, ssUserNo=00605, saveJsonObj=, rqstUserNo=, rqstUserName=, rqstDeptNo=, rqstDeptName=, frstRegisterId=00605, lastUpdusrId=00605}

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("rqstno"))
	    			|| "".equals(cmap.getString("extDt"))
	    			|| "".equals(cmap.getString("extReason"))
	    			|| "".equals(cmap.getString("rqstUserNo"))
	    			|| "".equals(cmap.getString("rqstUserName"))
	    			|| "".equals(cmap.getString("rqstDeptNo"))
	    			|| "".equals(cmap.getString("rqstDeptName"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//신청번호 생성
	    	cmap.put("extRqstno", MessageUtils.getRqstNo());

	    	//저장
	    	resultCnt = apprIoExtService.insertApprIoExt(cmap);

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
