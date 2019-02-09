package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprUsercngService {

	public CommonList getApprUsercngList(CommonMap cmap) throws Exception;
	
	public CommonList getApprUsercngAssetList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprUsercngView(CommonMap cmap) throws Exception;
	
	public int insertApprUsercng(CommonMap cmap) throws Exception;
	
	public void updateApprUsercng(CommonMap cmap) throws Exception;
	
	public void updateApprUsercng2(CommonMap cmap) throws Exception;
	
	public void deleteApprUsercng(CommonMap cmap) throws Exception;
	
	public void updateApprUsercngConfirm(CommonMap cmap) throws Exception;
	
	
}
