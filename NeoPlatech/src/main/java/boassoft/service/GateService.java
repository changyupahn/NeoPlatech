package boassoft.service;

import boassoft.common.CommonXmlList;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface GateService {

	public CommonList getAssetInfoList(CommonMap cmap) throws Exception;
	
	public int insertGateHistory(CommonMap cmap) throws Exception;
	
	public CommonXmlList getAssetInfoXml(CommonMap cmap) throws Exception;

}
