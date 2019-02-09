package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.InoutMapper;
import boassoft.service.InoutService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("InoutService")
public class InoutServiceImpl extends EgovAbstractServiceImpl implements InoutService{

	@Resource(name="inoutMapper")
    private InoutMapper inoutMapper;
	
	@Override
	public CommonList getInoutList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inoutMapper.getInoutList(cmap);
		list.totalRow = inoutMapper.getInoutListCnt(cmap);
		return list;
	}

}
