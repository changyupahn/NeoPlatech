package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface VirtAssetService {

	public String getVirtAssetNo(CommonMap cmap) throws Exception;
	
	public String getVirtRfidNo(CommonMap cmap) throws Exception;
	
	public CommonList getVirtAssetList(CommonMap cmap) throws Exception;
	
	public int getVirtAssetListCnt(CommonMap cmap) throws Exception;
	
	public int insertVirtAsset(CommonMap cmap) throws Exception;
	
	public int deleteVirtAsset(CommonMap cmap) throws Exception;
	
	public int deleteVirtAssetAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateVirtAssetConfirm(CommonMap cmap) throws Exception;
	
	public int updateVirtAssetCancel(CommonMap cmap) throws Exception;
	
}
