package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface BatchService {

	public void syncZeusAsset(CommonMap cmap) throws Exception;
	
	public void syncZeusOper(CommonMap cmap) throws Exception;
	
	public void syncZeusAs(CommonMap cmap) throws Exception;
	
	public void syncZeusCode(CommonMap cmap) throws Exception;
		
	public void syncZeusToAsset(CommonMap cmap) throws Exception;

	public void syncMisDoc(CommonMap cmap) throws Exception;
	
	public void syncMisDocTemp(CommonMap cmap) throws Exception;
	
	public void syncMisDocAppr(CommonMap cmap) throws Exception;
	
	public void syncUser(CommonMap cmap) throws Exception;
	
	public void syncDept(CommonMap cmap) throws Exception;
	
	public void syncItem(CommonMap cmap) throws Exception;
	
	public void syncAsset(CommonMap cmap) throws Exception;
	
	public void syncCust(CommonMap cmap) throws Exception;
	
	public void syncContr(CommonMap cmap) throws Exception;
	
	public void syncContrdtl(CommonMap cmap) throws Exception;
	
	
	
}
