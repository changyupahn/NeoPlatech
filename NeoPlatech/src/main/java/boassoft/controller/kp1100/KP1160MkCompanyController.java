package boassoft.controller.kp1100;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetService;
import boassoft.service.MkCompanyService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1160MkCompanyController {

	@Resource(name = "MkCompanyService")
    private MkCompanyService mkCompanyService;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "compSeqIdGnrService")
    private EgovIdGnrService compSeqIdGnrService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1160MkCompanyController.class);

    @RequestMapping(value="/kp1100/kp1160.do")
	public String kp1160(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList dataList = mkCompanyService.getMkCompanyList(cmap);
    	model.addAttribute("dataList", dataList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1100/kp1160";
	}

    @RequestMapping(value="/kp1100/kp1160Ajax.do")
	public String Kp1160Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("compSeq", cmap.getString("compSeq"));

    	if (!"".equals(cmap.getString("compSeq"))) {
        	CommonMap viewData = mkCompanyService.getMkCompanyView(cmap);

        	if (viewData.isEmpty()) {
        		//ROOT
        		viewData.put("compSeq", "0");
        		viewData.put("compCd", "0");
        		viewData.put("compName", "제조업체코드");
        		viewData.put("compLevel", "0");
        		model.addAttribute("viewData", viewData);
        		return "kp1100/kp1160RegAjax";
        	}

        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1160Ajax";
	}

    @RequestMapping(value="/kp1100/kp1160RegAjax.do")
	public String Kp1160RegAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("compSeq", cmap.getString("compSeq"));

    	if (!"".equals(cmap.getString("compSeq"))) {
        	CommonMap viewData = mkCompanyService.getMkCompanyView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1160RegAjax";
	}

    @RequestMapping(value="/kp1100/kp1160Proc.do")
	public String Kp1160Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("parentCompSeq", cmap.getString("parentCompSeq"));
	    	cmap.put("compSeq", cmap.getString("compSeq"));
	    	cmap.put("compLevel", cmap.getString("compLevel"));
	    	cmap.put("compCd", cmap.getString("compCd"));
	    	cmap.put("compName", cmap.getString("compName"));
	    	cmap.put("natnCd", cmap.getString("natnCd"));
	    	cmap.put("natnName", cmap.getString("natnName"));
	    	cmap.put("compTel", cmap.getString("compTel"));
	    	cmap.put("compMobile", cmap.getString("compMobile"));
	    	cmap.put("compFax", cmap.getString("compFax"));
	    	cmap.put("compEmail", cmap.getString("compEmail"));
	    	cmap.put("compHomepage", cmap.getString("compHomepage"));
	    	cmap.put("compAddr", cmap.getString("compAddr"));
	    	cmap.put("useYn", cmap.getString("useYn"));
	    	cmap.put("remark", cmap.getString("remark"));
	    	cmap.put("sortNum", cmap.getString("sortNum"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("parentCompSeq"))
	    			|| "".equals(cmap.getString("compLevel"))
	    			|| "".equals(cmap.getString("compCd"))
	    			|| "".equals(cmap.getString("compName"))
	    			|| "".equals(cmap.getString("useYn"))
	    			|| "".equals(cmap.getString("sortNum"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//코드중복체크
	    	CommonMap param = new CommonMap();
	    	param.put("compCd", cmap.getString("compCd"));
	    	CommonMap dupChkMap = mkCompanyService.getMkCompanyView2(param);
	    	if (!dupChkMap.isEmpty()) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 이미 등록된 코드값이 있습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	if (!"".equals(cmap.getString("compSeq"))) {
	    		//수정
	    		resultCnt = mkCompanyService.updateMkCompany(cmap);
	    	} else {
	    		//신규
	    		cmap.put("compSeq", compSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    		resultCnt = mkCompanyService.insertMkCompany(cmap);
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}

    @RequestMapping(value="/kp1100/kp1160DelProc.do")
	public String Kp1160DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("compSeq", cmap.getString("compSeq"));
	    	cmap.put("compCd", cmap.getString("compCd"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("compSeq"))
	    			|| "".equals(cmap.getString("compCd"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//조회
	    	CommonMap viewData = mkCompanyService.getMkCompanyView(cmap);

	    	//조회 결과 없을 경우
	    	if (viewData.isEmpty()) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//해당 코드를 사용하고 있는 자산이 있을 경우
	    	CommonMap cmap2 = new CommonMap();
	    	cmap2.put("sMkCompanyCd", viewData.getString("compCd"));
	    	int assetCnt = assetService.getAssetListCnt(cmap2);
	    	if (assetCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 해당 품목코드를 사용하고 있는 자산이 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//하위 코드가 있을 경우
	    	CommonMap cmap3 = new CommonMap();
	    	cmap3.put("sParentCompSeq", viewData.getString("compSeq"));
	    	int itemCnt = mkCompanyService.getMkCompanyListCnt(cmap3);
	    	if (itemCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 하위 품목코드가 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = mkCompanyService.deleteMkCompany2(cmap);

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	if (resultCnt > 0) {
	    	resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    	} else {
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
    	}

    	return resultMap.toJsonString(model);
	}

}
