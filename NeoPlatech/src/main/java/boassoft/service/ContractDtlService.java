package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ContractDtlService {

	public CommonList getContractDtlList(CommonMap cmap) throws Exception;
	
	public CommonMap getContractDtlView(CommonMap cmap) throws Exception;
	
	
}
