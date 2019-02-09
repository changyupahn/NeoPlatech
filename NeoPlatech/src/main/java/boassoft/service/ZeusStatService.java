package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ZeusStatService {

	public CommonList getZeusStatYearList(CommonMap cmap) throws Exception;
	
	public CommonList getZeusMonthStatList(CommonMap cmap) throws Exception;
	
	public CommonList getZeusEquipStatList(CommonMap cmap) throws Exception;
	
	
}
