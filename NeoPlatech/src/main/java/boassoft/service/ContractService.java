package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ContractService {

	public CommonList getContractList(CommonMap cmap) throws Exception;
	
	public CommonMap getContractView(CommonMap cmap) throws Exception;
	
	
}
