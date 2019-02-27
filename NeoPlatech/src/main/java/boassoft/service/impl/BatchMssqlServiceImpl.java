package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.BatchMssqlMapper;
import boassoft.service.BatchMssqlService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("batchMssqlService")
public class BatchMssqlServiceImpl extends EgovAbstractServiceImpl implements
		BatchMssqlService {

	@Resource(name = "BatchMssqlMapper")
	private BatchMssqlMapper batchMssqlMapper;

	@Override
	public CommonList getLGNewOrderPlanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getLGNewOrderPlanList(cmap);
	}

	@Override
	public CommonList getMartialBOM(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getMartialBOM(cmap);
	}

	@Override
	public CommonList getPackingWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getPackingWoSendListODOnlyList(cmap) ;
	}
	
	

}
