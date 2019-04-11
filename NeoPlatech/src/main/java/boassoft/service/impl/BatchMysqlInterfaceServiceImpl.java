package boassoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.BatchMysqlInterfaceMapper;
import boassoft.service.BatchMysqlInterfaceService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("batchMysqlInterfaceService")
public class BatchMysqlInterfaceServiceImpl extends EgovAbstractServiceImpl implements BatchMysqlInterfaceService {

	@Resource(name="BatchMysqlInterfaceMapper")
	private BatchMysqlInterfaceMapper batchMysqlInterfaceMapper;
	
	@Override
	public void insertLgNewOrderPlanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertLgNewOrderPlanList(cmap);
	}

	@Override
	public void deleteLgNewOrderPlanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteLgNewOrderPlanList(cmap);
	}

	@Override
	public void deleteMatrialBom(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteMatrialBom(cmap);
	}

	@Override
	public void insertMatrialBom(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertMatrialBom(cmap);
	}

	@Override
	public void deletePackingWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deletePackingWoSendListODOnlyList(cmap);
	}

	@Override
	public void insertPackingWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertPackingWoSendListODOnlyList(cmap);
	}

	@Override
	public void deletePublicProductList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deletePublicProductList(cmap);
	}

	@Override
	public void insertPublicProductList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertPublicProductList(cmap);
	}

	@Override
	public void deleteSubPartSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteSubPartSendList(cmap);
	}

	@Override
	public void insertSubPartSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartSendList(cmap);
	}

	@Override
	public void deletePackingWoSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deletePackingWoSendList(cmap);
	}

	@Override
	public void insertPackingWoSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertPackingWoSendList(cmap);
	}

	@Override
	public void deleteSubPartWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteSubPartWoSendListODOnlyList(cmap);
	}

	@Override
	public void insertSubPartWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListODOnlyList(cmap);
	}

	@Override
	public void deleteInjectionProductPalletList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteInjectionProductPalletList(cmap);
	}

	@Override
	public void insertInjectionProductPalletList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertInjectionProductPalletList(cmap);
	}

	@Override
	public void deleteAssayProductPalletList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteAssayProductPalletList(cmap);
	}

	@Override
	public void insertAssayProductPalletList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertAssayProductPalletList(cmap);
	}

	@Override
	public void deleteLgGanpanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.deleteLgGanpanList(cmap);
	}

	@Override
	public void insertLgGanpanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertLgGanpanList(cmap);
	}

	@Override
	public CommonList getSubPartWoSendListOdOnly(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMysqlInterfaceMapper.getSubPartWoSendListOdOnly(cmap);
	}

	@Override
	public void insertSubPartWoSendListOdOnly(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListOdOnly(cmap);
	}

	@Override
	public void insertSubPartWoSendListOdOnlyMStock(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListOdOnlyMStock(cmap);
	}

	@Override
	public void insertSubPartWoSendListOdOnlyMTransaction(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListOdOnlyMTransaction(cmap);
	}

	@Override
	public CommonList getSubPartWoSendListAll(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMysqlInterfaceMapper.getSubPartWoSendListAll(cmap);
	}

	@Override
	public void insertSubPartWoSendListAllMStock(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListAllMStock(cmap);
	}

	@Override
	public void insertSubPartWoSendListAllMTransaction(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertSubPartWoSendListAllMTransaction(cmap);
	}

	@Override
	public CommonList getPackingWoSendListAllCng(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMysqlInterfaceMapper.getPackingWoSendListAllCng(cmap);
	}

	@Override
	public void insertPackingWoSendListAllCngMStock(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertPackingWoSendListAllCngMStock(cmap);
	}

	@Override
	public void insertPackingWoSendListAllCngMTransaction(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.insertPackingWoSendListAllCngMTransaction(cmap);
	}

	@Override
	public void updateSubPartQtyOnHand(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.updateSubPartQtyOnHand(cmap);
	}

	@Override
	public void updateSubPartOdOnlyQtyOnHand(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.updateSubPartOdOnlyQtyOnHand(cmap);
	}

	@Override
	public void updatePackingWoSendListQtyOnHand(CommonMap cmap) throws Exception{
		// TODO Auto-generated method stub
		batchMysqlInterfaceMapper.updatePackingWoSendListQtyOnHand(cmap);
	}

}
