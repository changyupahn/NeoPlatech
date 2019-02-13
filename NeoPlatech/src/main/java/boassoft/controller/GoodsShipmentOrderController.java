package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonMap;
import boassoft.service.GoodsShipmentOrderService;


@Controller
public class GoodsShipmentOrderController {

	@Resource(name = "UserService")
    private UserService userService;
	
	@Resource(name = "GoodsShipmentOrderService")
	 private GoodsShipmentOrderService GoodsShipmentOrderService;
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(GoodsShipmentOrderController.class);
    
    @RequestMapping(value="/kp3000/kp3010.do")
    public String kp3010GoodsShipmentOrderSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "kp3000/kp3010";	
    }
    
    @RequestMapping(value="/kp3000/kp3010Ajax.do")
    public String kp1210Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	
    	
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
    	//파라미터
    	cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS").replaceAll("\\D", ""));
    	cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE").replaceAll("\\D", ""));
    	cmap.put("sOutContramtS", cmap.getString("sOutContramtS").replaceAll("\\D", ""));
    	cmap.put("sOutContramtE", cmap.getString("sOutContramtE").replaceAll("\\D", ""));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	
    	
		return null;
    	
    }
    
}
