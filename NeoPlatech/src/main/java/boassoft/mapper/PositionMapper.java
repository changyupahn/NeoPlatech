package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PositionMapper")
public interface PositionMapper {

	public CommonList getPositionList(CommonMap cmap) throws Exception;
	
	public int getPositionListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getPositionCdList(CommonMap cmap) throws Exception;
	
	public CommonMap getPositionView(CommonMap cmap) throws Exception;
	
	public int insertPosition(CommonMap cmap) throws Exception;
	
	public int updatePosition(CommonMap cmap) throws Exception;
	
	public int deletePosition(CommonMap cmap) throws Exception;
	
	public int deletePosition2(CommonMap cmap) throws Exception;
	
}
