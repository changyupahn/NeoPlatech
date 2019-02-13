package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ZeusAsListMapper")
public interface ZeusAsListMapper {

	public CommonList getZeusAsListList(CommonMap cmap) throws Exception;
	
	public int getZeusAsListListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusAsListView(CommonMap cmap) throws Exception;
	
	public int insertZeusAsList(CommonMap cmap) throws Exception;
	
	public int updateZeusAsList(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsList(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsList2(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsListAll(CommonMap cmap) throws Exception;		
	
}
