package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("InventoryStatMapper")
public interface InventoryStatMapper {

	public CommonList getDeptStat(CommonMap cmap) throws Exception;
	
	public CommonList getUserStat(CommonMap cmap) throws Exception;
	
	public CommonList getHosilStat(CommonMap cmap) throws Exception;
	
	public CommonList getAssetCateStat(CommonMap cmap) throws Exception;
	
	public CommonList getNewRegisterStat(CommonMap cmap) throws Exception;
	
	
}
