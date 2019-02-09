package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AssetCompntMapper")
public interface AssetCompntMapper {

	public CommonList getAssetCompntList(CommonMap cmap) throws Exception;
	
	public int getAssetCompntListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetCompntView(CommonMap cmap) throws Exception;
	
	public int insertAssetCompnt(CommonMap cmap) throws Exception;
	
	public int updateAssetCompnt(CommonMap cmap) throws Exception;
	
	public int deleteAssetCompnt(CommonMap cmap) throws Exception;
	
	public int deleteAssetCompnt2(CommonMap cmap) throws Exception;
	
}
