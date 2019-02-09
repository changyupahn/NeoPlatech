package boassoft.controller.kp1800;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.service.AssetService;
import boassoft.service.BatchService;
import boassoft.service.UserService;
import boassoft.service.ZeusCodeService;
import boassoft.service.ZeusService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.HttpZeusUtil;
import boassoft.util.SessionUtil;
import egovframework.com.cmm.service.EgovProperties;

@Controller
public class KP1810ZeusController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "ZeusService")
    private ZeusService zeusService;

	@Resource(name = "ZeusCodeService")
    private ZeusCodeService zeusCodeService;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "BatchService")
    private BatchService batchService;

	@Resource(name = "AssetService")
    private AssetService assetService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1810ZeusController.class);

    @RequestMapping(value="/kp1800/kp1810.do")
	public String kp1810(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

//    	batchService.syncUser(cmap);
//    	batchService.syncDept(cmap);
//    	batchService.syncCust(cmap);
//    	batchService.syncContr(cmap);
//    	batchService.syncContrdtl(cmap);
//    	batchService.syncMisDoc(cmap);
//    	batchService.syncMisDocAppr(cmap);
//
//    	batchService.syncZeusAsset(cmap);
//    	batchService.syncZeusToAsset(cmap);
//    	batchService.syncZeusCode(cmap);
//
//    	batchService.syncZeusAs(cmap);
//    	batchService.syncZeusOper(cmap);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1810";
	}

    @RequestMapping(value="/kp1800/kp1810Tab.do")
	public String kp1810Tab(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1810Tab";
	}

    @RequestMapping(value="/kp1800/kp1810Ajax.do")
	public String kp1810Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
//    	cmap.put("dataOrder", cmap.getString("dataOrder", "registDt"));
//    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "DESC"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	String ssGrantRead = cmap.getString("ssGrantRead");
    	String keywords = "";

    	if ("GRANT_MGR".equals(ssGrantRead)) {
    		keywords = cmap.getString("keywords");
    	} else {
    		keywords = SessionUtil.getString("userEmail");
    		cmap.put("keywords", keywords);

    		if ("".equals(keywords)) {
    			keywords = "99999999@99999999";
    		}
    	}

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s?page=%s&pageSize=%s&keywords=%s"
				, key
				, cmap.getString("pageIdx","1")
				, cmap.getString("pageSize","50")
				//, URLEncoder.encode(cmap.getString("keywords"), "utf-8")
				, URLEncoder.encode(keywords, "utf-8")
				);
		String resultStr = HttpZeusUtil.get(url);

		//결과 JSON
		//resultStr = "{\"fixedAsetNo\":null,\"equipNm\":null,\"registId\":null,\"registNm\":null,\"equipNo\":null,\"confirmYn\":null,\"deleteYn\":null,\"keywords\":null,\"subjects\":null,\"orgMgntNoYn\":null,\"orgMgntNo\":null,\"sort\":\"equipId\",\"sortOrder\":\"desc\",\"total\":759,\"page\":1,\"pageCount\":380,\"pageSize\":2,\"firstLinkedPage\":1,\"lastLinkedPage\":10,\"pageList\":[{\"equipId\":\"20171130000000212606\",\"equipCd\":\"01\",\"equipNo\":\"NFEC-2017-11-240982\",\"fixedAsetNo\":\"21B70634C\",\"korNm\":\"지구물리 탐사용 다용도 멀티채널 전자기 데이터 획득 시스템\",\"engNm\":\"Geophysical multifunctional multichannel  EM Data Acquisition System\",\"confirmYn\":\"Y\",\"deleteYn\":\"N\",\"statusCd\":\"06\",\"statusNm\":\"완료\",\"registDt\":\"2017-11-30\",\"modifyDt\":null,\"registId\":\"hkjung@kigam.re.kr\",\"useScopeCd\":\"2\",\"useScopeNm\":\"공동활용허용가능\",\"idleDisuseCd\":\"1\",\"idleDisuseNm\":\"활용\",\"organCd\":null,\"organNm\":\"한국지질자원연구원\",\"rndYn\":\"Y\",\"rndNm\":\"R&D 과제(연구개발사업 조사분석대상)\",\"registNm\":\"정현기\",\"apiYn\":\"N\",\"orgMgntNo\":null},{\"equipId\":\"20171130000000212596\",\"equipCd\":\"01\",\"equipNo\":\"NFEC-2017-11-240968\",\"fixedAsetNo\":\"21B70631C\",\"korNm\":\"비드제작용 전기로\",\"engNm\":\"Fusion machine\",\"confirmYn\":\"Y\",\"deleteYn\":\"N\",\"statusCd\":\"06\",\"statusNm\":\"완료\",\"registDt\":\"2017-11-30\",\"modifyDt\":null,\"registId\":\"ymk@kigam.re.kr\",\"useScopeCd\":\"1\",\"useScopeNm\":\"단독활용만 가능\",\"idleDisuseCd\":\"1\",\"idleDisuseNm\":\"활용\",\"organCd\":null,\"organNm\":\"한국지질자원연구원\",\"rndYn\":\"Y\",\"rndNm\":\"R&D 과제(연구개발사업 조사분석대상)\",\"registNm\":\"양명권\",\"apiYn\":\"N\",\"orgMgntNo\":null}],\"errors\":[]}";

    	model.addAttribute("printString", resultStr);

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1800/kp1810Excel.do")
	public String kp1810Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", "1");
    	cmap.put("pageSize", "0");

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	String ssGrantRead = cmap.getString("ssGrantRead");
    	String keywords = "";

    	if ("GRANT_MGR".equals(ssGrantRead)) {
    		keywords = cmap.getString("keywords");
    	} else {
    		keywords = SessionUtil.getString("userEmail");
    		cmap.put("keywords", keywords);
    		cmap.put("pageSize", "100");

    		if ("".equals(keywords)) {
    			keywords = "99999999@99999999";
    		}
    	}

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s?page=%s&pageSize=%s&keywords=%s"
				, key
				, cmap.getString("pageIdx","1")
				, cmap.getString("pageSize","50")
				//, URLEncoder.encode(cmap.getString("keywords"), "utf-8")
				, URLEncoder.encode(keywords, "utf-8")
				);
		String resultStr = HttpZeusUtil.get(url);

		JSONObject resultObject =  JSONObject.fromObject(resultStr);
		JSONArray jsonArray = resultObject.getJSONArray("pageList");
		CommonList resultList = new CommonList();
		for (int i=0; i<jsonArray.size(); i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			CommonMap result = new CommonMap();
			result.put("equipId", jsonObj.getString("equipId"));
			result.put("equipNo", jsonObj.getString("equipNo"));
			result.put("fixedAsetNo", jsonObj.getString("fixedAsetNo"));
			result.put("korNm", jsonObj.getString("korNm"));
			result.put("engNm", jsonObj.getString("engNm"));
			result.put("statusCd", jsonObj.getString("statusCd"));
			result.put("statusNm", jsonObj.getString("statusNm"));
			result.put("useScopeNm", jsonObj.getString("useScopeNm"));
			result.put("idleDisuseNm", jsonObj.getString("idleDisuseNm"));
			result.put("organNm", jsonObj.getString("organNm"));
			result.put("rndNm", jsonObj.getString("rndNm"));
			result.put("confirmYn", jsonObj.getString("confirmYn"));
			result.put("deleteYn", jsonObj.getString("deleteYn"));
			result.put("registId", jsonObj.getString("registId"));
			result.put("registNm", jsonObj.getString("registNm"));
			result.put("registDt", jsonObj.getString("registDt"));
			result.put("apiYn", jsonObj.getString("apiYn"));
			resultList.add(result);
		}

    	String[] headerListLgc1 = {"장비아이디","장비등록번호","자산번호","한글장비명","영문장비명","장비상태코드","장비상태","활용범위","활용상태","기관명","재원구분","승인여부","삭제여부","등록자아이디","등록자성명","등록일자","API등록"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"equipId","equipNo","fixedAsetNo","korNm","engNm","statusCd","statusNm","useScopeNm","idleDisuseNm","organNm","rndNm","confirmYn","deleteYn","registId","registNm","registDt","apiYn"};
    	String[] headerListTyp = {"TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT","TEXT"};
    	String[] headerListWidth = {"18","18","15","20","20","12","12","12","12","15","12","10","10","10","10","10","10"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, resultList, "ZEUS장비목록", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1800/kp1810Read.do")
	public String kp1810Read(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1810Read";
	}

    @RequestMapping(value="/kp1800/kp1810ReadAjax.do")
	public String kp1810ReadAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("equipId", cmap.getString("equipId"));

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s"
				, cmap.getString("equipId")
				, key
				);
		String resultStr = HttpZeusUtil.get(url);

		//결과 JSON
		//resultStr = "{\"fixedAsetNo\":null,\"equipNm\":null,\"registId\":null,\"registNm\":null,\"equipNo\":null,\"confirmYn\":null,\"deleteYn\":null,\"keywords\":null,\"subjects\":null,\"orgMgntNoYn\":null,\"orgMgntNo\":null,\"sort\":\"equipId\",\"sortOrder\":\"desc\",\"total\":759,\"page\":1,\"pageCount\":380,\"pageSize\":2,\"firstLinkedPage\":1,\"lastLinkedPage\":10,\"pageList\":[{\"equipId\":\"20171130000000212606\",\"equipCd\":\"01\",\"equipNo\":\"NFEC-2017-11-240982\",\"fixedAsetNo\":\"21B70634C\",\"korNm\":\"지구물리 탐사용 다용도 멀티채널 전자기 데이터 획득 시스템\",\"engNm\":\"Geophysical multifunctional multichannel  EM Data Acquisition System\",\"confirmYn\":\"Y\",\"deleteYn\":\"N\",\"statusCd\":\"06\",\"statusNm\":\"완료\",\"registDt\":\"2017-11-30\",\"modifyDt\":null,\"registId\":\"hkjung@kigam.re.kr\",\"useScopeCd\":\"2\",\"useScopeNm\":\"공동활용허용가능\",\"idleDisuseCd\":\"1\",\"idleDisuseNm\":\"활용\",\"organCd\":null,\"organNm\":\"한국지질자원연구원\",\"rndYn\":\"Y\",\"rndNm\":\"R&D 과제(연구개발사업 조사분석대상)\",\"registNm\":\"정현기\",\"apiYn\":\"N\",\"orgMgntNo\":null},{\"equipId\":\"20171130000000212596\",\"equipCd\":\"01\",\"equipNo\":\"NFEC-2017-11-240968\",\"fixedAsetNo\":\"21B70631C\",\"korNm\":\"비드제작용 전기로\",\"engNm\":\"Fusion machine\",\"confirmYn\":\"Y\",\"deleteYn\":\"N\",\"statusCd\":\"06\",\"statusNm\":\"완료\",\"registDt\":\"2017-11-30\",\"modifyDt\":null,\"registId\":\"ymk@kigam.re.kr\",\"useScopeCd\":\"1\",\"useScopeNm\":\"단독활용만 가능\",\"idleDisuseCd\":\"1\",\"idleDisuseNm\":\"활용\",\"organCd\":null,\"organNm\":\"한국지질자원연구원\",\"rndYn\":\"Y\",\"rndNm\":\"R&D 과제(연구개발사업 조사분석대상)\",\"registNm\":\"양명권\",\"apiYn\":\"N\",\"orgMgntNo\":null}],\"errors\":[]}";

    	model.addAttribute("printString", resultStr);

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1800/kp1810Modify.do")
	public String kp1810Modify(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1810Modify";
	}

    @RequestMapping(value="/kp1800/kp1810ModifyProc.do")
    public String kp1810ModifyProc(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = commonMap.setMultipartZeusImage(multiRequest, "", "Globals.Zeus.Img.fileStorePath", SessionUtil.getString("orgNo"));
    	cmap.put("_method", "PUT", false);

    	System.out.println(cmap);

    	if (cmap.getInt("filedataFileSize") > 0 && cmap.getInt("filedataFileSize") < (300 * 1024)) {
    		model.addAttribute("printString", "{\"errors\":[{\"code\":\"500\",\"message\":\"이미지 파일의 용량은 300KB 이상 가능합니다.\"}]}");
        	return "common/commonString";
    	}

    	String filePath = "";
		String fileName = "";
		if (!"".equals(cmap.getString("filedataStreFileNm"))) {
			filePath = cmap.getString("filedataFileStreCours") + "/" + cmap.getString("filedataStreFileNm");
			fileName = cmap.getString("filedataStreFileNm");
		}

		cmap.remove("filedataFileSn");
		cmap.remove("filedataFileExtsn");
		cmap.remove("filedataFileStreCours");
		cmap.remove("filedataStreFileNm");
		cmap.remove("filedataOrignlFileNm");
		cmap.remove("filedataFileSize");

		cmap.remove("saleComDt");
		cmap.remove("rndList[0].subjectSearchKeywords");
		cmap.remove("mainEquipSearchKeywords");
		cmap.remove("analysisInstNameKeyword");
		cmap.remove("rndList[0].officeYn");
		cmap.remove("transferComDt");
		cmap.remove("rfdEquipNmKeyword");
		cmap.remove("recyclingComDt");
		cmap.remove("zipSearchKeywords");
		cmap.remove("modelSearchKeywords");
		cmap.remove("projDetailNameKeyword");
		cmap.remove("disuseComCd");
		cmap.remove("disposalComDt");
		cmap.remove("grantOrganSearchKeywords");
		cmap.remove("setupSearchKeywords");
		cmap.remove("projectAnalysisNameKeyword");
		cmap.remove("reqInstNameKeyword");

    	CommonList paramList = new CommonList();
        Iterator<Entry<String,String[]>> i$ = cmap.entrySet().iterator();
		while(i$.hasNext()){
			Entry<String,String[]> entry = i$.next();
			Object value = entry.getValue();
			if (value != null) {
				Object toadd;
				if (value instanceof String[]) {
					String[] values = (String[]) value;

					StringBuffer sb = new StringBuffer();
					for(int i=0; i<values.length; i++){
						sb.append(values[i].trim());
						sb.append(",");
					}
					toadd = sb.toString().replaceAll(",$","");
				} else {
					toadd = value;
				}
				//this.put((String)entry.getKey(), toadd);
				CommonMap param = new CommonMap();
				param.put("key", (String)entry.getKey());
				param.put("val", toadd);
				if (!"fileData".equals(param.getString("key"))) {
					paramList.add(param);
				}
			}
		};

		System.out.println("paramList : " + paramList);

		String equipId = cmap.getString("equipId");

		 String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		 String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s/%s", equipId, key);
		 //String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

    	//String result = zeusService.modify(cmap, paramList, equipId);
		String result = zeusService.upload3(cmap, paramList, filePath, fileName, url);
		

    	model.addAttribute("printString", result);
    	return "common/commonString";
    }

    @RequestMapping(value="/kp1800/kp1810Write.do")
	public String kp1810Write(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//장비담당자 조회
    	CommonMap param = new CommonMap();
    	param.put("codeId", "managers");
    	param.put("code", SessionUtil.getString("userEmail"));
    	param.put("name", SessionUtil.getString("userName"));
    	CommonMap codeView = zeusCodeService.getZeusCodeView(param);
    	if (codeView.isEmpty()) {
    		codeView = zeusCodeService.getZeusCodeView2(param);
    	}
    	if (!codeView.isEmpty()) {
    		//장비담당자 기본 설정
        	cmap.put("managerId", codeView.getString("code"));
    	}

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1800/kp1810Write";
	}

    @RequestMapping(value="/kp1800/kp1810WriteProc.do")
	public String kp1810WriteProc(final MultipartHttpServletRequest multiRequest, ModelMap model, @RequestParam("fileData") MultipartFile fileData) throws Exception {
    	CommonMap cmap = commonMap.setMultipartZeusImage(multiRequest, "", "Globals.Zeus.Img.fileStorePath", SessionUtil.getString("orgNo"));

    	//{"errors":[{"code":"500","message":"서버에 오류가 발생하여 요청을 수행할 수 없습니다."}]}

    	System.out.println(cmap);

    	if (cmap.getInt("filedataFileSize") > 0 && cmap.getInt("filedataFileSize") < (300 * 1024)) {
    		model.addAttribute("printString", "{\"errors\":[{\"code\":\"500\",\"message\":\"이미지 파일의 용량은 300KB 이상 가능합니다.\"}]}");
        	return "common/commonString";
    	}

    	//filedataFileStreCours=D:/rfid_data/kigam/zeus/img/2017, filedataStreFileNm=201712290802535470.jpg
		String filePath = "";
		String fileName = "";
		if (!"".equals(cmap.getString("filedataStreFileNm"))) {
			filePath = cmap.getString("filedataFileStreCours") + "/" + cmap.getString("filedataStreFileNm");
			fileName = cmap.getString("filedataStreFileNm");
		}

		cmap.remove("filedataFileSn");
		cmap.remove("filedataFileExtsn");
		cmap.remove("filedataFileStreCours");
		cmap.remove("filedataStreFileNm");
		cmap.remove("filedataOrignlFileNm");
		cmap.remove("filedataFileSize");

		cmap.remove("saleComDt");
		cmap.remove("rndList[0].subjectSearchKeywords");
		cmap.remove("mainEquipSearchKeywords");
		cmap.remove("analysisInstNameKeyword");
		cmap.remove("rndList[0].officeYn");
		cmap.remove("transferComDt");
		cmap.remove("rfdEquipNmKeyword");
		cmap.remove("recyclingComDt");
		cmap.remove("zipSearchKeywords");
		cmap.remove("modelSearchKeywords");
		cmap.remove("projDetailNameKeyword");
		cmap.remove("disuseComCd");
		cmap.remove("disposalComDt");
		cmap.remove("grantOrganSearchKeywords");
		cmap.remove("setupSearchKeywords");
		cmap.remove("projectAnalysisNameKeyword");
		cmap.remove("reqInstNameKeyword");

    	CommonList paramList = new CommonList();
        Iterator<Entry<String,String[]>> i$ = cmap.entrySet().iterator();
		while(i$.hasNext()){
			Entry<String,String[]> entry = i$.next();
			Object value = entry.getValue();
			if (value != null) {
				Object toadd;
				if (value instanceof String[]) {
					String[] values = (String[]) value;

					StringBuffer sb = new StringBuffer();
					for(int i=0; i<values.length; i++){
						sb.append(values[i].trim());
						sb.append(",");
					}
					toadd = sb.toString().replaceAll(",$","");
				} else {
					toadd = value;
				}
				//this.put((String)entry.getKey(), toadd);
				CommonMap param = new CommonMap();
				param.put("key", (String)entry.getKey());
				param.put("val", toadd);
				if (!"fileData".equals(param.getString("key"))) {
					paramList.add(param);
				}
			}
		};

		System.out.println("paramList : " + paramList);


		String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
        String url = String.format("http://api.zeus.go.kr/api/eq/equips/%s", key);
		//String url = "http://203.247.172.17:10010/kp1800/kp1810WriteTest.do";

    	//String result = zeusService.write(cmap, paramList, filePath, fileName);
		String result = zeusService.upload3(cmap, paramList, filePath, fileName, url);

    	model.addAttribute("printString", result);
    	return "common/commonString";
    }

    @RequestMapping(value="/kp1800/kp1810WriteTest.do")
	public String kp1810WriteTest(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = commonMap.setMultipartImage(multiRequest, "", "Globals.Zeus.Img.fileStorePath", SessionUtil.getString("orgNo"));

    	System.out.println("WriteTest : " + cmap);

    	return "common/commonString";
    }

    @RequestMapping(value="/kp1800/kp1810DeleteProc.do")
    public String kp1810DeleteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("_method", "DELETE", false);

    	System.out.println(cmap);

		String equipId = cmap.getString("equipId");

    	String result = zeusService.delete(cmap, equipId);

    	model.addAttribute("printString", result);
    	return "common/commonString";
    }

    @RequestMapping(value="/kp1800/kp1810Asset.do")
	public String kp1810Asset(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap resultMap = new CommonMap();

    	try {
    		//세션 체크
    		CommonMap procSessionChk = userService.procSessionChk(cmap, request);
    		if (!procSessionChk.isEmpty()) {
    			return procSessionChk.toJsonString(model);
        	}

    		CommonMap viewData = assetService.getAssetDetail2(cmap);
    		viewData.put("aqusitDt", DateUtil.formatDate(viewData.getString("aqusitDt"),"-"));
    		resultMap.put("ret", "OK");
    		resultMap.put("retmsg", "성공");
    		resultMap.putAll(viewData);
    		return resultMap.toJsonString(model);

    	} catch (Exception e) {
			e.printStackTrace();
		}

    	resultMap.put("ret", "ERR");
		resultMap.put("retmsg", "실패");
    	return resultMap.toJsonString(model);
	}

    @RequestMapping(value="/kp1800/kp1810CmmnAjax.do")
	public String kp1810CmmnAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	CommonMap cmap2 = new CommonMap(request);

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap2, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	if (!"codes".equals(cmap.getString("apiType"))
    			&& !"models".equals(cmap.getString("apiType"))
    			&& !"main-equips".equals(cmap.getString("apiType"))
    			&& !"subjects".equals(cmap.getString("apiType"))
    			&& !"organs".equals(cmap.getString("apiType"))
    			&& !"setups".equals(cmap.getString("apiType"))
    			&& !"zips".equals(cmap.getString("apiType"))
    			&& !"red".equals(cmap.getString("apiType"))
    			) {
    		model.addAttribute("printString", "{}");
    		return "common/commonString";
    	}

    	String key = EgovProperties.getProperty("Globals.Zeus.apiKey");
		String url = String.format("http://api.zeus.go.kr/api/eq/%s/%s"
				, cmap.getString("apiType")
				, key
				);

    	CommonList paramList = new CommonList();
        Iterator<Entry<String,String[]>> i$ = cmap.entrySet().iterator();
		while(i$.hasNext()){
			Entry<String,String[]> entry = i$.next();
			Object value = entry.getValue();
			if (value != null) {
				Object toadd;
				if (value instanceof String[]) {
					String[] values = (String[]) value;

					StringBuffer sb = new StringBuffer();
					for(int i=0; i<values.length; i++){
						sb.append(values[i].trim());
						sb.append(",");
					}
					toadd = sb.toString().replaceAll(",$","");
				} else {
					toadd = value;
				}
				//this.put((String)entry.getKey(), toadd);
				CommonMap param = new CommonMap();
				param.put("key", (String)entry.getKey());
				param.put("val", toadd);
				if (!"apiType".equals(param.getString("key"))) {
					paramList.add(param);
				}
			}
		};

		String paramters = "";

		int cnt = 0;
		for (int i=0; i<paramList.size(); i++) {
			CommonMap param = paramList.getMap(i);

			if (!"".equals(param.getString("val"))) {
				if (cnt==0) {
					paramters = paramters + String.format("?%s=%s"
							, param.getString("key")
							, URLEncoder.encode(param.getString("val"),"UTF-8")
							);
				} else {
					paramters = paramters + String.format("&%s=%s"
							, param.getString("key")
							, URLEncoder.encode(param.getString("val"),"UTF-8")
							);
				}
				cnt++;
			}
		}

		String resultStr = HttpZeusUtil.get(url + paramters);
		System.out.println(resultStr);

    	model.addAttribute("printString", resultStr);

    	return "common/commonString";
	}


}
