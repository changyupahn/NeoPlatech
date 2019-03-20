package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("GoodsShipmentOutMapper")
public interface GoodsShipmentOutMapper {

	public CommonList getGoodsShipmentOutList(CommonMap cmap) throws Exception;

	public int getGoodsShipmentOutListCnt(CommonMap cmap) throws Exception;

	public CommonList getGoodsShipmentOutDetailList(CommonMap cmap) throws Exception;

	public int getGoodsShipmentOutDetailListCnt(CommonMap cmap) throws Exception;
	
	

}
