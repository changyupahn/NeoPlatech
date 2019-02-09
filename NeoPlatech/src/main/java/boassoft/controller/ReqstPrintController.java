package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ReqstPrintService;
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
public class ReqstPrintController {

	@Resource(name = "ReqstPrintService")
    private ReqstPrintService reqstPrintService;
	
	@Resource(name = "SystemService")
    private SystemService systemService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(PrintController.class);
    
    @RequestMapping(value="/reqst/print/execAjax.do")
	public String reqstPrintExecAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("assetNo", cmap.getArray("assetNo[]"));
    	
    	if (!"".equals(cmap.getString("assetNo"))) {
    		
    		cmap.put("reqstUserId", SessionUtil.getString("userId"));
    		cmap.put("labelSeq", "");
    		cmap.put("addrSeq", "");
    		cmap.put("printStatusCd", "T");
    		
    		reqstPrintService.insertReqstPrint(cmap);
    	}
    	
    	CommonMap result = new CommonMap();
    	result.put("result", "OK");
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
    
    @RequestMapping(value="/reqst/print/selectList.do")
	public String getReqstPrintList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "30"));
    	
    	//물품항목정보 (전체)
		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
		model.addAttribute("addcolMngList", addcolMngList);
		
		//검색 항목 개수 구하기
		int searchCnt = 0;
		for (int i=0; i<addcolMngList.size(); i++) {
			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
				searchCnt++;
			}
		}
		model.addAttribute("searchCnt", searchCnt);
    	
		//화면표시관리 (물품목록)
		cmap.put("dispType", "11");
		CommonList dispMngList11 = systemService.getDispMngList(cmap);
		model.addAttribute("dispMngList11", dispMngList11);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "reqst/reqstPrintList";
	}
    
    @RequestMapping(value="/reqst/print/selectListAjax.do")
	public String getAssetListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("reqstUserId", SessionUtil.getString("userId"));
    	cmap.put("printStatusCd", "T");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
    	//물품항목정보 (전체)
		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
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
		
		//화면표시관리 (물품목록)
		cmap.put("dispType", "11");
		CommonList dispMngList11 = systemService.getDispMngList(cmap);
		StringBuffer sbcol = new StringBuffer();
		for (int i=0; i<dispMngList11.size(); i++) {
			CommonMap dispMng = dispMngList11.getMap(i);
			sbcol.append(", ast.");
			sbcol.append(dispMng.getString("physicalName"));    			
		}
		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));
    	
    	CommonList reqstPrintList = reqstPrintService.getReqstPrintList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", reqstPrintList);
    	result.put("totalRow", reqstPrintList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
    
    @RequestMapping(value="/reqst/print/selectListXls.do")
	public String getAssetListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "999999");
    	cmap.put("reqstUserId", SessionUtil.getString("userId"));
    	cmap.put("printStatusCd", "T");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
    	//물품항목정보 (전체)
		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
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
			sbcol.append(", ast.");
			sbcol.append(dispMng.getString("physicalName"));    			
		}
		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));
		
		CommonList reqstPrintList = reqstPrintService.getReqstPrintList(cmap);
    	
    	int headerSize = dispMngList.size();
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
    	
    	ExcelUtil.write(request, response, reqstPrintList, "태그발행요청대상", headerListLgc, headerListPhc, headerListTyp);
    	
    	return null;
	}
    
    @RequestMapping(value="/reqst/print/deleteProc.do")
	public String deleteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("reqstUserId", SessionUtil.getString("userId"));
    	cmap.put("reqstSeq", cmap.getArray("reqstSeq[]"));
    	cmap.put("printStatusCd", "T");
    	
    	if (!"".equals(cmap.getString("reqstSeq"))) {
    		reqstPrintService.deleteReqstPrint(cmap);
    	}
    	
    	CommonMap result = new CommonMap();
    	result.put("result", "OK");
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
    @RequestMapping(value="/reqst/print/deleteAllProc.do")
	public String deleteAllProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("reqstUserId", SessionUtil.getString("userId"));
    	cmap.put("printStatusCd", "T");
    	
    	reqstPrintService.deleteReqstPrint(cmap);
    	
    	CommonMap result = new CommonMap();
    	result.put("result", "OK");
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
    @RequestMapping(value="/reqst/print/saveProc.do")
	public String reqstPrintSaveProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("reqstUserId", SessionUtil.getString("userId"));
    	cmap.put("prevPrintStatusCd", "T");
    	cmap.put("printStatusCd", "R");
    	
    	int updateCnt = reqstPrintService.updatePrintStatusCd_R(cmap);
    	
    	CommonMap result = new CommonMap();
    	result.put("result", "OK");
    	result.put("updateCnt", updateCnt);
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
	
}
