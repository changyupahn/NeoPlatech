package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ApprRqstMapper")
public interface ApprRqstMapper {

	public CommonList getApprRqstList(CommonMap cmap) throws Exception;
	
	public int getApprRqstListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getApprRqstView(CommonMap cmap) throws Exception;
	
	public void insertApprRqst(CommonMap cmap) throws Exception;
	
	public void updateApprRqst(CommonMap cmap) throws Exception;
	
	public void deleteApprRqst(CommonMap cmap) throws Exception;
	
	public int updateApprRqstStatusCd(CommonMap cmap) throws Exception;
	
	
}
