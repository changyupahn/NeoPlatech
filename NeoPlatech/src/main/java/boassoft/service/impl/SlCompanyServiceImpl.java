package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.SlCompanyMapper;
import boassoft.service.SlCompanyService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("slCompanyService")
public class SlCompanyServiceImpl extends EgovAbstractServiceImpl implements SlCompanyService{

	@Resource(name="SlCompanyMapper")
    private SlCompanyMapper slCompanyMapper;
	
	@Override
	public CommonList getSlCompanyList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = slCompanyMapper.getSlCompanyList(cmap);
		list.totalRow = slCompanyMapper.getSlCompanyListCnt(cmap);
		return list;
	}

	@Override
	public int getSlCompanyListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.getSlCompanyListCnt(cmap);
	}

	@Override
	public CommonMap getSlCompanyView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.getSlCompanyView(cmap);
	}

	@Override
	public int insertSlCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.insertSlCompany(cmap);
	}

	@Override
	public int updateSlCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.updateSlCompany(cmap);
	}

	@Override
	public int deleteSlCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.deleteSlCompany(cmap);
	}

	@Override
	public int deleteSlCompany2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return slCompanyMapper.deleteSlCompany2(cmap);
	}

}
