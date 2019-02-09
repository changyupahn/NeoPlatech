package boassoft.controller.kp9000;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.DeptService;
import boassoft.service.PositionService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP9040PosPopupController {

	@Resource(name = "PositionService")
    private PositionService positionService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP9040PosPopupController.class);

    @RequestMapping(value="/kp9000/kp9040.do")
	public String kp9040(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = positionService.getPositionList(cmap);
    	model.addAttribute("dataList", resultList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp9000/kp9040";
	}

    @RequestMapping(value="/kp9000/kp9040Ajax.do")
	public String Kp9020Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	CommonMap viewData = positionService.getPositionView(cmap);
    	model.addAttribute("viewData", viewData);

    	return "kp9000/kp9040Ajax";
	}

}
