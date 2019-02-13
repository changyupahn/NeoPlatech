package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ZeusAsMapper")
public interface ZeusAsMapper {

	public CommonList getZeusAsList(CommonMap cmap) throws Exception;
	
	public int getZeusAsListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusAsView(CommonMap cmap) throws Exception;
	
	public int insertZeusAs(CommonMap cmap) throws Exception;
	
	public int updateZeusAs(CommonMap cmap) throws Exception;
	
	public int deleteZeusAs(CommonMap cmap) throws Exception;
	
	public int deleteZeusAs2(CommonMap cmap) throws Exception;
	
	public int deleteZeusAsAll(CommonMap cmap) throws Exception;
		
}
