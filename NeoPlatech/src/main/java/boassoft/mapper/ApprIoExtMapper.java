package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprIoExtMapper")
public interface ApprIoExtMapper {

	public CommonList getApprIoExtList(CommonMap cmap) throws Exception;
	
	public int getApprIoExtListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoExtView(CommonMap cmap) throws Exception;
	
	public void insertApprIoExt(CommonMap cmap) throws Exception;
	
	public void updateApprIoExt(CommonMap cmap) throws Exception;
	
	public void deleteApprIoExt(CommonMap cmap) throws Exception;
	
	public void deleteApprIoExt2(CommonMap cmap) throws Exception;
}
