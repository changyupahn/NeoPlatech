package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InspItemMapper")
public interface InspItemMapper {

	public CommonList getInspItemList(CommonMap cmap) throws Exception;
	
	public int getInspItemListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getInspItemList2(CommonMap cmap) throws Exception;
	
	public int getInspItemListCnt2(CommonMap cmap) throws Exception;
	
	public CommonMap getInspItemView(CommonMap cmap) throws Exception;
	
	public int insertInspItem(CommonMap cmap) throws Exception;
	
	public int updateInspItem(CommonMap cmap) throws Exception;
	
	public int updateInspItem2(CommonMap cmap) throws Exception;
	
	public int deleteInspItem(CommonMap cmap) throws Exception;
	
	public int deleteInspItem2(CommonMap cmap) throws Exception;
	
	public int deleteInspItem3(CommonMap cmap) throws Exception;
	
	public int deleteInspItemAll(CommonMap cmap) throws Exception;
	
	
}
