package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ApprIoOutMapper")
public interface ApprIoOutMapper {

	public CommonList getApprIoOutList(CommonMap cmap) throws Exception;
	
	public int getApprIoOutListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getApprIoOutAssetList(CommonMap cmap) throws Exception;
	
	public int getApprIoOutAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprIoOutView(CommonMap cmap) throws Exception;
	
	public void insertApprIoOut(CommonMap cmap) throws Exception;
	
	public void updateApprIoOut(CommonMap cmap) throws Exception;
	
	public void deleteApprIoOut(CommonMap cmap) throws Exception;
	
}
