package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface InventoryService {

	public CommonList getInventoryYearList(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception;
	
	public CommonList getInventoryList(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryView(CommonMap cmap) throws Exception;
	
	public int insertInventoryMaster(CommonMap cmap) throws Exception;
	
	public void syncInventoryDetail(CommonMap cmap) throws Exception;
	
	public void updateInventory(CommonMap cmap) throws Exception;
	
	public int deleteInventory(CommonMap cmap) throws Exception;
	
	public CommonList getInventoryDetailList(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryDetailView(CommonMap cmap) throws Exception;
	
	public int updateTargetYn(CommonMap cmap) throws Exception;
	
	public int updateTargetYnChk(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateTargetYnAll(CommonMap cmap) throws Exception;
	
	public void updateInventoryPrint(CommonMap cmap) throws Exception;
	
	public void insertPrintHistory(CommonMap cmap) throws Exception;
	
	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception;
	
	public int updateRequest(CommonMap cmap) throws Exception;
	
	public int updateRequestCon(CommonMap cmap) throws Exception;
	
	public CommonMap selectRequest(CommonMap cmap) throws Exception;
	
	public CommonList selectRequestCon(CommonMap cmap) throws Exception;
	
	public int deleteRequestCon(CommonMap cmap, CommonList list) throws Exception;
	
	
}
