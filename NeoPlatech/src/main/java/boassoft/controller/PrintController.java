package boassoft.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boassoft.common.CommonXmlList;
import boassoft.common.CommonXmlManage;
import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.PrintService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.SessionUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PrintController {

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "printService")
    private PrintService printService;

	@Resource(name = "commonXmlManage")
    private CommonXmlManage commonXmlManage;

	/** log */
    protected static final Log LOG = LogFactory.getLog(PrintController.class);

    @RequestMapping(value="/print/targetListXml.do")
	public String printTargetListXml(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/print/targetListXml.do" + " - " + cmap);

    	cmap.put("orgNo", cmap.getString("orgNo"));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		commonXmlList = printService.getPrintTargetListXml(cmap);
    	}catch(Exception e){
    		//e.printStackTrace();
    	}

    	xmlString = commonXmlManage.writeXmlString(commonXmlList);

    	model.addAttribute("xmlString", xmlString);
		return "common/commonXml";
    }

    @RequestMapping(value="/print/targetSuccess.do")
	public String printTargetSuccess(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/print/targetSuccess.do" + " - " + cmap);

    	cmap.put("printSeq", cmap.getString("printSeq"));
    	cmap.put("assetSeq", cmap.getString("assetSeq"));
    	cmap.put("invYear", cmap.getString("invYear"));
    	cmap.put("invNo", cmap.getString("invNo"));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		int result = printService.updatePrintTargetSuccess(cmap);

    	}catch(Exception e){
    		//e.printStackTrace();
    	}

    	xmlString = commonXmlManage.writeXmlString(commonXmlList);

    	model.addAttribute("xmlString", xmlString);
		return "common/commonXml";
    }

    @RequestMapping(value="/print/targetFail.do")
	public String printTargetFail(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/print/targetFail.do" + " - " + cmap);

    	cmap.put("print_seq", cmap.getString("print_seq"));

    	CommonXmlList commonXmlList = new CommonXmlList();
    	String xmlString = "";

    	try{
    		int result = printService.updatePrintTargetFail(cmap);
    	}catch(Exception e){
    		//e.printStackTrace();
    	}

    	xmlString = commonXmlManage.writeXmlString(commonXmlList);

    	model.addAttribute("xmlString", xmlString);
		return "common/commonXml";
    }

    @RequestMapping(value="/print/exec.do")
	public String printExec(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	CommonMap printInfo = new CommonMap();
    	printInfo.put("asset_no", cmap.getString("asset_no", "").trim());
    	printInfo.put("asset_name", cmap.getString("asset_name", "").trim());
    	printInfo.put("asset_size", cmap.getString("asset_size", "").trim());
    	printInfo.put("user_name", cmap.getString("user_name", "").trim());
    	printInfo.put("dept_name", cmap.getString("dept_name", "").trim());
    	printInfo.put("printType", cmap.getString("print_type", "N").trim()); //N,S
    	printInfo.put("user_name_yn", "N");
    	printInfo.put("dept_name", "");
    	printInfo.put("rfid_no", printInfo.getString("asset_no").split("-")[0]);
    	printInfo.put("print_yn", "N");
    	printInfo.put("org", cmap.getString("org"));
    	printInfo.put("req_system", "DEF");

    	//태그인쇄히스토리 등록
    	printService.insertHistory(printInfo);

    	return "common/commonJson";
	}

    /**
     * 물품현황 메뉴에서의 프린트 요청
     * 히스토리 등록만 해두고, 실제 프린트는 CS에서 처리
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/asset/print/exec.do")
	public String assetPrintExec(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	cmap.put("tmp_title", cmap.getString("tmpTitle", ""));

    	cmap.put("checkbox", cmap.getArray("chk"));
    	cmap.put("sRePrintChk", cmap.getString("bRePrintChk"));
    	cmap.put("req_system", "AST");
    	cmap.remove("asset_no");

    	//태그인쇄히스토리 등록
    	assetService.insertPrintHistory(cmap);

    	return "common/commonJson";
	}

    /**
     * 재물조사 상세내역에서의 프린트 요청
     * 히스토리 등록만 해두고, 실제 프린트는 CS에서 처리
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/inventory/print/exec.do")
	public String inventoryPrintExec(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	cmap.put("tmp_title", cmap.getString("tmpTitle", ""));

    	cmap.put("inv_year", cmap.getString("sInvYear"));
    	cmap.put("inv_no", cmap.getString("sInvNo"));
    	cmap.put("checkbox", cmap.getArray("chk"));
    	cmap.put("sRePrintChk", cmap.getString("bRePrintChk"));
    	cmap.put("req_system", "INV");
    	cmap.remove("asset_no");

    	//태그인쇄히스토리 등록
    	inventoryService.insertPrintHistory(cmap);

    	return "common/commonJson";
    	//return "print/printExec";
	}

    @RequestMapping(value="/print/selectList.do")
	public String printSelectList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	CommonList printHistoryList = printService.getPrintHistoryList(cmap);
    	model.addAttribute("printHistoryList", printHistoryList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "print/printHistoryList";
	}

    @RequestMapping(value="/print/selectListXls.do")
	public String printSelectListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "99999");

    	CommonList printHistoryList = printService.getPrintHistoryList(cmap);
    	model.addAttribute("printHistoryList", printHistoryList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "print/printHistoryListXls";
	}

    @RequestMapping(value="/print/rePrint.do")
	public String printRePrint(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/print/rePrint.do" + " - " + cmap);

    	printService.insertRePrint(cmap);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	//return "redirect:/print/selectList.do";

    	//CommonMap cmForward = new CommonMap();
    	cmap.put("forwardUrl", "/print/selectList.do");
    	//cmap.put("forwardMsg", "재출력 요청되었습니다.");
    	model.addAttribute("cmForward", cmap);
    	return "common/commonOk";
	}

    @RequestMapping(value="/print/rePrintAjax.do")
	public String printRePrintAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/print/rePrintAjax.do" + " - " + cmap);

    	printService.insertRePrint(cmap);

    	//검색값 유지
    	CommonMap cmJson = new CommonMap();
    	cmJson.put("result", "Y");
    	model.addAttribute("jsonString",cmJson.toJsonString());
    	System.out.println("jsonString : " + cmJson.toJsonString());
    	return "common/commonJson";
    	//{aaa:"value",bbb:"value"}
	}

    @RequestMapping(value="/print/request.do")
	public String printRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("orgNo", SessionUtil.getString("orgNo"));

    	//태그인쇄히스토리 등록
    	assetService.insertPrintHistory(cmap);

    	return "common/commonJson";
	}

}
