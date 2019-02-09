package boassoft.controller.kp1000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.service.GrantMenuService;
import boassoft.service.LogService;
import boassoft.service.UserLogService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Controller
public class KP1010LoginController {

	@Resource(name = "UserService")
    private UserService userService;
	
	@Resource(name = "UserLogService")
    private UserLogService userLogService;

	@Resource(name = "GrantMenuService")
    private GrantMenuService grantMenuService;

	@Resource(name = "LogService")
    private LogService logService;

	@Resource(name = "userLogSeqIdGnrService")
    private EgovIdGnrService userLogSeqIdGnrService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1010LoginController.class);

    @RequestMapping(value="/kp1000/kp1010.do")
	public String kp1010(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	cmap.put("uno", cmap.getString("uno"));
    	cmap.put("userId", cmap.getString("uno", cmap.getString("userId")));
    	cmap.put("userPw", cmap.getString("uno", cmap.getString("userPw")));
    	
    	//사용자ID/비밀번호 대문자 변환
    	cmap.put("userId", cmap.getString("userId").toUpperCase());
    	cmap.put("userPw", cmap.getString("userPw").toUpperCase());

    	if (!"".equals(cmap.getString("userPw"))) {
    		cmap.put("userPw", EgovFileScrty.encryptPassword(cmap.getString("userPw")));
//    		System.out.println("user_pw : " + cmap.getString("user_pw"));
    	}

    	CommonMap userLoginView = userService.getUserLoginView(cmap);

    	if( userLoginView.isEmpty()){

    		//로그인 실패 로그 기록
    		CommonMap logMap = new CommonMap();
    		logMap.put("logType", "LOGIN_FAILED");
    		logMap.put("logDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		logMap.put("logCont", String.format("로그인 실패 [%s]", cmap.getString("userId")));
    		logMap.put("logIp", request.getRemoteAddr());
    		logService.insertLog(logMap);

    		CommonMap cmForward = new CommonMap();
    		cmForward.put("forwardMsg", "로그인 정보가 일치하지 않습니다.");
    		cmForward.put("forwardUrl", "/index.do");
    		model.addAttribute("cmForward", cmForward);
    		return "/common/commonOk";
    	} else {

    		//로그인 성공 로그 기록
    		CommonMap logMap = new CommonMap();
    		logMap.put("logType", "LOGIN_SUCCESS");
    		logMap.put("logDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		logMap.put("logCont", String.format("로그인 성공 [%s][%s]", cmap.getString("userId"), userLoginView.getString("userName")));
    		logMap.put("logIp", request.getRemoteAddr());
    		logService.insertLog(logMap);

    		//메뉴 권한 조회
        	cmap.put("grantNo", userLoginView.getString("grantNo"));
        	CommonList grantMenuList = grantMenuService.getGrantMenuList(cmap) ;

        	String grantReadYnArr = "";
        	String grantWriteYnArr = "";
        	String grantHreadYnArr = "";
        	String grantHwriteYnArr = "";
        	String grantManagerYnArr = "";

        	for (int i=0; i<grantMenuList.size(); i++) {
        		CommonMap grantMenu = grantMenuList.getMap(i);

        		if ("Y".equals(grantMenu.getString("grantReadYn"))) {
        			grantReadYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantWriteYn"))) {
        			grantWriteYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantHreadYn"))) {
        			grantHreadYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantHwriteYn"))) {
        			grantHwriteYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantManagerYn"))) {
        			grantManagerYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        	}

        	grantReadYnArr += "[KP1940]";

    		HttpSession session = request.getSession();

    		session.setAttribute("userLoginView",userLoginView);
    		session.setAttribute("userNo",userLoginView.getString("userNo"));
    		session.setAttribute("userName",userLoginView.getString("userName"));
    		session.setAttribute("deptNo",userLoginView.getString("deptNo"));
    		session.setAttribute("deptName",userLoginView.getString("deptName"));
    		session.setAttribute("empNo",userLoginView.getString("empNo"));
    		session.setAttribute("userEmail",userLoginView.getString("userEmail"));
    		session.setAttribute("grantNo",userLoginView.getString("grantNo"));

    		session.setAttribute("grantReadYnArr", grantReadYnArr);
    		session.setAttribute("grantWriteYnArr", grantWriteYnArr);
    		session.setAttribute("grantHreadYnArr", grantHreadYnArr);
    		session.setAttribute("grantHwriteYnArr", grantHwriteYnArr);
    		session.setAttribute("grantManagerYnArr", grantManagerYnArr);

    		return "redirect:/kp1300/kp1310.do";
    	}
	}

    /**
     * 일반 로그인 처리
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/kp1000/kp1010IdLoginProc.do")
	public String kp1010IdLoginProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	//사용자ID/비밀번호 대문자 변환
    	cmap.put("userId", cmap.getString("userId").toUpperCase());
    	cmap.put("userPw", cmap.getString("userPw").toUpperCase());
    	
    	if (!"".equals(cmap.getString("userPw"))) {
    		cmap.put("userPw", EgovFileScrty.encryptPassword(cmap.getString("userPw")));
    		//System.out.println("user_pw : " + cmap.getString("user_pw"));
    	}
    	
    	CommonMap userIdLoginView = userService.getUserIdLoginView(cmap);

    	if( userIdLoginView.isEmpty()){
    		
    		//접근로그 기록
        	cmap.put("userLogSeq", userLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("loginYn", "N");
        	userLogService.insertUserLog(cmap);
        	
    		CommonMap cmForward = new CommonMap();
    		cmForward.put("forwardMsg", "로그인 정보가 일치하지 않습니다.");
    		cmForward.put("forwardUrl", "/index.do");
    		model.addAttribute("cmForward", cmForward);
    		return "/common/commonOk";
    	} else {
    		
    		//접근로그 기록
        	cmap.put("userLogSeq", userLogSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		cmap.put("accessIp", request.getRemoteAddr());
    		cmap.put("loginYn", "Y");
        	userLogService.insertUserLog(cmap);

    		//메뉴 권한 조회
        	cmap.put("grantNo", userIdLoginView.getString("grantNo"));
        	if ("USR".equals(cmap.getString("grant"))) {
        		cmap.put("grantNo", cmap.getString("grant"));
        	}
        	CommonList grantMenuList = grantMenuService.getGrantMenuList(cmap) ;

        	String grantReadYnArr = "";
        	String grantWriteYnArr = "";
        	String grantHreadYnArr = "";
        	String grantHwriteYnArr = "";
        	String grantManagerYnArr = "";

        	for (int i=0; i<grantMenuList.size(); i++) {
        		CommonMap grantMenu = grantMenuList.getMap(i);

        		if ("Y".equals(grantMenu.getString("grantReadYn"))) {
        			grantReadYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantWriteYn"))) {
        			grantWriteYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantHreadYn"))) {
        			grantHreadYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantHwriteYn"))) {
        			grantHwriteYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        		if ("Y".equals(grantMenu.getString("grantManagerYn"))) {
        			grantManagerYnArr += "[" + grantMenu.getString("menuNo") + "]";
        		}
        	}

    		HttpSession session = request.getSession();

    		session.setAttribute("userLoginView",userIdLoginView);
    		session.setAttribute("userNo",userIdLoginView.getString("userNo"));
    		session.setAttribute("userName",userIdLoginView.getString("userName"));
    		session.setAttribute("deptNo",userIdLoginView.getString("deptNo"));
    		session.setAttribute("deptName",userIdLoginView.getString("deptName"));
    		session.setAttribute("empNo",userIdLoginView.getString("empNo"));
    		session.setAttribute("userEmail",userIdLoginView.getString("userEmail"));
    		session.setAttribute("grantNo",cmap.getString("grantNo"));
    		session.setAttribute("realGrantNo",userIdLoginView.getString("grantNo"));

    		session.setAttribute("grantReadYnArr", grantReadYnArr);
    		session.setAttribute("grantWriteYnArr", grantWriteYnArr);
    		session.setAttribute("grantHreadYnArr", grantHreadYnArr);
    		session.setAttribute("grantHwriteYnArr", grantHwriteYnArr);
    		session.setAttribute("grantManagerYnArr", grantManagerYnArr);

    		return "redirect:/kp1300/kp1310.do";
    	}
	}

    @RequestMapping(value="/kp1000/kp1011.do")
	public String kp1011(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

    	request.getSession().invalidate();

    	return "logout";
	}

    @RequestMapping(value="/kp1000/kp1011LogoutAjax.do")
	public String kp1011LogoutAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap resultMap = new CommonMap();

    	request.getSession().invalidate();

    	resultMap.put("ret", "OK");
		resultMap.put("retmsg", "성공");

    	return resultMap.toJsonString(model);
    }

	
}
