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

}
