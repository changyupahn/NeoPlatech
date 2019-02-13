package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ZeusOperMapper")
public interface ZeusOperMapper {

	public CommonList getZeusOperList(CommonMap cmap) throws Exception;
	
	public int getZeusOperListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusOperView(CommonMap cmap) throws Exception;
	
	public int insertZeusOper(CommonMap cmap) throws Exception;
	
	public int updateZeusOper(CommonMap cmap) throws Exception;
	
	public int deleteZeusOper(CommonMap cmap) throws Exception;
	
	public int deleteZeusOper2(CommonMap cmap) throws Exception;
	
	public int deleteZeusOperAll(CommonMap cmap) throws Exception;
}
