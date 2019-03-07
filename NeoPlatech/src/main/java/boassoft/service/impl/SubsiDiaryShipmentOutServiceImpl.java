package boassoft.service.impl;

import javax.annotation.Resource;

import boassoft.mapper.SubsiDiaryReceiptMapper;
import boassoft.mapper.SubsiDiaryShipmentOutMapper;
import boassoft.service.SubsiDiaryReceiptService;
import boassoft.service.SubsiDiaryShipmentOutService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

public class SubsiDiaryShipmentOutServiceImpl extends EgovAbstractServiceImpl implements SubsiDiaryShipmentOutService {

	@Resource(name="SubsiDiaryShipmentOutMapper")
	private SubsiDiaryShipmentOutMapper subsiDiaryShipmentOutMapper;
	
	@Override
	public CommonList getSubsiDiaryShipmentOutList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		CommonList list = subsiDiaryShipmentOutMapper.getSubsiDiaryShipmentOuttList(cmap);
		list.totalRow = subsiDiaryShipmentOutMapper.getSubsiDiaryShipmentOutListCnt(cmap);
		return list;
	}

}
