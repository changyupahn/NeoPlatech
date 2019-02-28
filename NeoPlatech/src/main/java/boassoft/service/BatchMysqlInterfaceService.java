package boassoft.service;

import boassoft.util.CommonMap;

public interface BatchMysqlInterfaceService {

	public void insertLgNewOrderPlanList(CommonMap cmap) throws Exception;
	
	public void deleteLgNewOrderPlanList(CommonMap cmap) throws Exception;
	
	public void deleteMatrialBom(CommonMap cmap) throws Exception;
	
	public void insertMatrialBom(CommonMap cmap) throws Exception;

	public void deletePackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
	
	public void insertPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public void deletePublicProductList(CommonMap cmap) throws Exception;

	public void insertPublicProductList(CommonMap cmap) throws Exception;
	
	public void deleteSubPartSendList(CommonMap cmap) throws Exception;

	public void insertSubPartSendList(CommonMap cmap) throws Exception;

	public void deletePackingWoSendList(CommonMap cmap) throws Exception;

	public void insertPackingWoSendList(CommonMap cmap) throws Exception;

}
