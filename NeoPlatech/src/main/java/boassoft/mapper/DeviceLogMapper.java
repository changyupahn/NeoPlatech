package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DeviceLogMapper")
public interface DeviceLogMapper {

	public CommonList getDeviceLogList(CommonMap cmap) throws Exception;
	
	public int getDeviceLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getDeviceLogView(CommonMap cmap) throws Exception;
	
	public int insertDeviceLog(CommonMap cmap) throws Exception;
	
	public int updateDeviceLog(CommonMap cmap) throws Exception;
	
	public int deleteDeviceLog(CommonMap cmap) throws Exception;
	
	public int deleteDeviceLog2(CommonMap cmap) throws Exception;
		
}
