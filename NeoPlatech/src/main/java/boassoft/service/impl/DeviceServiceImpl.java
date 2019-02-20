package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.DeviceMapper;
import boassoft.service.DeviceService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;


@Service("deviceService")
public class DeviceServiceImpl extends EgovAbstractServiceImpl implements  DeviceService {

	@Resource(name="DeviceMapper")
	private DeviceMapper deviceMapper;
	
	public CommonList getDeviceList(CommonMap cmap) throws Exception{
		CommonList list = deviceMapper.getDeviceList(cmap);
		list.totalRow = deviceMapper.getDeviceListCnt(cmap);
		return null;
		
	}
	
	public CommonMap getDeviceView(CommonMap cmap) throws Exception{
		return deviceMapper.getDeviceView(cmap);
		
	}
	
	public int insertDevice(CommonMap cmap) throws Exception{
		
		deviceMapper.insertDevice(cmap);
    	return 1;
	}
	
	public int updateDevice(CommonMap cmap) throws Exception{
		return deviceMapper.updateDevice(cmap);
	}
	
	public int deleteDevice(CommonMap cmap) throws Exception{
		return deviceMapper.deleteDevice(cmap);
	}
	
}
