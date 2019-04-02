package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("GoodsReceiptMapper")
public interface GoodsReceiptMapper {
	
	public CommonList getGoodsReceiptList(CommonMap cmap) throws Exception;

	public int getGoodsReceiptListCnt(CommonMap cmap) throws Exception;

	public CommonList getOptionVendorList(CommonMap cmap) throws Exception;

	public CommonList getOptionItemList(CommonMap cmap)  throws Exception;

	public CommonList getOptionPNoList(CommonMap cmap)  throws Exception;

	public CommonList getGoodsReceiptDetailList(CommonMap cmap) throws Exception;

	public int getGoodsReceiptDetailListCnt(CommonMap cmap) throws Exception;

	public int updateQtyOnHand(CommonMap cmap) throws Exception;

	public int insertRfidCInOrder(CommonMap cmap) throws Exception;

	public int insertRfidCInOrderLine(CommonMap cmap) throws Exception;
	

}
