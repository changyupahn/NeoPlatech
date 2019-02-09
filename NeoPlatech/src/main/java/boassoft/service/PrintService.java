package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.common.CommonXmlList;

public interface PrintService {

	public CommonXmlList getPrintTargetListXml(CommonMap cmap) throws Exception;
	
	public int updatePrintTargetSuccess(CommonMap cmap) throws Exception;
	
	public int updatePrintTargetFail(CommonMap cmap) throws Exception;
	
	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception;
	
	public void insertHistory(CommonMap cmap) throws Exception;
	
	public int insertHistory2(CommonMap cmap, CommonList list) throws Exception;
	
	public int insertHistory3(CommonMap cmap, CommonList list) throws Exception;
	
	public void updatePrintYn(CommonMap cmap) throws Exception;
	
	public void insertRePrint(CommonMap cmap) throws Exception;
	
	
}
