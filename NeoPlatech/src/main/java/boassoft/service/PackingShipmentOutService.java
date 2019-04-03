package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface PackingShipmentOutService {

	public CommonList getPackingShipmentOutList(CommonMap cmap) throws Exception;

	public int insertMOutOrder(CommonMap cmap) throws Exception;

	public int insertMOutOrderLine(CommonMap cmap) throws Exception;

	public int insertCInvoicePo(CommonMap cmap) throws Exception;

	public int insertCInvoicePoLine(CommonMap cmap) throws Exception;
	
	
}
