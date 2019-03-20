package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("GoodsReceiptMapper")
public interface GoodsReceiptMapper {
	
	public CommonList getGoodsReceiptList(CommonMap cmap) throws Exception;

	public int getGoodsReceiptListCnt(CommonMap cmap) throws Exception;
	

}
