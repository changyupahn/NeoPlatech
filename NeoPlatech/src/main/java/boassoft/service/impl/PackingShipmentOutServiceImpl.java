package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.PackingShipmentOutMapper;
import boassoft.service.PackingShipmentOutService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("packingShipmentOutService")
public class PackingShipmentOutServiceImpl extends EgovAbstractServiceImpl implements PackingShipmentOutService{

	@Resource(name="PackingShipmentOutMapper")
	private PackingShipmentOutMapper packingShipmentOutMapper;
	
	@Override
	public CommonList getPackingShipmentOutList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingShipmentOutMapper.getPackingShipmentOutList(cmap);
		list.totalRow = packingShipmentOutMapper.getPackingShipmentOutListCnt(cmap);	
		return list;
	}

}
