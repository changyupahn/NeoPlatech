package boassoft.controller.kp2100;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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

import boassoft.service.GoodsReceiptService;
import boassoft.service.PackingReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

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
    	
    	System.out.println(" dataOrder  kp2110 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2110 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2110" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2110 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2110 " + "  : " + cmap.getInt("pageLimit")); 
    	
    	return "kp2100/kp2131";    	    	
		
	}
	
	@RequestMapping(value="/kp2100/kp2131DetailAjax.do")
	public String kp2111GoodsReceiptDetailAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",0));
		
    	System.out.println(" dataOrder  kp2131DetailAjax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2131DetailAjax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2131DetailAjax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2131DetailAjax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2131DetailAjax " + "  : " + cmap.getInt("pageLimit"));  
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2131DetailAjax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp2131DetailAjax " + "  : " + cmap.toString());
    	CommonList resultList = packingReceiptService.getPackingReceiptDetailList(cmap);
    	
    	System.out.println(" resultList  kp2131DetailAjax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp2131DetailAjax  " + "  : " + resultList.size());
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
	public void Kp2111ComboItemSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = packingReceiptService.getOptionItemList(cmap);
		}catch(Exception e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().Exception()]" + e.getMessage());
		}
		
		JSONArray json = JSONArray.fromObject(commonCodeList);
		JSONObject j = new JSONObject();
		j.put("LIST", json.toString());
		PrintWriter pw = null;
		
		try{
			pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		}catch(UnsupportedEncodingException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}catch(IOException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}
		
		pw.print(j.toString());
		pw.flush();
		pw.close();
	}
	
	@RequestMapping(value="/kp2100/kp2131ComboPNoAjax.do")	
	public void Kp2111ComboPNoSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
		cmap.put("sltValue", cmap.getString("paramSltValue"));
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = packingReceiptService.getOptionPNoList(cmap);
		}catch(Exception e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboPNoSelect().Exception()]" + e.getMessage());
		}
		
		JSONArray json = JSONArray.fromObject(commonCodeList);
		JSONObject j = new JSONObject();
		j.put("LIST", json.toString());
		PrintWriter pw = null;
		
		try{
			pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		}catch(UnsupportedEncodingException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}catch(IOException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2131ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}
		
		pw.print(j.toString());
		pw.flush();
		pw.close();
	}
}
