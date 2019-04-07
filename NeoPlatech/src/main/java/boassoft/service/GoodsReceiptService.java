package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface GoodsReceiptService {

	public CommonList getGoodsReceiptList(CommonMap cmap) throws Exception;

	public CommonList getOptionVendorList(CommonMap cmap) throws Exception;

	public CommonList getOptionItemList(CommonMap cmap) throws Exception;

	public CommonList getOptionPNoList(CommonMap cmap) throws Exception;

	public CommonList getGoodsReceiptDetailList(CommonMap cmap) throws Exception;

	public int updateQtyOnHand(CommonMap cmap) throws Exception;

	public int insertRfidCInOrder(CommonMap cmap) throws Exception;

	public int insertRfidCInOrderLine(CommonMap cmap) throws Exception;

	public int updateQtyInvoiced(CommonMap cmap) throws Exception;
				
}
