package boassoft.controller.kp2200;

import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.GoodsReceiptService;
import boassoft.service.SubsiDiaryReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP2211GoodsInvoceDetailController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;

	@Resource(name = "goodsReceiptService")
	private GoodsReceiptService goodsReceiptService;
	
	@Resource(name = "subsiDiaryReceiptService")
	private SubsiDiaryReceiptService subsiDiaryReceiptService;

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "systemService")
	private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2211GoodsInvoceDetailController.class);
	
	@RequestMapping(value="/kp2200/kp2211.do")
	public String kp2210(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd", ""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
		cmap.put("searchDtKeyword", cmap.getString("searchDtKeyword", ""));
    	cmap.put("odId", cmap.getString("odId"));
		
    	System.out.println(" dataOrder " + "  : "  +cmap.getString("dataOrder", "") );
		System.out.println(" dataOrderArrow " + "  : "  +cmap.getString("dataOrderArrow", "") );
		System.out.println(" pageLimit " + "  : "  +cmap.getString("pageLimit", "") );
		System.out.println(" sRqstVendorCd " + "  : "  +cmap.getString("sRqstVendorCd", "") );
		System.out.println(" sRqstItemCd " + "  : "  +cmap.getString("sRqstItemCd", "") );
		System.out.println(" sRqstPNoCd " + "  : "  +cmap.getString("sRqstPNoCd", "") );
		System.out.println(" odId " + "  : "  +cmap.getString("odId", "") );
    	
    	
    	//화면표시관리 (포장재입고목록)
    	cmap.put("dispType", "GOODS_RECEIPT_LIST");
    	CommonList subsidiaryReceiptList = systemService.getDispMngList(cmap);
    	model.addAttribute("subsidiaryReceiptList", subsidiaryReceiptList);
    	
    	// 검색값 유지
    	model.addAttribute("cmRequest", cmap);

    	return "kp2200/kp2211";
		
	}
	
	@RequestMapping(value="/kp2200/kp2211Ajax.do")
	public String kp2210SubsiDiaryReceiptAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
	
		CommonMap cmap = new CommonMap(request);
		
		System.out.println("  cmap " + " : " + cmap.toString());
		
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
    	
		
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd", ""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
		cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS", ""));
		cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE", ""));
    	cmap.put("sOdId", cmap.getString("sOdId",""));
		
    	System.out.println(" dataOrder " + "  : "  +cmap.getString("dataOrder", "") );
		System.out.println(" dataOrderArrow " + "  : "  +cmap.getString("dataOrderArrow", "") );
		System.out.println(" pageLimit " + "  : "  +cmap.getString("pageLimit", "") );
		System.out.println(" sRqstVendorCd " + "  : "  +cmap.getString("sRqstVendorCd", "") );
		System.out.println(" sRqstItemCd " + "  : "  +cmap.getString("sRqstItemCd", "") );
		System.out.println(" sRqstPNoCd " + "  : "  +cmap.getString("sRqstPNoCd", "") );
		System.out.println(" searchDtKeywordS " + "  : "  +cmap.getString("searchDtKeywordS", "") );
		System.out.println(" searchDtKeywordE " + "  : "  +cmap.getString("searchDtKeywordE", "") );		
		System.out.println(" odId " + "  : "  +cmap.getString("odId", "") );
		
		//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	System.out.println(" /kp2200/kp2211Ajax.do resultList strat ");
    	
    	System.out.println(" /kp2200/kp2211Ajax.do cmap " + " : " + cmap.toString());    	    	
    		
    	CommonList resultList = subsiDiaryReceiptService.getSubsiDiaryReceiptList(cmap);
    	System.out.println(" resultList.size() " + resultList.size());
    	if(resultList.size() > 0){
    		for(int i = 0; i <  resultList.size(); i++){
    		CommonMap map = (CommonMap)resultList.get(i);
    		System.out.println(" OD_ID " + " : " + map.getString("odId"));
    		System.out.println(" DEMAND_ID " + " : " + map.getString("demandId"));
    		System.out.println(" LGE_DATE " + " : " + map.getString("legDate"));
    		System.out.println(" NEO_DATE " + " : " + map.getString("neoDate"));
    		}
    		
    	}
    	
    	CommonMap result = new CommonMap();
    	
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
	
	@RequestMapping(value = "/kp2200/kp2211Search.do")
	public String kp2110Search(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp2200/kp2211Search";

	}

}
