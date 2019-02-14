package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.LabelService;
import boassoft.service.SystemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;

@Controller
public class LabelController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;
	
	@Resource(name = "labelService")
    private LabelService labelService;
	
	@Resource(name = "systemService")
    private SystemService systemService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(LabelController.class);
    
    
    @RequestMapping(value="/label/selectList.do")
	public String getLabelList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "label/labelListPop";
	}
    
    @RequestMapping(value="/label/selectLabelListAjax.do")
	public String getLabelListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	
    	CommonList labelList = labelService.getLabelList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", labelList);
    	result.put("totalRow", labelList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
    
    @RequestMapping(value="/label/selectLabelOptListAjax.do")
	public String getLabelOptListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	cmap.put("labelSeq", cmap.getString("labelSeq"));
    	
    	CommonList labelList = labelService.getLabelList(cmap);
    	model.addAttribute("labelList", labelList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "label/labelOptList";
	}
    
    @RequestMapping(value="/label/selectSyscolListAjax.do")
	public String getSyscolListAjx(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	
    	CommonList addcolMngList = systemService.getAddcolMngList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", addcolMngList);
    	result.put("totalRow", addcolMngList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
	}
    
    @RequestMapping(value="/label/insertLabelAjax.do")
	public String insertLabelAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	cmap.put("useYn", "Y");
    	
    	int labelSeq = labelService.insertLabel(cmap);
    	
    	CommonMap result = new CommonMap();    	
    	if (labelSeq > 0) {
    		result.put("result", "OK");
    		result.put("labelSeq", labelSeq);
    	} else {
    		result.put("result", "ERROR");
    	}
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
    @RequestMapping(value="/label/selectLabelFormAjax.do")
	public String getLabelFormAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("labelUserId", SessionUtil.getString("userId"));
    	
    	CommonList labelColList = labelService.getLabelColList(cmap);
    	model.addAttribute("labelColList", labelColList);
    	
    	return "label/labelForm";
	}
    
	
}
