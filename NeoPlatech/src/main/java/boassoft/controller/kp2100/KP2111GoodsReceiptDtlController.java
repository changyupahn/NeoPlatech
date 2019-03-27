package boassoft.controller.kp2100;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import boassoft.service.GoodsReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
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
	protected static final Log LOG = LogFactory
			.getLog(KP2111GoodsReceiptDtlController.class);

	@RequestMapping(value = "/kp2100/kp2111.do")
	public String kp2111(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0))
				* cmap.getInt("pageSize", 50);
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
		cmap.put("pageSize", cmap.getString("pageSize", "50"));
		cmap.put("pageLimit", pageLimit);

		// 화면표시관리 (부자재입고목록)
		cmap.put("dispType", "GOODS_RECEIPT_DETAIL_LIST");
		CommonList goodsReceiptDetailList = systemService.getDispMngList(cmap);
		model.addAttribute("goodsReceiptDetailList", goodsReceiptDetailList);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp2100/kp2111";

	}

	@RequestMapping(value = "/kp2100/kp2111DetailAjax.do")
	public String kp2111GoodsReceiptDetailAjax(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);
		cmap.put("dataOrder",
				CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
		cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
		cmap.put("pageLimit", cmap.getInt("pageLimit", 0));
		cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));				

		// 그리드 세션 체크 및 메뉴 권한 설정
		CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
		
		if (!gridSessionChk.isEmpty()) {
			model.addAttribute("printString", gridSessionChk.toJsonString());
			return "common/commonString";
		}

		
		CommonList resultList = goodsReceiptService
				.getGoodsReceiptDetailList(cmap);
		
		CommonMap result = new CommonMap();
		result.put("resultList", resultList);
		result.put("totalRow", resultList.totalRow);
		model.addAttribute("printString", result.toJsonString());

		return "common/commonString";

	}

	@RequestMapping(value = "/kp2100/kp2111Search.do")
	public String kp2131Search(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {

		CommonMap cmap = new CommonMap(request);

		// 검색값 유지
		model.addAttribute("cmRequest", cmap);

		return "kp2100/kp2111Search";

	}

	@RequestMapping(value = "/code/optionVendorList.do")
	public String optionVendorList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
		cmap.put("sltValue", cmap.getString("paramSltValue"));

		CommonList commonCodeList = goodsReceiptService
				.getOptionVendorList(cmap);
		model.addAttribute("commonCodeList", commonCodeList);

		return "code/optionVendorList";

	}

	@RequestMapping(value = "/code/optionItemList.do")
	public String optionItemList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
		cmap.put("sltValue", cmap.getString("paramSltValue"));

		CommonList commonCodeList = goodsReceiptService.getOptionItemList(cmap);
		model.addAttribute("commonCodeList", commonCodeList);

		return "code/optionItemList";

	}

	@RequestMapping(value = "/code/optionPNoList.do")
	public String optionPNoList(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
		cmap.put("sltValue", cmap.getString("paramSltValue"));

		CommonList commonCodeList = goodsReceiptService.getOptionPNoList(cmap);
		model.addAttribute("commonCodeList", commonCodeList);

		return "code/optionPNoList";

	}

	@RequestMapping(value = "/kp2100/kp2111ComboItemAjax.do")
	public ModelAndView Kp2111ComboItemSelect(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
	
		CommonList commonCodeList = new CommonList();
		try {
			commonCodeList = goodsReceiptService.getOptionItemList(cmap);
		} catch (Exception e) {
			LOG.error("[" + this.getClass().getName()
					+ ".Kp2111ComboItemSelect().Exception()]" + e.getMessage());
		}
		HashMap<String, CommonList> resultMap = new HashMap<String, CommonList>();

		resultMap.put("LIST", commonCodeList);
	
		modelAndView.addAllObjects(resultMap);
		modelAndView.setViewName("jsonView");

		return modelAndView;
	}

	@RequestMapping(value = "/kp2100/kp2111ComboPNoAjax.do")
	public ModelAndView Kp2111ComboPNoSelect(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
		cmap.put("sltValue", cmap.getString("sRqstItemCd"));
		CommonList commonCodeList = new CommonList();
		try {
			commonCodeList = goodsReceiptService.getOptionPNoList(cmap);
		} catch (Exception e) {
			LOG.error("[" + this.getClass().getName()
					+ ".Kp2111ComboPNoSelect().Exception()]" + e.getMessage());
		}

		HashMap<String, CommonList> resultMap = new HashMap<String, CommonList>();

		resultMap.put("LIST", commonCodeList);
		modelAndView.addAllObjects(resultMap);
		modelAndView.setViewName("jsonView");
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/kp2100/kp2111Stock.do")
	public String Kp2111Stock(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}
	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("odId", obj.get("odId"));
	    		param.put("demandId", obj.get("demandId"));
	    		param.put("sReceiptCnt", obj.get("sReceiptCnt"));
	    		param.put("sRqstVendorCd", obj.get("sRqstVendorCd"));
	    		param.put("sRqstItemCd", obj.get("sRqstItemCd"));
	    		param.put("sRqstPNoCd", obj.get("sRqstPNoCd"));
	    		
	    		
	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("odId"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}
	    	
    		//파라미터
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
	    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",((CommonMap)paramList.get(0)).getString("sRqstVendorCd")));
			cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ((CommonMap)paramList.get(0)).getString("sRqstItemCd")));
			cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ((CommonMap)paramList.get(0)).getString("sRqstPNoCd")));
			cmap.put("odId", cmap.getString("odId", ((CommonMap)paramList.get(0)).getString("odId") ));
			cmap.put("demandId", cmap.getString("demandId", ((CommonMap)paramList.get(0)).getString("demandId")));
			cmap.put("sReceiptCnt", cmap.getInt("sReceiptCnt", ((CommonMap)paramList.get(0)).getInt("sReceiptCnt")));
			
			CommonList resultList = goodsReceiptService.getGoodsReceiptDetailList(cmap);

			double maxCnt = cmap.getDouble("sReceiptCnt", 0);
			double disCnt = 0;
			if(!resultList.isEmpty() && resultList.size() > 0){
				for(int i = 0; i < resultList.size(); i++){
					CommonMap gmap = resultList.getMap(i);
					double sumQty = gmap.getDouble("sumQty");
					System.out.println("999 sumQty " + " : " + sumQty );
					if(maxCnt > 0){
						System.out.println("000 maxCnt " + " : " + maxCnt );						      
						// maxCnt 300 sumQty 150  입고량  소요량   maxCnt 5  sumQty  0
						if(maxCnt >  sumQty){  // 입고량이  소요량보다 많으면  
							disCnt = maxCnt - sumQty;  // 150
					
							// 재고가 있을때 주문량 적으면   50 
							if(disCnt - gmap.getInt("qtyOnHand",0) >= 0){
							   	gmap.put("qtyOnHand",disCnt - gmap.getInt("qtyOnHand",0));
							   	gmap.put(gmap.getInt("preQtyOnHand",0),"0");
							   	resultCnt = goodsReceiptService.updateQtyOnHand(gmap);
							}else{  // 재고가 잇을때 주문량 보다 많으면 
								gmap.put("qtyOnHand",gmap.getInt("qtyOnHand",0));
								gmap.put("preQtyOnHand",disCnt);
								resultCnt = goodsReceiptService.updateQtyOnHand(gmap);
							}
							maxCnt = maxCnt - disCnt; 

							resultCnt++;
							
						}
					}else{						
						continue;
					} 
				}
			}
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}
    	
    	return resultMap.toJsonString(model);
		
	}
	
	@RequestMapping(value="/kp2100/kp2111Recall.do")
	public String Kp2111Recall(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;
    	
    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}
    		    	
    		JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("odId", obj.get("odId"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("odId"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}
	    	
    		//파라미터
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
	    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",""));
			cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
			cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
			cmap.put("odId", cmap.getString("odId", ""));
			cmap.put("demandId", cmap.getString("demandId", ""));
			cmap.put("sReceiptCnt", cmap.getInt("sReceiptCnt", 0));
			
			CommonList resultList = goodsReceiptService.getGoodsReceiptDetailList(cmap);
			int maxCnt = cmap.getInt("sReceiptCnt", 0);
			int disCnt = 0;
			if(!resultList.isEmpty() && resultList.size() > 0){
				for(int i = 0; i < resultList.size(); i++){
					CommonMap gmap = resultList.getMap(i);
					int sumQty = gmap.getInt("sumQty");
					
					if(maxCnt > 0){
												      
						// maxCnt 300 sumQty 150  입고량  소요량 
						if(maxCnt >  sumQty){  // 입고량이  소요량보다 많으면  
							disCnt = maxCnt - sumQty;  // 150
							// 재고가 있을때 주문량 적으면   50 
							if(disCnt - gmap.getInt("qtyOnHand",0) >= 0){
							   	gmap.put("qtyOnHand",disCnt - gmap.getInt("qtyOnHand",0));
							   	gmap.put(gmap.getInt("preQtyOnHand",0),"0");
							   	
							   	resultCnt = goodsReceiptService.updateQtyOnHand(gmap);
							}else{  // 재고가 잇을때 주문량 보다 많으면 
								gmap.put("qtyOnHand",gmap.getInt("qtyOnHand",0));
								gmap.put("preQtyOnHand",disCnt);
								resultCnt = goodsReceiptService.updateQtyOnHand(gmap);
							}
							
							maxCnt = maxCnt - disCnt; 
							resultCnt++;
						}
					}else{
						continue;
					}
				}
			}
			
    	} catch (Exception e) {
			e.printStackTrace();
		}
		
    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}
    	
    	return resultMap.toJsonString(model);
		
	}
}
