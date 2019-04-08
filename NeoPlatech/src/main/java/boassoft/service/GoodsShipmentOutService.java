package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface GoodsShipmentOutService {

	public CommonList GoodsShipmentOutList(CommonMap cmap) throws Exception;
		
	public CommonList getGoodsShipmentOutDetailList(CommonMap cmap) throws Exception;

	public int insertRridMOut(CommonMap gmap) throws Exception;

	public int insertRridMOutLine(CommonMap gmap) throws Exception;
}
