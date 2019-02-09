package boassoft.controller.kp1100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.PositionService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1130PositionController {

	@Resource(name = "PositionService")
    private PositionService positionService;
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1130PositionController.class);
    
    @RequestMapping(value="/kp1100/kp1130.do")
	public String kp1130(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	
    	CommonList dataList = positionService.getPositionList(cmap);
    	model.addAttribute("dataList", dataList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "kp1100/kp1130";
	}
    
    @RequestMapping(value="/kp1100/kp1130Ajax.do")
	public String Kp1110Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("posNo", cmap.getString("posNo"));
    	
    	if (!"".equals(cmap.getString("posNo"))) {
        	CommonMap viewData = positionService.getPositionView(cmap);
        	model.addAttribute("viewData", viewData);    		
    	}
    	
    	return "kp1100/kp1130Ajax";
	}
}
