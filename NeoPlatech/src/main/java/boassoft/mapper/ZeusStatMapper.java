package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MkNationMapper")
public interface ZeusStatMapper {

	public CommonList getZeusStatYearList(CommonMap cmap) throws Exception;
	
	public CommonList getZeusMonthStatList(CommonMap cmap) throws Exception;
	
	public int getZeusMonthStatListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getZeusEquipStatList(CommonMap cmap) throws Exception;
	
	public int getZeusEquipStatListCnt(CommonMap cmap) throws Exception;
	
	
}
