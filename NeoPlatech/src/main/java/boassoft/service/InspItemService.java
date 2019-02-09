package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface InspItemService {

	public CommonList getInspItemList(CommonMap cmap) throws Exception;
	
	public int getInspItemListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getInspItemList2(CommonMap cmap) throws Exception;
	
	public int getInspItemListCnt2(CommonMap cmap) throws Exception;
	
	public CommonMap getInspItemView(CommonMap cmap) throws Exception;
	
	public int insertInspItem(CommonMap cmap) throws Exception;
	
	public int insertInspItemAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int updateInspItem(CommonMap cmap) throws Exception;
	
	public int updateInspItemAll(CommonMap cmap, CommonList list) throws Exception;
	
	public int deleteInspItem(CommonMap cmap) throws Exception;
	
	public int deleteInspItem2(CommonMap cmap) throws Exception;
	
	public int deleteInspItemAll(CommonMap cmap) throws Exception;
	
	public int deleteInspItem3All(CommonMap cmap, CommonList list) throws Exception;
}
