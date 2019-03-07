package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("PackingReceiptMapper")
public interface PackingReceiptMapper {

	public CommonList getPackingReceiptList(CommonMap cmap) throws Exception;
	
	public int getPackingReceiptListCnt(CommonMap cmap) throws Exception;

}
