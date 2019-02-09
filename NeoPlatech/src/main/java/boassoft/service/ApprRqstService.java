package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ApprRqstService {
	
	public CommonList getApprRqstList(CommonMap cmap) throws Exception;
	
	public CommonMap getApprRqstView(CommonMap cmap) throws Exception;
	
	public void insertApprRqst(CommonMap cmap) throws Exception;
	
	public void updateApprRqst(CommonMap cmap) throws Exception;
	
	public void deleteApprRqst(CommonMap cmap) throws Exception;
	
	public int updateApprRqstStatusCd(CommonMap cmap) throws Exception;
	
	
}
