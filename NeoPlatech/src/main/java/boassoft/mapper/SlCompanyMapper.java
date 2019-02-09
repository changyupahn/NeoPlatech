package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("SlCompanyMapper")
public interface SlCompanyMapper {

	public CommonList getSlCompanyList(CommonMap cmap) throws Exception;
	
	public int getSlCompanyListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getSlCompanyView(CommonMap cmap) throws Exception;
	
	public int insertSlCompany(CommonMap cmap) throws Exception;
	
	public int updateSlCompany(CommonMap cmap) throws Exception;
	
	public int deleteSlCompany(CommonMap cmap) throws Exception;
	
	public int deleteSlCompany2(CommonMap cmap) throws Exception;
	
	
}
