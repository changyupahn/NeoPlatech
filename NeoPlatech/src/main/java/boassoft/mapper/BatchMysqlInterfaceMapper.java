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

	public int deletePublicProductList(CommonMap cmap) throws Exception;

	public int insertPublicProductList(CommonMap cmap) throws Exception;

	public int deleteSubPartSendList(CommonMap cmap) throws Exception;

	public int insertSubPartSendList(CommonMap cmap) throws Exception;

	public int deletePackingWoSendList(CommonMap cmap) throws Exception;

	public int insertPackingWoSendList(CommonMap cmap) throws Exception;

	public int deleteSubPartWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public int insertSubPartWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public int deleteInjectionProductPalletList(CommonMap cmap) throws Exception;

	public int insertInjectionProductPalletList(CommonMap cmap) throws Exception;

	public int deleteAssayProductPalletList(CommonMap cmap) throws Exception;

	public int insertAssayProductPalletList(CommonMap cmap) throws Exception;

	public int deleteLgGanpanList(CommonMap cmap) throws Exception;

	public int insertLgGanpanList(CommonMap cmap) throws Exception;
	
	
}
