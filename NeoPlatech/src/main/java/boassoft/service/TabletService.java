package boassoft.service;

import boassoft.common.CommonXmlList;
import boassoft.common.GoodsXmlList;
import boassoft.util.CommonMap;

public interface TabletService {

	public CommonXmlList getGoodsShipmentOutListXml(CommonMap cmap) throws Exception;
	
	public int getGoodsShipmentOutListXmlCnt(CommonMap cmap) throws Exception;

	public CommonXmlList getSubsiDiaryReceiptListXml(CommonMap cmap) throws Exception;

	public void updateGoodsShipment(CommonMap inv) throws Exception;

	public GoodsXmlList getPackingReceptListXml(CommonMap cmap) throws Exception;
}
