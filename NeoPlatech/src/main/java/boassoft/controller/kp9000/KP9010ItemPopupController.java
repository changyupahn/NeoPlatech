package boassoft.controller.kp9000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.CommonCodeService;
import boassoft.service.ItemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP9010ItemPopupController {

	@Resource(name = "ItemService")
    private ItemService itemService;

	@Resource(name = "CommonCodeService")
    private CommonCodeService commonCodeService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP9010ItemPopupController.class);

    @RequestMapping(value="/kp9000/kp9010.do")
	public String kp9010(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("sItemType", cmap.getString("sItemType", "DEFAULT"));
    	cmap.put("sUseYn", "Y");

    	cmap.put("paramCodeId", "COM007");
    	CommonList assetTypeCdList = commonCodeService.getCommonCodeList(cmap);
    	model.addAttribute("assetTypeCdList", assetTypeCdList);

    	CommonList itemList = itemService.getItemList(cmap);
    	model.addAttribute("dataList", itemList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp9000/kp9010";
	}

//    @RequestMapping(value="/kp9000/kp9010Ajax.do")
//	public String Kp9010Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//    	CommonMap cmap = new CommonMap(request);
//
//    	CommonMap viewData = itemService.getItemView(cmap);
//    	model.addAttribute("viewData", viewData);
//
//    	return "kp9000/kp9010Ajax";
//	}

    @RequestMapping(value="/kp9000/kp9011.do")
	public String kp9011(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("sItemType", "DEFAULT");
    	cmap.put("sUseYn", "Y");

    	CommonList itemList = itemService.getItemList(cmap);
    	model.addAttribute("dataList", itemList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp9000/kp9011";
	}

//    @RequestMapping(value="/kp9000/kp9011Ajax.do")
//	public String Kp9011Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//    	CommonMap cmap = new CommonMap(request);
//
//    	CommonMap viewData = itemService.getItemView(cmap);
//    	model.addAttribute("viewData", viewData);
//
//    	return "kp9000/kp9011Ajax";
//	}

    @RequestMapping(value="/kp9000/kp9012.do")
	public String kp9012(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("sItemType", "ZEUS");
    	cmap.put("sUseYn", "Y");

    	CommonList itemList = itemService.getItemList(cmap);
    	model.addAttribute("dataList", itemList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp9000/kp9012";
	}

//    @RequestMapping(value="/kp9000/kp9012Ajax.do")
//	public String Kp9012Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//    	CommonMap cmap = new CommonMap(request);
//
//    	CommonMap viewData = itemService.getItemView(cmap);
//    	model.addAttribute("viewData", viewData);
//
//    	return "kp9000/kp9012Ajax";
//	}

}
