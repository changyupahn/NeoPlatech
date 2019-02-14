package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.service.InventoryStatService;

@Controller
public class InventoryStatController {

	@Resource(name = "inventoryStatService")
    private InventoryStatService inventoryStatService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(InventoryStatController.class);
    
    @RequestMapping(value="/inventoryStat/deptStatList.do")
	public String deptStatList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList deptStatList = inventoryStatService.getDeptStat(cmap);
    	model.addAttribute("deptStatList", deptStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/deptStatList";
	}
    
    @RequestMapping(value="/inventoryStat/userStatList.do")
	public String userStatList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList userStatList = inventoryStatService.getUserStat(cmap);
    	model.addAttribute("userStatList", userStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/userStatList";
	}
    
    @RequestMapping(value="/inventoryStat/hosilStatList.do")
	public String hosilStatList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList hosilStatList = inventoryStatService.getHosilStat(cmap);
    	model.addAttribute("hosilStatList", hosilStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/hosilStatList";
	}
    
    @RequestMapping(value="/inventoryStat/assetCateStatList.do")
	public String assetCateStatList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList assetCateStatList = inventoryStatService.getAssetCateStat(cmap);
    	model.addAttribute("assetCateStatList", assetCateStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/assetCateStatList";
	}
    
    @RequestMapping(value="/inventoryStat/newRegisterStatList.do")
	public String newRegisterStatList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList newRegisterStatList = inventoryStatService.getNewRegisterStat(cmap);
    	model.addAttribute("newRegisterStatList", newRegisterStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/newRegisterStatList";
	}
    
    @RequestMapping(value="/inventoryStat/deptStatListXls.do")
	public String deptStatListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList deptStatList = inventoryStatService.getDeptStat(cmap);
    	model.addAttribute("deptStatList", deptStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/deptStatListXls";
	}
    
    @RequestMapping(value="/inventoryStat/userStatListXls.do")
	public String userStatListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList userStatList = inventoryStatService.getUserStat(cmap);
    	model.addAttribute("userStatList", userStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/userStatListXls";
	}
    
    @RequestMapping(value="/inventoryStat/hosilStatListXls.do")
	public String hosilStatListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList hosilStatList = inventoryStatService.getHosilStat(cmap);
    	model.addAttribute("hosilStatList", hosilStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/hosilStatListXls";
	}
    
    @RequestMapping(value="/inventoryStat/assetCateStatListXls.do")
	public String assetCateStatListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	
    	CommonList assetCateStatList = inventoryStatService.getAssetCateStat(cmap);
    	model.addAttribute("assetCateStatList", assetCateStatList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "inventoryStat/assetCateStatListXls";
	}
    
    @RequestMapping(value="/inventoryStat/newRegisterStatListXls.do")
 	public String newRegisterStatListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
     	CommonMap cmap = new CommonMap(request);
     	cmap.put("inv_year", cmap.getString("sInvYear"));
     	cmap.put("inv_no", cmap.getString("sInvNo"));
     	
     	CommonList newRegisterStatList = inventoryStatService.getNewRegisterStat(cmap);
     	model.addAttribute("newRegisterStatList", newRegisterStatList);
     	
     	//검색값 유지
     	model.addAttribute("cmRequest",cmap);
     	
     	return "inventoryStat/newRegisterStatListXls";
 	}
}
