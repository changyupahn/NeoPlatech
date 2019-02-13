package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.BldngMapper;
import boassoft.service.BldngService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("BldngService")
public class BldngServiceImpl extends EgovAbstractServiceImpl implements BldngService{

	@Resource(name="BldngMapper")
    private BldngMapper bldngMapper;
	
	@Override
	public CommonList getBldngList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = bldngMapper.getBldngList(cmap);
		list.totalRow = bldngMapper.getBldngListCnt(cmap);
		return list;
	}

	@Override
	public int getBldngListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.getBldngListCnt(cmap);
	}

	@Override
	public CommonMap getBldngView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.getBldngView(cmap);
	}

	@Override
	public int insertBldng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.insertBldng(cmap);
	}

	@Override
	public int updateBldng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.updateBldng(cmap);
	}

	@Override
	public int deleteBldng(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.deleteBldng(cmap);
	}

	@Override
	public int deleteBldng2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return bldngMapper.deleteBldng2(cmap);
	}

}
