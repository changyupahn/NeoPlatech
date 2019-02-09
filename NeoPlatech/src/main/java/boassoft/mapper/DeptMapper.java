package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("DeptMapper")
public interface DeptMapper {

	public CommonList getDeptList(CommonMap cmap) throws Exception;
	
	public int getDeptListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getDeptNoList(CommonMap cmap) throws Exception;
	
	public CommonList getDeptNoList2(CommonMap cmap) throws Exception;
	
	public CommonMap getDeptView(CommonMap cmap) throws Exception;
	
	public int insertDept(CommonMap cmap) throws Exception ;
	
	public int updateDept(CommonMap cmap) throws Exception;
	
	public int deleteDept(CommonMap cmap) throws Exception;
	
	public int deleteDept2(CommonMap cmap) throws Exception;
	
	
}
