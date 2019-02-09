package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.common.CommonXmlList;
import boassoft.common.CommonXmlManage;
import boassoft.service.GateService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Controller
public class GateController {

	@Resource(name = "GateService")
    private GateService gateService;
	
	@Resource(name = "CommonXmlManage")
    private CommonXmlManage commonXmlManage;
    
	/** log */
    protected static final Log LOG = LogFactory.getLog(GateController.class);
    
    
    @RequestMapping(value="/rfid/gate/saveNSelectXml.do")
	public String rfidGateSaveNSelectXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/rfid/gate/saveNSelect.do" + " - " + cmap);
    	cmap.put("gateno", cmap.getString("gateno"));
    	cmap.put("asset_no", cmap.getString("asset_no"));
    	
    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "-1";
    	
    	try{
    		if (!"".equals(cmap.getString("gateno")) 
    				&& !"".equals(cmap.getString("asset_no"))) {
    			
    			CommonList assetInfoList = gateService.getAssetInfoList(cmap);
    			
    			if (assetInfoList.size() > 0) {
    				CommonMap assetInfo = assetInfoList.getMap(0);
    				
    				cmap.put("asset_no", assetInfo.getString("asset_no"));
    				cmap.put("dept_name", assetInfo.getString("dept_name"));
    				cmap.put("user_name", assetInfo.getString("user_name"));
    				
    				int gateseq = gateService.insertGateHistory(cmap);
    				cmap.put("gateseq", gateseq);
    				
    				commonXmlList = gateService.getAssetInfoXml(cmap);
    		    	
    		    	if( commonXmlList.size() > 0 )
    		    		xmlString = commonXmlManage.writeXmlString(commonXmlList);
    			}
    		} else {
    			xmlString = "-2";
    		}
    	}catch(Exception e){
    		xmlString = "-2";
    		e.printStackTrace();
    	}
    	
    	model.addAttribute("xmlString", xmlString);
    	
		return "common/commonXml";
	}
}
