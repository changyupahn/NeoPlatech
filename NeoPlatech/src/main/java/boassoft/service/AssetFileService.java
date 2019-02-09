package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AssetFileService {

	public CommonList getAssetFileList(CommonMap cmap) throws Exception;
	
	public int getAssetFileListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetFileView(CommonMap cmap) throws Exception;
	
	public int insertAssetFile(CommonMap cmap) throws Exception;
	
	public int updateAssetFile(CommonMap cmap) throws Exception;
	
	public int deleteAssetFile(CommonMap cmap) throws Exception;
	
	public int deleteAssetFile2(CommonMap cmap) throws Exception;
	
	
}
