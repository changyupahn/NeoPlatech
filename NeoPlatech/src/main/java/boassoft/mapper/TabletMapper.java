package boassoft.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.common.GoodsXmlList;
import boassoft.util.CommonMap;

@Mapper("TabletMapper")
public interface TabletMapper {

	@SuppressWarnings("rawtypes")
	public List getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public void updateIGoodsShipmentOut(CommonMap cmap) throws Exception;
	
	public GoodsXmlList getPackingReceptListXml(CommonMap cmap) throws Exception;

	public GoodsXmlList getPackingShipmentOutListXml(CommonMap cmap)throws Exception;
}
