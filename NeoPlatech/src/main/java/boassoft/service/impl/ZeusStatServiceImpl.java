package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.ZeusStatMapper;
import boassoft.service.ZeusStatService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("ZeusStatService")
public class ZeusStatServiceImpl extends EgovAbstractServiceImpl implements ZeusStatService {

	@Resource(name="zeusStatMapper")
    private ZeusStatMapper zeusStatMapper;
	
	@Override
	public CommonList getZeusStatYearList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusStatMapper.getZeusStatYearList(cmap);
		return list;
	}

	@Override
	public CommonList getZeusMonthStatList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusStatMapper.getZeusMonthStatList(cmap);
		list.totalRow = zeusStatMapper.getZeusMonthStatListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getZeusEquipStatList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusStatMapper.getZeusEquipStatList(cmap);
		list.totalRow = zeusStatMapper.getZeusEquipStatListCnt(cmap);
		return list;
	}

}
