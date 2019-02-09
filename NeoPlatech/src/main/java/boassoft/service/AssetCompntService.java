package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetCompntService {

	public CommonList getAssetCompntList(CommonMap cmap) throws Exception;
	
	public int getAssetCompntListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetCompntView(CommonMap cmap) throws Exception;
	
	public int insertAssetCompnt(CommonMap cmap) throws Exception;
	
	public int updateAssetCompnt(CommonMap cmap) throws Exception;
	
	public int deleteAssetCompnt(CommonMap cmap) throws Exception;
	
	public int deleteAssetCompnt2(CommonMap cmap) throws Exception;
	
}
