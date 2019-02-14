package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ZeusOperListMapper;
import boassoft.service.ZeusOperListService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("zeusOperListService")
public class ZeusOperListServiceImpl extends EgovAbstractServiceImpl implements ZeusOperListService{

	@Resource(name="ZeusOperListMapper")
    private ZeusOperListMapper zeusOperListMapper;

	
	@Override
	public CommonList getZeusOperListList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusOperListMapper.getZeusOperListList(cmap);
		list.totalRow = zeusOperListMapper.getZeusOperListListCnt(cmap);
		return list;
	}

	@Override
	public int getZeusOperListListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.getZeusOperListListCnt(cmap);
	}

	@Override
	public CommonMap getZeusOperListView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.getZeusOperListView(cmap);
	}

	@Override
	public int insertZeusOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.insertZeusOperList(cmap);
	}

	@Override
	public int updateZeusOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.updateZeusOperList(cmap);
	}

	@Override
	public int deleteZeusOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.deleteZeusOperList(cmap);
	}

	@Override
	public int deleteZeusOperList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusOperListMapper.deleteZeusOperList2(cmap);
	}

}
