package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("MkCompanyMapper")
public interface MkCompanyMapper {

	public CommonList getMkCompanyList(CommonMap cmap) throws Exception;
	
	public int getMkCompanyListCnt(CommonMap cmap) throws Exception;
	
	public CommonList getMkCompanyCdList(CommonMap cmap) throws Exception;
	
	public CommonMap getMkCompanyView(CommonMap cmap) throws Exception;
	
	public CommonMap getMkCompanyView2(CommonMap cmap) throws Exception;
	
	public int insertMkCompany(CommonMap cmap) throws Exception;
	
	public int updateMkCompany(CommonMap cmap) throws Exception;
	
	public int deleteMkCompany(CommonMap cmap) throws Exception;
	
	public int deleteMkCompany2(CommonMap cmap) throws Exception;
	
}
