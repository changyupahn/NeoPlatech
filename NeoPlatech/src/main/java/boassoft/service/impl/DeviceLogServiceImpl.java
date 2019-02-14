package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.DeviceLogMapper;
import boassoft.service.DeviceLogService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("deviceLogService")
public class DeviceLogServiceImpl extends EgovAbstractServiceImpl  implements DeviceLogService{

	@Resource(name="DeviceLogMapper")
    private DeviceLogMapper deviceLogMapper;
	
	@Override
	public CommonList getDeviceLogList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = deviceLogMapper.getDeviceLogList(cmap);
		list.totalRow = deviceLogMapper.getDeviceLogListCnt(cmap);
		return list;
	}

	@Override
	public int getDeviceLogListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.getDeviceLogListCnt(cmap);
	}

	@Override
	public CommonMap getDeviceLogView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.getDeviceLogView(cmap);
	}

	@Override
	public int insertDeviceLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.insertDeviceLog(cmap);
	}

	@Override
	public int updateDeviceLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.updateDeviceLog(cmap);
	}

	@Override
	public int deleteDeviceLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.deleteDeviceLog(cmap);
	}

	@Override
	public int deleteDeviceLog2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deviceLogMapper.deleteDeviceLog2(cmap);
	}

}
