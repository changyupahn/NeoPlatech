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
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

/** 부품입고 */
@Controller
public class KP2110GoodsReceiptController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "goodsReceiptService")
	private GoodsReceiptService goodsReceiptService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2110GoodsReceiptController.class);
	
	@RequestMapping(value="/kp2100/kp2110.do")
	public String kp2110(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);  
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	System.out.println(" dataOrder  kp2110 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2110 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2110" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2110 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2110 " + "  : " + cmap.getInt("pageLimit")); 
    	
    	return "kp2100/kp2110";
				
	}
	
	
	@RequestMapping(value="/kp2100/kp2110Ajax.do")
	public String kp2110GoodsReceiptAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	
    	System.out.println(" dataOrder  kp2110Ajax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2110Ajax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2110Ajax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2110Ajax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2110Ajax " + "  : " + cmap.getInt("pageLimit"));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp22110Ajax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp22110Ajax " + "  : " + cmap.toString());
    	CommonList resultList = goodsReceiptService.getGoodsReceiptList(cmap);
    	System.out.println(" resultList  kp22110Ajax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp22110Ajax  " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
		
    	return "common/commonString";
		
	}
}
