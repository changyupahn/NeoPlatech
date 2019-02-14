package boassoft.controller.kp1300;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetAsService;
import boassoft.service.AssetService;
import boassoft.service.UserService;
import boassoft.service.ZeusAsListService;
//import boassoft.service.ZeusAsListService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.EgovMessageSource;

@Controller
public class KP1313AssetAsController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "assetAsService")
    private AssetAsService assetAsService;

	@Resource(name = "zeusAsListService")
    private ZeusAsListService zeusAsListService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1313AssetAsController.class);

    @RequestMapping(value="/kp1300/kp1313.do")
	public String kp1313(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1313";
	}

    @RequestMapping(value="/kp1300/kp1313Ajax.do")
	public String kp1313Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "9999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = assetAsService.getAssetAsList(cmap);
    	/*cmap.put("sAssetSeq", cmap.getString("assetSeq", "NNNN"));
    	CommonList resultList = zeusAsListService.getZeusAsListList(cmap);
    	*/CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

}
