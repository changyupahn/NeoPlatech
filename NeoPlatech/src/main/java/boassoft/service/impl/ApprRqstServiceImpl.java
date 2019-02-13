package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ApprRqstMapper;
import boassoft.mapper.BatchMapper;
import boassoft.service.ApprRqstService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("ApprRqstService")
public class ApprRqstServiceImpl extends EgovAbstractServiceImpl implements ApprRqstService {

	@Resource(name="ApprRqstMapper")
    private ApprRqstMapper apprRqstMapper;

	@Resource(name="BatchMapper")
    private BatchMapper batchMapper;
	
	@Override
	public CommonList getApprRqstList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = apprRqstMapper.getApprRqstList(cmap);
		list.totalRow = apprRqstMapper.getApprRqstListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getApprRqstView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return apprRqstMapper.getApprRqstView(cmap);
	}

	@Override
	public void insertApprRqst(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprRqstMapper.insertApprRqst(cmap);
	}

	@Override
	public void updateApprRqst(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprRqstMapper.updateApprRqst(cmap);
	}

	@Override
	public void deleteApprRqst(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		apprRqstMapper.deleteApprRqst(cmap);
	}

	@Override
	public int updateApprRqstStatusCd(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return batchMapper.updateApprRqst(cmap);
	}

}
