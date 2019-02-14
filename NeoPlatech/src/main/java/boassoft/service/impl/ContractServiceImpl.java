package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ContractMapper;
import boassoft.service.ContractService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("contractService")
public class ContractServiceImpl extends EgovAbstractServiceImpl implements ContractService {

	@Resource(name="ContractMapper")
    private ContractMapper contractMapper;
	
	@Override
	public CommonList getContractList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = contractMapper.getContractList(cmap);
		list.totalRow = contractMapper.getContractListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getContractView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return contractMapper.getContractView(cmap);
	}

}
