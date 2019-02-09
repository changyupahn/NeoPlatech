package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetAsService {

	public CommonList getAssetAsList(CommonMap cmap) throws Exception;
	
	public int getAssetAsListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetAsView(CommonMap cmap) throws Exception;
	
	public int insertAssetAs(CommonMap cmap) throws Exception;
	
	public int updateAssetAs(CommonMap cmap) throws Exception;
	
	public int deleteAssetAs(CommonMap cmap) throws Exception;
	
	public int deleteAssetAs2(CommonMap cmap) throws Exception;
	
	
}
