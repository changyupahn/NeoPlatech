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
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

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
		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", "0"); 
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
		
	@RequestMapping(value="/kp2100/kp2151Excel.do")
	public String kp2150Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
        CommonMap cmap = new CommonMap(request);
        
        int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", "0"); 
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("goWith", cmap.getString("goWith"));
    	
    	System.out.println("  pageIdx" + " : " + cmap.getString("pageIdx", "0"));
    	System.out.println("  pageSize" + " : " + cmap.getString("pageSize", "0"));
    	System.out.println("  pageLimit" + " : " + cmap.getString("pageLimit", "0"));
    	System.out.println("  dataOrder" + " : " + cmap.getString("dataOrder", "0"));
    	System.out.println("  dataOrderArrow" + " : " + cmap.getString("dataOrderArrow", "0"));
    	System.out.println("  goWith" + " : " + cmap.getString("goWith", "") );
    
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);    	
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	//사용자 기본 파라미터 설정
    	if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))
    			&& "USR".equals(cmap.getString("searchDiv"))) {
    		cmap.put("sUserNo", cmap.getString("sUserNo", SessionUtil.getString("userNo")));
    		cmap.put("sUserName", cmap.getString("sUserName", SessionUtil.getString("userName")));
    		cmap.put("sDeptNo", cmap.getString("sDeptNo", SessionUtil.getString("deptNo")));
    		cmap.put("sDeptName", cmap.getString("sDeptName", SessionUtil.getString("deptName")));
    	}
    	
    	CommonList resultList = goodsShipmentOutService.getGoodsShipmentOutDetailList(cmap);
    	   
    	System.out.println("  resultList.size()" + " : " + resultList.size());
    	
    	//화면표시관리 (부자재출고목록)
    	cmap.put("dispType", "GOODS_SHIPMENT_OUT_LIST_EXCEL");
    	CommonList dispMngList = systemService.getDispMngList(cmap);
    	int headerSize = dispMngList.size();
    	String[] headerListLgc1 = new String[headerSize];
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = new String[headerSize];
    	String[] headerListTyp = new String[headerSize];
    	String[] headerListWidth = new String[headerSize];
    	String[][] mergedRegion = null;
    	
        int idx = 0;
    	
    	while (idx<dispMngList.size()) {
    		CommonMap dispMng = dispMngList.getMap(idx);
    		headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = "" + Math.round(dispMng.getInt("default_width",100) / 10);
			idx++;
    	}
    	
    	ExcelUtil.write2(request, response, resultList, "부자재출고확인목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);
    	
    	
		return null;
        
	}   
}
