package boassoft.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Mapper("ContractMapper")
public interface ContractMapper {

	public CommonList getContractList(CommonMap cmap) throws Exception;
	
	public int getContractListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getContractView(CommonMap cmap) throws Exception;
	
	
}
