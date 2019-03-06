package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.GoodsReceiptMapper;
import boassoft.service.GoodsReceiptService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("goodsReceiptService")
public class GoodsReceiptServiceImpl extends EgovAbstractServiceImpl implements GoodsReceiptService {

	@Resource(name="GoodsReceiptMapper")
    private GoodsReceiptMapper goodsReceiptMapper;
	
	@Override
	public CommonList getGoodsReceiptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsReceiptMapper.getGoodsReceiptList(cmap);
		list.totalRow = goodsReceiptMapper.getGoodsReceiptListCnt(cmap);
		return list;
	}

}
