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

	@Override
	public CommonList getOptionVendorList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsReceiptMapper.getOptionVendorList(cmap);
		return list;
	}

	@Override
	public CommonList getOptionItemList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsReceiptMapper.getOptionItemList(cmap);
		return list;
	}

	@Override
	public CommonList getOptionPNoList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsReceiptMapper.getOptionPNoList(cmap);
		return list;
	}

	@Override
	public CommonList getGoodsReceiptDetailList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		CommonList list = goodsReceiptMapper.getGoodsReceiptDetailList(cmap);
		list.totalRow = goodsReceiptMapper.getGoodsReceiptDetailListCnt(cmap);
		return list;
	}

	@Override
	public int updateQtyOnHand(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

		resultCnt = goodsReceiptMapper.updateQtyOnHand(cmap);
		
		return resultCnt;
    	    	
	}

	@Override
	public int insertRfidCInOrder(CommonMap cmap) throws Exception {		
		// TODO Auto-generated method stub
		int resultCnt = 0;
		resultCnt = goodsReceiptMapper.insertRfidCInOrder(cmap);
		return resultCnt;
	}

	@Override
	public int insertRfidCInOrderLine(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;
		resultCnt = goodsReceiptMapper.insertRfidCInOrderLine(cmap);
		return resultCnt;
	}

	@Override
	public int updateQtyInvoiced(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;
		
		resultCnt = goodsReceiptMapper.updateQtyInvoiced(cmap);
		
		return resultCnt;
	}
	

}
