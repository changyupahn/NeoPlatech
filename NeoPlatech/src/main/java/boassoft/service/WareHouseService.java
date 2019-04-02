package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface WareHouseService {

	public CommonList getWareHouseList(CommonMap cmap) throws Exception;

	public CommonList getWareHouseDetailList(CommonMap cmap) throws Exception;
	
	
}
