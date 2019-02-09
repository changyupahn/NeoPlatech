package boassoft.controller.kp1100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.DeptService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP1120DeptController {

	@Resource(name = "DeptService")
    private DeptService deptService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1120DeptController.class);
    
    @RequestMapping(value="/kp1100/kp1120.do")
	public String kp1120(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	
    	CommonList dataList = deptService.getDeptList(cmap);
    	model.addAttribute("dataList", dataList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "kp1100/kp1120";
	}
    
    @RequestMapping(value="/kp1100/kp1120Ajax.do")
	public String Kp1110Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("deptNo", cmap.getString("deptNo"));
    	
    	if (!"".equals(cmap.getString("deptNo"))) {
        	CommonMap viewData = deptService.getDeptView(cmap);
        	model.addAttribute("viewData", viewData);    		
    	}
    	
    	return "kp1100/kp1120Ajax";
	}
}
