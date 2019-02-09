package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("GrantMapper")
public interface GrantMapper {

	public CommonList getGrantList(CommonMap cmap) throws Exception;
	
	public int getGrantListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getGrantView(CommonMap cmap) throws Exception;
	
	public int insertGrant(CommonMap cmap) throws Exception;
	
	public int updateGrant(CommonMap cmap) throws Exception;
	
	public int deleteGrant(CommonMap cmap) throws Exception;
	
	public int deleteGrant2(CommonMap cmap) throws Exception;
	
	
}
