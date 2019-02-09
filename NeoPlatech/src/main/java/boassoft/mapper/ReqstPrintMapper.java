package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ReqstPrintMapper")
public interface ReqstPrintMapper {
	
	public void insertReqstPrint(CommonMap cmap) throws Exception;
	
	public CommonList getReqstPrintList(CommonMap cmap) throws Exception;
	
	public int getReqstPrintListCnt(CommonMap cmap) throws Exception;
	
	public void deleteReqstPrint(CommonMap cmap) throws Exception;
	
	public int updatePrintStatusCd_R(CommonMap cmap) throws Exception;
	
}
