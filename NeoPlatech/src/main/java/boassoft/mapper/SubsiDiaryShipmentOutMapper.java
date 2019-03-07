package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SubsiDiaryShipmentOutMapper")
public interface SubsiDiaryShipmentOutMapper {

	public CommonList getSubsiDiaryShipmentOuttList(CommonMap cmap) throws Exception;
	
	public int getSubsiDiaryShipmentOutListCnt(CommonMap cmap) throws Exception;
}
