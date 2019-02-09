package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface DeviceService {

	public CommonList getDeviceList(CommonMap cmap) throws Exception;
	
	public CommonMap getDeviceView(CommonMap cmap) throws Exception;
	
	public int insertDevice(CommonMap cmap) throws Exception;
	
	public int updateDevice(CommonMap cmap) throws Exception;
	
	public int deleteDevice(CommonMap cmap) throws Exception;
	
	
}
