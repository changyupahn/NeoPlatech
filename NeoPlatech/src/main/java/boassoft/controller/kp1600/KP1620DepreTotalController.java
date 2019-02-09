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
import boassoft.util.ExcelUtil;

@Controller
public class KP1620DepreTotalController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "DepreService")
    private DepreService depreService;

	@Resource(name = "DepreAssetService")
    private DepreAssetService depreAssetService;

	@Resource(name = "UserService")
    private UserService userService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1620DepreTotalController.class);

    @RequestMapping(value="/kp1600/kp1620.do")
	public String kp1620(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
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

    	return "kp1600/kp1620";
	}

    @RequestMapping(value="/kp1600/kp1620Ajax.do")
	public String kp1620Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	CommonList resultList = depreAssetService.getDepreAssetTotal(cmap);

    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1600/kp1620Excel.do")
	public String kp1620Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = depreAssetService.getDepreAssetTotal(cmap);

    	String[] headerListLgc1 = {"구분","당년도기초액","","당년도증가액","","당년도감소액(불용처분)","","당년도기말잔액","","감가상각","","","","잔존가액"};
    	String[] headerListLgc2 = {"","수량","기초액(a)","수량","증가(b)","수량","감소(c)","수량","금액(d=a+b-c)","전년도상각누계액(a\')","증가(b\')","감소(c\')","당년도상각누계액(d\'=a\'+b\'-c\')",""};
    	String[] headerListPhc = {"결산과목명","당년도기초수량","당년도기초금액","당년도증가수량","당년도증가금액","당년도불용수량","당년도불용금액","당년도기말수량","당년도기말금액","전년도누계감가상각비","당년도증가감가상각비","당년도감소감가상각비","당년도누계감가상각비","잔존가액"};
    	String[] headerListTyp = {"TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"15","8","20","8","20","8","20","8","20","20","20","20","20","20"};
    	String[][] mergedRegion = {
    			{"0","0","1","0"},
    			{"0","1","0","2"},
    			{"0","3","0","4"},
    			{"0","5","0","6"},
    			{"0","7","0","8"},
    			{"0","9","0","12"},
    			{"0","13","1","13"}
    	};

    	ExcelUtil.write2(request, response, resultList, "감가상각총괄표", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1600/kp1620DtlExcel.do")
	public String kp1620DtlExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	cmap.put("sDepreYear", cmap.getString("sDepreYear"));
    	cmap.put("prevDepreDt", (cmap.getInt("sDepreYear") - 1) + "1231");
    	String prevDepreDt = DateUtil.formatDate(cmap.getString("prevDepreDt"),".");

    	CommonList resultList = depreAssetService.getDepreAssetList(cmap);

    	String[] headerListLgc1 = {"자산일련번호","자산구분","품목명","상세명칭","계정번호","자산번호","부서명","자산상태","처분일자","수량","취득가액","잔존가액","상각누계액","상각누계액(폐기제외)","취득일자","내용연수","상각율","상각법",(prevDepreDt+"취득가액"),(prevDepreDt+"잔존가액"),"당기증가분(취득가액)","당기감소분(취득가액)","당기감소분(잔존가액)","합계","전년도 상각누계액","당기말 감가상각비","당기말 취득가액","당기말 잔존가액"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"assetSeq","assetTypeName","itemName","assetName","accntNum","assetNo","deptName","assetStatusCd","disuseDt","assetCnt","aqusitAmt","remainAmt","sumDepreAmt2","sumDepreAmt","aqusitDt","usefulLife","depreRate","depreCd","prevAqusitAmt","prevRemainAmt","curAqusitAmt","curDisuseAqusitAmt","curDisuseRemainAmt","prevTotal","prevDepreAmt","curDepreAmt","lastAqusitAmt","remainAmt2"};
    	String[] headerListTyp = {"NUMBER","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","DATE","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","DATE","NUMBER","TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"15","15","15","20","10","10","15","10","10","10","15","15","15","15","10","10","10","15","15","15","15","15","15","15","15","15","15","15"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "감가상각세부내역", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1600/kp1620D1Excel.do")
	public String kp1620D1Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = depreAssetService.getDepreAssetTotal(cmap);

    	String[] headerListLgc1 = {"과목","기초잔액","당기증가액","당기감소액","기말잔액","감가상각누계액","미상각잔액","비고"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"결산과목명","당년도기초금액","당년도증가금액","당년도불용금액","당년도기말금액","당년도누계감가상각비","잔존가액","비고"};
    	String[] headerListTyp = {"TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","TEXT"};
    	String[] headerListWidth = {"15","20","20","20","20","20","20","20"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "유형고정자산명세서", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1600/kp1620D2Excel.do")
	public String kp1620D2Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = depreAssetService.getDepreAssetTotal(cmap);

    	String[] headerListLgc1 = {"자산과목","취득원가(기말잔액)","감가상각비","","","상각누계액","미상각잔액","상각방법"};
    	String[] headerListLgc2 = {"","","전기분(상각누계액)","당기감소(매각등)","당기분","","",""};
    	String[] headerListPhc = {"결산과목명","당년도기말금액","전년도누계감가상각비","당년도감소감가상각비","당년도증가감가상각비","당년도누계감가상각비","잔존가액","상각방법"};
    	String[] headerListTyp = {"TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","TEXT"};
    	String[] headerListWidth = {"15","20","20","20","20","20","20","20"};
    	String[][] mergedRegion = {
    			{"0","0","1","0"}, //start:y, start:x, end:y, end:x
    			{"0","1","1","1"},
    			{"0","2","0","4"},
    			{"0","5","1","5"},
    			{"0","6","1","6"},
    			{"0","7","1","7"}
    	};

    	ExcelUtil.write2(request, response, resultList, "감가상각비명세서", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1600/kp1620D3Excel.do")
	public String kp1620D3Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = depreAssetService.getDepreAssetTotal(cmap);

    	String[] headerListLgc1 = {"자산과목","품명","수량","처분일자","처분금액","","취득금액","감가상각누계액","미상각잔액","처분손익",""};
    	String[] headerListLgc2 = {"","","","","","","","","","잉여금대체","처분이익"};
    	String[] headerListPhc = {"결산과목명","당년도불용대표품명","당년도불용수량","당년도불용대표처분일자","당년도불용대표처분방법","당년도불용처분금액","당년도불용금액","당년도감소감가상각비","당년도불용미상각잔액","잉여금대체","당년도불용처분이익"};
    	String[] headerListTyp = {"TEXT","TEXT","NUMBER","DATE","TEXT","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"15","20","10","12","12","20","20","20","20","15","15"};
    	String[][] mergedRegion = {
    			{"0","0","1","0"}, //start:y, start:x, end:y, end:x
    			{"0","1","1","1"},
    			{"0","2","1","2"},
    			{"0","3","1","3"},
    			{"0","4","1","5"},
    			{"0","6","1","6"},
    			{"0","7","1","7"},
    			{"0","8","1","8"},
    			{"0","9","0","10"}
    	};

    	ExcelUtil.write2(request, response, resultList, "고정자산처분명세서", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1600/kp1620D4Excel.do")
	public String kp1620D4Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	cmap.put("sCurDisuseYn", "Y");

    	CommonList resultList = depreAssetService.getDepreAssetList(cmap);

    	String[] headerListLgc1 = {"자산과목","구분","계정번호","자산번호","부서명","취득일자","내용연수","취득금액(원)","상각누계(원)","당기상각액(원)","잔존금액(원)","품명","처분일자","처분금액(원)","처분손익","비고"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"assetTypeName","disuseTypeCd","accntNum","assetNo","deptName","aqusitDt","usefulLife","aqusitAmt","sumDepreAmt2","curDepreAmt","remainAmt","itemName","disuseDt","disuseAmt","disuseGainAmt","disuseCont"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","DATE","NUMBER","NUMBER","NUMBER","NUMBER","NUMBER","TEXT","DATE","NUMBER","NUMBER","TEXT"};
    	String[] headerListWidth = {"15","10","10","10","15","12","10","15","15","15","15","15","15","15","15","20"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "고정자산처분현황", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
