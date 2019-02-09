package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InspAssetMapper")
public interface InspAssetMapper {

	public CommonList getInspAssetList(CommonMap cmap) throws Exception;
	
	public int getInspAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getInspAssetView(CommonMap cmap) throws Exception;
	
	public int insertInspAsset(CommonMap cmap) throws Exception;
	
	public int updateInspAsset(CommonMap cmap) throws Exception;
	
	public int deleteInspAsset(CommonMap cmap) throws Exception;
	
	public int deleteInspAsset2(CommonMap cmap) throws Exception;
}
