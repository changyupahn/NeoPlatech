package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ContractDtlMapper")
public interface ContractDtlMapper {

	public CommonList getContractDtlList(CommonMap cmap) throws Exception;
	
	public int getContractDtlListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getContractDtlView(CommonMap cmap) throws Exception;
	
}
