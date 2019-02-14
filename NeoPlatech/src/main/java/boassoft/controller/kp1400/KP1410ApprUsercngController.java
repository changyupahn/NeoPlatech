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
import boassoft.service.ApprRqstService;
import boassoft.service.ApprUsercngService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.SessionUtil;


@Controller
public class KP1410ApprUsercngController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "apprUsercngService")
    private ApprUsercngService apprUsercngService;

	@Resource(name = "apprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "apprRqstService")
    private ApprRqstService apprRqstService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1410ApprUsercngController.class);

    @RequestMapping(value="/kp1400/kp1410.do")
	public String kp1410(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1410";
	}

    @RequestMapping(value="/kp1400/kp1410Ajax.do")
	public String kp1410Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = apprUsercngService.getApprUsercngList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1400/kp1410Search.do")
	public String kp1410Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1410Search";
	}

    @RequestMapping(value="/kp1400/kp1410Excel.do")
	public String kp1410Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = apprUsercngService.getApprUsercngList(cmap);

    	String[] headerListLgc1 = {"신청일자","인계부서","인계자","인수부서","인수자","자산건수","승인상태","승인완료일자"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"rqstRegtime","rqstDeptName","rqstUserName","aucDeptName","aucUserName","rqstAssetCnt","rqstStatusName","aucCompDt"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","NUMBER","TEXT","DATE"};
    	String[] headerListWidth = {"10","15","10","15","10","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "인수인계관리", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1400/kp1410DtlExcel.do")
	public String kp1410DtlExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = apprUsercngService.getApprUsercngAssetList(cmap);

    	String[] headerListLgc1 = {"신청일자","자산번호","품목명","상세명칭","인계부서","인계자","인수부서","인수자","자산건수","승인상태","승인완료일자"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"rqstRegtime","assetNo","itemName","assetName","rqstDeptName","rqstUserName","aucDeptName","aucUserName","rqstAssetCnt","rqstStatusName","aucCompDt"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","NUMBER","TEXT","DATE"};
    	String[] headerListWidth = {"10","10","15","20","15","10","15","10","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "인수인계관리_자산별", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1400/kp1411.do")
	public String kp1411(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("rqstno", cmap.getString("rqstno"));

    	//자산상세
    	CommonMap viewData = apprUsercngService.getApprUsercngView(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1400/kp1411";
	}

    @RequestMapping(value="/kp1400/kp1411Ajax.do")
	public String kp1411Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    /** 인수인계 승인 처리 */
    @RequestMapping(value="/kp1400/kp1411Proc.do")
	public String Kp1411Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

    		//파라미터
    		cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("rqstno"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	if ("GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {
	    		//승인상태 (승인완료)
	    		cmap.put("rqstStatusCd", "3");

	    		//승인상태변경
	    		resultCnt = apprRqstService.updateApprRqstStatusCd(cmap);

	    		//승인일자수정
	    		cmap.put("aucCompDt", DateUtil.getFormatDate("yyyyMMdd"));
	    		apprUsercngService.updateApprUsercng2(cmap);

	    		//자산사용자 변경 처리
	    		apprUsercngService.updateApprUsercngConfirm(cmap);
	    	}

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    		resultMap.put("rqstno", cmap.getString("rqstno"));
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}


}
