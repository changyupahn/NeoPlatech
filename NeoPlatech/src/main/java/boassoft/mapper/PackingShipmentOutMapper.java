package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PackingShipmentOutMapper")
public interface PackingShipmentOutMapper {

	public CommonList getPackingShipmentOutList(CommonMap cmap) throws Exception;
	
	public int getPackingShipmentOutListCnt(CommonMap cmap) throws Exception;
}
