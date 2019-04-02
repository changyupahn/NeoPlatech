package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface PackingReceiptService {
	
	public CommonList getPackingReceiptList(CommonMap cmap) throws Exception;
	
	public CommonList getOptionVendorList(CommonMap cmap) throws Exception;

	public CommonList getOptionItemList(CommonMap cmap) throws Exception;

	public CommonList getOptionPNoList(CommonMap cmap) throws Exception;
	
	public CommonList getPackingReceiptDetailList(CommonMap cmap) throws Exception;

	public int updateQtyOnHand(CommonMap cmap) throws Exception;

	public int insertCRecall(CommonMap cmap) throws Exception;

	public int insertCRecallLine(CommonMap cmap) throws Exception;

}
