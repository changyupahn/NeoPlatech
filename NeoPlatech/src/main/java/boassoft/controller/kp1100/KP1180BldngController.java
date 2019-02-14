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
import boassoft.service.CommonCodeService;
import boassoft.service.BldngService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1180BldngController {

	
	@Resource(name = "bldngService")
    private BldngService bldngService;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "bldngSeqIdGnrService")
    private EgovIdGnrService bldngSeqIdGnrService;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "commonCodeService")
    private CommonCodeService commonCodeService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1180BldngController.class);

    @RequestMapping(value="/kp1100/kp1180.do")
	public String kp1180(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");
    	cmap.put("sBldngType", cmap.getString("sBldngType", "DEFAULT"));

    	CommonList dataList = bldngService.getBldngList(cmap);
    	model.addAttribute("dataList", dataList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1100/kp1180";
	}

    @RequestMapping(value="/kp1100/kp1180Ajax.do")
	public String Kp1180Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("bldngSeq", cmap.getString("bldngSeq"));

    	if (!"".equals(cmap.getString("bldngSeq"))) {
        	CommonMap viewData = bldngService.getBldngView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1180Ajax";
	}

    @RequestMapping(value="/kp1100/kp1180RegAjax.do")
	public String Kp1180RegAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("bldngSeq", cmap.getString("sBldngSeq"));

    	if (!"".equals(cmap.getString("bldngSeq"))) {
        	CommonMap viewData = bldngService.getBldngView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1180RegAjax";
	}

    @RequestMapping(value="/kp1100/kp1180Proc.do")
	public String Kp1180Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("parentBldngSeq", cmap.getString("parentBldngSeq"));
	    	cmap.put("bldngSeq", cmap.getString("bldngSeq"));
	    	cmap.put("bldngNo", cmap.getString("bldngNo").toUpperCase().replaceAll("[^0-9]", ""));
	    	cmap.put("bldngLevel", cmap.getString("bldngLevel"));
	    	cmap.put("bldngName", cmap.getString("bldngName"));
	    	cmap.put("useYn", cmap.getString("useYn"));
	    	cmap.put("remark", cmap.getString("remark"));
	    	cmap.put("sortNum", cmap.getString("sortNum"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));
	    	
	    	cmap.put("bldngLevel", "1");

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("bldngNo"))
	    			|| "".equals(cmap.getString("bldngLevel"))
	    			|| "".equals(cmap.getString("bldngName"))
	    			|| "".equals(cmap.getString("useYn"))
	    			|| "".equals(cmap.getString("sortNum"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}
	    	
	    	//장소코드 체크
	    	if (cmap.getString("bldngNo").length() != 2) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "장소코드는 숫자 2자리로 입력해주세요.");
	    		return resultMap.toJsonString(model);
	    	}

	    	if (!"".equals(cmap.getString("bldngSeq"))) {
	    		//수정
	    		resultCnt = bldngService.updateBldng(cmap);
	    	} else {
	    		//신규
	    		cmap.put("bldngSeq", bldngSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    		resultCnt = bldngService.insertBldng(cmap);
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

    @RequestMapping(value="/kp1100/kp1180DelProc.do")
	public String Kp1180DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("bldngSeq", cmap.getString("bldngSeq"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("bldngSeq"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//조회
	    	CommonMap viewData = bldngService.getBldngView(cmap);

	    	//조회 결과 없을 경우
	    	if (viewData.isEmpty()) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//해당 장소코드를 사용하고 있는 자산이 있을 경우
	    	CommonMap cmap2 = new CommonMap();
	    	cmap2.put("sBldngNo", viewData.getString("bldngNo"));
	    	int assetCnt = assetService.getAssetListCnt(cmap2);
	    	if (assetCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 해당 장소코드를 사용하고 있는 자산이 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//하위 품목코드가 있을 경우
	    	CommonMap cmap3 = new CommonMap();
	    	cmap3.put("sParentBldngSeq", viewData.getString("bldngSeq"));
	    	int bldngCnt = bldngService.getBldngListCnt(cmap3);
	    	if (bldngCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 하위 장소코드가 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = bldngService.deleteBldng2(cmap);

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
