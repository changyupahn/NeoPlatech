package boassoft.mapper;

import java.util.List;

import boassoft.util.CommonMap;

public interface TabletMapper {

	public List getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public void updateIGoodsShipmentOut(CommonMap cmap) throws Exception;
}
