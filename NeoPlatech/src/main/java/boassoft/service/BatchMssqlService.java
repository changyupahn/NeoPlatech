package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface BatchMssqlService {

	public CommonList getLGNewOrderPlanList(CommonMap cmap) throws Exception;		
		
	public CommonList getMartialBOM(CommonMap cmap) throws Exception;

	public CommonList getPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
}