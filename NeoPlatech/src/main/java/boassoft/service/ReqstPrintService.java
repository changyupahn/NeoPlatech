package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ReqstPrintService {

	public void insertReqstPrint(CommonMap cmap) throws Exception;
	
	public CommonList getReqstPrintList(CommonMap cmap) throws Exception;
	
	public int getReqstPrintListCnt(CommonMap cmap) throws Exception;
	
	public void deleteReqstPrint(CommonMap cmap) throws Exception;
	
	public int updatePrintStatusCd_R(CommonMap cmap) throws Exception;
	
	
}
