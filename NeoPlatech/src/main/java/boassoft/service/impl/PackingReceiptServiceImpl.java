package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.GoodsReceiptMapper;
import boassoft.mapper.PackingReceiptMapper;
import boassoft.service.PackingReceiptService;
import boassoft.service.UserService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("packingReceiptService")
public class PackingReceiptServiceImpl extends EgovAbstractServiceImpl implements PackingReceiptService {

	@Resource(name="PackingReceiptMapper")
	private PackingReceiptMapper packingReceiptMapper;
	
	@Resource(name = "userService")
    private UserService userService;
	
	@Override
	public CommonList getPackingReceiptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingReceiptMapper.getPackingReceiptList(cmap);
		list.totalRow = packingReceiptMapper.getPackingReceiptListCnt(cmap);		
		return list;
	}

	@Override
	public CommonList getOptionVendorList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingReceiptMapper.getOptionVendorList(cmap);
		return list;
	}

	@Override
	public CommonList getOptionItemList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingReceiptMapper.getOptionItemList(cmap);
		return list;
	}

	@Override
	public CommonList getOptionPNoList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingReceiptMapper.getOptionPNoList(cmap);
		return list;
	}

	@Override
	public CommonList getPackingReceiptDetailList(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		CommonList list = packingReceiptMapper.getPackingReceiptDetailList(cmap);
		list.totalRow = packingReceiptMapper.getPackingReceiptDetailListCnt(cmap);
		return list;
	}

}
