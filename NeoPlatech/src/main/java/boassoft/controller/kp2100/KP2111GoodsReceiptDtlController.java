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
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP2111GoodsReceiptDtlController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "goodsReceiptService")
	private GoodsReceiptService goodsReceiptService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	@Resource(name = "systemService")
    private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2111GoodsReceiptDtlController.class);
	
	@RequestMapping(value="/kp2110/kp2111.do")
	public String kp2111(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("goWith", cmap.getString("goWith"));
		
		//부자재
		//CommonList goodsReceiptDetailList = goodsReceiptService.getGoodsReceiptDetail(cmap);
		//model.addAttribute("goodsReceiptDetailList", goodsReceiptDetailList);
		
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2111";
	
	}
	
}
