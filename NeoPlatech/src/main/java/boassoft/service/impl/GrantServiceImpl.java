package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.service.GrantService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("GrantService")
public class GrantServiceImpl implements GrantService {

	@Resource(name="grantMapper")
    private GrantMapper grantMapper;

	
	@Override
	public CommonList getGrantList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getGrantListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CommonMap getGrantView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteGrant(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteGrant2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
