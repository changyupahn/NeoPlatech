package boassoft.mapper;

import java.util.List;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("GateMapper")
public interface GateMapper {

	public CommonList getAssetInfoList(CommonMap cmap) throws Exception;
	
	public int insertGateHistory(CommonMap cmap) throws Exception;
	
	public List getAssetInfoXml(CommonMap cmap) throws Exception;
}
