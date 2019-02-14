package boassoft.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.excel.impl.EgovExcel2007ServiceImpl;
import egovframework.rte.fdl.excel.impl.EgovExcelServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.service.AppService;
import boassoft.service.AssetService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.DynmTableUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.NumberUtil;
import boassoft.util.SessionUtil;

@Controller
public class AssetController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "appService")
    private AppService appService;

	@Resource(name = "systemService")
    private SystemService systemService;

	@Resource(name = "assetNoIdGnrService")
    private EgovIdGnrService assetNoIdGnrService;

	@Resource(name = "rfidNoIdGnrService")
    private EgovIdGnrService rfidNoIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	/** log */
    protected static final Log LOG = LogFactory.getLog(AssetController.class);
    
    @RequestMapping(value="/asset/selectList.do")
   	public String getAssetList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
       	cmap.put("pageSize", cmap.getString("pageSize", "50"));

       	//물품항목정보 (전체)
   		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   		model.addAttribute("addcolMngList", addcolMngList);

   		//검색 항목 개수 구하기
   		int searchCnt = 0;
   		for (int i=0; i<addcolMngList.size(); i++) {
   			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
   				searchCnt++;
   			}
   		}
   		model.addAttribute("searchCnt", searchCnt);

   		//화면표시관리 (물품목록)
   		cmap.put("dispType", "ASSET_LIST");
   		CommonList dispMngList11 = systemService.getDispMngList(cmap);
   		model.addAttribute("dispMngList11", dispMngList11);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetList";
   	}

       @RequestMapping(value="/asset/selectListAjax.do")
   	public String getAssetListAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
       	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

       	//물품항목정보 (전체)
   		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   		model.addAttribute("addcolMngList", addcolMngList);

   		//검색 항목 구성
   		int colcnt = 0;
   		for (int i=0; i<addcolMngList.size(); i++) {
   			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
   				colcnt++;
   				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
   					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
   				}
   				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
   					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
   				}
   				else
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
   				}
   			}
   		}

   		//화면표시관리 (물품목록)
   		cmap.put("dispType", "11");
   		CommonList dispMngList11 = systemService.getDispMngList(cmap);
   		StringBuffer sbcol = new StringBuffer();
   		for (int i=0; i<dispMngList11.size(); i++) {
   			CommonMap dispMng = dispMngList11.getMap(i);
   			sbcol.append(", ast.");
   			sbcol.append(dispMng.getString("physicalName"));
   		}
   		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

       	CommonList assetList = assetService.getAssetList(cmap);
       	CommonMap result = new CommonMap();
       	result.put("resultList", assetList);
       	result.put("totalRow", assetList.totalRow);
       	model.addAttribute("printString", result.toJsonString());

       	return "common/commonString";
   	}

       @RequestMapping(value="/asset/selectListXls.do")
   	public String getAssetListXls(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("pageIdx", "1");
       	cmap.put("pageSize", "999999");
       	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
       	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));

       	//물품항목정보 (전체)
   		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   		model.addAttribute("addcolMngList", addcolMngList);

   		//검색 항목 구성
   		int colcnt = 0;
   		for (int i=0; i<addcolMngList.size(); i++) {
   			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
   				colcnt++;
   				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
   					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
   				}
   				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
   					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
   				}
   				else
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
   				}
   			}
   		}

   		//화면표시관리 (물품목록)
   		cmap.put("dispType", "97");
   		CommonList dispMngList = systemService.getDispMngList(cmap);
   		StringBuffer sbcol = new StringBuffer();
   		for (int i=0; i<dispMngList.size(); i++) {
   			CommonMap dispMng = dispMngList.getMap(i);
   			sbcol.append(", ast.");
   			sbcol.append(dispMng.getString("physicalName"));
   		}
   		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

       	CommonList assetList = assetService.getAssetList(cmap);

       	int headerSize = dispMngList.size();
       	String[] headerListLgc = new String[headerSize];
       	String[] headerListPhc = new String[headerSize];
       	String[] headerListTyp = new String[headerSize];
       	int idx = 0;
       	while (idx<dispMngList.size()) {
       		CommonMap dispMng = dispMngList.getMap(idx);
   			headerListLgc[idx] = dispMng.getString("logical_name");
   			headerListPhc[idx] = dispMng.getString("physical_name");
   			headerListTyp[idx] = dispMng.getString("data_disp_type");
   			idx++;
       	}
       	//headerListLgc[idx] = "사진";
   		//headerListPhc[idx] = "asset_file_cnt";

       	ExcelUtil.write(request, response, assetList, egovMessageSource.getMessage("excel.filename.assets"), headerListLgc, headerListPhc, headerListTyp);

       	return null;
   	}

       @RequestMapping(value="/asset/excelDn.do")
   	public String getExcelDn(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("pageIdx", "1");
       	cmap.put("pageSize", "3");

       	//물품항목정보 (전체)
   		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   		model.addAttribute("addcolMngList", addcolMngList);

   		//검색 항목 구성
   		int colcnt = 0;
   		for (int i=0; i<addcolMngList.size(); i++) {
   			if ("Y".equals(addcolMngList.getMap(i).getString("searchYn"))) {
   				colcnt++;
   				if ("3".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s")));
   					cmap.put("custSearValE"+colcnt, NumberUtil.formatInteger(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e")));
   				}
   				else if ("4".equals(addcolMngList.getMap(i).getString("dataDispType")))
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearValS"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_s"), "-"));
   					cmap.put("custSearValE"+colcnt, DateUtil.formatDate(cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")+"_e"), "-"));
   				}
   				else
   				{
   					cmap.put("custSearCol"+colcnt, DynmTableUtil.columnName(addcolMngList.getMap(i).getString("tableAlias", "ast"), addcolMngList.getMap(i).getString("physicalName")));
   					cmap.put("custSearVal"+colcnt, cmap.getString("s_"+addcolMngList.getMap(i).getString("physicalName")));
   				}
   			}
   		}

   		//화면표시관리 (물품목록)
   		cmap.put("dispType", "97");
   		CommonList dispMngList = systemService.getDispMngList(cmap);
   		StringBuffer sbcol = new StringBuffer();
   		for (int i=0; i<dispMngList.size(); i++) {
   			CommonMap dispMng = dispMngList.getMap(i);
   			sbcol.append(", ast.");
   			sbcol.append(dispMng.getString("physicalName"));
   		}
   		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

       	CommonList assetList = assetService.getAssetList(cmap);
       	model.addAttribute("assetList", assetList);

       	int headerSize = dispMngList.size();
       	String[] headerListLgc = new String[headerSize];
       	String[] headerListPhc = new String[headerSize];
       	String[] headerListTyp = new String[headerSize];
       	int idx = 0;
       	while (idx<dispMngList.size()) {
       		CommonMap dispMng = dispMngList.getMap(idx);
   			headerListLgc[idx] = dispMng.getString("logical_name");
   			headerListPhc[idx] = dispMng.getString("physical_name");
   			headerListTyp[idx] = dispMng.getString("data_disp_type");
   			idx++;
       	}

       	ExcelUtil.write(request, response, assetList, egovMessageSource.getMessage("excel.filename.assets.template"), headerListLgc, headerListPhc, headerListTyp);

       	return null;
   	}

       @RequestMapping(value="/asset/excelUp.do")
   	public String getExcelUp(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
       	CommonMap cmap = commonMap.setMultipartExcel(multiRequest,"","Globals.Asset.Excel.fileStorePath",SessionUtil.getString("orgNo"));
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/asset/excelUp.do" + " - " + cmap);

       	CommonMap cmJson = new CommonMap();
       	cmJson.put("result", "N");
       	cmJson.put("errorMsg", "");

       	HttpSession session = multiRequest.getSession();

       	if (!"".equals(cmap.getString("xlsFileStreFileNm"))) {
       		System.out.println("Excel Upload OK");

       		EgovExcelServiceImpl egovExcelService = new EgovExcelServiceImpl();
       		EgovExcel2007ServiceImpl egovExcel2007Service = new EgovExcel2007ServiceImpl();

       		try {
       			//{NEW_AQUSIT_AMT=, DEPRE_CURR_AMT=, DEPRE_PREV_AMT=, DEPRE_PREV_ACCU_AMT=, ORG_NAME=, HOSIL=, ASSET_SELL_AMT=, AQUSIT_AMT=, ASSET_CATE=, CPEX=, DEPT_NAME=, DISP_PNLC_AMT=, ASSET_NAME=, USEFUL_LIFE=, ASSET_NO=, DEPRE_CURR_ACCU_AMT=, ASSET_STATE=, DEPRE_CURR_DISP_AMT=, ASSET_DISP_DT=, ASSET_MNG_TYPE=, DEPRE_TRGT_AMT=, ASSET_DISP_AMT=, ASSET_TYPE=, AQUSIT_DT=, ASSET_BLNC_AMT=, ASSET_SIZE=, USER_NAME=,
       			//xls_file_PATH=D:/rfid_data/asset/excel/2014, xls_file_NFNM=201409161201152620.xls, xls_file_OFNM=물품현황_20140916.xls, xls_file_SN=0, xls_file_RST=true, xls_file_EXT=xls}

       			File xlsFile = new File(cmap.getString("xlsFileFileStreCours") + File.separator + cmap.getString("xlsFileStreFileNm"));

       			if (xlsFile != null && xlsFile.isFile()) {
       				XSSFWorkbook hwb2007 = null;
       				XSSFSheet hst2007 = null;
       				Workbook hwb = null;
       				Sheet hst = null;

       				if (egovExcel2007Service.checkWorkbook(xlsFile.getAbsolutePath())) {
       					hwb2007 = egovExcel2007Service.loadWorkbook(xlsFile.getAbsolutePath());
           				hst2007 = hwb2007.getSheetAt(0);
       				} else if (egovExcelService.checkWorkbook(xlsFile.getAbsolutePath())) {
       					hwb = egovExcelService.loadWorkbook(xlsFile.getAbsolutePath());
       					hst = hwb.getSheetAt(0);
       				}

   					boolean validationCheck = true;
   					CommonList xList = new CommonList();

   					cmap.put("dispType", "97");
   					CommonList excelMngList = systemService.getDispMngList(cmap);

   			    	int headerSize = excelMngList.size();
   			    	String[] headerListLgc = new String[headerSize];
   			    	String[] headerListPhc = new String[headerSize];
   			    	String[] dataDispType = new String[headerSize];
   					for (int k=0; k<excelMngList.size(); k++) {
   						CommonMap excelMng = excelMngList.getMap(k);
   						headerListLgc[k] = excelMng.getString("logical_name");
   						headerListPhc[k] = excelMng.getString("physical_name").toLowerCase();
   						dataDispType[k] = excelMng.getString("data_disp_type");
   					}

   					if (hst != null || hst2007 != null) {
   						if (hst != null) {
   							for(int i=0; i<=hst.getLastRowNum(); i++){
   								HSSFRow hsr = (HSSFRow) hst.getRow(i);
   								CommonMap xMap = new CommonMap();

   								if (i == 0)
   								{
   									for (int k=0; k<headerListLgc.length; k++) {
   										String cellValue = ExcelUtil.getCell(hsr, k).trim();
   										if (!headerListLgc[k].equals(cellValue)) {
   											cmJson.put("errorMsg", cellValue + "는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
   											break;
   										}
   									}
   								}
   								else
   								{
   									for (int k=0; k<headerListLgc.length; k++) {
   										String val = ExcelUtil.getCell(hsr, k);

   										if ("0".equals(dataDispType[k])) {			//코드형
   											val = val.replaceAll("[^0-9a-zA-Z-]","");
   										} else if ("1".equals(dataDispType[k])) {	//일반형
   											//TODO
   										} else if ("2".equals(dataDispType[k])) {	//문자형
   											//TODO
   										} else if ("3".equals(dataDispType[k])) {	//숫자형
   											val = val.replaceAll("[^0-9.]","");
   											val = val.split("\\.")[0];
   										} else if ("4".equals(dataDispType[k])) {	//날짜형
   											val = val.replaceAll("[^0-9]","");
   											val = DateUtil.formatDate(val, "-");
   										}

   										xMap.put(headerListPhc[k], val);
   									}

   									xList.add(xMap);
   								}
   							}
   						}
   						if (hst2007 != null) {
   							for(int i=0; i<=hst2007.getLastRowNum(); i++){
   								XSSFRow xsr = hst2007.getRow(i);
   								CommonMap xMap = new CommonMap();

   								if (i == 0)
   								{
   									for (int k=0; k<headerListLgc.length; k++) {
   										String cellValue = ExcelUtil.getCell(xsr, k).trim();
   										if (!headerListLgc[k].equals(cellValue)) {
   											validationCheck = false;
   											cmJson.put("errorMsg", cellValue + "는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
   											break;
   										}
   									}
   								}
   								else
   								{
   									for (int k=0; k<headerListLgc.length; k++) {
   										String val = ExcelUtil.getCell(xsr, k);

   										if ("0".equals(dataDispType[k])) {			//코드형
   											val = val.replaceAll("[^0-9a-zA-Z-]","");
   										} else if ("1".equals(dataDispType[k])) {	//일반형
   											//TODO
   										} else if ("2".equals(dataDispType[k])) {	//문자형
   											//TODO
   										} else if ("3".equals(dataDispType[k])) {	//숫자형
   											val = val.replaceAll("[^0-9.]","");
   											val = val.split("\\.")[0];
   										} else if ("4".equals(dataDispType[k])) {	//날짜형
   											val = val.replaceAll("[^0-9]","");
   											val = DateUtil.formatDate(val, "-");
   										}

   										xMap.put(headerListPhc[k], val);
   									}

   									xList.add(xMap);
   								}
   							}
   						}

   						if (validationCheck) {
   							for (int j=0; j<xList.size(); j++) {
   								CommonMap xMap = xList.getMap(j);

   								for (int k=0; k<excelMngList.size(); k++) {
   									CommonMap excelMng = excelMngList.getMap(k);

   									String nullYn = excelMng.getString("null_yn");
   									String dataDispTypeNum = excelMng.getString("data_disp_type");
   				    				String physicalName = excelMng.getString("physical_name").toLowerCase();
   									String val = xMap.getString(excelMng.getString("physical_name"));

   									if ("N".equals(nullYn) && "".equals(val.trim()) && !"asset_no".equals(physicalName)) {
   										validationCheck = false;
   										cmJson.put("errorMsg", (j+2) + "번 라인의 " + excelMng.getString("logical_name") + "는 필수값 입니다. 빈값이 들어갈 수 없습니다.");
   										break;
   									}
   									if ("4".equals(dataDispTypeNum) && !"".equals(val.trim()) && val.trim().length() != 10) {
   										validationCheck = false;
   										cmJson.put("errorMsg", (j+2) + "번 라인의 " + excelMng.getString("logical_name") + "는 날짜형으로 YYYY-MM-DD 형태로 입력되어야 합니다. ex) 1995-01-01");
   										break;
   									}
   								}
   							}
   						}


   						if (validationCheck) {

   							//임시부여코드 생성
   							cmap.put("tmpCode", DateUtil.getFormatDate("yyyyMMddHHmmssSSS"));

   					    	//물품항목정보 (전체)
   							CommonList dispMngList = excelMngList;

   				    		StringBuffer sbcol = new StringBuffer();
   				    		sbcol.append("TMP_CODE");
   				    		for (int i=0; i<dispMngList.size(); i++) {
   				    			CommonMap dispMng = dispMngList.getMap(i);
   				    			sbcol.append(",");
   				    			sbcol.append(dispMng.getString("physicalName"));
   				    		}
   				    		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   							//임시 저장 (엑셀 테이블)
   				    		/*
   				    		if (xList.size() > 0) {
   				    			cmap.put("dispMngList", dispMngList);
   				    			assetService.insertAssetExcelBatch(cmap, xList, multiRequest.getSession());
   				    		} */
   							for(int i=0; i<xList.size(); i++){
   								CommonMap xMap = xList.getMap(i);
   								xMap.put("orgNo", cmap.getString("orgNo"));
   								xMap.put("colList", cmap.getString("colList"));

   								String[] svalue = new String[dispMngList.size()+1];
   								svalue[0] = cmap.getString("tmpCode");
   					    		for (int k=0; k<dispMngList.size(); k++) {
   					    			CommonMap dispMng = dispMngList.getMap(k);

   					    			svalue[k+1] = xMap.getString(dispMng.getString("physicalName"));
   					    		}
   					    		xMap.put("colValue", svalue);

   								assetService.insertAssetExcel(xMap);

   								session.setAttribute("excelUpStatus", (i+1) + " / " + xList.size());
   							}

   							//assetService.createAssetExcelBackup(cmap);

   							sbcol = new StringBuffer();
   				    		for (int i=0; i<dispMngList.size(); i++) {
   				    			CommonMap dispMng = dispMngList.getMap(i);
   				    			if ("Y".equals(dispMng.getString("modifyYn"))) {
   					    			sbcol.append(", ast.");
   					    			sbcol.append(dispMng.getString("physicalName"));
   					    			sbcol.append(" = axc.");
   					    			sbcol.append(dispMng.getString("physicalName"));
   				    			}
   				    		}
   				    		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   							int updateCnt = assetService.updateAssetExcelAll2(cmap);



   							CommonList addcolMngList = systemService.getAddcolMngList(cmap);

   							sbcol = new StringBuffer();
   				    		for (int i=0; i<addcolMngList.size(); i++) {
   				    			CommonMap addcolMng = addcolMngList.getMap(i);
   				    			sbcol.append(",");
   				    			sbcol.append(addcolMng.getString("physicalName"));
   				    		}
   				    		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   							int insertCnt = 0;
   							CommonList newList = assetService.getAssetNewExcelList(cmap);
   							for(int i=0; i<newList.size(); i++){
   								CommonMap xMap = newList.getMap(i);
   								xMap.put("orgNo", cmap.getString("orgNo"));
   								xMap.put("colList", cmap.getString("colList"));

   								String gnrAssetNo = "";
   								String gnrRfidNo = "";

   								if ("".equals(xMap.getString("assetNo"))
   										&& "".equals(xMap.getString("rfidNo"))) {
   									gnrAssetNo = assetNoIdGnrService.getNextStringId().replaceAll("^[0]+", "");
   									gnrRfidNo = rfidNoIdGnrService.getNextStringId().replaceAll("^[0]+", "");
   								} else if (!"".equals(xMap.getString("assetNo"))
   										&& !"".equals(xMap.getString("rfidNo"))) {
   									gnrAssetNo = xMap.getString("assetNo");
   									gnrRfidNo = xMap.getString("rfidNo");
   								} else {
   									//이도저도 아닌... failed 처리
   									continue;
   								}

   								xMap.put("assetNo", gnrAssetNo);
   								xMap.put("rfidNo", gnrRfidNo);

   								String[] svalue = new String[addcolMngList.size()];
   					    		for (int k=0; k<addcolMngList.size(); k++) {
   					    			CommonMap addcolMng = addcolMngList.getMap(k);

   					    			if ("ASSET_NO".equals(addcolMng.getString("physicalName").toUpperCase()))
   					    			{
   					    				svalue[k] = xMap.getString("assetNo");
   					    			}
   					    			else if ("RFID_NO".equals(addcolMng.getString("physicalName").toUpperCase()))
   					    			{
   					    				svalue[k] = xMap.getString("rfidNo");
   					    			}
   					    			else
   					    			{
   					    				svalue[k] = xMap.getString(addcolMng.getString("physicalName"));
   					    			}
   					    		}
   					    		xMap.put("colValue", svalue);

   								assetService.insertAsset(xMap);
   								insertCnt++;
   							}

   							int failCnt = 0;
   							failCnt = failCnt + (xList.size() - updateCnt - insertCnt);

   							//재물조사 동기화
   							inventoryService.syncInventoryDetail(cmap);

   							//처리 완료 후 임시 엑셀 파일 삭제
   							assetService.deleteAssetExcelAll(cmap);

   							System.out.println("insertCnt : " + insertCnt);
   							System.out.println("updateCnt : " + updateCnt);
   							System.out.println("failCnt : " + failCnt);

   							cmJson.put("regCnt", insertCnt);
   							cmJson.put("modCnt", updateCnt);
   							cmJson.put("failCnt", failCnt);
   							cmJson.put("result", "Y");
   						}
   					}

   					//1년이상 지난 백업DATA 삭제
   					//assetService.deleteAssetExcelBackup(cmap);
       			}

       		} catch (Exception e) {
       			e.printStackTrace();
       		}
       	}

       	String forwardMsg = "";

       	if (!"".equals(cmJson.getString("errorMsg"))) {
       		forwardMsg = cmJson.getString("errorMsg");
       	} else {
       		forwardMsg = "저장 완료 되었습니다.\\r\\n"
       				+ "[처리 결과]\\r\\n"
       				+ "신규 : " + cmJson.getString("regCnt") + " 건\\r\\n"
       				+ "수정 : " + cmJson.getString("modCnt") + " 건\\r\\n"
       				+ "실패 : " + cmJson.getString("failCnt") + " 건\\r\\n";
       	}

       	cmJson.put("forwardMsg", forwardMsg);
       	cmJson.put("forwardUrl", "none");
       	cmJson.put("prevCustomScript", "parent.fnXlsUpCallback('"+ forwardMsg +"');");

       	model.addAttribute("cmForward", cmJson);
       	return "common/commonOk";
       	//model.addAttribute("jsonString", cmJson.toJsonString());
       	//return "common/commonJson";
   	}

       @RequestMapping(value="/asset/excelUpStatus.do")
   	public String getExcelUpStatus(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));

       	String excelUpStatus = SessionUtil.getString("excelUpStatus");

       	CommonMap result = new CommonMap();
       	if ("ERROR".equals(excelUpStatus)) {
       		result.put("result", "ERROR");
           	result.put("status", excelUpStatus);
       	} else {

   	    	result.put("result", "OK");
   	    	result.put("status", excelUpStatus);
       	}

       	model.addAttribute("printString", result.toJsonString());
       	return "common/commonString";
       }

       @RequestMapping(value="/asset/excelUpStatusClose.do")
   	public String getExcelUpStatusClose(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {

       	CommonMap result = new CommonMap();
       	result.put("result", "OK");
       	result.put("status", SessionUtil.getString("excelUpStatus"));
       	model.addAttribute("printString", result.toJsonString());

       	request.getSession().removeAttribute("excelUpStatus");

       	return "common/commonString";
       }

       @RequestMapping(value="/asset/select.do")
   	public String getAssetDetail(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("assetNo"));

       	//물품항목정보 (전체)
   		CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   		model.addAttribute("addcolMngList", addcolMngList);

   		//화면표시관리 (물품목록)
   		cmap.put("dispType", "97");
   		CommonList dispMngList97 = systemService.getDispMngList(cmap);
   		model.addAttribute("dispMngList97", dispMngList97);

       	//물품상세
       	CommonMap asset = assetService.getAssetDetail(cmap);
       	model.addAttribute("asset", asset);

       	//물품히스토리 (재물조사)
       	CommonList assetHistoryList = inventoryService.getAssetHistoryList(cmap);
       	model.addAttribute("assetHistoryList", assetHistoryList);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetDetail";
   	}

       @RequestMapping(value="/asset/writeForm.do")
   	public String getAssetModifyForm(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("pageIdx", "1");
       	cmap.put("pageSize", "99999");
       	String[] chkArr = cmap.getArray("chkAssetNo");
       	cmap.put("checkbox", chkArr);

       	//화면표시관리 (항목전체)
   		cmap.put("disp_type", "98");
   		CommonList dispMngList98 = systemService.getDispMngList(cmap);
   		model.addAttribute("dispMngList98", dispMngList98);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetWrite";
   	}

       @RequestMapping(value="/asset/modifyFormAjax.do")
   	public String getAssetModifyFormAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("no", cmap.getString("param_no"));

       	CommonMap asset = assetService.getAssetDetail(cmap);
       	model.addAttribute("asset", asset);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetDetailRow";
   	}

       @RequestMapping(value="/asset/writeFormProc.do")
   	public String getAssetWriteFormProc(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
       	CommonMap cmap = commonMap.setMultipartNoFile(multiRequest, "", "Globals.Asset.Excel.fileStorePath",SessionUtil.getString("orgNo"));
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));

       	boolean validationCheck = true;
       	ArrayList<CommonMap> xList = new ArrayList<CommonMap>();

       	CommonMap cmJson = new CommonMap();
       	cmJson.put("result", "N");
       	cmJson.put("errorMsg", "");

       	//화면표시관리 (항목전체)
   		cmap.put("disp_type", "98");
   		CommonList dispMngList = systemService.getDispMngList(cmap);

       	String[] chkRow = multiRequest.getParameterValues("chk_row");

       	try {
   	    	if (chkRow != null && chkRow.length > 0) {

   	    		String procMode = cmap.getString("proc_mode");
   	    		int startNum = 0;
   	    		int endNum = 0;
   	    		if ("one".equals(procMode)) {
   	    			startNum = 0;
   	    			endNum = 1;
   	    		} else if ("multi".equals(procMode)) {
   	    			startNum = 1;
   	    			endNum = chkRow.length;
   	    		}

   	    		for (int i=startNum; i<endNum; i++) {
   	    			CommonMap xMap = new CommonMap();

   	    			for (int k=0; k<dispMngList.size(); k++) {
   	    				CommonMap dispMng = dispMngList.getMap(k);

   	    				String nullYn = dispMng.getString("null_yn");
   	    				String dataDispTypeNum = dispMng.getString("data_disp_type");
   	    				String physicalName = dispMng.getString("physical_name").toLowerCase();

   	    				String[] reqArr = multiRequest.getParameterValues(physicalName);
   	    				String val = reqArr[i];

   	    				if ("0".equals(dataDispTypeNum)) {			//코드형
   							val = val.replaceAll("[^0-9a-zA-Z-]","");
   						} else if ("1".equals(dataDispTypeNum)) {	//일반형
   							//TODO
   						} else if ("2".equals(dataDispTypeNum)) {	//문자형
   							//TODO
   						} else if ("3".equals(dataDispTypeNum)) {	//숫자형
   							val = val.replaceAll("[^0-9.]","");
   							val = val.split("\\.")[0];
   						} else if ("4".equals(dataDispTypeNum)) {	//날짜형
   							val = val.replaceAll("[^0-9]","");
   							val = DateUtil.formatDate(val, "-");
   						}

   	    				if ("N".equals(nullYn) && "".equals(val.trim()) && !"asset_no".equals(physicalName)) {
   	    					validationCheck = false;
   	    					cmJson.put("errorMsg", dispMng.getString("logical_name") + "는 필수값 입니다. 빈값이 들어갈 수 없습니다.");
   	    					break;
   	    				}
   	    				if ("4".equals(dataDispTypeNum) && !"".equals(val.trim()) && val.trim().length() != 10) {
   							validationCheck = false;
   							cmJson.put("errorMsg", dispMng.getString("logical_name") + "는 날짜형으로 YYYY-MM-DD 형태로 입력되어야 합니다. ex) 1995-01-01");
   							break;
   						}

   	    				xMap.put(physicalName, val);
   	    			}
   	    			xList.add(xMap);
   	    		}
   	    	}
       	} catch (Exception e) {
       		validationCheck = false;
       		multiRequest.getSession().setAttribute("excelUpStatus", "ERROR");
       		System.out.println(e.toString());
       	}


       	if (validationCheck) {

       		//임시부여코드 생성
   			cmap.put("tmpCode", DateUtil.getFormatDate("yyyyMMddHHmmssSSS"));

   	    	//물품항목정보 (전체)
   			CommonList addcolMngList = systemService.getAddcolMngList(cmap);
   			model.addAttribute("addcolMngList", addcolMngList);

       		StringBuffer sbcol = new StringBuffer();
       		sbcol.append("TMP_CODE");
       		for (int i=0; i<addcolMngList.size(); i++) {
       			CommonMap addcolMng = addcolMngList.getMap(i);
       			sbcol.append(",");
       			sbcol.append(addcolMng.getString("physicalName"));
       		}
       		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   			//임시 저장 (엑셀 테이블)
   			for(int i=0; i<xList.size(); i++){
   				CommonMap xMap = xList.get(i);
   				xMap.put("orgNo", cmap.getString("orgNo"));
   				xMap.put("colList", cmap.getString("colList"));

   				String[] svalue = new String[addcolMngList.size()+1];
   				svalue[0] = cmap.getString("tmpCode");
   	    		for (int k=0; k<addcolMngList.size(); k++) {
   	    			CommonMap addcolMng = addcolMngList.getMap(k);

   	    			svalue[k+1] = xMap.getString(addcolMng.getString("physicalName"));
   	    		}
   	    		xMap.put("colValue", svalue);

   				assetService.insertAssetExcel(xMap);
   			}

   			//assetService.createAssetExcelBackup(cmap);

   			sbcol = new StringBuffer();
       		for (int i=0; i<addcolMngList.size(); i++) {
       			CommonMap addcolMng = addcolMngList.getMap(i);
       			if ("Y".equals(addcolMng.getString("modifyYn"))) {
   	    			sbcol.append(", ast.");
   	    			sbcol.append(addcolMng.getString("physicalName"));
   	    			sbcol.append(" = axc.");
   	    			sbcol.append(addcolMng.getString("physicalName"));
       			}
       		}
       		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   			int updateCnt = assetService.updateAssetExcelAll2(cmap);
   			//int updateAdiCnt = assetService.updateAssetAdiExcelAll(cmap);

   			sbcol = new StringBuffer();
       		for (int i=0; i<addcolMngList.size(); i++) {
       			CommonMap addcolMng = addcolMngList.getMap(i);
       			sbcol.append(",");
       			sbcol.append(addcolMng.getString("physicalName"));
       		}
       		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

   			int insertCnt = 0;
   			CommonList newList = assetService.getAssetNewExcelList(cmap);
   			for(int i=0; i<newList.size(); i++){
   				CommonMap xMap = newList.getMap(i);
   				xMap.put("orgNo", cmap.getString("orgNo"));
   				xMap.put("colList", cmap.getString("colList"));

   				String gnrAssetNo = "";
   				String gnrRfidNo = "";

   				if ("".equals(xMap.getString("assetNo"))
   						&& "".equals(xMap.getString("rfidNo"))) {
   					gnrAssetNo = assetNoIdGnrService.getNextStringId().replaceAll("^[0]+", "");
   					gnrRfidNo = rfidNoIdGnrService.getNextStringId().replaceAll("^[0]+", "");
   				} else if (!"".equals(xMap.getString("assetNo"))
   						&& !"".equals(xMap.getString("rfidNo"))) {
   					gnrAssetNo = xMap.getString("assetNo");
   					gnrRfidNo = xMap.getString("rfidNo");
   				} else {
   					//이도저도 아닌... failed 처리
   					continue;
   				}

   				xMap.put("assetNo", gnrAssetNo);
   				xMap.put("rfidNo", gnrRfidNo);

   				String[] svalue = new String[addcolMngList.size()];
   	    		for (int k=0; k<addcolMngList.size(); k++) {
   	    			CommonMap addcolMng = addcolMngList.getMap(k);

   	    			if ("ASSET_NO".equals(addcolMng.getString("physicalName").toUpperCase()))
   	    			{
   	    				svalue[k] = xMap.getString("assetNo");
   	    			}
   	    			else if ("RFID_NO".equals(addcolMng.getString("physicalName").toUpperCase()))
   	    			{
   	    				//BSR + 1234567890 + END
   	    				svalue[k] = xMap.getString("rfidNo");
   	    			}
   	    			else
   	    			{
   	    				svalue[k] = xMap.getString(addcolMng.getString("physicalName"));
   	    			}
   	    		}
   	    		xMap.put("colValue", svalue);

   				assetService.insertAsset(xMap);
   				insertCnt++;
   			}

   			int failCnt = 0;
   			failCnt = failCnt + (xList.size() - updateCnt - insertCnt);

   			//재물조사 동기화
   			inventoryService.syncInventoryDetail(cmap);

   			//처리 완료 후 임시 엑셀 파일 삭제
   			assetService.deleteAssetExcelAll(cmap);

   			System.out.println("insertCnt : " + insertCnt);
   			System.out.println("updateCnt : " + updateCnt);
   			System.out.println("failCnt : " + failCnt);

   			cmJson.put("regCnt", insertCnt);
   			cmJson.put("modCnt", updateCnt);
   			cmJson.put("failCnt", failCnt);
   			cmJson.put("result", "Y");
   		}

   		//2년이상 지난 백업DATA 삭제
//   		assetService.deleteAssetExcelBackup(cmap);

       	String forwardMsg = "";

       	if (!"".equals(cmJson.getString("errorMsg"))) {
       		forwardMsg = cmJson.getString("errorMsg");
       	} else {
       		forwardMsg = "저장 완료 되었습니다.\\r\\n"
       				+ "[처리 결과]\\r\\n"
       				+ "신규 : " + cmJson.getString("regCnt") + " 건\\r\\n"
       				+ "수정 : " + cmJson.getString("modCnt") + " 건\\r\\n"
       				+ "실패 : " + cmJson.getString("failCnt") + " 건\\r\\n";
       	}

       	cmJson.put("forwardMsg", forwardMsg);
       	cmJson.put("forwardUrl", "none");
       	cmJson.put("prevCustomScript", "parent.fnXlsUpCallback('"+ forwardMsg +"');");

       	model.addAttribute("cmForward", cmJson);
       	return "common/commonOk";
       	//model.addAttribute("jsonString", cmJson.toJsonString());
       	//return "common/commonJson";
   	}

       @RequestMapping(value="/asset/deleteProc.do")
   	public String deleteProc(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getArray("assetNo[]"));

       	//삭제
       	assetService.deleteAssetArr(cmap);

       	//재물조사 동기화
   		inventoryService.syncInventoryDetail(cmap);

       	CommonMap result = new CommonMap();
       	result.put("result", "OK");
       	model.addAttribute("printString", result.toJsonString());
       	return "common/commonString";
   	}

       @RequestMapping(value="/asset/selectRow.do")
   	public String getAssetDetailRow(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("asset_no", cmap.getString("asset_no"));

       	CommonMap asset = assetService.getAssetDetail(cmap);
       	model.addAttribute("asset", asset);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetDetailRow";
   	}

       @RequestMapping(value="/asset/getImageList.do")
   	public String getAssetImageList(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("paramAssetNo"));

       	CommonList imgList = assetService.getAssetImgList(cmap);
       	model.addAttribute("imgList",imgList);

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetImageList";
       }

       @RequestMapping(value="/asset/getImageView.do")
   	public String getAssetImageView(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("assetNo"));
       	cmap.put("fileDt", cmap.getString("fileDt"));

       	//검색값 유지
       	model.addAttribute("cmRequest",cmap);

       	return "asset/assetImageView";
       }

       @RequestMapping(value="/asset/image/uploadWeb.do")
   	public String assetImageUploadWeb(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
       	CommonMap cmap = commonMap.setMultipartNoFile(multiRequest, "", "Globals.Asset.Img.fileStorePath", SessionUtil.getString("orgNo"));
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("assetNo", ""));

       	try{

       		if (!"".equals(cmap.getString("assetNo"))
       				&& !"".equals(cmap.getString("fileStreFileNm"))) {

       			cmap = commonMap.setMultipartImage(multiRequest, "", "Globals.Asset.Img.fileStorePath", SessionUtil.getString("orgNo"));

       			cmap.put("orgNo", SessionUtil.getString("orgNo"));
       			cmap.put("filePath", cmap.getString("fileFileStreCours"));
       			cmap.put("webFilePath", EgovProperties.getProperty("Globals.Asset.Img.fileWebPath").replaceAll("[{]orgNo[}]", cmap.getString("orgNo")) + DateUtil.getFormatDate("yyyyMM"));
   				cmap.put("fileNm", cmap.getString("fileStreFileNm"));
   				cmap.put("orignlFileNm", cmap.getString("fileOrignlFileNm"));
   				cmap.put("fileExt", cmap.getString("fileFileExtsn"));
   				cmap.put("fileDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));

   				appService.insertAssetImage(cmap);
       		}
       	}catch(Exception e){
       		e.printStackTrace();
       	}

       	CommonMap cmForward = new CommonMap();
       	cmForward.put("assetNo", cmap.getString("assetNo"));
       	cmForward.put("forwardUrl", "/asset/select.do");
       	cmForward.put("forwardMsg", "등록되었습니다.");
       	model.addAttribute("cmForward", cmForward);
       	return "common/commonOk";
   	}

       @RequestMapping(value="/asset/image/deleteWeb.do")
   	public String assetImageDeleteWeb(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("assetNo", ""));
       	cmap.put("fileDt", cmap.getString("fileDt", ""));

   		if (!"".equals(cmap.getString("fileDt"))
   				&& !"".equals(cmap.getString("assetNo"))
   				) {

   			appService.deleteAssetImage(cmap);
   		}

   		CommonMap cmForward = new CommonMap();
       	cmForward.put("assetNo", cmap.getString("assetNo"));
       	cmForward.put("forwardUrl", "/asset/select.do");
       	cmForward.put("forwardMsg", "삭제되었습니다.");
       	model.addAttribute("cmForward", cmForward);
       	return "common/commonOk";
   	}

       @RequestMapping(value="/asset/getImage.do")
   	public void getAssetImage(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
       	CommonMap cmap = new CommonMap(request);
       	cmap.put("orgNo", SessionUtil.getString("orgNo"));
       	cmap.put("assetNo", cmap.getString("assetNo"));
       	cmap.put("fileDt", cmap.getString("fileDt"));

       	CommonMap assetImg = assetService.getAssetImg(cmap);
       	String filePath = assetImg.getString("filePath");
       	String fileNm = assetImg.getString("fileNm");
       	String fileExt = assetImg.getString("fileExtsn");
       	String rootPath = EgovProperties.getProperty("Globals.RootPath");

       	// 2011.10.10 보안점검 후속조치
   		File file = null;
   		FileInputStream fis = null;

   		BufferedInputStream in = null;
   		ByteArrayOutputStream bStream = null;

   		try {
   			file = new File(filePath, fileNm);

   			if( file == null || !file.exists() ){
   				file = new File(rootPath + "/images/contents/noimg.gif");
   			}

   		    fis = new FileInputStream(file);

   		    in = new BufferedInputStream(fis);
   		    bStream = new ByteArrayOutputStream();

   		    int imgByte;
   		    while ((imgByte = in.read()) != -1) {
   			bStream.write(imgByte);
   		    }

   			String type = "";

   			if (fileExt != null && !"".equals(fileExt)) {
   			    if ("jpg".equals(fileExt.toLowerCase())) {
   				type = "image/jpeg";
   			    } else {
   				type = "image/" + fileExt.toLowerCase();
   			    }
   			    type = "image/" + fileExt.toLowerCase();

   			} else {
   			    LOG.debug("Image fileType is null.");
   			}

   			response.setHeader("Content-Type", type);
   			response.setContentLength(bStream.size());

   			bStream.writeTo(response.getOutputStream());

   			response.getOutputStream().flush();
   			response.getOutputStream().close();

   			// 2011.10.10 보안점검 후속조치 끝
   		} finally {
   			if (bStream != null) {
   				try {
   					bStream.close();
   				} catch (Exception ignore) {
   					//System.out.println("IGNORE: " + ignore);
   					LOG.debug("IGNORE: " + ignore.getMessage());
   				}
   			}
   			if (in != null) {
   				try {
   					in.close();
   				} catch (Exception ignore) {
   					//System.out.println("IGNORE: " + ignore);
   					LOG.debug("IGNORE: " + ignore.getMessage());
   				}
   			}
   			if (fis != null) {
   				try {
   					fis.close();
   				} catch (Exception ignore) {
   					//System.out.println("IGNORE: " + ignore);
   					LOG.debug("IGNORE: " + ignore.getMessage());
   				}
   			}
   		}
   	}

}
