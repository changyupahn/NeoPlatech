package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprAssetService {

	public CommonList getApprAssetList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprAssetView(CommonMap cmap) throws Exception;
	
	public void insertApprAsset(CommonMap cmap) throws Exception;
	
	public int insertApprAssetAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateApprAsset(CommonMap cmap) throws Exception;
	
	public int updateApprAssetAll(CommonMap cmap, CommonList list) throws Exception;
	
	public void deleteApprAsset(CommonMap cmap) throws Exception;
	
	public int updateAsset(CommonMap cmap) throws Exception;
	
	
}
