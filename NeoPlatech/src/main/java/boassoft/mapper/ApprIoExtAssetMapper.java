package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprIoExtAssetMapper")
public interface ApprIoExtAssetMapper {

	public CommonList getApprIoExtAssetList(CommonMap cmap) throws Exception;
	
	public int getApprIoExtAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoExtAssetView(CommonMap cmap) throws Exception;
	
	public int insertApprIoExtAsset(CommonMap cmap) throws Exception;
	
	public int updateApprIoExtAsset(CommonMap cmap) throws Exception;
	
	public int deleteApprIoExtAsset(CommonMap cmap) throws Exception;
	
	public int deleteApprIoExtAsset2(CommonMap cmap) throws Exception;
	
	
}
