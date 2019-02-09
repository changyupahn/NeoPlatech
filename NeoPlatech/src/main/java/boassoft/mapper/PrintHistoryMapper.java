package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PrintHistoryMapper")
public interface PrintHistoryMapper {

	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception;
	
	public int getPrintHistoryListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getPrintHistoryView(CommonMap cmap) throws Exception;
	
	public int insertPrintHistory(CommonMap cmap) throws Exception;
	
	public int updatePrintHistory(CommonMap cmap) throws Exception;
	
	public int deletePrintHistory(CommonMap cmap) throws Exception;
	
	public int deletePrintHistory2(CommonMap cmap) throws Exception;
	
	
}
