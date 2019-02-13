package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MisMapper")
public interface MisMapper {

	public CommonList getMisDocList(CommonMap cmap) throws Exception;
	
	public CommonList getMisUserList(CommonMap cmap) throws Exception;
	
	public CommonList getMisDeptList(CommonMap cmap) throws Exception;
	
	public CommonList getMisCustList(CommonMap cmap) throws Exception;
	
	public CommonList getMisContrList(CommonMap cmap) throws Exception;
	
	public CommonList getMisContrdtlList(CommonMap cmap) throws Exception;
		
	
}
