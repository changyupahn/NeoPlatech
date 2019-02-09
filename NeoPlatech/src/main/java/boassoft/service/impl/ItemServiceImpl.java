package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ItemMapper;
import boassoft.service.ItemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("ItemService")
public class ItemServiceImpl extends EgovAbstractServiceImpl implements ItemService{

	@Resource(name="itemMapper")
    private ItemMapper itemMapper;
	
	@Override
	public CommonList getItemList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("searchKeyword"))) {
			CommonList itemCdList = itemMapper.getItemCdList(cmap);
			String listStr = "";
			for (int i=0; i<itemCdList.size(); i++) {
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq01"))) listStr += itemCdList.getMap(i).getString("itemSeq01") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq02"))) listStr += itemCdList.getMap(i).getString("itemSeq02") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq03"))) listStr += itemCdList.getMap(i).getString("itemSeq03") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq04"))) listStr += itemCdList.getMap(i).getString("itemSeq04") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq05"))) listStr += itemCdList.getMap(i).getString("itemSeq05") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq06"))) listStr += itemCdList.getMap(i).getString("itemSeq06") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq07"))) listStr += itemCdList.getMap(i).getString("itemSeq07") + ",";
				if (!"".equals(itemCdList.getMap(i).getString("itemSeq08"))) listStr += itemCdList.getMap(i).getString("itemSeq08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "0000";
			}
			cmap.put("listStr", listStr);
			cmap.put("sItemSeqArr", cmap.getArray("listStr"));
		}
		
		CommonList list = itemMapper.getItemList(cmap);
		list.totalRow = itemMapper.getItemListCnt(cmap);
		return list;
	}

	@Override
	public int getItemListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.getItemListCnt(cmap);
	}

	@Override
	public CommonMap getItemView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.getItemView(cmap);
	}

	@Override
	public int insertItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.insertItem(cmap);
	}

	@Override
	public int updateItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.updateItem(cmap);
	}

	@Override
	public int deleteItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.deleteItem(cmap);
	}

	@Override
	public int deleteItem2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return itemMapper.deleteItem2(cmap);
	}

}
