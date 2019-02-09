package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface LogService {

	public CommonList getLogList(CommonMap cmap) throws Exception;
	
	public int getLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getLogView(CommonMap cmap) throws Exception;
	
	public int insertLog(CommonMap cmap) throws Exception;
	
	public int updateLog(CommonMap cmap) throws Exception;
	
	public int deleteLog(CommonMap cmap) throws Exception;
	
	public int deleteLog2(CommonMap cmap) throws Exception;
	
	
}
