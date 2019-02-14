package boassoft.controller.kp1900;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.CommonCodeService;
import boassoft.service.GrantService;
import boassoft.service.UserLogService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.utl.sim.service.EgovFileScrty;

@Controller
public class KP1950CodeMgrController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "commonCodeService")
    private CommonCodeService commonCodeService;

	@Resource(name = "grantService")
    private GrantService grantService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1950CodeMgrController.class);

    @RequestMapping(value="/kp1900/kp1950.do")
	public String kp1950(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1950";
	}
    
    @RequestMapping(value="/kp1900/kp1950Ajax.do")
	public String kp1950UserAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//코드 값 고정 (자산번호 자동생성 코드 관리)
    	cmap.put("paramCodeId", "");
    	
    	CommonList resultList = commonCodeService.getCommonCodeList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1900/kp1950Proc.do")
	public String Kp1950Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("userId", cmap.getString("userId"));
	    	cmap.put("userPw", cmap.getString("userPw"));
	    	cmap.put("userName", cmap.getString("userName"));
	    	cmap.put("grantNo", "MGR");
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("userId"))
	    			|| "".equals(cmap.getString("userPw"))
	    			|| "".equals(cmap.getString("userName"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}
	    	
	    	//비밀번호 암호화
	    	if (!"".equals(cmap.getString("userPw"))) {
	    		cmap.put("userPw", EgovFileScrty.encryptPassword(cmap.getString("userPw")));
	    	}

	    	//조회
	    	CommonMap user = userService.getUserView(cmap);

	    	if (user.isEmpty()) {
	    		//등록
		    	resultCnt = userService.insertUser(cmap);
	    	} else {
	    		//수정
		    	resultCnt = userService.updateUser(cmap);
	    	}

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

    @RequestMapping(value="/kp1900/kp1950DelProc.do")
	public String Kp1950DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	resultCnt = userService.deleteUser(cmap);

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
