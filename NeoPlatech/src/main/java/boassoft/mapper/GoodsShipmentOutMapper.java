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

	public int insertRridMOut(CommonMap cmap) throws Exception;

	public int insertRridMOutLine(CommonMap cmap) throws Exception;

	public int insertRfidMIn(CommonMap cmap) throws Exception;

	public int insertRfidMInLine(CommonMap cmap) throws Exception;
	

}
