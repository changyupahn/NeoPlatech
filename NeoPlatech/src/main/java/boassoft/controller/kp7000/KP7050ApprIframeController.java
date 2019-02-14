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
import boassoft.service.ApprIoExtService;
import boassoft.service.ApprIoInService;
import boassoft.service.ApprIoOutService;
import boassoft.service.ApprUsercngService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP7050ApprIframeController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "apprIoOutService")
    private ApprIoOutService apprIoOutService;

	@Resource(name = "apprIoExtService")
    private ApprIoExtService apprIoExtService;

	@Resource(name = "apprIoInService")
    private ApprIoInService apprIoInService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP7050ApprIframeController.class);

    /** MIS용 승인정보IFRAME - 반입신청 */
    @RequestMapping(value="/kp7000/kp7050.do")
	public String kp7050(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inRqstno", cmap.getString("rqstno")); //inRqstno

    	//반입정보
    	CommonMap viewDataIn = apprIoInService.getApprIoInView(cmap);
    	model.addAttribute("viewDataIn", viewDataIn);
    	cmap.put("rqstno", viewDataIn.getString("rqstno"));

    	//반출정보
    	CommonMap viewData = apprIoOutService.getApprIoOutView(cmap);
    	model.addAttribute("viewData", viewData);

    	//반입연장정보
    	CommonList viewDataExtList = apprIoExtService.getApprIoExtList(cmap);
    	model.addAttribute("viewDataExtList", viewDataExtList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp7000/kp7050";
	}

    @RequestMapping(value="/kp7000/kp7050Ajax.do")
	public String kp7050Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
