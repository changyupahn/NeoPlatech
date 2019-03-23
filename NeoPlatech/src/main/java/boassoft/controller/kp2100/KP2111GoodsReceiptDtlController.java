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
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);    	

    	//화면표시관리 (부자재입고목록)
    	cmap.put("dispType", "GOODS_RECEIPT_DETAIL_LIST");
    	CommonList goodsReceiptDetailList = systemService.getDispMngList(cmap);
    	model.addAttribute("goodsReceiptDetailList", goodsReceiptDetailList);
    	
		// 검색값 유지
		model.addAttribute("cmRequest", cmap);
		
		System.out.println(" dataOrder  kp2110 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2110 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2110" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2110 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2110 " + "  : " + cmap.getInt("pageLimit")); 

		return "kp2100/kp2111";

	}
	
	@RequestMapping(value="/kp2100/kp2111DetailAjax.do")
	public String kp2111GoodsReceiptDetailAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",0));
    	
    	System.out.println(" dataOrder  kp2111DetailAjax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2111DetailAjax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2111DetailAjax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2111DetailAjax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2111DetailAjax " + "  : " + cmap.getInt("pageLimit"));   	    	
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2111DetailAjax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp2111DetailAjax " + "  : " + cmap.toString());
    	CommonList resultList = goodsReceiptService.getGoodsReceiptDetailList(cmap);
    	System.out.println(" resultList  kp2111DetailAjax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp2111DetailAjax  " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
		
    	return "common/commonString";
		
	}
	
	@RequestMapping(value="/kp2100/kp2111Search.do")
	public String kp2131Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
		
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2111Search";
		
	}
	
	
	@RequestMapping(value="/code/optionVendorList.do")
	public String optionVendorList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
		
    	CommonList commonCodeList = goodsReceiptService.getOptionVendorList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
    	
    	return "code/optionVendorList";
		
	}

	@RequestMapping(value="/code/optionItemList.do")
	public String optionItemList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
    	
    	CommonList commonCodeList = goodsReceiptService.getOptionItemList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
    	
    	
    	return "code/optionItemList";
		
	
	}
	
	
	@RequestMapping(value="/code/optionPNoList.do")	
	public String optionPNoList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("paramCodeId"));
    	cmap.put("sltValue", cmap.getString("paramSltValue"));
    	
    	CommonList commonCodeList = goodsReceiptService.getOptionPNoList(cmap);
    	model.addAttribute("commonCodeList", commonCodeList);
    	
    	return "code/optionPNoList";
		
	}
		
	@RequestMapping(value="/kp2100/kp2111ComboItemAjax.do")	
	public void Kp2111ComboItemSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
		System.out.println (" codeId " + " : " + cmap.getString("codeId"));
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = goodsReceiptService.getOptionItemList(cmap);
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
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}catch(IOException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}
		
		pw.print(j.toString());
		pw.flush();
		pw.close();
	}
	
	@RequestMapping(value="/kp2100/kp2111ComboPNoAjax.do")	
	public void Kp2111ComboPNoSelect(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		cmap.put("codeId", cmap.getString("sRqstVendorCd"));
		cmap.put("sltValue", cmap.getString("sRqstItemCd"));		
		System.out.println (" codeId " + " : " + cmap.getString("codeId"));
		System.out.println (" sltValue " + " : " + cmap.getString("sltValue"));
		CommonList commonCodeList = new CommonList();
		try{
			commonCodeList = goodsReceiptService.getOptionPNoList(cmap);
		}catch(Exception e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboPNoSelect().Exception()]" + e.getMessage());
		}
		
		JSONArray json = JSONArray.fromObject(commonCodeList);
		JSONObject j = new JSONObject();
		j.put("LIST", json.toString());
		PrintWriter pw = null;
		
		try{
			pw = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"));
		}catch(UnsupportedEncodingException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}catch(IOException e){
			LOG.error("[" + this.getClass().getName() + ".Kp2111ComboItemSelect().UnsupportedEncodingException()]" + e.getMessage());
		}
		
		pw.print(j.toString());
		pw.flush();
		pw.close();
	}
}
