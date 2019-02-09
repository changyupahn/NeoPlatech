package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AddressService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.SessionUtil;

@Controller
public class AddressController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;
	
	@Resource(name = "AddressService")
    private AddressService addressService;
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(AddressController.class);
    
    @RequestMapping(value="/address/selectList.do")
    public String getAddressList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	return "address/addrListPop";
	}
	
    @RequestMapping(value="/address/selectAddressListAjax.do")
   	public String getAddressListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("addrUserId", SessionUtil.getString("userId"));
       	
       	CommonList addressList = addressService.getAddressList(cmap);
       	CommonMap result = new CommonMap();
       	result.put("resultList", addressList);
       	result.put("totalRow", addressList.totalRow);
       	model.addAttribute("printString", result.toJsonString());
       	
       	return "common/commonString";
   	}
    
    @RequestMapping(value="/address/selectAddressOptListAjax.do")
  	public String getLabelOptListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
      	CommonMap cmap = new CommonMap(request);
      	cmap.put("orgNo", SessionUtil.getString("orgNo"));
      	cmap.put("addrUserId", SessionUtil.getString("userId"));
      	cmap.put("addrSeq", cmap.getString("addrSeq"));
      	
      	CommonList addrList = addressService.getAddressList(cmap);
      	model.addAttribute("addrList", addrList);
      	
      	//검색값 유지
      	model.addAttribute("cmRequest",cmap);
      	
      	return "address/addrOptList";
  	}
    
    @RequestMapping(value="/address/insertAddressAjax.do")
	public String insertAddressAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	cmap.put("useYn", "Y");
    	
    	int addrSeq = addressService.insertAddress(cmap);
    	
    	CommonMap result = new CommonMap();    	
    	if (addrSeq > 0) {
    		result.put("result", "OK");
    		result.put("addrSeq", addrSeq);
    	} else {
    		result.put("result", "ERROR");
    	}
    	model.addAttribute("printString", result.toJsonString());
    	return "common/commonString";
	}
    
    @RequestMapping(value="/address/selectAddressFormAjax.do")
	public String getAddressFormAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));
    	cmap.put("addrUserId", SessionUtil.getString("userId"));
    	
    	CommonMap addressInfo = addressService.getAddressInfo(cmap);
    	model.addAttribute("addressInfo", addressInfo);
    	
    	return "address/addrForm";
	}
}
