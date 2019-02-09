package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprAssetMapper")
public interface ApprAssetMapper {

	public CommonList getApprAssetList(CommonMap cmap) throws Exception;
	
	public int getApprAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprAssetView(CommonMap cmap) throws Exception;

	public void insertApprAsset(CommonMap cmap) throws Exception;
	
	public int updateApprAsset(CommonMap cmap) throws Exception;
	
	public void deleteApprAsset(CommonMap cmap) throws Exception;
	
	public int updateAsset(CommonMap cmap) throws Exception;
	
	
}
