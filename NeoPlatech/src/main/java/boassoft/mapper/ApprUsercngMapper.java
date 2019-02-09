package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprUsercngMapper")
public interface ApprUsercngMapper {

	public CommonList getApprUsercngList(CommonMap cmap) throws Exception;
	
	public int getApprUsercngListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getApprUsercngAssetList(CommonMap cmap) throws Exception;
	
	public int getApprUsercngAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprUsercngView(CommonMap cmap) throws Exception;
	
	public void insertApprUsercng(CommonMap cmap) throws Exception;
	
	public void updateApprUsercng(CommonMap cmap) throws Exception;
	
	public void updateApprUsercng2(CommonMap cmap) throws Exception;
	
	public void deleteApprUsercng(CommonMap cmap) throws Exception;
	
	
}
