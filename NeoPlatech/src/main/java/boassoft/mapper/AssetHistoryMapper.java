package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AssetHistoryMapper")
public interface AssetHistoryMapper {

	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception;
	
	public int getAssetHistoryListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetHistoryView(CommonMap cmap) throws Exception;
		
	public int insertAssetHistory(CommonMap cmap) throws Exception;
	
	public int updateAssetHistory(CommonMap cmap) throws Exception;
	
	public int deleteAssetHistory(CommonMap cmap) throws Exception;
	
	public int deleteAssetHistory2(CommonMap cmap) throws Exception;

	
	
	
}
