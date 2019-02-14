package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.service.EgovProperties;
import boassoft.service.UserService;
import boassoft.util.CommonMap;

@Controller
public class MisLoginController {

	@Resource(name = "userService")
    private UserService userService;
    
    @RequestMapping(value="/mis/login.do")
	public String misLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("uno", cmap.getString("uno"));
    	cmap.put("sign", cmap.getString("sign"));
    	
    	//파라미터 체크
    	if ("".equals(cmap.getString("uno"))
    			|| "".equals(cmap.getString("sign"))) {
    		return null;
    	}
    	
    	//sign 값 체크
    	String signHashKey = EgovProperties.getProperty("Globals.Sign.HashKey");
    	String sign2 = cmap.getString("uno") + signHashKey;
    	cmap.put("sign2", sign2);
    	sign2 = cmap.getSha256Encrypt("sign2");
    	if (!cmap.getString("sign").equalsIgnoreCase(sign2)) {
    		return null;
    	}
    	
    	cmap.put("userNo", cmap.getString("uno"));
    	CommonMap userLoginView = userService.getUserLoginView(cmap);
    	
    	if( userLoginView.isEmpty()){
    		CommonMap cmForward = new CommonMap();
    		cmForward.put("forwardMsg", "로그인 정보가 일치하지 않습니다.");
    		cmForward.put("forwardUrl", "/index.do");
    		model.addAttribute("cmForward", cmForward);
    		return "/common/commonOk";
    	} else {
    		
    		HttpSession session = request.getSession();
    		
    		session.setAttribute("userLoginView",userLoginView);
    		session.setAttribute("userNo",userLoginView.getString("userNo"));
    		session.setAttribute("userEmpNo",userLoginView.getString("userEmpNo"));
    		session.setAttribute("userName",userLoginView.getString("userName"));
    		session.setAttribute("grantNo",userLoginView.getString("grantNo"));
    		
    		return "redirect:/asset/selectList.do";
    	}
	}
}
