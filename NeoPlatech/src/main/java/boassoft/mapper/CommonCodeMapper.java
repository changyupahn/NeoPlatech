package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("CommonCodeMapper")
public interface CommonCodeMapper {

	public CommonList getCommonCodeList(CommonMap cmap) throws Exception;
	
	public CommonMap getCommonCodeView(CommonMap cmap) throws Exception;
	
	public int insertCommonCode(CommonMap cmap) throws Exception;
	
	public int updateCommonCode(CommonMap cmap) throws Exception;
	
	public int deleteCommonCode(CommonMap cmap) throws Exception;
	
	public int deleteCommonCode2(CommonMap cmap) throws Exception;
	
}
