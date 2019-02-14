package boassoft.controller.kp1300;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boassoft.service.AppService;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.BatchMysqlService;
import boassoft.service.InventoryService;
import boassoft.service.SystemService;
import boassoft.service.UserService;
import boassoft.util.CamelUtil;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.ExcelUtil;
import boassoft.util.StringUtil;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.rte.fdl.excel.impl.EgovExcel2007ServiceImpl;
import egovframework.rte.fdl.excel.impl.EgovExcelServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Controller
public class KP1319AssetXlsUpController {

	@Resource(name = "commonMap")
    private CommonMap commonMap;

	@Resource(name = "assetService")
    private AssetService assetService;

	@Resource(name = "assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Resource(name = "inventoryService")
    private InventoryService inventoryService;

	@Resource(name = "appService")
    private AppService appService;

	@Resource(name = "systemService")
    private SystemService systemService;

	@Resource(name = "userService")
    private UserService userService;

	@Resource(name = "batchMysqlService")
    private BatchMysqlService batchMysqlService;

	@Resource(name = "assetSeqIdGnrService")
    private EgovIdGnrService assetSeqIdGnrService;

	@Resource(name = "egovMessageSource")
    private EgovMessageSource egovMessageSource;

	/** log */
    protected static final Log LOG = LogFactory.getLog(KP1319AssetXlsUpController.class);

    @RequestMapping(value="/kp1300/kp1319.do")
	public String kp1319(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);

    	//검색값 유지
    	model.addAttribute("cmRequest",cmap);

    	return "kp1300/kp1319";
	}

    @RequestMapping(value="/kp1300/kp1319Excel.do")
	public String kp1319Excel(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
    	CommonMap cmap = new CommonMap(request);
    	cmap.put("pageIdx", cmap.getString("pageIdx", "1"));
    	cmap.put("pageSize", "3");
    	cmap.put("dataOrder", CamelUtil.deconvert2CamelCase(cmap.getString("dataOrder")));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow"));
    	cmap.put("sAssetDiv", "2");

    	CommonList resultList = assetService.getAssetList(cmap);

    	//화면표시관리 (자산목록)
		cmap.put("dispType", "ASSET_LIST_EXCEL_TMPL");
		CommonList dispMngList = systemService.getDispMngList(cmap);

		int headerSize = dispMngList.size();
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

    	ExcelUtil.write2(request, response, resultList, "엑셀업로드양식", headerListLgc1, headerListLgc2, headerListPhc, headerListTyp, mergedRegion, headerListWidth, 20);

    	return null;
	}

    @RequestMapping(value="/kp1300/kp1319Proc.do")
	public String kp1319Proc(final MultipartHttpServletRequest multiRequest, ModelMap model) throws Exception {
    	CommonMap cmap = commonMap.setMultipartExcel(multiRequest,"","Globals.Asset.Excel.fileStorePath","");
    	System.out.println(DateUtil.getFormatDate("yyyy-MM-dd HH:mm:ss") + " - " + "/kp1300/kp1319Proc.do" + " - " + cmap);

    	//세션 체크
		CommonMap procSessionChk = userService.procSessionChk(cmap, multiRequest);
		if (!procSessionChk.isEmpty()) {
			CommonMap cmJson = new CommonMap();
	    	cmJson.put("forwardMsg", "로그인 세션이 만료 되었습니다. 다시 접속해주세요.");
	    	cmJson.put("forwardUrl", "none");
	    	cmJson.put("prevCustomScript", "parent.fnXlsUpCallback('"+ cmJson.getString("forwardMsg") +"');");
	    	model.addAttribute("cmForward", cmJson);
	    	return "common/commonOk";
    	}

    	CommonMap cmJson = new CommonMap();
    	cmJson.put("result", "N");
    	cmJson.put("errorMsg", "");

    	int insertCnt = 0;
    	int updateCnt = 0;

    	if (!"".equals(cmap.getString("xlsFileStreFileNm"))) {
    		System.out.println("Excel Upload OK");

    		EgovExcelServiceImpl egovExcelService = new EgovExcelServiceImpl();
    		EgovExcel2007ServiceImpl egovExcel2007Service = new EgovExcel2007ServiceImpl();

    		try {
    			File xlsFile = new File(cmap.getString("xlsFileFileStreCours") + File.separator + cmap.getString("xlsFileStreFileNm"));

    			if (xlsFile != null && xlsFile.isFile()) {
    				XSSFWorkbook hwb2007 = null;
    				XSSFSheet hst2007 = null;
    				HSSFWorkbook hwb = null;
    				HSSFSheet hst = null;

    				if (egovExcel2007Service.checkWorkbook(xlsFile.getAbsolutePath())) {
    					hwb2007 = egovExcel2007Service.loadWorkbook(xlsFile.getAbsolutePath());
        				hst2007 = hwb2007.getSheetAt(0);
    				} else if (egovExcelService.checkWorkbook(xlsFile.getAbsolutePath())) {
    					hwb =  (HSSFWorkbook) egovExcelService.loadWorkbook(xlsFile.getAbsolutePath());
    					hst = hwb.getSheetAt(0);
    				}

					boolean validationCheck = true;
					CommonList xList = new CommonList();

					cmap.put("dispType", "ASSET_LIST_EXCEL_TMPL");
					CommonList excelMngList = systemService.getDispMngList(cmap);
					CommonList excelMngListOri = excelMngList;
					
					cmap.put("dispType", "ASSET_LIST_EXCEL_TMPL2");
					CommonList excelMngList2 = systemService.getDispMngList(cmap);

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
								HSSFRow hsr = hst.getRow(i);
								CommonMap xMap = new CommonMap();

								if (i == 0)
								{
									for (int k=0; k<headerListLgc.length; k++) {
										String cellValue = ExcelUtil.getCell(hsr, k).trim();
										if (!headerListLgc[k].equals(cellValue) && validationCheck) {
											validationCheck = false;
											cmJson.put("errorMsg", cellValue + "(은)는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
										}
									}
									
									if (validationCheck == false) {
										validationCheck = true;
										cmJson.put("errorMsg", "");
										excelMngList = excelMngList2;
										
										headerSize = excelMngList.size();
								    	headerListLgc = new String[headerSize];
								    	headerListPhc = new String[headerSize];
								    	dataDispType = new String[headerSize];
										for (int k=0; k<excelMngList.size(); k++) {
											CommonMap excelMng = excelMngList.getMap(k);
											headerListLgc[k] = excelMng.getString("logical_name");
											headerListPhc[k] = excelMng.getString("physical_name").toLowerCase();
											dataDispType[k] = excelMng.getString("data_disp_type");
										}
										
										for (int k=0; k<headerListLgc.length; k++) {
											String cellValue = ExcelUtil.getCell(hsr, k).trim();
											if (!headerListLgc[k].equals(cellValue) && validationCheck) {
												validationCheck = false;
												cmJson.put("errorMsg", cellValue + "(은)는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
											}
										}
									}
								}
								else
								{
									if (validationCheck) {
										boolean sw = false;
										for (int k=0; k<headerListLgc.length; k++) {
											String val = StringUtil.nvl(ExcelUtil.getCell(hsr, k), "");
	
											//엔터값 체크
											val = StringUtil.n2null(val);
	
											if (!"".equals(val)) {
												sw = true;
											}
	
											if ("TEXT".equals(dataDispType[k])) {	//문자형
												//TODO
											} else if ("NUMBER".equals(dataDispType[k])) {	//숫자형
												val = val.replaceAll("[^0-9.]","");
												val = val.split("\\.")[0];
											} else if ("DATE".equals(dataDispType[k])) {	//날짜형
												val = val.replaceAll("[^0-9]","");
												val = DateUtil.formatDate(val, "-");
											}
	
											xMap.put(headerListPhc[k], val);
										}
	
										if (sw) {
											xList.add(xMap);
										}
									}
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
										if (!headerListLgc[k].equals(cellValue) && validationCheck) {
											validationCheck = false;
											cmJson.put("errorMsg", cellValue + "(은)는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
										}
									}
									
									if (validationCheck == false) {
										validationCheck = true;
										cmJson.put("errorMsg", "");
										excelMngList = excelMngList2;
										
										headerSize = excelMngList.size();
								    	headerListLgc = new String[headerSize];
								    	headerListPhc = new String[headerSize];
								    	dataDispType = new String[headerSize];
										for (int k=0; k<excelMngList.size(); k++) {
											CommonMap excelMng = excelMngList.getMap(k);
											headerListLgc[k] = excelMng.getString("logical_name");
											headerListPhc[k] = excelMng.getString("physical_name").toLowerCase();
											dataDispType[k] = excelMng.getString("data_disp_type");
										}
										
										for (int k=0; k<headerListLgc.length; k++) {
											String cellValue = ExcelUtil.getCell(xsr, k).trim();
											if (!headerListLgc[k].equals(cellValue) && validationCheck) {
												validationCheck = false;
												cmJson.put("errorMsg", cellValue + "(은)는 엑셀 양식에 없는 항목입니다. 양식을 확인해 주세요.");
											}
										}
									}
								}
								else
								{
									if (validationCheck) {
										boolean sw = false;
										for (int k=0; k<headerListLgc.length; k++) {
											String val = StringUtil.nvl(ExcelUtil.getCell(xsr, k), "");
	
											//엔터값 체크
											val = StringUtil.n2null(val);
	
											if (!"".equals(val)) {
												sw = true;
											}
	
											if ("TEXT".equals(dataDispType[k])) {	//문자형
												//TODO
											} else if ("NUMBER".equals(dataDispType[k])) {	//숫자형
												val = val.replaceAll("[^0-9.]","");
												val = val.split("\\.")[0];
											} else if ("DATE".equals(dataDispType[k])) {	//날짜형
												val = val.replaceAll("[^0-9]","");
												val = DateUtil.formatDate(val, "-");
											}
	
											xMap.put(headerListPhc[k], val);
										}
	
										if (sw) {
											xList.add(xMap);
										}
									}
								}
							}
						}

						if (validationCheck) {
							for (int j=0; j<xList.size(); j++) {
								CommonMap xMap = xList.getMap(j);

								for (int k=0; k<excelMngList.size(); k++) {
									CommonMap excelMng = excelMngList.getMap(k);

									String nullYn = excelMng.getString("null_yn");
									String dataDispTypeStr = excelMng.getString("data_disp_type");
				    				String physicalName = excelMng.getString("physical_name").toLowerCase();
									String val = xMap.getString(excelMng.getString("physical_name")).trim();

									if ("N".equals(nullYn) && "".equals(val)) {
										validationCheck = false;
										cmJson.put("errorMsg", (j+2) + "번 라인의 " + excelMng.getString("logical_name") + "는 필수값 입니다. 빈값이 들어갈 수 없습니다.");
										break;
									}
									if ("DATE".equals(dataDispTypeStr) && !"".equals(val.trim()) && val.trim().length() != 10) {
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
							CommonList dispMngList = excelMngListOri;

				    		StringBuffer sbcol = new StringBuffer();
				    		sbcol.append("tmp_code");
				    		for (int i=0; i<dispMngList.size(); i++) {
				    			CommonMap dispMng = dispMngList.getMap(i);
				    			sbcol.append(",");
				    			sbcol.append(dispMng.getString("physicalName"));
				    		}
				    		cmap.put("colList", sbcol.toString().replaceAll("^[,]", ""));

							//임시 저장 (엑셀 테이블)
				    		if (xList.size() > 0) {
					    		CommonList tempList = new CommonList();
								for (int i=0; i<xList.size(); i++) {
									CommonMap xMap = xList.getMap(i);
									CommonMap temp = new CommonMap();

									temp.put("tmpCode", cmap.getString("tmpCode"));

									for (int k=0; k<dispMngList.size(); k++) {
						    			CommonMap dispMng = dispMngList.getMap(k);

						    			temp.put(dispMng.getString("physicalName"), xMap.getString(dispMng.getString("physicalName")));
						    		}

									tempList.add(temp);
								}

								batchMysqlService.loadDataFile("rfid_asset_excel", tempList);

								CommonList dataList = assetService.getAssetNewExcelList(cmap);

								//validationCheck = false;
								for (int k=0; k<dataList.size(); k++) {
									if (validationCheck) {
										CommonMap data = dataList.getMap(k);
										
//										if (!data.getString("assetStatusCd").matches("정상|불용")) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 자산상태는 [정상,불용] 값만 입력할 수 있습니다.");
//											validationCheck = false;
//										}
										
										if (!data.getString("assetNo").matches("^[0-9]{4}[-]{1}[0-9]{4}[-]{1}[0-9]{4}$")) {
											//5-20111228-09570
											cmJson.put("errorMsg", (k+1) + "번 라인의 자산번호의 형식이 맞지 않습니다. 자산번호는 14자리로 입력되어야 합니다.");
											validationCheck = false;
										}

//										if ("".equals(data.getString("userName"))) {111111
//											cmJson.put("errorMsg", (k+1) + "번 라인의 사번으로 사용자를 조회할 수 없습니다. 값을 확인해 주세요.");
//											validationCheck = false;
//										}
//
//										if ("".equals(data.getString("deptName"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 사번으로 사용자의 부서를 조회할 수 없습니다. 값을 확인해 주세요.");
//											validationCheck = false;
//										}
//
//										if ("퇴직".equals(data.getString("userType"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 사번은 퇴직자의 사번으로 등록할 수 없습니다.");
//											validationCheck = false;
//										}
//
//										if ("".equals(data.getString("itemName"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 품목코드를 조회할 수 없습니다. 값을 확인해 주세요.");
//											validationCheck = false;
//										}
//
//										if ("".equals(data.getString("usefulLife"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 품목코드로 내용연수를 조회할 수 없습니다. 값을 확인해 주세요.");
//											validationCheck = false;
//										}
//
//										if ("".equals(data.getString("depre_cd"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 품목코드로 감가상각법을 조회할 수 없습니다. 값을 확인해 주세요.");
//											validationCheck = false;
//										}
//
//										if ("정액법".equals(data.getString("depre_cd"))
//												&& "0".equals(data.getString("usefulLife"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 품목코드는 내용연수가 0으로 설정되어 있습니다. 품목코드 메뉴에서 내용연수를 수정해주세요.");
//											validationCheck = false;
//										}
//
//										if ("정률법".equals(data.getString("depre_cd"))
//												&& "0".equals(data.getString("usefulLife"))) {
//											cmJson.put("errorMsg", (k+1) + "번 라인의 품목코드는 내용연수가 0으로 설정되어 있습니다. 품목코드 메뉴에서 내용연수를 수정해주세요.");
//											validationCheck = false;
//										}
									}
								} //end for

								if (validationCheck) {
									/*for (int k=0; k<dataList.size(); k++) {
										CommonMap data = dataList.getMap(k);
										data.put("assetSeq", assetSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
										data.put("frstRegisterId", cmap.getString("ssUserNo"));
										data.put("lastUpdusrId", cmap.getString("ssUserNo"));
										int resultCnt = assetService.insertAsset(data);
										if (resultCnt == 1) {
											updateCnt++;
										} else if (resultCnt == 2) {
											insertCnt++;
										}
									}*/
									
									insertCnt = assetService.insertAssetExcelAll(cmap);
									updateCnt = assetService.updateAssetExcelAll(cmap);
									
								}

				    		} //end if (xList.size() > 0)

							cmJson.put("insertCnt", insertCnt);
							cmJson.put("updateCnt", updateCnt);
							cmJson.put("result", "Y");
						} //end if (validationCheck)
					}
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
    				+ "신규 : " + cmJson.getString("insertCnt") + " 건\\r\\n"
    				+ "수정 : " + cmJson.getString("updateCnt") + " 건\\r\\n";
    	}

    	cmJson.put("forwardMsg", forwardMsg);
    	cmJson.put("forwardUrl", "none");
    	cmJson.put("prevCustomScript", "parent.fnXlsUpCallback('"+ forwardMsg +"');");

    	model.addAttribute("cmForward", cmJson);
    	return "common/commonOk";
	}

}
