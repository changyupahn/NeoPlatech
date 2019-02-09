package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AdminService {

	public CommonMap getValidationQuery(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminView(CommonMap cmap) throws Exception;
	
	public CommonList getAdminList(CommonMap cmap) throws Exception;
	
	public CommonList getAdminPrintList(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminPrintN(CommonMap cmap) throws Exception;
	
	public CommonMap getAdminPrintS(CommonMap cmap) throws Exception;
	
	public void insertAdmin(CommonMap cmap) throws Exception;
	
	public void updateAdmin(CommonMap cmap) throws Exception;
	
	public void deleteAdmin(CommonMap cmap) throws Exception;

	
}
