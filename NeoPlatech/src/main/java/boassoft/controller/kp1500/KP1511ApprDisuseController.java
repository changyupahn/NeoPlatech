package boassoft.controller.kp1500;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ApprAssetService;
import boassoft.service.ApprDisuseService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1511ApprDisuseController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "ApprDisuseService")
    private ApprDisuseService apprDisuseService;

	@Resource(name = "ApprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1511ApprDisuseController.class);

    @RequestMapping(value="/kp1500/kp1511.do")
	public String kp1511(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	//상세
    	CommonMap viewData = apprDisuseService.getApprDisuseView(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1500/kp1511";
	}

    @RequestMapping(value="/kp1500/kp1511Ajax.do")
	public String kp1511Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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


}
