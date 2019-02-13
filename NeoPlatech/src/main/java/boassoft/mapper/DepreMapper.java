package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("DepreMapper")
public interface DepreMapper {

	public CommonMap getMaxDepreView(CommonMap cmap) throws Exception;
	
	public CommonMap getMinAqusitDt(CommonMap cmap) throws Exception;
	
	public CommonList getDepreList(CommonMap cmap) throws Exception;
	
	public int getDepreListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getDepreView(CommonMap cmap) throws Exception;
	
	public int insertDepre(CommonMap cmap) throws Exception;
	
	public int updateDepre(CommonMap cmap) throws Exception;
	
	public int deleteDepre(CommonMap cmap) throws Exception;
	
	public int deleteDepre2(CommonMap cmap) throws Exception;
	
	public CommonList getDepreYearList(CommonMap cmap) throws Exception;
	
	
}
