package boassoft.controller.kp1200;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.CommonCodeService;
import boassoft.service.ContractDtlService;
import boassoft.service.ContractService;
import boassoft.service.InspItemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.StringUtil;

@Controller
public class KP1211InspItemController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "contractService")
    private ContractService contractService;

	@Resource(name = "contractDtlService")
    private ContractDtlService contractDtlService;

	@Resource(name = "inspItemService")
    private InspItemService inspItemService;

	@Resource(name = "commonCodeService")
    private CommonCodeService commonCodeService;

	@Resource(name = "userService")
    private UserService userService;



	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1211InspItemController.class);

    /** 납품내역 */
    @RequestMapping(value="/kp1200/kp1211.do")
	public String kp1211(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//계약정보
    	CommonMap viewData = contractService.getContractView(cmap);
    	model.addAttribute("viewData",viewData);

    	//공통코드
    	model.addAttribute("com001Str", commonCodeService.getCommonCodeJqGridStr("COM001")); //상각법
    	model.addAttribute("com002Str", commonCodeService.getCommonCodeJqGridStr("COM002")); //사용여부
    	model.addAttribute("com005Str", commonCodeService.getCommonCodeJqGridStr("COM005")); //활용범위
    	model.addAttribute("com006Str", commonCodeService.getCommonCodeJqGridStr("COM006")); //취득방법
    	model.addAttribute("com013Str", commonCodeService.getCommonCodeJqGridStr("COM013")); //통화단위

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);


    	return "kp1200/kp1211";
	}

    /** 납품내역 상세 목록 */
    @RequestMapping(value="/kp1200/kp1211Ajax.do")
	public String kp1211Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = inspItemService.getInspItemList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    /** 납품내역 저장 */
    @RequestMapping(value="/kp1200/kp1211Proc.do")
	public String Kp1211Proc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("purno", cmap.getString("purno"));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("purno"))
	    			|| "".equals(cmap.getString("saveJsonArray"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("inspItemSeq", obj.get("inspItemSeq"));
	    		param.put("purno", obj.get("purno"));
	    		param.put("rqstno", obj.get("rqstno"));
	    		param.put("prodno", obj.get("prodno"));
	    		param.put("assetTypeCd", obj.get("assetTypeCd"));
	    		param.put("itemCd", obj.get("itemCd"));
	    		param.put("itemName", obj.get("itemName"));
	    		param.put("zeusItemCd", obj.get("zeusItemCd"));
	    		param.put("zeusItemName", obj.get("zeusItemName"));
	    		param.put("assetName", obj.get("assetName"));
	    		param.put("assetEname", obj.get("assetEname"));
	    		param.put("mkNationCd", obj.get("mkNationCd"));
	    		param.put("mkNationName", obj.get("mkNationName"));
	    		param.put("mkCompanyCd", obj.get("mkCompanyCd"));
	    		param.put("mkCompanyName", obj.get("mkCompanyName"));
	    		param.put("slCompanyCd", obj.get("slCompanyCd"));
	    		param.put("slCompanyName", obj.get("slCompanyName"));
	    		param.put("assetCnt", obj.get("assetCnt"));
	    		param.put("assetUnitCd", obj.get("assetUnitCd"));
	    		param.put("aqusitUnitAmt", obj.get("aqusitUnitAmt"));
	    		param.put("aqusitAmt", obj.get("aqusitAmt"));
	    		param.put("aqusitForeignAmt", StringUtil.nvl(obj.get("aqusitForeignAmt"), null));
	    		param.put("capiTypeCd", obj.get("capiTypeCd"));
	    		param.put("accntNum", obj.get("accntNum"));
	    		param.put("usefulLife", obj.get("usefulLife"));
	    		param.put("depreCd", obj.get("depreCd"));
	    		param.put("assetSize", obj.get("assetSize"));
	    		param.put("aqusitTypeCd", obj.get("aqusitTypeCd"));
	    		param.put("purposeOfUse", obj.get("purposeOfUse"));
	    		param.put("swYn", obj.get("swYn"));
	    		param.put("zeusYn", obj.get("zeusYn"));
	    		param.put("etubeYn", obj.get("etubeYn"));
	    		//param.put("aplctnRangeCd", obj.get("aplctnRangeCd"));
	    		param.put("aplctnRangeCd", "단독활용"); //DEFAULT 단독활용으로 하고 제우스 연계 업데이트

	    		//숫자형 체크
	    		param.put("assetCnt", param.getString("assetCnt").replaceAll("\\D",""));
	    		param.put("aqusitUnitAmt", param.getString("aqusitUnitAmt").replaceAll("\\D",""));
	    		param.put("aqusitAmt", param.getString("aqusitAmt").replaceAll("\\D",""));
	    		param.put("usefulLife", param.getString("usefulLife").replaceAll("\\D",""));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("purno"))
		    			|| "".equals(param.getString("rqstno"))
		    			|| "".equals(param.getString("prodno"))
		    			|| "".equals(param.getString("assetTypeCd"))
		    			|| "".equals(param.getString("itemCd"))
		    			|| "".equals(param.getString("itemName"))
		    			|| "".equals(param.getString("assetName"))
		    			|| "".equals(param.getString("assetCnt"))
		    			|| "".equals(param.getString("aqusitUnitAmt"))
		    			|| "".equals(param.getString("aqusitAmt"))
		    			|| "".equals(param.getString("usefulLife"))
		    			|| "".equals(param.getString("depreCd"))
		    			|| "".equals(param.getString("aqusitTypeCd"))
		    			|| "".equals(param.getString("swYn"))
		    			|| "".equals(param.getString("zeusYn"))
		    			|| "".equals(param.getString("etubeYn"))
		    			|| "".equals(param.getString("aplctnRangeCd"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//저장
	    	resultCnt = inspItemService.insertInspItemAll(cmap, paramList);

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

    /** 납품내역 삭제 */
    @RequestMapping(value="/kp1200/kp1211DelProc.do")
	public String Kp1211DelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("purno", cmap.getString("purno"));
	    	cmap.put("saveJsonArray", cmap.getString("saveJsonArray", false));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("purno"))
	    			|| "".equals(cmap.getString("saveJsonArray"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	JSONArray saveJsonArray = JSONArray.fromObject(cmap.getString("saveJsonArray", "[]", false));
	    	CommonList paramList = new CommonList();
	    	for (int i=0; i<saveJsonArray.size(); i++) {
	    		JSONObject obj = saveJsonArray.getJSONObject(i);
	    		CommonMap param = new CommonMap();
	    		param.put("inspItemSeq", obj.get("inspItemSeq"));
	    		param.put("purno", obj.get("purno"));
	    		param.put("rqstno", obj.get("rqstno"));
	    		param.put("prodno", obj.get("prodno"));

	    		//필수 파라미터 체크
		    	if ("".equals(param.getString("purno"))
		    			|| "".equals(param.getString("rqstno"))
		    			|| "".equals(param.getString("prodno"))
		    			) {
		    		resultMap.put("ret", "ERR");
		    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
		    		return resultMap.toJsonString(model);
		    	}

		    	paramList.add(param);
	    	}

	    	//삭제
	    	resultCnt = inspItemService.deleteInspItem3All(cmap, paramList);

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
