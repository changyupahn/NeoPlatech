package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ZeusMapper")
public interface ZeusMapper {

	public CommonList getZeusList(CommonMap cmap) throws Exception;
	
	public int getZeusListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusView(CommonMap cmap) throws Exception;
	
	public int insertZeus(CommonMap cmap) throws Exception;
	
	public int updateZeus(CommonMap cmap) throws Exception;
	
	public int deleteZeus(CommonMap cmap) throws Exception;
	
	public int deleteZeus2(CommonMap cmap) throws Exception;
	
	public int deleteZeusAll(CommonMap cmap) throws Exception;
	
	
}
