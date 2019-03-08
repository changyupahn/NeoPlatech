package boassoft.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.BatchMysqlInterfaceMapper;
import boassoft.service.BatchMysqlInterfaceService;
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

}
