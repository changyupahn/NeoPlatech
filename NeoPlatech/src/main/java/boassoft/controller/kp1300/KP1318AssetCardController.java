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
import boassoft.service.AssetFileService;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.DepreService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1318AssetCardController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "appService")
    private AppService appService;

	@Resource(name = "depreService")
    private DepreService depreService;

	@Resource(name = "assetFileService")
    private AssetFileService assetFileService;

	@Resource(name = "systemService")
    private SystemService systemService;

	@Resource(name = "assetNoIdGnrService")
    private EgovIdGnrService assetNoIdGnrService;

	@Resource(name = "rfidNoIdGnrService")
    private EgovIdGnrService rfidNoIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1318AssetCardController.class);

    @RequestMapping(value="/kp1300/kp1318.do")
	public String kp1318(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));
    	cmap.put("sAssetDiv", "3");

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//자산의 이력
    	cmap.put("histOrder", "ASC");
    	CommonList histList = assetHistoryService.getAssetHistoryList(cmap);
    	model.addAttribute("histList",histList);

    	//상각 내역 (연도별)
    	CommonList depreList = depreService.getDepreYearList(cmap);
    	model.addAttribute("depreList",depreList);

    	//자산의 이미지
    	cmap.put("assetNo", viewData.getString("assetNo"));
    	CommonList imgList = assetFileService.getAssetFileList(cmap);
    	model.addAttribute("imgList",imgList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1318";
	}

}
