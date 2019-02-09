package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetSubService {

	public int updateAssetSub(CommonMap cmap) throws Exception;
	
	public int updateAssetSubAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int deleteAssetSub(CommonMap cmap) throws Exception;
	
	public int deleteAssetSubAll(CommonMap cmap, CommonList list) throws Exception;
	
}
