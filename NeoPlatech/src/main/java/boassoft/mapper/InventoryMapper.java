package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InventoryMapper")
public interface InventoryMapper {

	public CommonList getInventoryYearList(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception;
	
	public CommonList getInventoryList(CommonMap cmap) throws Exception;
	
	public int getInventoryListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryView(CommonMap cmap) throws Exception;
	
	public int getMaxJaemulNo(CommonMap cmap) throws Exception;
	
	public void insertInventoryMaster(CommonMap cmap) throws Exception;
	
	public void insertInventoryDetail(CommonMap cmap) throws Exception;
	
	public void insertInventoryDetailSync(CommonMap cmap) throws Exception;
	
	public int deleteInventoryDetailSync(CommonMap cmap) throws Exception;
	
	public void updateInventory(CommonMap cmap) throws Exception;
	
	public int deleteInventory(CommonMap cmap) throws Exception;
	
	public CommonList getInventoryDetailList(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryDetailView(CommonMap cmap) throws Exception;
	
	public int getInventoryDetailListCnt(CommonMap cmap) throws Exception;
	
	public int updateTargetYn(CommonMap cmap) throws Exception;
	
	public void updateInventoryPrint(CommonMap cmap) throws Exception;
	
	public void insertPrintHistory(CommonMap cmap) throws Exception;
	
	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception;
	
	public void insertRequest(CommonMap cmap) throws Exception;
	
	public int updateRequest(CommonMap cmap) throws Exception;
	
	public int updateRequestCon(CommonMap cmap) throws Exception;
	
	public CommonMap selectRequest(CommonMap cmap) throws Exception;
	
	public CommonList selectRequestCon(CommonMap cmap) throws Exception;
	
	public void deleteRequest(CommonMap cmap) throws Exception;
	
	public int deleteRequestCon(CommonMap cmap) throws Exception;
		
}
