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
import boassoft.service.MkNationService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1150MkNationController {

	@Resource(name = "mkNationService")
    private MkNationService mkNationService;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "natnSeqIdGnrService")
    private EgovIdGnrService natnSeqIdGnrService;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1150MkNationController.class);

    @RequestMapping(value="/kp1100/kp1150.do")
	public String kp1150(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList dataList = mkNationService.getMkNationList(cmap);
    	model.addAttribute("dataList", dataList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1100/kp1150";
	}

    @RequestMapping(value="/kp1100/kp1150Ajax.do")
	public String Kp1110Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("natnSeq", cmap.getString("natnSeq"));

    	if (!"".equals(cmap.getString("natnSeq"))) {
        	CommonMap viewData = mkNationService.getMkNationView(cmap);

        	if (viewData.isEmpty()) {
        		//ROOT
        		viewData.put("natnSeq", "0");
        		viewData.put("natnCd", "0");
        		viewData.put("natnName", "제조국코드");
        		viewData.put("natnLevel", "0");
        		model.addAttribute("viewData", viewData);
        		return "kp1100/kp1150RegAjax";
        	}

        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1150Ajax";
	}

    @RequestMapping(value="/kp1100/kp1150RegAjax.do")
	public String Kp1110RegAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("natnSeq", cmap.getString("sNatnSeq"));

    	if (!"".equals(cmap.getString("natnSeq"))) {
        	CommonMap viewData = mkNationService.getMkNationView(cmap);
        	model.addAttribute("viewData", viewData);
    	}

    	return "kp1100/kp1150RegAjax";
	}

    @RequestMapping(value="/kp1100/kp1150Proc.do")
	public String Kp1110Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	if ("".equals(cmap.getString("parentNatnSeq"))
	    			|| "".equals(cmap.getString("natnCd"))
	    			|| "".equals(cmap.getString("natnLevel"))
	    			|| "".equals(cmap.getString("natnName"))
	    			|| "".equals(cmap.getString("useYn"))
	    			|| "".equals(cmap.getString("sortNum"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	if (!"".equals(cmap.getString("natnSeq"))) {
	    		//수정
	    		resultCnt = mkNationService.updateMkNation(cmap);
	    	} else {
	    		//신규
	    		cmap.put("natnSeq", natnSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
	    		resultCnt = mkNationService.insertMkNation(cmap);
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

    @RequestMapping(value="/kp1100/kp1150DelProc.do")
	public String Kp1110DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("natnSeq", cmap.getString("natnSeq"));
	    	cmap.put("natnCd", cmap.getString("natnCd"));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("natnSeq"))
	    			|| "".equals(cmap.getString("natnCd"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//조회
	    	CommonMap viewData = mkNationService.getMkNationView(cmap);

	    	//조회 결과 없을 경우
	    	if (viewData.isEmpty()) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "처리 중 오류가 발생하였습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//해당 코드를 사용하고 있는 자산이 있을 경우
	    	CommonMap cmap2 = new CommonMap();
	    	cmap2.put("sMkNationCd", viewData.getString("natnCd"));
	    	int assetCnt = assetService.getAssetListCnt(cmap2);
	    	if (assetCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 해당 품목코드를 사용하고 있는 자산이 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//하위 코드가 있을 경우
	    	CommonMap cmap3 = new CommonMap();
	    	cmap3.put("sParentNatnSeq", viewData.getString("natnSeq"));
	    	int itemCnt = mkNationService.getMkNationListCnt(cmap3);
	    	if (itemCnt > 0) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "[오류] 하위 품목코드가 있어 삭제할 수 없습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = mkNationService.deleteMkNation2(cmap);

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
