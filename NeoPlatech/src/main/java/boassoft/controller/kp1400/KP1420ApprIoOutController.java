package boassoft.controller.kp1400;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ApprAssetService;
import boassoft.service.ApprIoExtService;
import boassoft.service.ApprIoInService;
import boassoft.service.ApprIoOutService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.StringUtil;

public class KP1420ApprIoOutController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "apprIoOutService")
    private ApprIoOutService apprIoOutService;

	@Resource(name = "apprIoExtService")
    private ApprIoExtService apprIoExtService;

	@Resource(name = "apprIoInService")
    private ApprIoInService apprIoInService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1420ApprIoOutController.class);

    @RequestMapping(value="/kp1400/kp1420.do")
	public String kp1420(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1420";
	}

    @RequestMapping(value="/kp1400/kp1420Ajax.do")
	public String kp1420Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	//cmap.put("sRqstStatusCd", cmap.getString("sRqstStatusCd", "2"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = apprIoOutService.getApprIoOutList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1400/kp1420Search.do")
	public String kp1420Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1420Search";
	}

    @RequestMapping(value="/kp1400/kp1420Excel.do")
	public String kp1420Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		CommonMap cmForward = new CommonMap();
        	cmForward.put("forwardUrl", "");
        	cmForward.put("forwardMsg", "조회 권한이 없거나 로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
        	model.addAttribute("cmForward", cmForward);
        	return "common/commonOk";
    	}

    	CommonList resultList = apprIoOutService.getApprIoOutList(cmap);

    	String[] headerListLgc1 = {"반출일자","반입예정일자","실반입일자","신청부서","신청자","자산건수","승인상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"outDt","expInDt","inDt","rqstDeptName","rqstUserName","rqstAssetCnt","rqstStatusName"};
    	String[] headerListTyp = {"DATE","DATE","DATE","TEXT","TEXT","NUMBER","TEXT"};
    	String[] headerListWidth = {"10","10","10","15","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "반출반입관리", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1400/kp1420DtlExcel.do")
	public String kp1420DtlExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		CommonMap cmForward = new CommonMap();
        	cmForward.put("forwardUrl", "");
        	cmForward.put("forwardMsg", "조회 권한이 없거나 로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
        	model.addAttribute("cmForward", cmForward);
        	return "common/commonOk";
    	}

    	CommonList resultList = apprIoOutService.getApprIoOutAssetList(cmap);

    	String[] headerListLgc1 = {"자산번호","품목명","상세명칭","반출일자","반입예정일자","실반입일자","신청부서","신청자","자산건수","승인상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"assetNo","itemName","assetName","outDt","expInDt","inDt","rqstDeptName","rqstUserName","rqstAssetCnt","rqstStatusName"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","DATE","DATE","DATE","TEXT","TEXT","NUMBER","TEXT"};
    	String[] headerListWidth = {"10","10","15","10","10","10","15","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "반출반입관리_자산별", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1400/kp1421.do")
	public String kp1421(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	//반출정보
    	CommonMap viewData = apprIoOutService.getApprIoOutView(cmap);
    	model.addAttribute("viewData", viewData);

    	//반입연장정보
    	CommonList viewDataExtList = apprIoExtService.getApprIoExtList(cmap);
    	model.addAttribute("viewDataExtList", viewDataExtList);

    	//반입정보
    	CommonMap viewDataIn = apprIoInService.getApprIoInView(cmap);
    	model.addAttribute("viewDataIn", viewDataIn);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1421";
	}

    @RequestMapping(value="/kp1400/kp1421Ajax.do")
	public String kp1421Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = apprAssetService.getApprAssetList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1400/kp1421Hwp.do")
	public String kp1421Hwp(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	//반출정보
    	CommonMap viewData = apprIoOutService.getApprIoOutView(cmap);
    	if (viewData.getString("outDt").replaceAll("\\D","").length() >= 8) {
	    	viewData.put("outDtStr", String.format("%s년&nbsp;&nbsp;%s월&nbsp;&nbsp;%s일"
	    			, viewData.getString("outDt").replaceAll("\\D","").substring(0,4)
	    			, viewData.getString("outDt").replaceAll("\\D","").substring(4,6)
	    			, viewData.getString("outDt").replaceAll("\\D","").substring(6,8)
	    			));
    	}
    	model.addAttribute("viewData", viewData);

    	//반입연장정보
    	CommonList viewDataExtList = apprIoExtService.getApprIoExtList(cmap);
    	model.addAttribute("viewDataExtList", viewDataExtList);

    	//반입정보
    	CommonMap viewDataIn = apprIoInService.getApprIoInView(cmap);
    	model.addAttribute("viewDataIn", viewDataIn);

    	//반출자산목록
    	CommonList resultList = apprAssetService.getApprAssetList(cmap);
    	model.addAttribute("dataList", resultList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1421Hwp";
	}


}
