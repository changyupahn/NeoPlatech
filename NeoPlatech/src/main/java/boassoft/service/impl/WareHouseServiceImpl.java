package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.WareHouseMapper;
import boassoft.service.WareHouseService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("wareHouseService")
public class WareHouseServiceImpl extends EgovAbstractServiceImpl implements WareHouseService {

	@Resource(name="WareHouseMapper")
    private WareHouseMapper wareHouseMapper;
	
	@Override
	public CommonList getWareHouseList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = wareHouseMapper.getWareHouseList(cmap);
		list.totalRow = wareHouseMapper.getWareHouseListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getWareHouseDetailList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = wareHouseMapper.getWareHouseDetailList(cmap);
		list.totalRow = wareHouseMapper.getWareHouseDetailListCnt(cmap);
		return list;
	}

}
