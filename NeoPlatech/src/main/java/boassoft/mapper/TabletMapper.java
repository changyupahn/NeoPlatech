package boassoft.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.common.GoodsXmlList;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("TabletMapper")
public interface TabletMapper {

	
	public List getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public void updateIGoodsShipmentOut(CommonMap cmap) throws Exception;
	
	public GoodsXmlList getPackingReceptListXml(CommonMap cmap) throws Exception;

	public CommonList getPackingShipmentOutListXml(CommonMap cmap) throws Exception;

	public List getGoodsShipmentDetailOutXml(CommonMap cmap) throws Exception;

	public List goodsShipmentDetailKitItemOutXml(CommonMap cmap) throws Exception;

	public List goodsShipmentDetailRefItemOutXml(CommonMap cmap) throws Exception;

	public List optionVendorListXml(CommonMap cmap) throws Exception;

}
