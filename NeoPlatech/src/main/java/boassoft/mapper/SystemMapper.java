package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SystemMapper")
public interface SystemMapper {

	public CommonList getAddcolMngList(CommonMap cmap) throws Exception;
	
	public CommonList getDispMngList(CommonMap cmap) throws Exception;
	
	
}
