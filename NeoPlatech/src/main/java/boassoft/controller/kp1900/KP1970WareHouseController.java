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
import boassoft.service.WareHouseService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

/** 창고관리 */
@Controller
public class KP1970WareHouseController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "wareHouseService")
	private WareHouseService wareHouseService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP1970WareHouseController.class);
	
	@RequestMapping(value="/kp1900/kp1970.do")
	public String kp1970(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);     	
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "kp1900/kp1970";
	}
	
	@RequestMapping(value="/kp1900/kp1970Ajax.do")
	public String kp1970WareHouseAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
    	System.out.println(" dataOrder " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize " + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit " + "  : " + cmap.getInt("pageLimit"));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap " + "  : " + cmap.toString());
    	CommonList resultList = wareHouseService.getWareHouseList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
		
	}
}
