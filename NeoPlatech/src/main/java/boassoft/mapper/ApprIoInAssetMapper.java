package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ApprIoInAssetMapper")
public interface ApprIoInAssetMapper {

	public CommonList getApprIoInAssetList(CommonMap cmap) throws Exception;
	
	public int getApprIoInAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoInAssetView(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoInAssetAllYn(CommonMap cmap) throws Exception;
	
	public int insertApprIoInAsset(CommonMap cmap) throws Exception;
	
	public int updateApprIoInAsset(CommonMap cmap) throws Exception;
	
	public int deleteApprIoInAsset(CommonMap cmap) throws Exception;
	
	public int deleteApprIoInAsset2(CommonMap cmap) throws Exception;
}
