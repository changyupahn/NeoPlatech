package boassoft.controller.kp1300;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AppService;
import boassoft.service.ApprRqstService;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1315AssetHistController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "AssetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "AppService")
    private AppService appService;

	@Resource(name = "SystemService")
    private SystemService systemService;

	@Resource(name = "assetNoIdGnrService")
    private EgovIdGnrService assetNoIdGnrService;

	@Resource(name = "rfidNoIdGnrService")
    private EgovIdGnrService rfidNoIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "ApprRqstService")
    private ApprRqstService apprRqstService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1315AssetHistController.class);

    @RequestMapping(value="/kp1300/kp1315.do")
	public String kp1315(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1315";
	}

    @RequestMapping(value="/kp1300/kp1315Ajax.do")
	public String kp1315Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "9999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "2");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = assetHistoryService.getAssetHistoryList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1300/kp1315Appr.do")
	public String kp1315Appr(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	CommonMap viewData = apprRqstService.getApprRqstView(cmap);

    	if ("1".equals(viewData.getString("apprType"))) {
    		return "redirect:/kp1400/kp1411.do?rqstno=" + viewData.getString("rqstno");
    	} else if ("2".equals(viewData.getString("apprType"))) {
    		return "redirect:/kp1400/kp1421.do?rqstno=" + viewData.getString("rqstno");
    	} else if ("3".equals(viewData.getString("apprType"))) {
    		return "redirect:/kp1500/kp1511.do?rqstno=" + viewData.getString("rqstno");
    	} else {
    		return null;
    	}
	}

	
}
