package boassoft.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import boassoft.mapper.AssetMapper;
import boassoft.mapper.DeptMapper;
import boassoft.mapper.SndMisMapper;
import boassoft.service.AssetHistoryService;
import boassoft.service.AssetService;
import boassoft.service.VirtAssetService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.SessionUtil;
import boassoft.util.StringUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("assetService")
public class AssetServiceImpl extends EgovAbstractServiceImpl implements AssetService {

	@Resource(name="AssetMapper")
    private AssetMapper assetMapper;
	
	@Resource(name="DeptMapper")
    private DeptMapper deptMapper;
	
	@Resource(name="virtAssetService")
    private VirtAssetService virtAssetService;
	
	@Resource(name="SndMisMapper")
    private SndMisMapper sndMisMapper;
	
	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;
	
	@Resource(name = "sndSeqIdGnrService")
    private EgovIdGnrService sndSeqIdGnrService;
	
	@Resource(name = "histSeqIdGnrService")
    private EgovIdGnrService histSeqIdGnrService;
	
	public void setSearchArr(CommonMap cmap) throws Exception {
		String[] sItemNameArr = cmap.getString("sItemName").split("_");
    	if (sItemNameArr.length > 1) {
    		for (int i=0; i<sItemNameArr.length; i++) {
    			if (i == 0) {
    				cmap.put("sItemName", sItemNameArr[i]);
    			} else {
    				cmap.put("sItemName" + StringUtil.lpad((i+""), 2, "0"), sItemNameArr[i]);
    			}
    		}
    	}

    	String[] sUserNameArr = cmap.getString("sUserName").split("_");
    	if (sUserNameArr.length > 1) {
    		for (int i=0; i<sUserNameArr.length; i++) {
    			if (i == 0) {
    				cmap.put("sUserName", sUserNameArr[i]);
    			} else {
    				cmap.put("sUserName" + StringUtil.lpad((i+""), 2, "0"), sUserNameArr[i]);
    			}
    		}
    	}

    	String[] sDeptNameArr = cmap.getString("sDeptName").split("_");
    	if (sDeptNameArr.length > 1) {
    		for (int i=0; i<sDeptNameArr.length; i++) {
    			if (i == 0) {
    				cmap.put("sDeptName", sDeptNameArr[i]);
    			} else {
    				cmap.put("sDeptName" + StringUtil.lpad((i+""), 2, "0"), sDeptNameArr[i]);
    			}
    		}
    	}

    	String[] sPosNameArr = cmap.getString("sPosName").split("_");
    	if (sPosNameArr.length > 1) {
    		for (int i=0; i<sPosNameArr.length; i++) {
    			if (i == 0) {
    				cmap.put("sPosName", sPosNameArr[i]);
    			} else {
    				cmap.put("sPosName" + StringUtil.lpad((i+""), 2, "0"), sPosNameArr[i]);
    			}
    		}
    	}

    	String[] searchKeywordArr = cmap.getString("searchKeyword").split("_");
    	if (searchKeywordArr.length > 1) {
    		for (int i=0; i<searchKeywordArr.length; i++) {
    			if (i == 0) {
    				cmap.put("searchKeyword", searchKeywordArr[i]);
    			} else {
    				cmap.put("searchKeyword" + StringUtil.lpad((i+""), 2, "0"), searchKeywordArr[i]);
    			}
    		}
    	}

    	//자산번호 검색 시 멀티검색(배열) 설정
    	String[] sAssetNoMulti = cmap.getString("searchKeyword").split("\r\n");
    	String[] sAssetNoMulti2 = cmap.getString("searchKeyword").split("\n");
    	if ("1".equals(cmap.getString("searchGubun")) && !"".equals(cmap.getString("searchKeyword"))) {
    		if (sAssetNoMulti.length > 1) {
	    		cmap.put("sAssetNoMulti", sAssetNoMulti);
	    		cmap.put("searchKeyword", "");
    		} else if (sAssetNoMulti2.length > 1) {
    			cmap.put("sAssetNoMulti", sAssetNoMulti2);
	    		cmap.put("searchKeyword", "");
    		}
    	}
	}

	
	public String setAssetCate(CommonMap cmap) throws Exception {
		String result = "N";

		try {
			cmap.put("asset_cate", cmap.getString("asset_cate"));
	    	cmap.put("aqusit_dt", cmap.getString("aqusit_dt").replaceAll("\\D",""));

	    	if (cmap.getString("asset_cate").length() >= 3
	    			&& cmap.getString("aqusit_dt").length() >= 8
	    			&& cmap.getString("asset_cate").length() >= 3
	    			) {
	    		cmap.put("asset_cate_st", cmap.getString("asset_cate").substring(0,3));
	    		cmap.put("aqusit_yyyy", cmap.getString("aqusit_dt").substring(0,4));

	    		result = "Y";
	    	} else {
	    		result = "N";
	    	}
		} catch (Exception e) {
			result = "N";
		}

		return result;
	}
	
	public CommonList getAssetList(CommonMap cmap) throws Exception {

		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	if (!"".equals(cmap.getString("sDeptNo"))) {
    		CommonMap param = new CommonMap();
    		param.put("searchKeyword", cmap.getString("sDeptNo"));
			CommonList deptNoList = deptMapper.getDeptNoList2(param);
			String listStr = "";
			for (int i=0; i<deptNoList.size(); i++) {
				if (!"".equals(deptNoList.getMap(i).getString("deptNo01")) && i==0) listStr += deptNoList.getMap(i).getString("deptNo01") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo02"))) listStr += deptNoList.getMap(i).getString("deptNo02") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo03"))) listStr += deptNoList.getMap(i).getString("deptNo03") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo04"))) listStr += deptNoList.getMap(i).getString("deptNo04") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo05"))) listStr += deptNoList.getMap(i).getString("deptNo05") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo06"))) listStr += deptNoList.getMap(i).getString("deptNo06") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo07"))) listStr += deptNoList.getMap(i).getString("deptNo07") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo08"))) listStr += deptNoList.getMap(i).getString("deptNo08") + ",";
			}

			//자산담당자가 아니라면 자기 부서 및 하위 부서를 제외한 상위 다른 부서는 볼 수 없다.
			if (!"GRANT_MGR".equals(cmap.getString("ssGrantRead"))) {

				param.put("searchKeyword", cmap.getString("ssDeptNo"));
				CommonList deptNoList2 = deptMapper.getDeptNoList2(param);
				String listStr2 = "";
				for (int i=0; i<deptNoList2.size(); i++) {
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo01")) && i==0) listStr2 += deptNoList2.getMap(i).getString("deptNo01") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo02"))) listStr2 += deptNoList2.getMap(i).getString("deptNo02") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo03"))) listStr2 += deptNoList2.getMap(i).getString("deptNo03") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo04"))) listStr2 += deptNoList2.getMap(i).getString("deptNo04") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo05"))) listStr2 += deptNoList2.getMap(i).getString("deptNo05") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo06"))) listStr2 += deptNoList2.getMap(i).getString("deptNo06") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo07"))) listStr2 += deptNoList2.getMap(i).getString("deptNo07") + ",";
					if (!"".equals(deptNoList2.getMap(i).getString("deptNo08"))) listStr2 += deptNoList2.getMap(i).getString("deptNo08") + ",";
				}
				cmap.put("listStr2", listStr2.replaceAll("[,]$", ""));

				if (listStr2.indexOf(cmap.getString("sDeptNo")) == -1) {
					listStr = "";
				}
	    	}

			if ("".equals(listStr)) {
				listStr = "00000";
			}

			cmap.put("listStr", listStr.replaceAll("[,]$", ""));
			cmap.put("sDeptNoArr", cmap.getArray("listStr"));
		}

    	CommonList list = assetMapper.getAssetList(cmap);
    	list.totalRow = assetMapper.getAssetListCnt(cmap);
		return list;
	}
	
	public int getAssetListCnt(CommonMap cmap) throws Exception {
		return assetMapper.getAssetListCnt(cmap);
	}

	public CommonList getAssetTargetList(CommonMap cmap) throws Exception {

		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

    	if (!"".equals(cmap.getString("sDeptNo"))) {
    		CommonMap param = new CommonMap();
    		param.put("searchKeyword", cmap.getString("sDeptNo"));
			CommonList deptNoList = deptMapper.getDeptNoList2(param);
			String listStr = "";
			for (int i=0; i<deptNoList.size(); i++) {
				if (!"".equals(deptNoList.getMap(i).getString("deptNo01")) && i==0) listStr += deptNoList.getMap(i).getString("deptNo01") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo02"))) listStr += deptNoList.getMap(i).getString("deptNo02") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo03"))) listStr += deptNoList.getMap(i).getString("deptNo03") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo04"))) listStr += deptNoList.getMap(i).getString("deptNo04") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo05"))) listStr += deptNoList.getMap(i).getString("deptNo05") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo06"))) listStr += deptNoList.getMap(i).getString("deptNo06") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo07"))) listStr += deptNoList.getMap(i).getString("deptNo07") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo08"))) listStr += deptNoList.getMap(i).getString("deptNo08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "00000";
			}
			cmap.put("listStr", listStr.replaceAll("[,]$", ""));
			cmap.put("sDeptNoArr", cmap.getArray("listStr"));
		}

    	CommonList list = assetMapper.getAssetTargetList(cmap);
    	list.totalRow = assetMapper.getAssetTargetListCnt(cmap);
		return list;
	}

	public int getAssetTargetListCnt(CommonMap cmap) throws Exception {
		return assetMapper.getAssetTargetListCnt(cmap);
	}

	public CommonMap getAssetDetail(CommonMap cmap) throws Exception {
		return assetMapper.getAssetDetail(cmap);
	}

	public CommonMap getAssetDetail2(CommonMap cmap) throws Exception {
		return assetMapper.getAssetDetail2(cmap);
	}

	public CommonList getAssetImgList(CommonMap cmap) throws Exception {
		CommonList list = assetMapper.getAssetImgList(cmap);
		return list;
	}

	public CommonMap getAssetImg(CommonMap cmap) throws Exception {
		return assetMapper.getAssetImg(cmap);
	}

	public void insertPrintHistory(CommonMap cmap) throws Exception {
		cmap.put("print_type", cmap.getString("print_type", cmap.getString("printType")));
		cmap.put("print_dt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
		cmap.put("ss_org_no", cmap.getString("ss_org_no", "1"));
		assetMapper.insertPrintHistory(cmap);
	}

	public int insertAsset(CommonMap cmap) throws Exception {
//		//자산번호
//		String assetNo = virtAssetService.getVirtAssetNo(cmap);
//		cmap.put("assetNo", assetNo);
		//자산순번
		String assetSubNo = "00";
		cmap.put("assetSubNo", assetSubNo);
//		//자산번호가 4자리로 입력 들어왔을 경우 신규등록으로 판단하여 자동 일련번호 생성 (품목코드4자리 + 일련번호 5자리)
//		if (cmap.getString("assetNo").length() == 4) {
//			String maxAssetNo = assetDAO.getMaxAssetNo(cmap).getString("maxAssetNo");
//			cmap.put("assetNo", maxAssetNo);
//		}
		//RFID번호
		String rfidNo = virtAssetService.getVirtRfidNo(cmap);
		cmap.put("rfidNo", rfidNo);
		//바코드번호
		cmap.put("barcNo", StringUtil.lpad(cmap.getString("assetSeq"),12,"0"));
		//잔존가
		cmap.put("aqusitRemainAmt", cmap.getString("aqusitAmt"));
		//단가
		cmap.put("aqusitUnitAmt", cmap.getString("aqusitAmt"));
		//수량
		cmap.put("assetCnt", "1");
//		//내용연수
//		cmap.put("usefulLife", "0");
		//불용여부
		cmap.put("disuseYn", "N");
		if ("불용".equals(cmap.getString("assetStatusCd"))) {
			cmap.put("disuseYn", "Y");
		}
//		//자산상태
//		cmap.put("assetStatusCd", "정상");
		//소프트웨어여부
		if (!"Y".equals(cmap.getString("swYn"))) {
			cmap.put("swYn", "N");
		}
		//ZEUS장비여부
		if (!"Y".equals(cmap.getString("zeusYn"))) {
			cmap.put("zeusYn", "N");
		}
		//E-TUBE장비여부
		if (!"Y".equals(cmap.getString("etubeYn"))) {
			cmap.put("etubeYn", "N");
		}
		//태그출력건수
		cmap.put("tagPrintCnt", "0");
		//취득일자, 처분일자 - 기호 삭제
		cmap.put("aqusitDt", cmap.getString("aqusitDt").replaceAll("\\D", ""));
		cmap.put("disuseDt", cmap.getString("disuseDt").replaceAll("\\D", ""));
//		//품목코드 (자산번호에 추출)
//		cmap.put("itemCd", cmap.getString("assetNo").substring(0,2));
//		//건물번호 (자산번호에 추출)
//		cmap.put("bldngNo", cmap.getString("assetNo").substring(2,4));
		
//		//물품분류명 및 장소명 조회
//		CommonMap codeData = assetDAO.getAssetColCodeName(cmap);
//		cmap.put("itemName", codeData.getString("itemName"));
//		cmap.put("bldngName", codeData.getString("bldngName"));
		
		//수정 전 정보조회 (히스토리 등록을 위해)
		CommonMap viewData = assetMapper.getAssetDetail2(cmap);
		
		//자산 등록/수정 처리
		int resultCnt = 0;
		if (viewData.isEmpty()) {
			assetMapper.insertAsset(cmap);
			resultCnt = 2;
		} else if ("N".equals(viewData.getString("useYn"))) {
			//이미 삭제되었던 자산의 경우 완전 삭제 후 신규 등록으로 처리
			assetMapper.deleteAsset2(cmap);
			assetMapper.insertAsset(cmap);
			resultCnt = 2;
		} else {
			assetMapper.updateAsset2(cmap);
			resultCnt = 1;
		}
		
		//변경 히스토리 등록
		if (resultCnt == 2) {
        	cmap.put("histTypeCd", "1");
        	cmap.put("histContent", "자산취득");
        	assetHistoryService.insertAssetHistory(cmap);
		} else if (resultCnt == 1) {
	    	assetHistoryService.insertAssetHistoryDetail(viewData, cmap, "ASSET_HIST3", false);
		}
		
		return resultCnt;
	}

	public int updateAsset(CommonMap cmap) throws Exception {
		return assetMapper.updateAsset(cmap);
	}

	public int updateAssetMgr(CommonMap cmap) throws Exception {
		return assetMapper.updateAssetMgr(cmap);
	}

	public int updateAssetUsr(CommonMap cmap) throws Exception {
		return assetMapper.updateAssetUsr(cmap);
	}

	public int deleteAsset(CommonMap cmap) throws Exception {
		return assetMapper.deleteAsset(cmap);
	}
	

	
	public int deleteAsset2(CommonMap cmap, CommonList list) throws Exception{
    	int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("assetSeq", param.getString("assetSeq"));

    		assetMapper.deleteAsset(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	public void deleteAssetArr(CommonMap cmap) throws Exception {
		assetMapper.deleteAssetArr(cmap);
	}

	public void createAssetExcelBackup(CommonMap cmap) throws Exception {
		assetMapper.createAssetExcelBackup(cmap);
	}

	public void deleteAssetExcelBackup(CommonMap cmap) throws Exception {
		cmap.put("create_dt", DateUtil.addYear(DateUtil.getFormatDate("yyyyMMdd"), -1) + "000000" );
		System.out.println("create_dt : " + cmap.getString("create_dt"));

		CommonList backupList = assetMapper.getAssetExcelBackupList(cmap);

		for (int i=0; i<backupList.size(); i++) {
			CommonMap backup = backupList.getMap(i);
			backup.put("create_dt", backup.getString("create_dt"));

			try {
				if (backup.getString("create_dt").length() == 14) {
					assetMapper.dropAssetExcelBackup01(backup);
					assetMapper.dropAssetExcelBackup02(backup);
				}
			} catch (Exception e){
				System.out.println("[ERROR] : " + e.getMessage());
			} finally {
				assetMapper.dropAssetExcelBackup03(backup);
			}
		}
	}

	public CommonList getAssetNewExcelList(CommonMap cmap) throws Exception {
		CommonList list = assetMapper.getAssetNewExcelList(cmap);
		return list;
	}

	public void deleteAssetExcelAll(CommonMap cmap) throws Exception {
		assetMapper.deleteAssetExcelAll(cmap);
	}

	public void insertAssetExcelBatch(CommonMap cmap, CommonList dataList, HttpSession session) throws Exception {
		assetMapper.insertAssetExcelBatch(cmap, dataList, session);
	}

	public void insertAssetExcel(CommonMap cmap) throws Exception {
		assetMapper.insertAssetExcel(cmap);
	}

	public int insertAssetExcelAll(CommonMap cmap) throws Exception {
		//자산순번
		String assetSubNo = "00";
		cmap.put("assetSubNo", assetSubNo);
		//단가
		cmap.put("aqusitUnitAmt", "0");
		//내용연수
		cmap.put("usefulLife", "0");
		//태그출력건수
		cmap.put("tagPrintCnt", "0");
		//취득일자, 유효기간  - 기호 삭제
		cmap.put("aqusitDt", cmap.getString("aqusitDt").replaceAll("\\D", ""));
		cmap.put("exprDt", cmap.getString("exprDt").replaceAll("\\D", ""));
		//소프트웨어여부
		cmap.put("swYn", "N");
		//제우스장비여부
		cmap.put("zeusYn", "N");
		//etube장비여부
		cmap.put("etubeYn", "N");
		//부외자산여부
		cmap.put("outAssetYn", "N");
		//불용여부
		cmap.put("disuseYn", "N");
		//검수여부
		cmap.put("inspYn", "Y");
		//태그출력회수
		cmap.put("tagPrintCnt", "0");
		//태그리딩여부
		cmap.put("tagReadYn", "N");
		//등록자/수정자ID
		cmap.put("frstRegisterId", SessionUtil.getString("userNo"));
    	cmap.put("lastUpdusrId", SessionUtil.getString("userNo"));
		
		//자산 등록 처리
		int resultCnt = assetMapper.insertAssetExcelAll(cmap);
		
		return resultCnt;
	}

	public void insertAssetAdiExcelAll(CommonMap cmap) throws Exception {
		assetMapper.insertAssetAdiExcelAll(cmap);
	}

	public int updateAssetExcelAll(CommonMap cmap) throws Exception {
		return assetMapper.updateAssetExcelAll(cmap);
	}

	public int updateAssetExcelAll2(CommonMap cmap) throws Exception {
		return assetMapper.updateAssetExcelAll2(cmap);
	}

	public int updateAssetAdiExcelAll(CommonMap cmap) throws Exception {
		return assetMapper.updateAssetAdiExcelAll(cmap);
	}

	public int updateAssetRecovery(CommonMap cmap, CommonList list) throws Exception {
    	int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		resultCnt += assetMapper.updateAssetRecovery(param);
    	}

    	return resultCnt;
	}

	public int updateAssetInsp(CommonMap cmap, CommonList list) throws Exception {
    	int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		resultCnt += assetMapper.updateAssetInsp(param);
    	}

    	return resultCnt;
	}

	public int updateAssetOut(CommonMap cmap, CommonList list) throws Exception {
    	int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		resultCnt += assetMapper.updateAssetOut(param);

    		CommonMap viewData = assetMapper.getAssetDetail(param);

    		if (!viewData.isEmpty()) {
	    		//변경정보 MIS전송
		    	CommonMap sndMap = new CommonMap();
		    	sndMap.put("sndSeq", sndSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
		    	sndMap.put("sndDiv", "4"); //1:정보변경, 2:불용신청, 3:불용승인, 4:부외자산변경
		    	sndMap.put("assetNo", viewData.getString("etisAssetNo").replaceAll("-",""));
		    	sndMap.put("mgtDeptCd", null);
		    	sndMap.put("useDeptCd", null);
		    	sndMap.put("useEmpNo", null);
		    	sndMap.put("assetStatusCd", null);
		    	sndMap.put("disuseDt", null);
		    	sndMap.put("disuseApprovalDt", null);
		    	sndMap.put("outAssetYn", "Y");
		    	sndMap.put("sndYn", "N");
		    	sndMisMapper.insertSndMis(sndMap);
    		}
    	}

    	return resultCnt;
	}

	public int updateAssetAll(CommonMap cmap, CommonList list) throws Exception{
    	int resultCnt = 0;

    	//수정
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("topDeptNo", cmap.getString("topDeptNo"));
    		param.put("topDeptName", cmap.getString("topDeptName"));
    		param.put("deptNo", cmap.getString("deptNo"));
    		param.put("deptName", cmap.getString("deptName"));
    		param.put("userNo", cmap.getString("userNo"));
    		param.put("userName", cmap.getString("userName"));
    		param.put("posNo", cmap.getString("posNo"));
    		param.put("posName", cmap.getString("posName"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		//수정 전 정보조회 (히스토리 등록을 위해)
    		CommonMap viewData = assetMapper.getAssetDetail(param);

    		//저장
    		assetMapper.updateAssetAll(param);

    		//변경 히스토리 등록
	    	assetHistoryService.insertAssetHistoryDetail(viewData, param, "ASSET_HIST2", false);

    		resultCnt++;
    	}

    	return resultCnt;
	}
	
	public CommonMap getAssetColCodeName(CommonMap cmap) throws Exception {
		return assetMapper.getAssetColCodeName(cmap);
	}

}
