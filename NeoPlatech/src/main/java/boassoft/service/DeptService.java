package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface DeptService {

	public CommonList getDeptList(CommonMap cmap) throws Exception;
	
	public int getDeptListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getDeptView(CommonMap cmap) throws Exception;
	
	public int insertDept(CommonMap cmap) throws Exception;
	
	public int updateDept(CommonMap cmap) throws Exception;
	
	public int deleteDept(CommonMap cmap) throws Exception;
	
	public int deleteDept2(CommonMap cmap) throws Exception;
	
}
