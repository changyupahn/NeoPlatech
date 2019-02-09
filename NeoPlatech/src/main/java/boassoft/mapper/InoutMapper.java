package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InoutMapper")
public interface InoutMapper {

	public CommonList getInoutList(CommonMap cmap) throws Exception;
	
	public int getInoutListCnt(CommonMap cmap) throws Exception;
	
}
