package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ContractDtlMapper;
import boassoft.service.ContractDtlService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("contractDtlService")
public class ContractDtlServiceImpl extends EgovAbstractServiceImpl implements ContractDtlService{

	@Resource(name="ContractDtlMapper")
    private ContractDtlMapper contractDtlMapper;
	
	@Override
	public CommonList getContractDtlList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = contractDtlMapper.getContractDtlList(cmap);
		list.totalRow = contractDtlMapper.getContractDtlListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getContractDtlView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return contractDtlMapper.getContractDtlView(cmap);
	}

}
