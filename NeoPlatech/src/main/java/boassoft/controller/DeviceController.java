package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.DeviceService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;

@Controller
public class DeviceController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;
	
	@Resource(name = "DeviceService")
    private DeviceService deviceService;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(DeviceController.class);
    
    @RequestMapping(value="/system/deviceManage.do")
	public String deviceManage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	
    	//관리자 목록
    	CommonList deviceList = deviceService.getDeviceList(cmap);
    	model.addAttribute("deviceList", deviceList);    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "system/deviceManage";
	}
    
    @RequestMapping(value="/system/deviceManageDetailAjax.do")
	public String deviceManageDetailAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	
    	//정보
    	CommonMap deviceView = deviceService.getDeviceView(cmap);
    	
    	model.addAttribute("jsonString", deviceView.toJsonString());    	
    	
    	return "common/commonJson";
	}
    
    @RequestMapping(value="/system/deviceManageProc.do")
	public String deviceManageProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
//      	cmap.put("frst_regist_pnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
//      	cmap.put("frst_register_id", SessionUtil.getString("user_id"));
//      	cmap.put("last_updt_pnttm", DateUtil.getFormatDate("yyyyMMddHHmmss"));
//      	cmap.put("last_updusr_id", SessionUtil.getString("user_id"));
     	
      	if ("I".equals(cmap.getString("procMode"))) {
    		deviceService.insertDevice(cmap);
    	} else if ("U".equals(cmap.getString("procMode"))) {
    		deviceService.updateDevice(cmap);
    	}
    	
    	return "redirect:/system/deviceManage.do";
	}
    
    @RequestMapping(value="/system/deviceManageDelProc.do")
	public String deviceManageDelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	
    	deviceService.deleteDevice(cmap);
    	
    	return "redirect:/system/deviceManage.do";
	}    
}
