package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ApprIoInMapper")
public interface ApprIoInMapper {

	public CommonList getApprIoInList(CommonMap cmap) throws Exception;
	
	public int getApprIoInListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoInView(CommonMap cmap) throws Exception;
	
	public void insertApprIoIn(CommonMap cmap) throws Exception;
	
	public void updateApprIoIn(CommonMap cmap) throws Exception;
	
	public void deleteApprIoIn(CommonMap cmap) throws Exception;
}
