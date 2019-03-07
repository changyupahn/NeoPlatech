package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface GoodsShipmentOrderService {

	public CommonList goodsShipmentOrderList(CommonMap cmap) throws Exception;
	
	public CommonList goodsShipmentOrderlineList(CommonMap cmap) throws Exception;
	
	public CommonList goodsOutOrderlineList(CommonMap cmap) throws Exception;
	
	public int inserGoodsShipmentOrder(CommonMap cmap) throws Exception;
	
	public int updateGoodsShipmentOrder(CommonMap cmap) throws Exception;
	
	public int deleteGoodsShipmentOrder(CommonMap cmap) throws Exception;
	
	public int inserGoodsShipmentCancel(CommonMap cmap) throws Exception;
	
	public int updateGgoodsShipmentCancel(CommonMap cmap) throws Exception;
	
	public int deleteGoodsShipmentCancel(CommonMap cmap) throws Exception;
	
	public int inserGoodsOutOrder(CommonMap cmap) throws Exception;
	
	public int updateGoodsOutOrder(CommonMap cmap) throws Exception;
	
	public int deleteGoodsOutOrder(CommonMap cmap) throws Exception;
	
}
