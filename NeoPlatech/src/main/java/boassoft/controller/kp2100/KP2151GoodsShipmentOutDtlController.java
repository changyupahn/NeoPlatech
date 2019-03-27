package boassoft.controller.kp2100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.GoodsShipmentOutService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Controller
public class KP2151GoodsShipmentOutDtlController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "goodsShipmentOutService")
	private GoodsShipmentOutService goodsShipmentOutService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	@Resource(name = "systemService")
    private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2111GoodsReceiptDtlController.class);
	
	@RequestMapping(value="/kp2100/kp2151.do")
	public String kp2151(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	cmap.put("goWith", cmap.getString("goWith"));
		
    	//화면표시관리 (부자재입고목록)
    	cmap.put("dispType", "GOODS_SHIPMENT_OUT_LIST");
    	CommonList goodsShipmentOutList = systemService.getDispMngList(cmap);
    	model.addAttribute("goodsShipmentOutList", goodsShipmentOutList);
    	
    	//자산상세
    	CommonMap dispMng = new CommonMap();
    	
         CommonList goodsShipmentOutDetailList = goodsShipmentOutService.getGoodsShipmentOutDetailList(cmap);
         
         if(!goodsShipmentOutDetailList.isEmpty()){
        	 dispMng = goodsShipmentOutDetailList.getMap(0);
        	 
        	String sDemandId = dispMng.getString("DEMAND_ID");
  			String sLgPartNo = dispMng.getString("LG_PART_NO");
  			String sBomQty = dispMng.getString("BOM_QTY");
  			String sSendingPcName = dispMng.getString("SENDING_PC_NAME");
  			String sSendTime = dispMng.getString("SEND_TIME");
         	
         }
    	    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	model.addAttribute("viewData",dispMng);
    	    		
    	return "kp2100/kp2151";
	
	}
	
	@RequestMapping(value="/kp2100/kp2151DetailAjax.do")
	public String kp2151GoodsShipmentOutDetailAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", pageLimit); 
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("goWith", cmap.getString("goWith"));
    	    
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2150DetailAjax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	    	
    	CommonList resultList = goodsShipmentOutService.getGoodsShipmentOutDetailList(cmap);

    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	
    	return "common/commonString";    	    	
				
	}
	
	  /** 자산상세정보 탭구성 */
	@RequestMapping(value="/kp2100/kp2151Detail.do")
	public String kp2151Detail(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
		
	 	//화면표시관리 (자산목록)
		//화면표시관리 (부자재입고목록)
    	cmap.put("dispType", "GOODS_SHIPMENT_OUT_LIST");
    	CommonList goodsShipmentOutList = systemService.getDispMngList(cmap);
    	model.addAttribute("goodsShipmentOutList", goodsShipmentOutList);		    	

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
							
    	return "kp2100/kp2151Detail";
		
		
	}
		
	@RequestMapping(value="/kp2100/kp2151Search.do")
	public String kp2150Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        CommonMap cmap = new CommonMap(request);
		           
    	//자산상세
    	CommonMap dispMng = new CommonMap();

    	
         CommonList goodsShipmentOutDetailList = goodsShipmentOutService.getGoodsShipmentOutDetailList(cmap);
         
         if(!goodsShipmentOutDetailList.isEmpty()){
        	 dispMng = goodsShipmentOutDetailList.getMap(0);
        	 
        	String sDemandId = dispMng.getString("DEMAND_ID");
  			String sLgPartNo = dispMng.getString("LG_PART_NO");
  			String sBomQty = dispMng.getString("BOM_QTY");
  			String sSendingPcName = dispMng.getString("SENDING_PC_NAME");
  			String sSendTime = dispMng.getString("SEND_TIME");
         	
         }         			 		
         
    	model.addAttribute("viewData", dispMng);
        
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2151Search";		
	}
}
