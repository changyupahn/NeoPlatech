package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface DeviceLogService {

	public CommonList getDeviceLogList(CommonMap cmap) throws Exception;
	
	public int getDeviceLogListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getDeviceLogView(CommonMap cmap) throws Exception;
	
	public int insertDeviceLog(CommonMap cmap) throws Exception;
	
	public int updateDeviceLog(CommonMap cmap) throws Exception;
	
	public int deleteDeviceLog(CommonMap cmap) throws Exception;
	
	public int deleteDeviceLog2(CommonMap cmap) throws Exception;
}
