package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ZeusCodeMapper")
public interface ZeusCodeMapper {

	public CommonList getZeusCodeList(CommonMap cmap) throws Exception;
	
	public int getZeusCodeListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusCodeView(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusCodeView2(CommonMap cmap) throws Exception;
	
	public int insertZeusCode(CommonMap cmap) throws Exception;
	
	public int updateZeusCode(CommonMap cmap) throws Exception;
	
	public int deleteZeusCode(CommonMap cmap) throws Exception;
	
	public int deleteZeusCode2(CommonMap cmap) throws Exception;
	
	public int deleteZeusCodeAll(CommonMap cmap) throws Exception;
	
	
}
