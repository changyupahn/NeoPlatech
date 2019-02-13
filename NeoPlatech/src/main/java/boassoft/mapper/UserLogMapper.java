package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("UserLogMapper")
public interface UserLogMapper {

	public CommonList getUserLogList(CommonMap cmap) throws Exception;
	
	public int getUserLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getUserLogView(CommonMap cmap) throws Exception;
	
	public int insertUserLog(CommonMap cmap) throws Exception;
	
	public int updateUserLog(CommonMap cmap) throws Exception;
	
	public int deleteUserLog(CommonMap cmap) throws Exception;
	
	public int deleteUserLog2(CommonMap cmap) throws Exception;
	
	
}
