package boassoft.controller.kp2100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.GoodsReceiptService;
import boassoft.service.GoodsShipmentOutService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

/** 부품출고 */
@Controller
public class KP2150GoodsShipmentOutController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "goodsShipmentOutService")
	private GoodsShipmentOutService goodsShipmentOutService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2150GoodsShipmentOutController.class);
	
	@RequestMapping(value="/kp2100/kp2150.do")
	public String kp2150(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
		
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	System.out.println(" dataOrder  kp2150 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2150 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2150" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2150 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2150 " + "  : " + cmap.getInt("pageLimit")); 
    	
		return "kp2100/kp2150";
		
		
	}
	
	@RequestMapping(value="/kp2100/kp2150Ajax.do")
	public String kp2110GoodsReceiptAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
    	System.out.println(" dataOrder  kp2150Ajax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2150Ajax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2150Ajax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2150Ajax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2150Ajax " + "  : " + cmap.getInt("pageLimit"));
		
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp22110Ajax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp2150Ajax " + "  : " + cmap.toString());
    	CommonList resultList = goodsShipmentOutService.GoodsShipmentOutList(cmap);
    	System.out.println(" resultList  kp2150Ajax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp21500Ajax  " + "  : " + resultList.size());

    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	
    	return "common/commonString";
		
		
	}
}
