package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.UserDeptMapper;
import boassoft.service.UserDeptService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("UserDeptService")
public class UserDeptServiceImpl extends EgovAbstractServiceImpl implements UserDeptService{

	@Resource(name="UserDeptMapper")
    private UserDeptMapper userDeptMapper;
	
	@Override
	public CommonList getUserDeptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = userDeptMapper.getUserDeptList(cmap);
		list.totalRow = userDeptMapper.getUserDeptListCnt(cmap);
		return list;
	}

	@Override
	public int getUserDeptListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.getUserDeptListCnt(cmap);
	}

	@Override
	public CommonList getUserDeptListChk(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = userDeptMapper.getUserDeptListChk(cmap);
		return list;
	}

	@Override
	public CommonList getUserDeptListAll(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = userDeptMapper.getUserDeptListAll(cmap);
		return list;
	}

	@Override
	public CommonMap getUserDeptView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.getUserDeptView(cmap);
	}

	@Override
	public int insertUserDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.insertUserDept(cmap);
	}

	@Override
	public int insertUserDeptAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("userId", cmap.getString("userId"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		userDeptMapper.insertUserDept(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateUserDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.updateUserDept(cmap);
	}

	@Override
	public int deleteUserDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.deleteUserDept(cmap);
	}

	@Override
	public int deleteUserDept2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return userDeptMapper.deleteUserDept2(cmap);
	}

	@Override
	public int deleteUserDeptAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//삭제
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("userId", cmap.getString("userId"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		userDeptMapper.deleteUserDept(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

}
