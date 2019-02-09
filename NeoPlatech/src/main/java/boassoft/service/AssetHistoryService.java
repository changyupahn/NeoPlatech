package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetHistoryService {

	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception;
	
	public int getAssetHistoryListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetHistoryView(CommonMap cmap) throws Exception;
	
	public int insertAssetHistory(CommonMap cmap) throws Exception;
	
	public int insertAssetHistory(CommonMap oriAsset, CommonMap cmap) throws Exception;
	
	public int insertAssetHistoryDetail(CommonMap oriAsset, CommonMap cmap, String dispType, boolean allowNullValue) throws Exception;
}
