package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ZeusAsListService {

	public CommonList getZeusAsListList(CommonMap cmap) throws Exception;
	
	public int getZeusAsListListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusAsListView(CommonMap cmap) throws Exception;
	
	public int insertZeusAsList(CommonMap cmap) throws Exception;
	
	public int updateZeusAsList(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsList(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsList2(CommonMap cmap) throws Exception;
	
	
}
