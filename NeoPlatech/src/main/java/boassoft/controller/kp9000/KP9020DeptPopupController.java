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
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP9020DeptPopupController {

	@Resource(name = "deptService")
    private DeptService deptService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(KP9020DeptPopupController.class);
    
    @RequestMapping(value="/kp9000/kp9020.do")
	public String kp9020(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	
    	CommonList deptList = deptService.getDeptList(cmap);
    	model.addAttribute("dataList", deptList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "kp9000/kp9020";
	}
    
    @RequestMapping(value="/kp9000/kp9020Ajax.do")
	public String Kp9020Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	CommonMap viewData = deptService.getDeptView(cmap);
    	model.addAttribute("viewData", viewData);
    	
    	return "kp9000/kp9020Ajax";
	}
    
}
