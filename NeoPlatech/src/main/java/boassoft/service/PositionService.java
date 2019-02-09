package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import org.springframework.stereotype.Service;

@Service("PositionService")
public interface PositionService {

	public CommonList getPositionList(CommonMap cmap) throws Exception;
	
	public int getPositionListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getPositionView(CommonMap cmap) throws Exception;
	
	public int insertPosition(CommonMap cmap) throws Exception;
	
	public int updatePosition(CommonMap cmap) throws Exception;
	
	public int deletePosition(CommonMap cmap) throws Exception;
	
	public int deletePosition2(CommonMap cmap) throws Exception;
	
}
