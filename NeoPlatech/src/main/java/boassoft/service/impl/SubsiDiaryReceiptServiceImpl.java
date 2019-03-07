package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.SubsiDiaryReceiptMapper;
import boassoft.service.SubsiDiaryReceiptService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("subsiDiaryReceiptService")
public class SubsiDiaryReceiptServiceImpl extends EgovAbstractServiceImpl implements SubsiDiaryReceiptService{

	@Resource(name="SubsiDiaryReceiptMapper")
	private SubsiDiaryReceiptMapper subsiDiaryReceiptMapper;
	
	@Override
	public CommonList getSubsiDiaryReceiptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = subsiDiaryReceiptMapper.getSubsiDiaryReceiptList(cmap);
		list.totalRow = subsiDiaryReceiptMapper.getSubsiDiaryReceiptListCnt(cmap);
		return list;
	}

}
