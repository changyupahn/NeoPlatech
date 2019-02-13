package boassoft.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.UserMapper;
import boassoft.service.GrantMenuService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import boassoft.util.SessionUtil;

@Service("UserService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

	
	@Resource(name="UserMapper")
	private UserMapper userMapper;
	
	@Resource(name="GrantMenuService")
    private GrantMenuService grantMenuService;
	
	public CommonMap gridSessionChk(CommonMap cmap, HttpServletRequest request) throws Exception{
		CommonMap result = new CommonMap();
		
		//세션값
		cmap.put("ssUserNo", SessionUtil.getString("userNo"));
		cmap.put("ssDeptNo", SessionUtil.getString("deptNo"));

		//세션 체크
    	if ("".equals(cmap.getString("ssUserNo"))
    			) {
        	result.put("resultList", new CommonList());
        	result.put("totalRow", -1);
    	} else {
    		//메뉴 권한 설정
        	grantMenuService.setGrantMenu(cmap, request);
    	}
		
		return result;
		
	}
	
	public CommonMap procSessionChk(CommonMap cmap, HttpServletRequest request) throws Exception{
		
		CommonMap result = new CommonMap();

    	//세션값
		cmap.put("ssUserNo", SessionUtil.getString("userNo"));
		cmap.put("ssDeptNo", SessionUtil.getString("deptNo"));
		
		
		//세션 체크
    	if ("".equals(cmap.getString("ssUserNo"))
    			) {
    		CommonMap resultMap = new CommonMap();
    		resultMap.put("ret", "ERR");
    		resultMap.put("retmsg", "로그인 세션이 만료되었습니다. 다시 로그인 해주세요.");
    		return resultMap;
    	} else {
    		//메뉴 권한 설정
        	grantMenuService.setGrantMenu(cmap, request);
    	}

    	return result;
		
		
	}
	
	
	public CommonMap getUserLoginView(CommonMap cmap) throws Exception{
		return userMapper.getUserLoginView(cmap);
	}
	
	public CommonMap getUserKeyLoginView(CommonMap cmap) throws Exception{
		return userMapper.getUserKeyLoginView(cmap);
	}
	
	public CommonMap getUserIdLoginView(CommonMap cmap) throws Exception{
		return userMapper.getUserIdLoginView(cmap);
	}
	
	public CommonList getUserList(CommonMap cmap) throws Exception {
		CommonList list = userMapper.getUserList(cmap);
		list.totalRow = userMapper.getUserListCnt(cmap);
		return list;
	}
	
	public int getUserListCnt(CommonMap cmap) throws Exception {
		return userMapper.getUserListCnt(cmap);
	}
	
	public CommonMap getUserView(CommonMap cmap) throws Exception{
		return userMapper.getUserView(cmap);	
	}
	
	public int insertUser(CommonMap cmap) throws Exception{
		return userMapper.insertUser(cmap);
	}
	
	public int updateUser(CommonMap cmap) throws Exception{
		return userMapper.updateUser(cmap);
	}
	
	public int deleteUser(CommonMap cmap) throws Exception{
		return userMapper.deleteUser(cmap);
	}
	
	public int deleteUser2(CommonMap cmap) throws Exception{
    	return userMapper.deleteUser2(cmap);
	}
	
	public int deleteUserAll(CommonMap cmap) throws Exception{
		return userMapper.deleteUserAll(cmap);
	}
	
	public int updateUserGrant(CommonMap cmap) throws Exception{
	 	return userMapper.updateUserGrant(cmap);
	}
	
	public int updateUserGrantAll(CommonMap cmap, CommonList list) throws Exception{
		
		int resultCnt = 0;
		
		//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		
    		//사용자 정보 조회
    		CommonMap userView = userMapper.getUserView(param);
    		
    		param.put("grantNo", cmap.getString("grantNo"));
    		param.put("userNo", param.getString("userNo"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));
    		
    		resultCnt += userMapper.updateUserGrant(param);
    		
    		//권한변경 로그 기록
    		CommonMap logMap = new CommonMap();
    		
    		logMap.put("logType", "GRANT_MODIFY");
    		logMap.put("logDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		logMap.put("logCont", String.format("[%s][%s]의 권한을 [%s]에서 [%s]로 변경"
    				, userView.getString("userNo")
    				, userView.getString("userName")
    				, userView.getString("grantNo")
    				, cmap.getString("grantNo")
    				));
    		logMap.put("logIp", cmap.getString("logIp"));
    		//logService.insertLog(logMap);
    	}
    	
    	return resultCnt;
	}
}
