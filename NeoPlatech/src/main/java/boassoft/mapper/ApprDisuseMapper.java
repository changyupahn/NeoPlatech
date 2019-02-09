package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprDisuseMapper")
public interface ApprDisuseMapper {

	public CommonList getApprDisuseList(CommonMap cmap) throws Exception;
	
	public int getApprDisuseListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getApprDisuseAssetList(CommonMap cmap) throws Exception;
	
	public int getApprDisuseAssetListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprDisuseView(CommonMap cmap) throws Exception;
	
	public void insertApprDisuse(CommonMap cmap) throws Exception;
	
	public void updateApprDisuse(CommonMap cmap) throws Exception;
	
	public void deleteApprDisuse(CommonMap cmap) throws Exception;
	
	
}
