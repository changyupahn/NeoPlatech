package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.ZeusAsListMapper;
import boassoft.service.ZeusAsListService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("ZeusAsListService")
public class ZeusAsListServiceImpl extends EgovAbstractServiceImpl implements  ZeusAsListService {

	@Resource(name="ZeusAsListMapper")
    private ZeusAsListMapper zeusAsListMapper;
	
	@Override
	public CommonList getZeusAsListList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusAsListMapper.getZeusAsListList(cmap);
		list.totalRow = zeusAsListMapper.getZeusAsListListCnt(cmap);
		return list;
	}

	@Override
	public int getZeusAsListListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.getZeusAsListListCnt(cmap);
	}

	@Override
	public CommonMap getZeusAsListView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.getZeusAsListView(cmap);
	}

	@Override
	public int insertZeusAsList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.insertZeusAsList(cmap);
	}

	@Override
	public int updateZeusAsList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.updateZeusAsList(cmap);
	}

	@Override
	public int deleteZeusAsList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.deleteZeusAsList(cmap);
	}

	@Override
	public int deleteZeusAsList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusAsListMapper.deleteZeusAsList2(cmap);
	}

}
