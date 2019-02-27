package boassoft.mapper;

import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BatchMysqlInterfaceMapper")
public interface BatchMysqlInterfaceMapper {

	public int insertLgNewOrderPlanList(CommonMap cmap) throws Exception;
 
	public int deleteLgNewOrderPlanList(CommonMap cmap) throws Exception;
	
	public int insertMatrialBom(CommonMap cmap) throws Exception;
	
	public int deleteMatrialBom(CommonMap cmap) throws Exception;
	
	public int deletePackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
	
	public int insertPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
}