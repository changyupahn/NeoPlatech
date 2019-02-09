package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface UserLogMapper {

	public CommonList getUserLogList(CommonMap cmap) throws Exception;
	
	public int getUserLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getUserLogView(CommonMap cmap) throws Exception;
	
	public int insertUserLog(CommonMap cmap) throws Exception;
	
	public int updateUserLog(CommonMap cmap) throws Exception;
	
	public int deleteUserLog(CommonMap cmap) throws Exception;
	
	public int deleteUserLog2(CommonMap cmap) throws Exception;
	
	
}
