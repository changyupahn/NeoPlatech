package boassoft.controller.kp2100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.PackingReceiptService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;

/** 포장재 입고 */
@Controller
public class KP2130PackingReceiptController {

	@Resource(name = "commonMap")
	private CommonMap commonMap;
	
	@Resource(name = "packingReceiptService")
	private PackingReceiptService packingReceiptService;
	
	@Resource(name = "userService")
    private UserService userService;
	
	@Resource(name = "systemService")
    private SystemService systemService;
	
	/** log */
	protected static final Log LOG = LogFactory.getLog(KP2130PackingReceiptController.class);
	
	@RequestMapping(value="/kp2100/kp2130.do")
	public String kp2130(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));
    	cmap.put("pageLimit", pageLimit);
    	
    	//화면표시관리 (포장재입고목록)
    	cmap.put("dispType", "PACKING_RECEIPT_LIST");
    	CommonList packingReceiptList = systemService.getDispMngList(cmap);
    	model.addAttribute("packingReceiptList", packingReceiptList);
    	
    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);
    	
    	System.out.println(" dataOrder  kp2130 " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2130 " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2130" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2130 " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2130 " + "  : " + cmap.getInt("pageLimit"));     	    	
    	
		return "kp2100/kp2130";    	    	
	}
	
	@RequestMapping(value="/kp2100/kp2130Ajax.do")
	public String kp2130PackingReceiptAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		
		CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("pageLimit", cmap.getInt("pageLimit",1));
		
    	System.out.println(" dataOrder  kp2130Ajax " + "  : " + CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	System.out.println(" dataOrderArrow kp2130Ajax " + "  : " + cmap.getString("dataOrderArrow"));
    	System.out.println(" pageSize  kp2130Ajax" + "  : " + cmap.getInt("pageSize"));
    	System.out.println(" pageIdx  kp2130Ajax " + "  : " + cmap.getInt("pageIdx"));
    	System.out.println(" pageLimit kp2130Ajax " + "  : " + cmap.getInt("pageLimit"));
    	
    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	System.out.println(" gridSessionChk kp2130Ajax " + "  : " + gridSessionChk.toString());
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}
    	
    	System.out.println(" cmap kp2130Ajax " + "  : " + cmap.toString());
    	CommonList resultList = packingReceiptService.getPackingReceiptList(cmap);
    	System.out.println(" resultList  kp2130Ajax " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp2130Ajax  " + "  : " + resultList.size());
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());
    	    	
    	return "common/commonString";
				
	}
	
	@RequestMapping(value="/kp2100/kp2130Search.do")
	public String kp2130Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
	CommonMap cmap = new CommonMap(request);			  
	
		//검색값 유지
    	model.addAttribute("cmRequest",cmap);
		
    	return "kp2100/kp2130Search";
	}
	
	@RequestMapping(value="/kp2100/kp2130Excel.do")
	public String kp2130Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		CommonMap cmap = new CommonMap(request);
		
		int pageLimit = (cmap.getInt("page", 1) - cmap.getInt("pageIdx", 1)) * cmap.getInt("pageSize", 50) ;
		cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("pageLimit", pageLimit); 
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	
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
    	
    	System.out.println(" cmap kp2130Excel " + "  : " + cmap.toString());
    	CommonList resultList = packingReceiptService.getPackingReceiptList(cmap);
    	System.out.println(" resultList  kp2130Excel " + "  : " + resultList.toString());
    	System.out.println(" resultList.size() kp2130Excel  " + "  : " + resultList.size());
    	
     	//화면표시관리 (자산목록)
    	cmap.put("dispType", "PACKING_RECEIPT_LIST_EXCEL");
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
    	
    	ExcelUtil.write2(request, response, resultList, "포장재입고목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);
    	    	
		return null;
		
		
	}
}
