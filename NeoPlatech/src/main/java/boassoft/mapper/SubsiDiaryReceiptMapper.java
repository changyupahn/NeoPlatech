package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("SubsiDiaryReceiptMapper")
public interface SubsiDiaryReceiptMapper {
	
    public CommonList getSubsiDiaryReceiptList(CommonMap cmap) throws Exception;
	
	public int getSubsiDiaryReceiptListCnt(CommonMap cmap) throws Exception;
	
}
