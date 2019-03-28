package boassoft.controller.kp2100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.PackingShipmentOutService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

/** 포장재 출고 */
@Controller
public class KP2170PackingShipmentOutController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "packingShipmentOutService")
	private PackingShipmentOutService packingShipmentOutService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	@Resource(name = "systemService")
    private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2170PackingShipmentOutController.class);
	
	@RequestMapping(value="/kp2100/kp2170.do")
	public String kp2170(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 0) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	
    	//화면표시관리 (포장재출고목록)
    	cmap.put("dispType", "PACKING_SHIPMENT_OUT_LIST");
    	CommonList packingShipmentOutList = systemService.getDispMngList(cmap);
    	model.addAttribute("packingShipmentOutList", packingShipmentOutList);
    	
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);    	   
    	
    	return "kp2100/kp2170"; 
	}
	
	@RequestMapping(value="/kp2100/kp2170Ajax.do")
	public String kp2130ShipmentOutAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
    	   
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2170Ajax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	    	
    	CommonList resultList = packingShipmentOutService.getPackingShipmentOutList(cmap);
    	
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	
    	return "common/commonString";
    	
	}
	
	@RequestMapping(value="/kp2100/kp2170Search.do")
	public String kp2170Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);			  
		
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2170Search";
								
	}
	
	@RequestMapping(value="/kp2100/kp2170Excel.do")
	public String kp2170Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
        CommonMap cmap = new CommonMap(request);
		
		int pageLimit = (cmap.getInt("page", 2) - cmap.getInt("pageIdx", 0)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "0"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", "1"); 
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	System.out.println("  pageIdx" + " : " + cmap.getString("pageIdx", "0"));
    	System.out.println("  pageSize" + " : " + cmap.getString("pageSize", "0"));
    	System.out.println("  pageLimit" + " : " + cmap.getString("pageLimit", "0"));
    	System.out.println("  dataOrder" + " : " + cmap.getString("dataOrder", "0"));
    	System.out.println("  dataOrderArrow" + " : " + cmap.getString("dataOrderArrow", "0"));
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
    	    	
    	CommonList resultList = packingShipmentOutService.getPackingShipmentOutList(cmap);
    	
    	//화면표시관리 (자산목록)
    	cmap.put("dispType", "PACKING_SHIPMENT_OUT_LIST_EXCEL");
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
    	
    	ExcelUtil.write2(request, response, resultList, "포장재출고목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);
    	
		return null;
	}
}
