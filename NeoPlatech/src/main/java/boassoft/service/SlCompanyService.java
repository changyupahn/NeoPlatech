package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface SlCompanyService {

	public CommonList getSlCompanyList(CommonMap cmap) throws Exception;
	
	public int getSlCompanyListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getSlCompanyView(CommonMap cmap) throws Exception;
	
	public int insertSlCompany(CommonMap cmap) throws Exception;
	
	public int updateSlCompany(CommonMap cmap) throws Exception;
	
	public int deleteSlCompany(CommonMap cmap) throws Exception;
	
	public int deleteSlCompany2(CommonMap cmap) throws Exception;
}
