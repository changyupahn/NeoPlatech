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

	@Override
	public CommonList getPublicProductList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getPublicProductList(cmap);
	}

	@Override
	public CommonList getSubPartSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getSubPartSendList(cmap);
	}

	@Override
	public CommonList getPackingWoSendList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getPackingWoSendList(cmap);
	}

	@Override
	public CommonList getSubPartWoSendListODOnlyList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getSubPartWoSendListODOnlyList(cmap);
	}

	@Override
	public CommonList getInjectionProductPalletList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getInjectionProductPalletList(cmap);
	}

	@Override
	public CommonList getAssayProductPalletList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getAssayProductPalletList(cmap);
	}

	@Override
	public CommonList getLgGanpanList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMssqlMapper.getLgGanpanList(cmap);
	}
	
	

}
