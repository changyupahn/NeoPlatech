package boassoft.controller.kp1900;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.GrantService;
import boassoft.service.UserLogService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.TokenMngUtil;
import egovframework.com.utl.sim.service.EgovFileScrty;

@Controller
public class KP1910UserGrantController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "userLogService")
    private UserLogService userLogService;

	@Resource(name = "grantService")
    private GrantService grantService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1910UserGrantController.class);

    @RequestMapping(value="/kp1900/kp1910.do")
	public String kp1910(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//권한롤 목록
    	CommonList grantList = grantService.getGrantList(cmap);
    	model.addAttribute("grantList", grantList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1910";
	}
    
    @RequestMapping(value="/kp1900/kp1910UserAjax.do")
	public String kp1910UserAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = userService.getUserList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1910LogAjax.do")
	public String kp1910LogAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = userLogService.getUserLogList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1910Proc.do")
	public String Kp1910Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("userNo", cmap.getString("userNo"));
	    	cmap.put("userId", cmap.getString("userId").toUpperCase());
	    	cmap.put("userPw", cmap.getString("userPw").toUpperCase());
	    	cmap.put("userName", cmap.getString("userName"));
	    	cmap.put("grantNo", cmap.getString("grantNo"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("userId"))
	    			|| "".equals(cmap.getString("userName"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		//return resultMap.toJsonString(model);
	    		CommonMap cmForward = new CommonMap();
	        	cmForward.put("forwardUrl", "none");
	        	cmForward.put("forwardMsg", resultMap.getString("retmsg"));
	        	model.addAttribute("cmForward", cmForward);
	        	return "common/commonOk";
	    	}
	    	
	    	//비밀번호 암호화
	    	if (!"".equals(cmap.getString("userPw"))) {
	    		cmap.put("userPw", EgovFileScrty.encryptPassword(cmap.getString("userPw")));
	    	}

	    	//조회
	    	CommonMap user = userService.getUserView(cmap);

	    	if (user.isEmpty()) {
		    	
		    	if ("".equals(cmap.getString("userPw"))) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    	} else {
		    		
		    		//userKey 생성
		    		cmap.put("userKey", TokenMngUtil.createAppToken(32));
		    		
		    		//등록
			    	resultCnt = userService.insertUser(cmap);
			    	resultMap.put("retmsg", "등록되었습니다.");
		    	}
	    	} else {
	    		//수정
		    	resultCnt = userService.updateUser(cmap);
		    	resultMap.put("retmsg", "수정되었습니다.");
	    	}

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");    		
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	//return resultMap.toJsonString(model);
    	CommonMap cmForward = new CommonMap();
    	cmForward.put("forwardUrl", "none");
    	cmForward.put("forwardMsg", resultMap.getString("retmsg"));
    	cmForward.put("prevCustomScript", "parent.fnSaveCallback();");
    	model.addAttribute("cmForward", cmForward);
    	return "common/commonOk";
	}

    @RequestMapping(value="/kp1900/kp1910DelProc.do")
	public String Kp1910DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("userNo", cmap.getString("userNo"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("userNo"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = userService.deleteUser2(cmap);

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
