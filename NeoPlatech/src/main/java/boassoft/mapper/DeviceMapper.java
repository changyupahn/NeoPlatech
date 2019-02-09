package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DeviceMapper")
public interface DeviceMapper {

	public CommonList getDeviceList(CommonMap cmap) throws Exception;
	
	public int getDeviceListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getDeviceView(CommonMap cmap) throws Exception;
	
	public void insertDevice(CommonMap cmap) throws Exception;
	
	public int updateDevice(CommonMap cmap) throws Exception;
	
	public int deleteDevice(CommonMap cmap) throws Exception;
		
}
