package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("PackingReceiptMapper")
public interface PackingReceiptMapper {

	public CommonList getPackingReceiptList(CommonMap cmap) throws Exception;
	
	public int getPackingReceiptListCnt(CommonMap cmap) throws Exception;

	public CommonList getOptionVendorList(CommonMap cmap) throws Exception;

	public CommonList getOptionItemList(CommonMap cmap) throws Exception;

	public CommonList getOptionPNoList(CommonMap cmap) throws Exception;

	public CommonList getPackingReceiptDetailList(CommonMap cmap) throws Exception;

	public int getPackingReceiptDetailListCnt(CommonMap cmap) throws Exception;

	public int updateQtyOnHand(CommonMap cmap) throws Exception;

	public int insertCRecall(CommonMap cmap) throws Exception;

	public int insertCRecallLine(CommonMap cmap) throws Exception;

}
