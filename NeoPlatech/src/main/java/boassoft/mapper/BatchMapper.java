package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("BatchMapper")
public interface BatchMapper {

	public void syncZeusToAsset(CommonMap cmap) throws Exception;
	
	public CommonList getDocNList(CommonMap cmap) throws Exception;
	
	public void insertDoc(CommonMap cmap) throws Exception;
	
	public int updateDoc(CommonMap cmap) throws Exception;
	
	public int updateDocUpdateynY(CommonMap cmap) throws Exception;
	
	public int updateApprRqst(CommonMap cmap) throws Exception;
	
	public int updateApprIoExt(CommonMap cmap) throws Exception;
	
	public int updateApprIoIn(CommonMap cmap) throws Exception;
	
	public int updateApprDisuse(CommonMap cmap) throws Exception;
	
	public CommonMap getApprUsercngView(CommonMap cmap) throws Exception;
	
	public int updateAssetUsercng(CommonMap cmap) throws Exception;
	
	public int updateAssetIoOut(CommonMap cmap) throws Exception;
	
	public int updateAssetIoIn(CommonMap cmap) throws Exception;
	
	public int updateAssetDisuse(CommonMap cmap) throws Exception;
	
	public void updateAssetStatusCd(CommonMap cmap) throws Exception;
	
	public int insertUser(CommonMap cmap) throws Exception;
	
	public int updateUser(CommonMap cmap) throws Exception;
	
	public int deleteUser(CommonMap cmap) throws Exception;
	
	public int deleteMisUserAll(CommonMap cmap) throws Exception;
	
	public int insertDept(CommonMap cmap) throws Exception;
	
	public int updateDept(CommonMap cmap) throws Exception;
	
	public int deleteDept(CommonMap cmap) throws Exception;
	
}
