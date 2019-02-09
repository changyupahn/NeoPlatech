package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ZeusOperListMapper")
public interface ZeusOperListMapper {

	public CommonList getZeusOperListList(CommonMap cmap) throws Exception;
	
	public int getZeusOperListListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusOperListView(CommonMap cmap) throws Exception;
	
	public int insertZeusOperList(CommonMap cmap) throws Exception;
	
	public int updateZeusOperList(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperList(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperList2(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperListAll(CommonMap cmap) throws Exception;
		
	
}
