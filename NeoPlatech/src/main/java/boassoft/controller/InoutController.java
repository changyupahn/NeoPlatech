package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.InoutService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class InoutController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;
	
	@Resource(name = "InoutService")
    private InoutService inoutService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(InoutController.class);
    
    
    @RequestMapping(value="/inout/selectList.do")
	public String inoutSelectList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	CommonList inoutList = inoutService.getInoutList(cmap);
    	model.addAttribute("inoutList", inoutList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inout/inoutList";
	}
    
    @RequestMapping(value="/inout/selectListXls.do")
	public String inoutSelectListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "999999");
    	
    	CommonList inoutList = inoutService.getInoutList(cmap);
    	model.addAttribute("inoutList", inoutList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inout/inoutListXls";
	}
}
