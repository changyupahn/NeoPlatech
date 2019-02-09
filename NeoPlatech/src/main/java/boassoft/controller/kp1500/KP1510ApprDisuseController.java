package boassoft.controller.kp1500;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.ApprAssetService;
import boassoft.service.ApprDisuseService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;

@Controller
public class KP1510ApprDisuseController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "ApprDisuseService")
    private ApprDisuseService apprDisuseService;

	@Resource(name = "ApprAssetService")
    private ApprAssetService apprAssetService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1510ApprDisuseController.class);

    @RequestMapping(value="/kp1500/kp1510.do")
	public String kp1510(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1500/kp1510";
	}

    @RequestMapping(value="/kp1500/kp1510Ajax.do")
	public String kp1510Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = apprDisuseService.getApprDisuseList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1500/kp1510Search.do")
	public String kp1510Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1500/kp1510Search";
	}

    @RequestMapping(value="/kp1500/kp1510Tab.do")
	public String kp1510Tab(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1500/kp1510Tab";
	}

    @RequestMapping(value="/kp1500/kp1510Excel.do")
	public String kp1510Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = apprDisuseService.getApprDisuseList(cmap);

    	String[] headerListLgc1 = {"신청일자","승인일자","처리일자","신청부서","신청자","자산건수","승인상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"rqstRegtime","disuseCompDt","disuseProcDt","disuseProcCdStr","rqstDeptName","rqstUserName","rqstAssetCnt","rqstStatusName"};
    	String[] headerListTyp = {"DATE","DATE","DATE","TEXT","TEXT","TEXT","NUMBER","TEXT"};
    	String[] headerListWidth = {"10","10","10","10","15","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "불용처분및폐기", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1500/kp1510DtlExcel.do")
	public String kp1510DtlExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	CommonList resultList = apprDisuseService.getApprDisuseAssetList(cmap);

    	String[] headerListLgc1 = {"자산번호","품목명","상세명칭","신청일자","승인일자","처리일자","처리상태","처분이익","처리내용","신청부서","신청자","자산건수","승인상태"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"assetNo","itemName","assetName","rqstRegtime","disuseCompDt","disuseProcDt","disuseProcCdStr","disuseProcAmt","disuseProcCont","rqstDeptName","rqstUserName","rqstAssetCnt","rqstStatusName"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","DATE","DATE","DATE","TEXT","NUMBER","TEST","TEXT","TEXT","NUMBER","TEXT"};
    	String[] headerListWidth = {"10","10","15","10","10","10","10","10","10","15","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "불용처분및폐기_자산별", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}


}
