package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.GrantMapper;
import boassoft.service.GrantService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("grantService")
public class GrantServiceImpl implements GrantService {

	@Resource(name="GrantMapper")
    private GrantMapper grantMapper;

	
	@Override
	public CommonList getGrantList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = grantMapper.getGrantList(cmap);
		list.totalRow = grantMapper.getGrantListCnt(cmap);
		return list;
	}

	@Override
	public int getGrantListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.getGrantListCnt(cmap);
	}

	@Override
	public CommonMap getGrantView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.getGrantView(cmap);
	}

	@Override
	public int insertGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.insertGrant(cmap);
	}

	@Override
	public int updateGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.updateGrant(cmap);
	}

	@Override
	public int deleteGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.deleteGrant(cmap);
	}

	@Override
	public int deleteGrant2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return grantMapper.deleteGrant2(cmap);
	}

}
