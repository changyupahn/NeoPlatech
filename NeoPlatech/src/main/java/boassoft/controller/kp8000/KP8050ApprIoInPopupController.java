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
import boassoft.service.ApprIoExtService;
import boassoft.service.ApprIoInService;
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
public class KP8050ApprIoInPopupController {

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "GrantMenuService")
    private GrantMenuService grantMenuService;

	@Resource(name = "ApprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "ApprIoOutService")
    private ApprIoOutService apprIoOutService;

	@Resource(name = "ApprIoExtService")
    private ApprIoExtService apprIoExtService;

	@Resource(name = "ApprIoInService")
    private ApprIoInService apprIoInService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP8050ApprIoInPopupController.class);

    @RequestMapping(value="/kp8000/kp8050.do")
	public String kp8050(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "100"));

    	//반출 정보
    	CommonMap viewData = apprIoOutService.getApprIoOutView(cmap);
    	model.addAttribute("viewData", viewData);

    	//반입연장정보
    	CommonList viewDataExtList = apprIoExtService.getApprIoExtList(cmap);
    	model.addAttribute("viewDataExtList", viewDataExtList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp8000/kp8050";
	}

    @RequestMapping(value="/kp8000/kp8050Ajax.do")
	public String kp8050Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    /** 반입 신청 저장 */
    @RequestMapping(value="/kp8000/kp8050Proc.do")
	public String Kp8050Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    			|| "".equals(cmap.getString("inDt"))
	    			//|| "".equals(cmap.getString("inRemark"))
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
	    	cmap.put("inRqstno", MessageUtils.getRqstNo());

	    	//저장
	    	resultCnt = apprIoInService.insertApprIoIn(cmap);

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

    /** 반입 처리 (담당자권한) */
    @RequestMapping(value="/kp8000/kp8050MngProc.do")
	public String Kp8050MngProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("rqstUserNo", userLoginView.getString("userNo"));
	    	cmap.put("rqstUserName", userLoginView.getString("userName"));
	    	cmap.put("rqstDeptNo", userLoginView.getString("deptNo"));
	    	cmap.put("rqstDeptName", userLoginView.getString("deptName"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//{rqstno=1511852671227I116986, rqstRegtime=2017-11-28, reason=자산 인수인계, aucUserNo=20229, aucUserName=한태양,
	    	//aucDeptNo=3B010, aucDeptName=지질연구센터, lang=KO, ssUserNo=00605, saveJsonObj=, rqstUserNo=, rqstUserName=, rqstDeptNo=, rqstDeptName=, frstRegisterId=00605, lastUpdusrId=00605}

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("saveJsonArray"))
	    			|| "".equals(cmap.getString("rqstno"))
	    			|| "".equals(cmap.getString("inDt"))
	    			//|| "".equals(cmap.getString("inRemark"))
	    			|| "".equals(cmap.getString("rqstUserNo"))
	    			|| "".equals(cmap.getString("rqstUserName"))
	    			|| "".equals(cmap.getString("rqstDeptNo"))
	    			|| "".equals(cmap.getString("rqstDeptName"))
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
	    	cmap.put("inRqstno", MessageUtils.getRqstNo());

	    	//저장
	    	resultCnt = apprIoInService.insertApprIoInMng(cmap, paramList);

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
