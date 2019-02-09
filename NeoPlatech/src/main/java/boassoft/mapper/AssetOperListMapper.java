package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AssetOperListMapper")
public interface AssetOperListMapper {

	public CommonList getAssetOperListList(CommonMap cmap) throws Exception;
	
	public int getAssetOperListListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetOperListView(CommonMap cmap) throws Exception;
	
	public int insertAssetOperList(CommonMap cmap) throws Exception;
	
	public int updateAssetOperList(CommonMap cmap) throws Exception;
	
	public int deleteAssetOperList(CommonMap cmap) throws Exception;
	
	public int deleteAssetOperList2(CommonMap cmap) throws Exception;
	
	
}
