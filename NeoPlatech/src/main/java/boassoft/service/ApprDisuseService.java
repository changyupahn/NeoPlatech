package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprDisuseService {

	public CommonList getApprDisuseList(CommonMap cmap) throws Exception;
	
	public CommonList getApprDisuseAssetList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprDisuseView(CommonMap cmap) throws Exception;
	
	public int insertApprDisuse(CommonMap cmap) throws Exception;
	
	public void updateApprDisuse(CommonMap cmap) throws Exception;
	
	public void deleteApprDisuse(CommonMap cmap) throws Exception;
	
	
}
