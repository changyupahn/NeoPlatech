package boassoft.controller.kp7000;

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
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP7010ApprIframeController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "apprUsercngService")
    private ApprUsercngService apprUsercngService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP7010ApprIframeController.class);

    /** MIS용 승인정보IFRAME - 인수인계 */
    @RequestMapping(value="/kp7000/kp7010.do")
	public String kp7010(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	//자산상세
    	CommonMap viewData = apprUsercngService.getApprUsercngView(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp7000/kp7010";
	}

    @RequestMapping(value="/kp7000/kp7010Ajax.do")
	public String kp7010Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

//    	//그리드 세션 체크 및 메뉴 권한 설정
//    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
//    	if (!gridSessionChk.isEmpty()) {
//    		model.addAttribute("printString", gridSessionChk.toJsonString());
//        	return "common/commonString";
//    	}

    	CommonList resultList = apprAssetService.getApprAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}


}
