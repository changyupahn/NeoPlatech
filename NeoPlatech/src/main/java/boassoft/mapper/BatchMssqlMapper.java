package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BatchMssqlMapper")
public interface BatchMssqlMapper {

	public CommonList getLGNewOrderPlanList(CommonMap cmap) throws Exception;

	public CommonList getMartialBOM(CommonMap cmap) throws Exception;
	
	public CommonList getPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public CommonList getPublicProductList(CommonMap cmap) throws Exception;

	public CommonList getSubPartSendList(CommonMap cmap) throws Exception;

	public CommonList getPackingWoSendList(CommonMap cmap) throws Exception;

	public CommonList getSubPartWoSendListODOnlyList(CommonMap cmap) throws Exception;
}
