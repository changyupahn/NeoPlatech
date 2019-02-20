package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AdminService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovFileScrty;

@Controller
public class AdminController {

	@Resource(name = "adminService")
	private AdminService adminService;

	/** log */
	protected static final Log LOG = LogFactory.getLog(AdminController.class);

	@RequestMapping(value = "/adminIndex.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		// 로그인 되어 있다면( 세션이 있다면 ) 메인 페이지로 이동
		if (isAuthenticated) {
			return "redirect:/kp1300/kp1310.do";
		}
		return "index";
	}

	@RequestMapping(value = "/loginForm.do")
	public String loginForm(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		return "index";
	}

	@RequestMapping(value = "/validationQuery.do")
	public String getValidationQuery(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		adminService.getValidationQuery(cmap);
		return "common/commonString";
	}

	@RequestMapping(value = "/loginProc.do")
	public String loginProc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		if (!"".equals(cmap.getString("userPw"))) {
			cmap.put("userPw",
					EgovFileScrty.encryptPassword(cmap.getString("userPw")));
			// System.out.println("user_pw : " + cmap.getString("user_pw"));
		}

		CommonMap adminLoginView = adminService.getAdminLoginView(cmap);

		if (adminLoginView.isEmpty()) {
			CommonMap cmForward = new CommonMap();
			cmForward.put("forwardMsg", "로그인 정보가 일치하지 않습니다.");
			cmForward.put("forwardUrl", "/index.do");
			model.addAttribute("cmForward", cmForward);
			return "/common/commonOk";
		} else {

			HttpSession session = request.getSession();

			session.setAttribute("adminMap", adminLoginView);
			session.setAttribute("userId", adminLoginView.getString("userId"));
			session.setAttribute("userName",
					adminLoginView.getString("userName"));
			session.setAttribute("orgNo", adminLoginView.getString("orgNo"));

			return "redirect:/kp1300/kp1310.do";
		}
	}

	@RequestMapping(value = "/logoutProc.do")
	public String logoutProc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		request.getSession().invalidate();

		return "redirect:/loginForm.do";
	}

	@RequestMapping(value = "/system/userManage.do")
	public String userManage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("orgNo", SessionUtil.getString("orgNo"));

		// 관리자 목록
		CommonList adminList = adminService.getAdminList(cmap);
		model.addAttribute("adminList", adminList);
		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "system/userManage";
	}

	@RequestMapping(value = "/system/userManageDetailAjax.do")
	public String userManageDetailAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("orgNo", SessionUtil.getString("orgNo"));

		// 관리자 정보
		CommonMap adminView = adminService.getAdminView(cmap);
		System.out.println("adminView : " + adminView);

		model.addAttribute("jsonString", adminView.toJsonString());

		return "common/commonJson";
	}

	@RequestMapping(value = "/system/userManageProc.do")
	public String userManageProc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("orgNo", SessionUtil.getString("orgNo"));
		cmap.put("useYn", "Y");

		if (!"".equals(cmap.getString("userPw"))) {
			cmap.put("userPw",
					EgovFileScrty.encryptPassword(cmap.getString("userPw")));
		}

		if ("I".equals(cmap.getString("procMode"))) {
			adminService.insertAdmin(cmap);
		} else if ("U".equals(cmap.getString("procMode"))) {
			adminService.updateAdmin(cmap);
		}

		return "redirect:/system/userManage.do";
	}

	@RequestMapping(value = "/system/userManageDelProc.do")
	public String userManageDelProc(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("orgNo", SessionUtil.getString("orgNo"));

		adminService.deleteAdmin(cmap);

		return "redirect:/system/userManage.do";
	}

	@RequestMapping(value = "/system/syncManage.do")
	public String syncManage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		// 동기화 히스토리
		// CommonList assetSyncHistory = assetService.getAssetSyncHistory(cmap);
		// model.addAttribute("assetSyncHistory", assetSyncHistory);
		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "system/syncManage";
	}
}
