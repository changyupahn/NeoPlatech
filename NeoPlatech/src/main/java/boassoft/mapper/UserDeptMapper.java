package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("UserDeptMapper")
public interface UserDeptMapper {

	public CommonList getUserDeptList(CommonMap cmap) throws Exception;
	
	public int getUserDeptListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptListChk(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptListAll(CommonMap cmap) throws Exception;
	
	public CommonMap getUserDeptView(CommonMap cmap) throws Exception;
	
	public int insertUserDept(CommonMap cmap) throws Exception;
	
	public int updateUserDept(CommonMap cmap) throws Exception;
	
	public int deleteUserDept(CommonMap cmap) throws Exception;
	
	public int deleteUserDept2(CommonMap cmap) throws Exception;
	
	
}
