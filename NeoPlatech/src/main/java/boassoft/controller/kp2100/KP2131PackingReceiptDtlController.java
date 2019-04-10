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

import boassoft.service.PackingReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

@Controller
public class KP2131PackingReceiptDtlController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "packingReceiptService")
	private PackingReceiptService packingReceiptService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "systemService")
	private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory
			.getLog(KP2111GoodsReceiptDtlController.class);
	
	@RequestMapping(value = "/kp2100/kp2131.do")
	public String kp2131(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);		
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	
    	//화면표시관리 (부자재입고목록)
    	cmap.put("dispType", "PACKING_RECEIPT_DETAIL_LIST");
    	CommonList packingReceiptDetailList = systemService.getDispMngList(cmap);
    	model.addAttribute("packingReceiptDetailList", packingReceiptDetailList);
    	
    	// 검색값 유지
    	model.addAttribute("cmRequest", cmap);
    	    	
    	return "kp2100/kp2131";    	    	
		
	}
	
	@RequestMapping(value="/kp2100/kp2131DetailAjax.do")
	public String kp2111GoodsReceiptDetailAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",0));
    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
    	
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2131DetailAjax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	

    	CommonList resultList = packingReceiptService.getPackingReceiptDetailList(cmap);
    	
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
		
    	return "common/commonString";
		
	}
	
	@RequestMapping(value="/kp2100/kp2131Search.do")
	public String kp2131Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
		
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2131Search";
		
	}
	
	@RequestMapping(value="/code/comboVendorList.do")
	public String optionVendorList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
    	
    	CommonList commonCodeList = packingReceiptService.getOptionVendorList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
		
    	return "code/comboVendorList";				
	}
	
	@RequestMapping(value="/code/comboItemList.do")
	public String optionItemList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
    	
    	CommonList commonCodeList = packingReceiptService.getOptionItemList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
    	    	
    	return "code/comboItemList";
			
	}
	
	@RequestMapping(value="/code/comboPNoList.do")	
	public String optionPNoList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
    	
    	CommonList commonCodeList = packingReceiptService.getOptionPNoList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
    	
    	return "code/comboPNoList";
		
	}
	
	@RequestMapping(value="/kp2100/kp2131ComboItemAjax.do")	
	public ModelAndView Kp2111ComboItemSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();	
		CommonMap cmap = new CommonMap(request);		
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
	
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = packingReceiptService.getOptionItemList(cmap);
		}catch(Exception e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().Exception()]" + e.getMessage());
		}
		
		 HashMap<String, CommonList> resultMap = new HashMap<String, CommonList>();
		 resultMap.put("LIST", commonCodeList);
		 modelAndView.addAllObjects(resultMap);
		 modelAndView.setViewName("jsonView");	
		
		return modelAndView;
	}
	
	@RequestMapping(value="/kp2100/kp2131ComboPNoAjax.do")	
	public ModelAndView Kp2111ComboPNoSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();	
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
		cmap.put("sltValue", cmap.getString("sRqstItemCd"));
		
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = packingReceiptService.getOptionPNoList(cmap);
		}catch(Exception e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboPNoSelect().Exception()]" + e.getMessage());
		}
		
		HashMap<String, CommonList> resultMap = new HashMap<String, CommonList>();		
		 resultMap.put("LIST", commonCodeList);
		 modelAndView.addAllObjects(resultMap);
		 modelAndView.setViewName("jsonView");	
		 return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/kp2100/kp2131Stock.do")
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
			
			CommonList resultList = packingReceiptService.getPackingReceiptDetailList(cmap);
		
			double maxCnt = cmap.getDouble("sReceiptCnt", 0);
			double disCnt = 0;
			if(!resultList.isEmpty() && resultList.size() > 0){
				for(int i = 0; i < resultList.size(); i++){
					CommonMap gmap = resultList.getMap(i);
					double sumQty = gmap.getDouble("sumQty");
					
					if(maxCnt > 0){
											      
						// maxCnt 300 sumQty 150  입고량  소요량   maxCnt 5  sumQty  0
						if(maxCnt >  sumQty){  // 입고량이  소요량보다 많으면  
							disCnt = maxCnt - sumQty;  // 150
					
							// 재고가 있을때 주문량 적으면   50 
							if(disCnt - gmap.getInt("qtyOnHand",0) >= 0){
							   	gmap.put("qtyOnHand",disCnt - gmap.getInt("qtyOnHand",0));
							   	gmap.put(gmap.getInt("preQtyOnHand",0),"0");
							
							   	resultCnt = packingReceiptService.updateQtyOnHand(gmap);
							}else{  // 재고가 잇을때 주문량 보다 많으면 
								gmap.put("qtyOnHand",gmap.getInt("qtyOnHand",0));
								gmap.put("preQtyOnHand",disCnt);
								resultCnt = packingReceiptService.updateQtyOnHand(gmap);
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/kp2100/kp2131Recall.do")
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
	    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd",((CommonMap)paramList.get(0)).getString("sRqstVendorCd")));
			cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ((CommonMap)paramList.get(0)).getString("sRqstItemCd")));
			cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ((CommonMap)paramList.get(0)).getString("sRqstPNoCd")));
			cmap.put("odId", cmap.getString("odId", ((CommonMap)paramList.get(0)).getString("odId") ));
			cmap.put("demandId", cmap.getString("demandId", ((CommonMap)paramList.get(0)).getString("demandId")));
			cmap.put("sReceiptCnt", cmap.getInt("sReceiptCnt", ((CommonMap)paramList.get(0)).getInt("sReceiptCnt")));
			
			CommonList resultList = packingReceiptService.getPackingReceiptDetailList(cmap);
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
							   	
							   	resultCnt = packingReceiptService.updateQtyOnHand(gmap);
							}else{  // 재고가 잇을때 주문량 보다 많으면 
								gmap.put("qtyOnHand",gmap.getInt("qtyOnHand",0));
								gmap.put("preQtyOnHand",disCnt);
								resultCnt = packingReceiptService.updateQtyOnHand(gmap);
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
	
	@RequestMapping(value="/kp2100/kp2131Excel.do")
	public String kp2130Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		
		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", "0"); 
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sRqstVendorCd", cmap.getString("sRqstVendorCd", ""));
		cmap.put("sRqstItemCd", cmap.getString("sRqstItemCd", ""));
		cmap.put("sRqstPNoCd", cmap.getString("sRqstPNoCd", ""));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);    	
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	//사용자 기본 파라미터 설정
    	if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
    			&& "USR".equals(cmap.getString("searchDiv"))) {
    		cmap.put("sUserNo", cmap.getString("sUserNo", SessionUtil.getString("userNo")));
    		cmap.put("sUserName", cmap.getString("sUserName", SessionUtil.getString("userName")));
    		cmap.put("sDeptNo", cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
    		cmap.put("sDeptName", cmap.getString("sDeptName", SessionUtil.getString("deptName")));
    	}    	    		
    	
    	CommonList resultList = packingReceiptService.getPackingReceiptDetailList(cmap);    	    	
    	
    	//화면표시관리 (자산목록)
    	cmap.put("dispType", "PACKING_RECEIPT_DETAIL_LIST_EXCEL");
    	CommonList dispMngList = systemService.getDispMngList(cmap);    	    
    	
    	int headerSize = dispMngList.size();
    	String[] headerListLgc1 = new String[headerSize];
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = new String[headerSize];
    	String[] headerListTyp = new String[headerSize];
    	String[] headerListWidth = new String[headerSize];
    	String[][] mergedRegion = null;
    	int idx = 0;
    	
    	while (idx<dispMngList.size()) {
    		CommonMap dispMng = dispMngList.getMap(idx);
    		headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = "" + Math.round(dispMng.getInt("default_width",100) / 10);
			idx++;
    	}
    	
    	ExcelUtil.write2(request, response, resultList, "포장재입고확인목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);
    	    	
		return null;
		
	}	
}
