package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("LogMapper")
public interface LogMapper {

	public CommonList getLogList(CommonMap cmap) throws Exception;
	
	public int getLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getLogView(CommonMap cmap) throws Exception;
	
	public int insertLog(CommonMap cmap) throws Exception;
	
	public int updateLog(CommonMap cmap) throws Exception;
	
	public int deleteLog(CommonMap cmap) throws Exception;
	
	public int deleteLog2(CommonMap cmap) throws Exception;
		
}
