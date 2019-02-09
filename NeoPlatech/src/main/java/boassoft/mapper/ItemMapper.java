package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ItemMapper")
public interface ItemMapper {

	public CommonList getItemList(CommonMap cmap) throws Exception;
	
	public int getItemListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getItemCdList(CommonMap cmap) throws Exception;
	
	public CommonMap getItemView(CommonMap cmap) throws Exception;
	
	public int insertItem(CommonMap cmap) throws Exception;
	
	public int updateItem(CommonMap cmap) throws Exception;
	
	public int deleteItem(CommonMap cmap) throws Exception;
	
	public int deleteItem2(CommonMap cmap) throws Exception;
	
}
