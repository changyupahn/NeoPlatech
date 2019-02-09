package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface UserDeptService {

	public CommonList getUserDeptList(CommonMap cmap) throws Exception;
	
	public int getUserDeptListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptListChk(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptListAll(CommonMap cmap) throws Exception;
	
	public CommonMap getUserDeptView(CommonMap cmap) throws Exception;
	
	public int insertUserDept(CommonMap cmap) throws Exception;
	
	public int insertUserDeptAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateUserDept(CommonMap cmap) throws Exception;
	
	public int deleteUserDept(CommonMap cmap) throws Exception;
	
	public int deleteUserDept2(CommonMap cmap) throws Exception;
	
	public int deleteUserDeptAll(CommonMap cmap, CommonList list) throws Exception;
	
	
}
