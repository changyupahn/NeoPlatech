package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;


public interface MkNationService {

	public CommonList getMkNationList(CommonMap cmap) throws Exception;
	
	public CommonList getMkNationList2(CommonMap cmap) throws Exception;
	
	public int getMkNationListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getMkNationView(CommonMap cmap) throws Exception;
	
	public int insertMkNation(CommonMap cmap) throws Exception;
	
	public int updateMkNation(CommonMap cmap) throws Exception;
	
	public int deleteMkNation(CommonMap cmap) throws Exception;
	
	public int deleteMkNation2(CommonMap cmap) throws Exception;
	
	
}
