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
	

	@Override
	public CommonList getGoodsShipmentOutDetailList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsShipmentOutMapper.getGoodsShipmentOutDetailList(cmap);
		list.totalRow = goodsShipmentOutMapper.getGoodsShipmentOutDetailListCnt(cmap);
		return list;
	}


	@Override
	public int insertRridMOut(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;
		resultCnt = goodsShipmentOutMapper.insertRridMOut(cmap);
		return resultCnt;
	}


	@Override
	public int insertRridMOutLine(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;
		resultCnt = goodsShipmentOutMapper.insertRridMOutLine(cmap);
		return resultCnt;
	}	
	
	
}
