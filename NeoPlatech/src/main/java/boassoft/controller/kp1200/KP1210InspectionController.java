package boassoft.controller.kp1200;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ContractDtlService;
import boassoft.service.ContractService;
import boassoft.service.InspItemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;

@Controller
public class KP1210InspectionController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "contractService")
    private ContractService contractService;

	@Resource(name = "contractDtlService")
    private ContractDtlService contractDtlService;

	@Resource(name = "inspItemService")
    private InspItemService inspItemService;

	@Resource(name = "userService")
    private UserService userService;
	
	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1210InspectionController.class);

    @RequestMapping(value="/kp1200/kp1210.do")
	public String kp1210(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1200/kp1210";
	}

    @RequestMapping(value="/kp1200/kp1210Ajax.do")
	public String kp1210Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//파라미터
    	cmap.put("searchDtKeywordS", cmap.getString("searchDtKeywordS").replaceAll("\\D", ""));
    	cmap.put("searchDtKeywordE", cmap.getString("searchDtKeywordE").replaceAll("\\D", ""));
    	cmap.put("sContramtS", cmap.getString("sContramtS").replaceAll("\\D", ""));
    	cmap.put("sContramtE", cmap.getString("sContramtE").replaceAll("\\D", ""));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = contractService.getContractList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1200/kp1210DtlAjax.do")
	public String kp1210DtlAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = contractDtlService.getContractDtlList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1200/kp1210Search.do")
	public String kp1210Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1200/kp1210Search";
	}

    @RequestMapping(value="/kp1200/kp1210Excel.do")
	public String kp1210Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = contractService.getContractList(cmap);

    	String[] headerListLgc1 = {"계약번호","계약명","구매신청자","거래처","계약금액","계약일자","계약종료일자","납품장소","납품일자","검수일자","검수상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"contrno","purnm","userhisname","custhisname","contramt","contrdt","contrenddt","deliveryplace","deliverydt","inspDt","inspStatusCdStr"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT"};
    	String[] headerListWidth = {"10","20","10","15","10","10","10","10","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "계약검수관리", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
