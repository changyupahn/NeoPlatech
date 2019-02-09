package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprIoInService {

	public CommonList getApprIoInList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoInView(CommonMap cmap) throws Exception;
	
	public int insertApprIoIn(CommonMap cmap) throws Exception;
	
	public int insertApprIoInMng(CommonMap cmap, CommonList list) throws Exception;
	
	public void updateApprIoIn(CommonMap cmap) throws Exception;
	
	public void deleteApprIoIn(CommonMap cmap) throws Exception;
}
