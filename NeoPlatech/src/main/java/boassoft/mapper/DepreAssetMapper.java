package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DepreAssetMapper")
public interface DepreAssetMapper {

	public int getDepreAssetTargetCnt(CommonMap cmap) throws Exception;
	
	public int insertDepreAsset(CommonMap cmap) throws Exception;
	
	public int updateDepreAsset(CommonMap cmap) throws Exception;
	
	public CommonList getDepreAssetTotal(CommonMap cmap) throws Exception;
	
	public CommonMap getDepreAssetTotalSum(CommonMap cmap) throws Exception;
	
	public CommonList getDepreAssetList(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain2(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain2Year(CommonMap cmap) throws Exception;
	
	public int deleteDepreAsset2(CommonMap cmap) throws Exception;
	
	
}
