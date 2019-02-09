package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface DepreAssetService {

	public int getDepreAssetTargetCnt(CommonMap cmap) throws Exception;
	
	public int insertDepreAsset(CommonMap cmap) throws Exception;
	
	public CommonList getDepreAssetTotal(CommonMap cmap) throws Exception;
	
	public CommonMap getDepreAssetTotalSum(CommonMap cmap) throws Exception;
	
	public CommonList getDepreAssetList(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain2(CommonMap cmap) throws Exception;
	
	public int updateDepreAssetRemain2Year(CommonMap cmap) throws Exception;
	
	public int deleteDepreAsset2(CommonMap cmap) throws Exception;
	
	
}
