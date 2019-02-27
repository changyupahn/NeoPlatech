package boassoft.service;

import boassoft.util.CommonMap;

public interface BatchMysqlInterfaceService {

	public void insertLgNewOrderPlanList(CommonMap cmap) throws Exception;
	
	public void deleteLgNewOrderPlanList(CommonMap cmap) throws Exception;
	
	public void deleteMatrialBom(CommonMap cmap) throws Exception;
	
	public void insertMatrialBom(CommonMap cmap) throws Exception;

	public void deletePackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
	
	public void insertPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;
}
