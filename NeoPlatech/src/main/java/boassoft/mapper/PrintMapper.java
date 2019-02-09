package boassoft.mapper;

import java.util.List;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PrintMapper")
public interface PrintMapper {

	public List getPrintTargetListXml(CommonMap cmap) throws Exception;
	
	public int updatePrintTargetListXml(CommonMap cmap) throws Exception;
	
	public int updatePrintTargetSuccess(CommonMap cmap) throws Exception;
	
	public int updatePrintTargetFail(CommonMap cmap) throws Exception;
	
	public int updateRePrintTargetListXml(CommonMap cmap) throws Exception;
	
	public int updateRePrintTargetSuccess(CommonMap cmap) throws Exception;
	
	public int updateRePrintTargetFail(CommonMap cmap) throws Exception;
	
	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception;
	
	public int getPrintHistoryListCnt(CommonMap cmap) throws Exception;
	
	public void insertHistory(CommonMap cmap) throws Exception;
	
	public int insertHistory2(CommonMap cmap) throws Exception;
	
	public int insertHistoryTmp(CommonMap cmap) throws Exception;
	
	public void insertHistoryTmpApply(CommonMap cmap) throws Exception;
	
	public void updatePrintYn(CommonMap cmap) throws Exception;
	
	public void insertRePrint(CommonMap cmap) throws Exception;
}
