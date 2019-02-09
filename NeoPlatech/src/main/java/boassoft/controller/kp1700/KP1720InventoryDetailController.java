package boassoft.controller.kp1700;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.InventoryStatService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.ExcelUtil;

@Controller
public class KP1720InventoryDetailController {

	@Resource(name = "CommonMap")
    private CommonMap commonMap;

	@Resource(name = "InventoryService")
    private InventoryService inventoryService;

	@Resource(name = "InventoryStatService")
    private InventoryStatService inventoryStatService;

	@Resource(name = "AssetService")
    private AssetService assetService;

	@Resource(name = "UserService")
    private UserService userService;

	@Resource(name = "SystemService")
    private SystemService systemService;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1720InventoryDetailController.class);

    @RequestMapping(value="/kp1700/kp1720.do")
	public String kp1720(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", cmap.getString("pageSize", "50"));

    	//마지막 재물조사 차수
    	CommonMap invLast = inventoryService.getInventoryLast(cmap);
    	model.addAttribute("invLast",invLast);
    	
    	//화면표시관리 (재물조사목록)
		cmap.put("dispType", "INVENTORY_LIST");
		CommonList dispAssetList = systemService.getDispMngList(cmap);
		model.addAttribute("dispAssetList", dispAssetList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1720";
	}

    @RequestMapping(value="/kp1700/kp1720Ajax.do")
	public String kp1720Ajax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "11");
    	cmap.put("sInvTargetYn", "Y");    	
    	
    	if ("hosil".equalsIgnoreCase(cmap.getString("dataOrder"))) {
    		cmap.put("dataOrder", "inv.hosil");
    	}

    	//그리드 세션 체크 및 메뉴 권한 설정
    	CommonMap gridSessionChk = userService.gridSessionChk(cmap, request);
    	if (!gridSessionChk.isEmpty()) {
    		model.addAttribute("printString", gridSessionChk.toJsonString());
        	return "common/commonString";
    	}

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = inventoryService.getInventoryDetailList(cmap);
    	CommonMap result = new CommonMap();
    	result.put("resultList", resultList);
    	result.put("totalRow", resultList.totalRow);
    	model.addAttribute("printString", result.toJsonString());

    	return "common/commonString";
	}

    @RequestMapping(value="/kp1700/kp1720Search.do")
	public String kp1720Search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//재물조사차수 목록
    	CommonList invList = inventoryService.getInventoryList(cmap);
    	model.addAttribute("invList", invList);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1700/kp1720Search";
	}

    @RequestMapping(value="/kp1700/kp1720Excel.do")
	public String kp1720Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "11");
    	cmap.put("sInvTargetYn", "Y");
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

    	//복수검색 설정
    	assetService.setSearchArr(cmap);

    	CommonList resultList = inventoryService.getInventoryDetailList(cmap);
    	
    	String excelFileName = "재물조사내역";
    	
    	if (resultList.size() > 1) {
    		excelFileName = resultList.getMap(0).getString("topDeptName") + "_" + resultList.getMap(0).getString("deptName");
    	}
    	
    	//화면표시관리 (재물조사목록)
		cmap.put("dispType", "INVENTORY_LIST");
		CommonList dispMngList = systemService.getDispMngList(cmap);
		
		int headerSize = dispMngList.size() + 3;
    	String[] headerListLgc1 = new String[headerSize];
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = new String[headerSize];
    	String[] headerListTyp = new String[headerSize];
    	String[] headerListWidth = new String[headerSize];
    	String[][] mergedRegion = null;
    	int idx = 0;
    	while (idx<dispMngList.size()) {
    		CommonMap dispMng = dispMngList.getMap(idx);
			headerListLgc1[idx] = dispMng.getString("logical_name");
			headerListPhc[idx] = dispMng.getString("physical_name");
			headerListTyp[idx] = dispMng.getString("data_disp_type");
			headerListWidth[idx] = "" + Math.round(dispMng.getInt("default_width",100) / 10);
			idx++;
    	}
    	
    	headerListLgc1[idx] = "자산일련번호";
		headerListPhc[idx] = "ASSET_SEQ";
		headerListTyp[idx] = "TEXT";
		headerListWidth[idx] = "" + Math.round(100 / 10);
		idx++;
		
		headerListLgc1[idx] = "RFID번호";
		headerListPhc[idx] = "RFID_NO";
		headerListTyp[idx] = "TEXT";
		headerListWidth[idx] = "" + Math.round(300 / 10);
		idx++;
		
		headerListLgc1[idx] = "RFID번호ORI";
		headerListPhc[idx] = "RFID_NO2";
		headerListTyp[idx] = "TEXT";
		headerListWidth[idx] = "" + Math.round(300 / 10);
		idx++;
    	
    	

    	ExcelUtil.write2(request, response, resultList, excelFileName, headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1700/kp1720CateExcel.do")
	public String kp1720CateExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = inventoryStatService.getAssetCateStat(cmap);

    	float sum_total = 0;
    	float sum_inv_target_cnt = 0;
    	float sum_tag_read_cnt = 0;
    	float sum_no_read_cnt = 0;
    	float sum_insp_per = 0;

    	CommonList tempList = new CommonList();

    	for(int i=0; i<resultList.size(); i++){
    		CommonMap result = resultList.getMap(i);

    		int total = result.getInt("total");
    		int inv_target_cnt = result.getInt("inv_target_cnt");
    		int tag_read_cnt = result.getInt("tag_read_cnt");
    		int no_read_cnt = result.getInt("no_read_cnt");
    		int insp_per = Math.round( 1.0f * tag_read_cnt / inv_target_cnt * 100 );

    		sum_total += total;
    		sum_inv_target_cnt += inv_target_cnt;
    		sum_tag_read_cnt += tag_read_cnt;
    		sum_no_read_cnt += no_read_cnt;

    		result.put("inspPer", insp_per);

    		tempList.add(result);
    	}

    	sum_insp_per = Math.round( 1.0f * sum_tag_read_cnt / sum_inv_target_cnt * 100 );

    	//합계
    	CommonMap result = new CommonMap();
    	result.put("assetTypeName", "합계");
    	result.put("total", sum_total);
    	result.put("inv_target_cnt", sum_inv_target_cnt);
    	result.put("tag_read_cnt", sum_tag_read_cnt);
    	result.put("no_read_cnt", sum_no_read_cnt);
    	result.put("inspPer", sum_insp_per);
    	tempList.add(result);

    	String[] headerListLgc1 = {"자산구분","조사대상(건)","확인(건)","미확인(건)","실사율(%)"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"assetTypeName","invTargetCnt","tagReadCnt","noReadCnt","inspPer"};
    	String[] headerListTyp = {"TEXT","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"15","12","12","12","12"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, tempList, "자산구분별통계", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1700/kp1720DeptExcel.do")
	public String kp1720DeptExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = inventoryStatService.getDeptStat(cmap);

    	float sum_total = 0;
    	float sum_inv_target_cnt = 0;
    	float sum_tag_read_cnt = 0;
    	float sum_no_read_cnt = 0;
    	float sum_insp_per = 0;

    	CommonList tempList = new CommonList();

    	for(int i=0; i<resultList.size(); i++){
    		CommonMap result = resultList.getMap(i);

    		int total = result.getInt("total");
    		int inv_target_cnt = result.getInt("inv_target_cnt");
    		int tag_read_cnt = result.getInt("tag_read_cnt");
    		int no_read_cnt = result.getInt("no_read_cnt");
    		int insp_per = Math.round( 1.0f * tag_read_cnt / inv_target_cnt * 100 );

    		sum_total += total;
    		sum_inv_target_cnt += inv_target_cnt;
    		sum_tag_read_cnt += tag_read_cnt;
    		sum_no_read_cnt += no_read_cnt;

    		result.put("inspPer", insp_per);

    		tempList.add(result);
    	}

    	sum_insp_per = Math.round( 1.0f * sum_tag_read_cnt / sum_inv_target_cnt * 100 );

    	//합계
    	CommonMap result = new CommonMap();
    	result.put("topDeptName", "합계");
    	result.put("deptName", "");
    	result.put("total", sum_total);
    	result.put("inv_target_cnt", sum_inv_target_cnt);
    	result.put("tag_read_cnt", sum_tag_read_cnt);
    	result.put("no_read_cnt", sum_no_read_cnt);
    	result.put("inspPer", sum_insp_per);
    	tempList.add(result);

    	String[] headerListLgc1 = {"지역","부서(실)","조사대상(건)","확인(건)","미확인(건)","실사율(%)"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"topDeptName","deptName","invTargetCnt","tagReadCnt","noReadCnt","inspPer"};
    	String[] headerListTyp = {"TEXT","TEXT","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"15","15","12","12","12","12"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, tempList, "부서(실)별통계", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1700/kp1720HosilExcel.do")
	public String kp1720HosilExcel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "999999");

    	CommonList resultList = inventoryStatService.getHosilStat(cmap);

    	float sum_total = 0;
    	float sum_inv_target_cnt = 0;
    	float sum_tag_read_cnt = 0;
    	float sum_no_read_cnt = 0;
    	float sum_insp_per = 0;

    	CommonList tempList = new CommonList();

    	for(int i=0; i<resultList.size(); i++){
    		CommonMap result = resultList.getMap(i);

    		int total = result.getInt("total");
    		int inv_target_cnt = result.getInt("inv_target_cnt");
    		int tag_read_cnt = result.getInt("tag_read_cnt");
    		int no_read_cnt = result.getInt("no_read_cnt");
    		int insp_per = Math.round( 1.0f * tag_read_cnt / inv_target_cnt * 10000 ) / 100;

    		sum_total += total;
    		sum_inv_target_cnt += inv_target_cnt;
    		sum_tag_read_cnt += tag_read_cnt;
    		sum_no_read_cnt += no_read_cnt;

    		result.put("inspPer", insp_per);

    		tempList.add(result);
    	}

    	sum_insp_per = Math.round( 1.0f * sum_tag_read_cnt / sum_inv_target_cnt * 10000 ) / 100;

    	//합계
    	CommonMap result = new CommonMap();
    	result.put("posName", "합계");
    	result.put("total", sum_total);
    	result.put("inv_target_cnt", sum_inv_target_cnt);
    	result.put("tag_read_cnt", sum_tag_read_cnt);
    	result.put("no_read_cnt", sum_no_read_cnt);
    	result.put("inspPer", sum_insp_per);
    	tempList.add(result);

    	String[] headerListLgc1 = {"위치","조사대상(건)","확인(건)","미확인(건)","실사율(%)"};
    	String[] headerListLgc2 = null;
    	String[] headerListPhc = {"posName","invTargetCnt","tagReadCnt","noReadCnt","inspPer"};
    	String[] headerListTyp = {"TEXT","NUMBER","NUMBER","NUMBER","NUMBER"};
    	String[] headerListWidth = {"30","12","12","12","12"};
    	String[][] mergedRegion = null;

    	ExcelUtil.write2(request, response, tempList, "위치별통계", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

}
