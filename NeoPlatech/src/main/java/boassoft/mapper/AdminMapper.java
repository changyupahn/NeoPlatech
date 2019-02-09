package boassoft.mapper;

import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;



@Mapper("AdminMapper")
public interface AdminMapper {

	public CommonMap getValidationQuery(CommonMap cmap) throws Exception; 
	
	public CommonMap getAdminLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminView(CommonMap cmap) throws Exception;
	
	public CommonList getAdminList(CommonMap cmap) throws Exception;
	
	public int getAdminListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getAdminPrintList(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminPrintN(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminPrintS(CommonMap cmap) throws Exception;

	public void deleteUserPrint(CommonMap cmap) throws Exception;
	
	public void insertUserPrintN(CommonMap cmap) throws Exception;
	
	public void insertUserPrintS(CommonMap cmap) throws Exception;
	
	public void insertUser(CommonMap cmap) throws Exception;
	
	public void updateUser(CommonMap cmap) throws Exception;
	
	public void deleteUser(CommonMap cmap) throws Exception;
}
