package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface InspAssetService {

	public CommonList getInspAssetList(CommonMap cmap) throws Exception;
	
	public int getInspAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getInspAssetView(CommonMap cmap) throws Exception;
	
	public int insertInspAsset(CommonMap cmap) throws Exception;
	
	public int insertInspAssetAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateInspAsset(CommonMap cmap) throws Exception;
	
	public int deleteInspAsset(CommonMap cmap) throws Exception;
	
	public int deleteInspAsset2(CommonMap cmap) throws Exception;
	
	public int deleteInspAsset2All(CommonMap cmap, CommonList list) throws Exception;
}
