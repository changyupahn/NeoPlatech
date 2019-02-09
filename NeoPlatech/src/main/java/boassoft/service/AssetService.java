package boassoft.service;

import javax.servlet.http.HttpSession;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetService {

	public void setSearchArr(CommonMap cmap) throws Exception;
	
	public String setAssetCate(CommonMap cmap) throws Exception;
	
	public CommonList getAssetList(CommonMap cmap) throws Exception;
	
	public int getAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getAssetTargetList(CommonMap cmap) throws Exception;
	
	public int getAssetTargetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetDetail(CommonMap cmap) throws Exception;
		
	public CommonMap getAssetDetail2(CommonMap cmap) throws Exception;
	
	public CommonList getAssetImgList(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetImg(CommonMap cmap) throws Exception;
	
	public void insertPrintHistory(CommonMap cmap) throws Exception;
	
	public int insertAsset(CommonMap cmap) throws Exception;
	
	public int updateAsset(CommonMap cmap) throws Exception;
	
	public int updateAssetMgr(CommonMap cmap) throws Exception;
	
	public int updateAssetUsr(CommonMap cmap) throws Exception;
	
	public int deleteAsset(CommonMap cmap) throws Exception;
	
	public int deleteAsset2(CommonMap cmap, CommonList list) throws Exception;
	
	public void deleteAssetArr(CommonMap cmap) throws Exception;
	
	public void createAssetExcelBackup(CommonMap cmap) throws Exception;
	
	public void deleteAssetExcelBackup(CommonMap cmap) throws Exception;
	
	public CommonList getAssetNewExcelList(CommonMap cmap) throws Exception;
	
	public void deleteAssetExcelAll(CommonMap cmap) throws Exception;
	
	public void insertAssetExcelBatch(CommonMap cmap, CommonList dataList, HttpSession session) throws Exception;
	
	public void insertAssetExcel(CommonMap cmap) throws Exception;
	
	public int insertAssetExcelAll(CommonMap cmap) throws Exception;
	
	public void insertAssetAdiExcelAll(CommonMap cmap) throws Exception;
	
	public int updateAssetExcelAll(CommonMap cmap) throws Exception;
	
	public int updateAssetExcelAll2(CommonMap cmap) throws Exception;
	
	public int updateAssetAdiExcelAll(CommonMap cmap) throws Exception;
	
	public int updateAssetRecovery(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateAssetInsp(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateAssetOut(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateAssetAll(CommonMap cmap, CommonList list) throws Exception;
	
	public CommonMap getAssetColCodeName(CommonMap cmap) throws Exception;
}
