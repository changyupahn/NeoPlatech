package boassoft.controller.kp9000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetService;
import boassoft.service.DeptService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;

@Controller
public class KP9080AssetPopupController {

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "assetService")
    private AssetService assetService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP9080AssetPopupController.class);

    @RequestMapping(value="/kp9000/kp9080.do")
	public String kp9080(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp9000/kp9080";
	}

    @RequestMapping(value="/kp9000/kp9080Ajax.do")
	public String Kp9080Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    	if ("1".equals(cmap.getString("searchGubun"))) {
    		cmap.put("searchKeyword", cmap.getString("searchKeyword").toUpperCase());
    	}

    	//사용자 기본 파라미터 설정
    	if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {
    		cmap.put("sUserNo", cmap.getString("sUserNo", SessionUtil.getString("userNo")));
    		cmap.put("sUserName", cmap.getString("sUserName", SessionUtil.getString("userName")));
    	}

    	CommonList resultList = assetService.getAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

}
