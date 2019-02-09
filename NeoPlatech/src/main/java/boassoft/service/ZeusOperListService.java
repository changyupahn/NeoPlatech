package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ZeusOperListService {

	public CommonList getZeusOperListList(CommonMap cmap) throws Exception;
	
	public int getZeusOperListListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusOperListView(CommonMap cmap) throws Exception;
	
	public int insertZeusOperList(CommonMap cmap) throws Exception;
	
	public int updateZeusOperList(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperList(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperList2(CommonMap cmap) throws Exception;
	
}
