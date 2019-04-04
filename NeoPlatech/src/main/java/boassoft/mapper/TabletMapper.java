package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.common.GoodsXmlList;
import boassoft.util.CommonMap;

@Mapper("TabletMapper")
public interface TabletMapper {

	
	public GoodsXmlList getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public void updateIGoodsShipmentOut(CommonMap cmap) throws Exception;
	
	public GoodsXmlList getPackingReceptListXml(CommonMap cmap) throws Exception;

	public GoodsXmlList getPackingShipmentOutListXml(CommonMap cmap) throws Exception;

	public GoodsXmlList getGoodsShipmentDetailOutXml(CommonMap cmap) throws Exception;

	public GoodsXmlList goodsShipmentDetailKitItemOutXml(CommonMap cmap) throws Exception;

	public GoodsXmlList goodsShipmentDetailRefItemOutXml(CommonMap cmap) throws Exception;

	public GoodsXmlList optionVendorListXml(CommonMap cmap) throws Exception;

}
