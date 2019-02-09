package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprIoExtService {

	public CommonList getApprIoExtList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoExtView(CommonMap cmap) throws Exception;
	
	public int insertApprIoExt(CommonMap cmap) throws Exception;
	
	public void updateApprIoExt(CommonMap cmap) throws Exception;
	
	public void deleteApprIoExt(CommonMap cmap) throws Exception;
	
	
}
