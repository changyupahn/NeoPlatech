package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprIoOutService {

	public CommonList getApprIoOutList(CommonMap cmap) throws Exception;
	
	public CommonList getApprIoOutAssetList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoOutView(CommonMap cmap) throws Exception;
	
	public int insertApprIoOut(CommonMap cmap) throws Exception;
	
	public void updateApprIoOut(CommonMap cmap) throws Exception;
	
	public void deleteApprIoOut(CommonMap cmap) throws Exception;
	
	
}
