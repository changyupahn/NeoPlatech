package boassoft.controller.kp1600;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.DepreAssetService;
import boassoft.service.DepreService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.SessionUtil;

@Controller
public class KP1610DepreController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "DepreService")
    private DepreService depreService;

	@Resource(name = "DepreAssetService")
    private DepreAssetService depreAssetService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1610DepreController.class);

    @RequestMapping(value="/kp1600/kp1610.do")
	public String kp1610(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//마지막 상각월 조회
    	CommonMap maxDepreView = depreService.getMaxDepreView(cmap);
    	cmap.put("depreDt", maxDepreView.getString("depreDt").replaceAll("\\D", ""));

    	//감가 상각 내역이 없으면 취득일이 가장 예전것을 가져옴.
    	if (maxDepreView.isEmpty()) {
    		CommonMap minAqusitDtView = depreService.getMinAqusitDt(cmap);
    		cmap.put("depreDt", minAqusitDtView.getString("aqusitDt").replaceAll("\\D", ""));
    	}

    	if (cmap.getString("depreDt").length() != 8) {
    		cmap.put("depreDt", "19560101");
    	}

    	cmap.put("depreYear", cmap.getString("depreDt").substring(0,4));
    	cmap.put("depreMonth", cmap.getString("depreDt").substring(4,6));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1600/kp1610";
	}

    @RequestMapping(value="/kp1600/kp1610Ajax.do")
	public String kp1610Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = depreService.getDepreList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1600/kp1610Proc.do")
	public String kp1610Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("depreYear", cmap.getString("depreYear").replaceAll("\\D", ""));
        	cmap.put("depreMonth", cmap.getString("depreMonth").replaceAll("\\D", ""));
        	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

        	//필수 파라미터 체크
        	if ("".equals(cmap.getString("depreYear"))
        			|| "".equals(cmap.getString("depreMonth"))
        			|| cmap.getString("depreYear").length() != 4
        			|| cmap.getString("depreMonth").length() != 2
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}

        	//2017년 이전 감가상각내역 삭제 금지 (맞춰놓은 금액)
        	if (cmap.getInt("depreYear") < 2018) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "2017년 이전 감가상각내역은 삭제할 수 없습니다.");
        		return resultMap.toJsonString(model);
        	}

        	String yyyy = cmap.getString("depreYear");
        	String yyyyMM = cmap.getString("depreYear") + cmap.getString("depreMonth");

        	cmap.put("curDepreDt", yyyyMM + DateUtil.getLastDateOfMonth(yyyyMM));
        	cmap.put("curDepreYear", yyyy);
        	cmap.put("minimumRemainAmt", 0); //잔존가 설정 현재 0원으로 고정

        	//마지막 상각월 조회
        	CommonMap maxDepreView = depreService.getMaxDepreView(cmap);
        	cmap.put("tmpDepreDt", maxDepreView.getString("depreDt").replaceAll("\\D", ""));

        	//감가 상각 내역이 없으면 취득일이 가장 예전것을 가져옴.
        	if (maxDepreView.isEmpty()) {
        		CommonMap minAqusitDtView = depreService.getMinAqusitDt(cmap);
        		cmap.put("tmpDepreDt", minAqusitDtView.getString("aqusitDt").replaceAll("\\D", ""));
        	} else {
        		//상각 내역이 있으면 월 + 1
        		String tmpDepreDt = DateUtil.addMonth(cmap.getString("tmpDepreDt").substring(0,6)+"01", 1);
            	String tmp_yyyyMM = tmpDepreDt.substring(0,6);
            	tmpDepreDt = tmp_yyyyMM + DateUtil.getLastDateOfMonth(tmp_yyyyMM);
            	cmap.put("tmpDepreDt", tmpDepreDt);
        	}

        	if (cmap.getString("tmpDepreDt").length() != 8) {
        		cmap.put("tmpDepreDt", "19560101");
        	}

        	String tmp_yyyyMM = cmap.getString("tmpDepreDt").substring(0,6);
        	String tmpDepreDt = tmp_yyyyMM + DateUtil.getLastDateOfMonth(tmp_yyyyMM);
        	cmap.put("tmpDepreDt", tmpDepreDt);

        	while (cmap.getInt("tmpDepreDt") <= cmap.getInt("curDepreDt")) {
        		cmap.put("depreDt", cmap.getString("tmpDepreDt"));
        		cmap.put("depreYear", cmap.getString("tmpDepreDt").substring(0,4));

        		//기존 상각 정보 삭제
        		depreAssetService.deleteDepreAsset2(cmap);
        		depreService.deleteDepre2(cmap);

        		//상각 마스터 정보
        		cmap.put("depreMonth", cmap.getString("tmpDepreDt").substring(0,4)
            			+ "년"
            			+ cmap.getString("tmpDepreDt").substring(4,6)
            			+ "월");
        		cmap.put("depreCnt", depreAssetService.getDepreAssetTargetCnt(cmap));
        		depreService.insertDepre(cmap);

        		//상각 처리 (월별)
        		cmap.put("depreMonth", cmap.getString("tmpDepreDt").substring(4,6));
            	resultCnt += depreAssetService.insertDepreAsset(cmap);

            	//잔존가 업데이트
            	depreAssetService.updateDepreAssetRemain(cmap);

            	//loop : temp 값 세팅
            	tmpDepreDt = DateUtil.addMonth(tmp_yyyyMM+"01", 1);
            	tmp_yyyyMM = tmpDepreDt.substring(0,6);
            	tmpDepreDt = tmp_yyyyMM + DateUtil.getLastDateOfMonth(tmp_yyyyMM);
            	cmap.put("tmpDepreDt", tmpDepreDt);

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

    @RequestMapping(value="/kp1600/kp1610DelProc.do")
	public String kp1610DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
    		cmap.put("depreDt", cmap.getString("depreDt").replaceAll("\\D", ""));
        	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

        	//필수 파라미터 체크
        	if ("".equals(cmap.getString("depreDt"))
        			|| cmap.getString("depreDt").length() != 8
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}

        	//2017년 이전 감가상각내역 삭제 금지 (맞춰놓은 금액)
        	if (cmap.getInt("depreDt") < 20180101) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "2017년 이전 감가상각내역은 삭제할 수 없습니다.");
        		return resultMap.toJsonString(model);
        	}

        	//잔존가 복구
        	depreAssetService.updateDepreAssetRemain2(cmap);
        	//감가상각_자산내역 삭제
        	depreAssetService.deleteDepreAsset2(cmap);
        	//감가상각_마스터 삭제
    		resultCnt = depreService.deleteDepre2(cmap);

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

    @RequestMapping(value="/kp1600/kp1610DelYearProc.do")
	public String kp1610DelYearProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();
    	int resultCnt = 0;

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

    		//마지막 상각월 조회
        	CommonMap maxDepreView = depreService.getMaxDepreView(cmap);
        	cmap.put("depreDt", maxDepreView.getString("depreDt").replaceAll("\\D", ""));

        	//파라미터
    		cmap.put("depreDt", cmap.getString("depreDt").replaceAll("\\D", ""));
        	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

        	//필수 파라미터 체크
        	if ("".equals(cmap.getString("depreDt"))
        			|| cmap.getString("depreDt").length() != 8
        			) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
        		return resultMap.toJsonString(model);
        	}

        	//2017년 이전 감가상각내역 삭제 금지 (맞춰놓은 금액)
        	if (cmap.getInt("depreDt") < 20180101) {
        		resultMap.put("ret", "ERR");
        		resultMap.put("retmsg", "2017년 이전 감가상각내역은 삭제할 수 없습니다.");
        		return resultMap.toJsonString(model);
        	}

        	String depreDt = cmap.getString("depreDt");
        	String depreLastYear = cmap.getString("depreDt").substring(0,4);

        	//잔존가 복구
        	cmap.put("depreDt", depreDt);
        	cmap.put("depreLastYear", depreLastYear);
        	depreAssetService.updateDepreAssetRemain2Year(cmap);
        	//감가상각_자산내역 삭제
        	cmap.put("depreDt", "");
        	cmap.put("depreLastYear", depreLastYear);
        	depreAssetService.deleteDepreAsset2(cmap);
        	//감가상각_마스터 삭제
        	cmap.put("depreDt", "");
        	cmap.put("depreLastYear", depreLastYear);
    		resultCnt = depreService.deleteDepre2(cmap);

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
