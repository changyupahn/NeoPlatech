package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ItemService {

	public CommonList getItemList(CommonMap cmap) throws Exception;
	
	public int getItemListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getItemView(CommonMap cmap) throws Exception;
	
	public int insertItem(CommonMap cmap) throws Exception;
	
	public int updateItem(CommonMap cmap) throws Exception;
	
	public int deleteItem(CommonMap cmap) throws Exception;
	
	public int deleteItem2(CommonMap cmap) throws Exception;
	
}
