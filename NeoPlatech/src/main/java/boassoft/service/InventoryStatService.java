package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface InventoryStatService {

	public CommonList getDeptStat(CommonMap cmap) throws Exception;
	
	public CommonList getUserStat(CommonMap cmap) throws Exception;
	
	public CommonList getHosilStat(CommonMap cmap) throws Exception;
	
	public CommonList getAssetCateStat(CommonMap cmap) throws Exception;
	
	public CommonList getNewRegisterStat(CommonMap cmap) throws Exception;
		
	
}
