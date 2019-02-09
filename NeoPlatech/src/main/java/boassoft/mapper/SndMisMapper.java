package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SndMisMapper")
public interface SndMisMapper {

	public CommonList getSndMisList(CommonMap cmap) throws Exception;
	
	public int getSndMisListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getSndMisView(CommonMap cmap) throws Exception;
	
	public int insertSndMis(CommonMap cmap) throws Exception;
	
	public int updateSndMis(CommonMap cmap) throws Exception;
	
	public int deleteSndMis(CommonMap cmap) throws Exception;
	
	public int deleteSndMis2(CommonMap cmap) throws Exception;
	
}
