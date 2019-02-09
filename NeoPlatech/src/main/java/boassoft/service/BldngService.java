package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface BldngService {

	public CommonList getBldngList(CommonMap cmap) throws Exception;
	
	public int getBldngListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getBldngView(CommonMap cmap) throws Exception;
	
	public int insertBldng(CommonMap cmap) throws Exception;
	
	public int updateBldng(CommonMap cmap) throws Exception;
	
	public int deleteBldng(CommonMap cmap) throws Exception;
	
	public int deleteBldng2(CommonMap cmap) throws Exception;
	
	
}
