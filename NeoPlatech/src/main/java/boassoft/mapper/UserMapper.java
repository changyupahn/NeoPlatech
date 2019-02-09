package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UserMapper")
public interface UserMapper {

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
	
}
