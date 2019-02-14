package boassoft.controller.kp1300;


import java.net.URLEncoder;

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

import boassoft.service.AssetAsService;
import boassoft.service.AssetOperListService;
import boassoft.service.AssetService;
import boassoft.service.UserService;
import boassoft.service.ZeusAsListService;
//import boassoft.service.ZeusAsListService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;
import boassoft.util.HttpZeusUtil;
import boassoft.util.PagingUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;

@Controller
public class KP1313AssetOperController {

	
	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "assetOperListService")
    private AssetOperListService assetOperListService;

	@Resource(name = "zeusAsListService")
    private ZeusAsListService zeusAsListService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	@Resource(name = "userService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1313AssetOperController.class);

    @RequestMapping(value="/kp1300/kp1313Oper.do")
	public String kp1313Oper(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1313Oper";
	}

    @RequestMapping(value="/kp1300/kp1313OperAjax.do")
	public String kp1313OperAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "9999");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = assetOperListService.getAssetOperListList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1300/kp1313OperWrite.do")
	public String kp1313OperWrite(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("assetSeq", cmap.getString("assetSeq"));

    	//자산상세
    	CommonMap viewData = assetService.getAssetDetail(cmap);
    	model.addAttribute("viewData", viewData);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1313OperWrite";
	}

    @RequestMapping(value="/kp1300/kp1313OperWriteProc.do")
	public String kp1313OperWriteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("assetSeq", cmap.getString("assetSeq").replaceAll("\\D", ""));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("assetSeq"))
	    			|| "".equals(cmap.getString("registrentNm"))
	    			|| "".equals(cmap.getString("journaluserNm"))
	    			|| "".equals(cmap.getString("journalSdt"))
	    			|| "".equals(cmap.getString("journalShour"))
	    			|| "".equals(cmap.getString("journalSminute"))
	    			|| "".equals(cmap.getString("journalEdt"))
	    			|| "".equals(cmap.getString("journalEhour"))
	    			|| "".equals(cmap.getString("journalEminute"))
	    			|| "".equals(cmap.getString("useOrganClassCd"))
	    			|| "".equals(cmap.getString("useOrganNm"))
	    			|| "".equals(cmap.getString("useDeptNm"))
	    			|| "".equals(cmap.getString("sampleCnt"))
	    			|| "".equals(cmap.getString("inputManHour"))
	    			|| "".equals(cmap.getString("journalPrc"))
	    			|| "".equals(cmap.getString("useTypeCd"))
	    			|| "".equals(cmap.getString("useOrganTypeCd"))
	    			|| "".equals(cmap.getString("useScopeCd"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//저장
	    	resultCnt = assetOperListService.insertAssetOperList(cmap);

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

    @RequestMapping(value="/kp1300/kp1313OperDelProc.do")
	public String kp1313OperDelProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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
	    	cmap.put("assetSeq", cmap.getString("assetSeq").replaceAll("\\D", ""));
	    	cmap.put("operSeq", cmap.getString("operSeq").replaceAll("\\D", ""));
	    	cmap.put("frstRegisterId", cmap.getString("ssUserNo"));
	    	cmap.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    	//필수 파라미터 체크
	    	if ("".equals(cmap.getString("assetSeq"))
	    			|| "".equals(cmap.getString("operSeq"))
	    			) {
	    		resultMap.put("ret", "ERR");
	    		resultMap.put("retmsg", "필수 입력값이 누락되었습니다.");
	    		return resultMap.toJsonString(model);
	    	}

	    	//삭제
	    	resultCnt = assetOperListService.deleteAssetOperList(cmap);

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

    @RequestMapping(value="/kp1300/kp1313OperTab.do")
	public String kp1313OperTab(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1313OperTab";
	}

    @RequestMapping(value="/kp1800/kp1813OperExcel.do")
	public String kp1813OperExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	//CommonList resultList = zeusStatService.getZeusEquipStatList(cmap);
    	CommonList resultList = new CommonList();

    	//순번(rnum) 붙이기
    	CommonList tempList = PagingUtil.setPagingRnum(resultList, cmap);

    	String[] headerListLgc1 = {"순번","장비명","자산번호","장비등록번호","취득일자","운영시간","운영일","유지보수시간","유지일"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"rnum","korNm","assetNo","equipNo","takeDt","operHour","operDate","asHour","asDate"};
    	String[] headerListTyp = {"DEFAULT","TEXT","TEXT","TEXT","TEXT","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"8","20","15","17","10","8","8","8","8"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, tempList, "ZEUS장비별운영현황("+cmap.getString("sYear")+"년도)", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
