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

	public int deleteItem(CommonMap cmap) throws Exception;
	
	public int deleteAsset(CommonMap cmap) throws Exception;
	
	public int deleteMisDeptAll(CommonMap cmap) throws Exception;
	
	public int deleteMisItemAll(CommonMap cmap) throws Exception;
	
	public int deleteMisAssetAll(CommonMap cmap) throws Exception;
	
	public int insertCust(CommonMap cmap) throws Exception;
	
	public int updateCust(CommonMap cmap) throws Exception;
	
	public int deleteCust(CommonMap cmap) throws Exception;
	
	public int deleteMisCustAll(CommonMap cmap) throws Exception;
	
	public int insertContr(CommonMap cmap) throws Exception;
	
	public int updateContr(CommonMap cmap) throws Exception;
	
	public int deleteContr(CommonMap cmap) throws Exception;
	
	public int deleteMisContrAll(CommonMap cmap) throws Exception;
	
	public int insertContrdtl(CommonMap cmap) throws Exception;
	
	public int updateContrdtl(CommonMap cmap) throws Exception;
	
	public int deleteContrdtl(CommonMap cmap) throws Exception;
	
	public int deleteMisContrdtlAll(CommonMap cmap) throws Exception;
	
	public int insertMisUserAll(CommonMap cmap) throws Exception;
	
	public int insertMisDeptAll(CommonMap cmap) throws Exception;
	
	public int insertMisItemAll(CommonMap cmap) throws Exception;
	
	public int insertMisAssetAll(CommonMap cmap) throws Exception;
	
	public int mergeUser(CommonMap cmap) throws Exception;
	
	public int mergeDept(CommonMap cmap) throws Exception;
	
	public int mergeItem(CommonMap cmap) throws Exception;
	
	public int mergeAsset(CommonMap cmap) throws Exception;
	
	public int mergeMisAsset(CommonMap cmap) throws Exception;
		
}
