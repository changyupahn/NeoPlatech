package boassoft.controller.kp2100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.SubsiDiaryShipmentOutService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

/** 부자재출고  */
@Controller
public class KP2160SubsiDiaryShipmentOutController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "subsiDiaryShipmentOutService")
	private SubsiDiaryShipmentOutService subsiDiaryShipmentOutService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2160SubsiDiaryShipmentOutController.class);
	
	@RequestMapping(value="/kp2100/kp2160.do")
	public String kp2160(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	System.out.println(" dataOrder  kp2160 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2160 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2160" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2160 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2160 " + "  : " + cmap.getInt("pageLimit")); 
    	
    	return "kp2100/kp2160";
	}
	
	@RequestMapping(value="/kp2100/kp2160Ajax.do")
	public String kp2120SubsiDiaryReceiptAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
    	System.out.println(" dataOrder  kp2160Ajax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2160Ajax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2160Ajax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2160Ajax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2160Ajax " + "  : " + cmap.getInt("pageLimit"));
	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp22110Ajax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp2120Ajax " + "  : " + cmap.toString());
    	CommonList resultList = subsiDiaryShipmentOutService.getSubsiDiaryShipmentOutList(cmap);
    	System.out.println(" resultList  kp22110Ajax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp22110Ajax  " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
}
