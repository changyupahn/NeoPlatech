package boassoft.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.common.GoodsXml;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("TabletMapper")
public interface TabletMapper {

	
	public List<GoodsXml> getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public void updateIGoodsShipmentOut(CommonMap cmap) throws Exception;
	
	public List<GoodsXml> getPackingReceptListXml(CommonMap cmap) throws Exception;

	public CommonList getPackingShipmentOutListXml(CommonMap cmap) throws Exception;

	public List<GoodsXml> getGoodsShipmentDetailOutXml(CommonMap cmap) throws Exception;

	public List<GoodsXml> goodsShipmentDetailKitItemOutXml(CommonMap cmap) throws Exception;

	public List<GoodsXml> goodsShipmentDetailRefItemOutXml(CommonMap cmap) throws Exception;

	public List<GoodsXml> optionVendorListXml(CommonMap cmap) throws Exception;

	public List<GoodsXml> optionPartNoListXml(CommonMap cmap) throws Exception;

	public CommonList getPackingShipmentOutDetailListXml(CommonMap cmap) throws Exception;

	public CommonList getPackingResultListXml(CommonMap cmap) throws Exception;

	public CommonList getGoodsResultListXml(CommonMap cmap) throws Exception;

}
