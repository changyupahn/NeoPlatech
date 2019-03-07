package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.GoodsShipmentOutMapper;
import boassoft.service.GoodsShipmentOutService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("goodsShipmentOutService")
public class GoodsShipmentOutServiceImpl extends EgovAbstractServiceImpl implements GoodsShipmentOutService{

	@Resource(name="GoodsShipmentOutMapper")
	private GoodsShipmentOutMapper goodsShipmentOutMapper;
		

	@Override
	public CommonList GoodsShipmentOutList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsShipmentOutMapper.getGoodsShipmentOutList(cmap);
		list.totalRow = goodsShipmentOutMapper.getGoodsShipmentOutListCnt(cmap);
		return list;
	}
	
	
}
