package boassoft.service;

import javax.servlet.http.HttpServletRequest;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface UserService {

	public CommonMap gridSessionChk(CommonMap cmap, HttpServletRequest request) throws Exception;
	
	public CommonMap procSessionChk(CommonMap cmap, HttpServletRequest request) throws Exception;
	
	public CommonMap getUserLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getUserKeyLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getUserIdLoginView(CommonMap cmap) throws Exception;
	
	public CommonList getUserList(CommonMap cmap) throws Exception;
	
	public int getUserListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getUserView(CommonMap cmap) throws Exception;
	
	public int insertUser(CommonMap cmap) throws Exception;
	
	public int updateUser(CommonMap cmap) throws Exception;
	
	public int deleteUser(CommonMap cmap) throws Exception;
	
	public int deleteUser2(CommonMap cmap) throws Exception;
	
	public int deleteUserAll(CommonMap cmap) throws Exception;
	
	public int updateUserGrant(CommonMap cmap) throws Exception;
	
	public int updateUserGrantAll(CommonMap cmap, CommonList list) throws Exception;
		
}
