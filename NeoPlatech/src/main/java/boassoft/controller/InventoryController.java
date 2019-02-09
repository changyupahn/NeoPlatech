package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.DynmTableUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.NumberUtil;
import boassoft.util.SessionUtil;

@Controller
public class InventoryController {

	
	@Resource(name = "InventoryService")
    private InventoryService inventoryService;
	
	@Resource(name = "SystemService")
    private SystemService systemService;
	
	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(InventoryController.class);
    
    
    @RequestMapping(value="/inventory/selectList.do")
	public String getInventoryList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	
    	CommonList inventoryList = inventoryService.getInventoryList(cmap);
    	model.addAttribute("inventoryList", inventoryList);
    	
    	CommonList inventoryYearList = inventoryService.getInventoryYearList(cmap);
    	model.addAttribute("inventoryYearList", inventoryYearList);
        
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventory/inventoryList";
	}
    
    @RequestMapping(value="/inventory/writeForm.do")
	public String getInventoryWriteForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	return "inventory/inventoryWrite";
	}
    
    @RequestMapping(value="/inventory/writeProc.do")
	public String getInventoryWriteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	
    	inventoryService.insertInventoryMaster(cmap);
    	
    	CommonMap cmForward = new CommonMap();
    	cmForward.put("forwardUrl", "pop_close");
    	model.addAttribute("cmForward", cmForward);
    	return "common/commonOk";
	}
    
    @RequestMapping(value="/inventory/deleteProc.do")
	public String getInventoryDeleteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("invYear", cmap.getString("invYear"));
    	cmap.put("invNO", cmap.getString("invNo"));
    	
    	inventoryService.deleteInventory(cmap);
    	
    	cmap.put("forwardUrl", "/inventory/selectList.do");
    	cmap.put("forwardMsg", "삭제되었습니다.");
    	model.addAttribute("cmForward", cmap);
    	return "common/commonOk";
	}
    
    @RequestMapping(value="/inventory/selectDetailList.do")
	public String getInventoryDetailList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "30"));
    	cmap.put("invYear", cmap.getString("sInvYear"));
    	cmap.put("invNo", cmap.getString("sInvNo"));
    	
    	if ("".equals(cmap.getString("sInvYear")) || "".equals(cmap.getString("sInvNo"))) {
    		return "redirect:/inventory/selectList.do";
    	}

//    	//물품항목정보 (전체)
//		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
//		model.addAttribute("addcolMngList", addcolMngList);
		
		//물품항목정보 (전체)
		cmap.put("dispType", "99");
		CommonList dispMngList99 = systemService.getDispMngList(cmap);
		model.addAttribute("dispMngList99", dispMngList99);
		
		//검색 항목 개수 구하기
		int searchCnt = 0;
		for (int i=0; i<dispMngList99.size(); i++) {
			if ("Y".equals(dispMngList99.getMap(i).getString("searchYn"))) {
				searchCnt++;
			}
		}
		model.addAttribute("searchCnt", searchCnt);
		
		//화면표시관리 (재물조사목록)
		cmap.put("dispType", "21");
		CommonList dispMngList21 = systemService.getDispMngList(cmap);
		model.addAttribute("dispMngList21", dispMngList21);
    	
    	//재물조사 마스터 조회
    	CommonMap inventoryMaster = inventoryService.getInventoryView(cmap);
    	model.addAttribute("inventoryMaster", inventoryMaster);
    	
//    	//재물조사 물품 목록
//    	CommonList inventoryDetailList = inventoryService.getInventoryDetailList(cmap);
//    	model.addAttribute("inventoryDetailList", inventoryDetailList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);    	
    	
    	return "inventory/inventoryDetailList";
	}
    
    @RequestMapping(value="/inventory/selectDetailListAjax.do")
	public String getInventoryDetailListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "30"));
    	cmap.put("invYear", cmap.getString("sInvYear"));
    	cmap.put("invNo", cmap.getString("sInvNo"));
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
    	if ("".equals(cmap.getString("sInvYear")) || "".equals(cmap.getString("sInvNo"))) {
//    		return "redirect:/inventory/selectList.do";
    	}

    	//물품항목정보 (전체)
		cmap.put("dispType", "99");
		CommonList addcolMngList = systemService.getDispMngList(cmap);
		model.addAttribute("addcolMngList", addcolMngList);
		
		//검색 항목 구성
		int colcnt = 0;
		for (int i=0; i<addcolMngList.size(); i++) {
			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
				colcnt++;
				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
				} 
				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
				} 
				else 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
				}
			}
		}
		
		//화면표시관리 (재물조사목록)
		cmap.put("dispType", "21");
		CommonList dispMngList21 = systemService.getDispMngList(cmap);
		StringBuffer sbcol = new StringBuffer();
		for (int i=0; i<dispMngList21.size(); i++) {
			CommonMap dispMng = dispMngList21.getMap(i);
			sbcol.append(", "+ dispMng.getString("tableAlias") +".");
			sbcol.append(dispMng.getString("physicalName"));    			
		}
		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));
    	
    	CommonList inventoryDetailList = inventoryService.getInventoryDetailList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", inventoryDetailList);
    	result.put("totalRow", inventoryDetailList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
    
	@RequestMapping(value="/inventory/selectDetailListXls.do")
	public String getInventoryDetailListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("invYear", cmap.getString("sInvYear"));
    	cmap.put("invNo", cmap.getString("sInvNo"));
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
    	if ("".equals(cmap.getString("sInvYear")) || "".equals(cmap.getString("sInvNo"))) {
//    		return "redirect:/inventory/selectList.do";
    	}

    	//물품항목정보 (전체)
		cmap.put("dispType", "99");
		CommonList addcolMngList = systemService.getDispMngList(cmap);
		model.addAttribute("addcolMngList", addcolMngList);
		
		//검색 항목 구성
		int colcnt = 0;
		for (int i=0; i<addcolMngList.size(); i++) {
			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
				colcnt++;
				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
				} 
				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
				} 
				else 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
				}
			}
		}
		
		//화면표시관리 (물품목록-엑셀)
		cmap.put("dispType", "99");
		CommonList dispMngList = systemService.getDispMngList(cmap);
		StringBuffer sbcol = new StringBuffer();
		for (int i=0; i<dispMngList.size(); i++) {
			CommonMap dispMng = dispMngList.getMap(i);
			sbcol.append(", "+ dispMng.getString("tableAlias") +".");
			sbcol.append(dispMng.getString("physicalName"));    			
		}
		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));
    	
    	//재물조사 물품 목록
    	CommonList inventoryDetailList = inventoryService.getInventoryDetailList(cmap);
    	model.addAttribute("inventoryDetailList", inventoryDetailList);
    	
    	int headerSize = dispMngList.size() + 2;
    	String[] headerListLgc = new String[headerSize];
    	String[] headerListPhc = new String[headerSize];
    	String[] headerListTyp = new String[headerSize];
    	int idx = 0;
    	while (idx<dispMngList.size()) {
    		CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			idx++;
    	}
    	
    	headerListLgc[idx] = "자산일련번호";
		headerListPhc[idx] = "ASSET_SEQ";
		headerListTyp[idx] = "TEXT";
		idx++;
		
		headerListLgc[idx] = "RFID번호";
		headerListPhc[idx] = "RFID_NO";
		headerListTyp[idx] = "TEXT";
		idx++;
    	
    	ExcelUtil.write(request, response, inventoryDetailList, egovMessageSource.getMessage("excel.filename.inventory.detail"), headerListLgc, headerListPhc, headerListTyp);
    	
    	return null;
	}
    
    @RequestMapping(value="/inventory/selectDetail.do")
	public String getInventoryDetailView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("asset_no", cmap.getString("asset_no"));
    	
    	//상세정보
    	CommonMap inventoryDetail = inventoryService.getInventoryDetailView(cmap);
    	model.addAttribute("inventoryDetail", inventoryDetail);
    	
    	//물품히스토리 (재물조사)
    	CommonList assetHistoryList = inventoryService.getAssetHistoryList(cmap);
    	model.addAttribute("assetHistoryList", assetHistoryList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);    	
    	
    	return "inventory/inventoryDetailView";
	}
    
    @RequestMapping(value="/inventory/targetSetting.do")
	public String targetSetting(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("invYear", cmap.getString("sInvYear"));
    	cmap.put("invNo", cmap.getString("sInvNo"));
    	cmap.put("invTargetYn", cmap.getString("uTargetYn"));
    	cmap.put("chkList", cmap.getArray("chkList"));

    	//물품항목정보 (전체)
		cmap.put("dispType", "99");
		CommonList addcolMngList = systemService.getDispMngList(cmap);
		model.addAttribute("addcolMngList", addcolMngList);
		
		//검색 항목 구성
		int colcnt = 0;
		for (int i=0; i<addcolMngList.size(); i++) {
			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
				colcnt++;
				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
				} 
				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType"))) 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
				} 
				else 
				{
					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
				}
			}
		}
    	
    	inventoryService.updateTargetYn(cmap);
    	
    	CommonMap result = new CommonMap();
    	result.put("result", "OK");
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
}
