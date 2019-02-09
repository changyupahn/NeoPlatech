package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface DepreService {

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
