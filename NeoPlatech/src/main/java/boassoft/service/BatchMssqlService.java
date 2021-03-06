package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface BatchMssqlService {

	public CommonList getLGNewOrderPlanList(CommonMap cmap) throws Exception;		
		
	public CommonList getMartialBOM(CommonMap cmap) throws Exception;

	public CommonList getPackingWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public CommonList getPublicProductList(CommonMap cmap) throws Exception;
	
	public CommonList getSubPartSendList(CommonMap cmap) throws Exception;

	public CommonList getPackingWoSendList(CommonMap cmap) throws Exception;

	public CommonList getSubPartWoSendListODOnlyList(CommonMap cmap) throws Exception;

	public CommonList getInjectionProductPalletList(CommonMap cmap) throws Exception;

	public CommonList getAssayProductPalletList(CommonMap cmap) throws Exception;

	public CommonList getLgGanpanList(CommonMap cmap) throws Exception;

	public void insertSubPartWoSendListOdOnlyInputQty(CommonMap cmap) throws Exception;

	public void insertSubPartWoSendListAllInputQty(CommonMap cmap) throws Exception;

	public void insertPackingWoSendListAllCngInputQty(CommonMap cmap) throws Exception;
	
	public int insertAssayOrderQty(CommonMap cmap) throws Exception;

	public CommonList selectAssayOrderQty(CommonMap gmap) throws Exception;
	
	

}
