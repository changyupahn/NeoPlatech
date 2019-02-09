package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.UserLogMapper;
import boassoft.service.UserLogService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("UserLogService")
public class UserLogServiceImpl extends EgovAbstractServiceImpl implements UserLogService {

	@Resource(name="userLogMapper")
    private UserLogMapper userLogMapper;
	
	@Override
	public CommonList getUserLogList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = userLogMapper.getUserLogList(cmap);
		list.totalRow = userLogMapper.getUserLogListCnt(cmap);
		return list;
	}

	@Override
	public int getUserLogListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.getUserLogListCnt(cmap);
	}

	@Override
	public CommonMap getUserLogView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.getUserLogView(cmap);
	}

	@Override
	public int insertUserLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.insertUserLog(cmap);
	}
		
	@Override
	public int updateUserLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.updateUserLog(cmap);
	}

	@Override
	public int deleteUserLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.deleteUserLog(cmap);
	}

	@Override
	public int deleteUserLog2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userLogMapper.deleteUserLog2(cmap);
	}

}
