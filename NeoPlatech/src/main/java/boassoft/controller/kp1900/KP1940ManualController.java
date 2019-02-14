package boassoft.controller.kp1900;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.UserService;
import boassoft.util.CommonMap;

/** 매뉴얼 관리 */
@Controller
public class KP1940ManualController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1940ManualController.class);

    @RequestMapping(value="/kp1900/kp1940.do")
	public String kp1940(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1940";
	}

    @RequestMapping(value="/kp1900/kp1941.do")
	public String kp1941(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1941";
	}

    @RequestMapping(value="/kp1900/kp1942.do")
	public String kp1942(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1942";
	}

    @RequestMapping(value="/kp1900/kp1943.do")
	public String kp1943(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1943";
	}

    @RequestMapping(value="/kp1900/kp1944.do")
	public String kp1944(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1944";
	}

    @RequestMapping(value="/kp1900/kp1945.do")
	public String kp1945(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1900/kp1945";
	}
}
