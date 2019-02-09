package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface PrintHistoryService {

	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception;
	
	public int getPrintHistoryListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getPrintHistoryView(CommonMap cmap) throws Exception;
	
	public int insertPrintHistory(CommonMap cmap) throws Exception;
	
	public int insertPrintHistoryAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updatePrintHistory(CommonMap cmap) throws Exception;
	
	public int deletePrintHistory(CommonMap cmap) throws Exception;
	
	public int deletePrintHistory2(CommonMap cmap) throws Exception;
	
	
}
