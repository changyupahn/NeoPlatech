package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.InventoryStatMapper;
import boassoft.service.InventoryStatService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("inventoryStatService")
public class InventoryStatServiceImpl extends EgovAbstractServiceImpl implements InventoryStatService {

	@Resource(name="InventoryStatMapper")
    private InventoryStatMapper inventoryStatMapper;
	
	@Override
	public CommonList getDeptStat(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryStatMapper.getDeptStat(cmap);
		return list;
	}

	@Override
	public CommonList getUserStat(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryStatMapper.getUserStat(cmap);
		return list;
	}

	@Override
	public CommonList getHosilStat(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryStatMapper.getHosilStat(cmap);
		return list;
	}

	@Override
	public CommonList getAssetCateStat(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryStatMapper.getAssetCateStat(cmap);
		return list;
	}

	@Override
	public CommonList getNewRegisterStat(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryStatMapper.getNewRegisterStat(cmap);
		return list;
	}

}
