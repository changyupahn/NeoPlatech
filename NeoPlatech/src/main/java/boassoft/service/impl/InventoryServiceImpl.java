package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.InventoryMapper;
import boassoft.service.InventoryService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Service("InventoryService")
public class InventoryServiceImpl extends EgovAbstractServiceImpl implements InventoryService {

	@Resource(name = "InventoryMapper")
	private InventoryMapper inventoryMapper;

	@Override
	public CommonList getInventoryYearList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryMapper.getInventoryYearList(cmap);
		return list;
	}

	@Override
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getInventoryLast(cmap);
	}

	@Override
	public CommonList getInventoryList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryMapper.getInventoryList(cmap);
		list.totalRow = inventoryMapper.getInventoryListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getInventoryView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getInventoryView(cmap);
	}

	@Override
	public int insertInventoryMaster(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put(
				"inv_year",
				cmap.getString("inv_start_dt").replaceAll("\\D", "")
						.substring(0, 4));
		cmap.put("inv_no", inventoryMapper.getMaxJaemulNo(cmap));

		inventoryMapper.insertInventoryMaster(cmap);
		inventoryMapper.insertInventoryDetail(cmap);
		return 1;
	}

	@Override
	public void syncInventoryDetail(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		// 마지막 재물조사 조회
		CommonMap inventoryLast = inventoryMapper.getInventoryLast(cmap);
		cmap.put("invYear", inventoryLast.getString("invYear"));
		cmap.put("invNo", inventoryLast.getString("invNo"));

		if (!"".equals(cmap.getString("invYear"))
				&& !"".equals(cmap.getString("invNo"))) {
			inventoryMapper.insertInventoryDetailSync(cmap);
			inventoryMapper.deleteInventoryDetailSync(cmap);
		}
	}

	@Override
	public void updateInventory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		inventoryMapper.updateInventory(cmap);
	}

	@Override
	public int deleteInventory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.deleteInventory(cmap);
	}

	@Override
	public CommonList getInventoryDetailList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("dataOrder", cmap.getString("dataOrder").replaceAll("[^0-9a-zA-Z_.]",""));
    	cmap.put("dataOrderArrow", cmap.getString("dataOrderArrow", "asc").toLowerCase().replaceAll("^(asc|desc)$","$1"));

		CommonList list = inventoryMapper.getInventoryDetailList(cmap);
		list.totalRow = inventoryMapper.getInventoryDetailListCnt(cmap);
		return list;
	}

	@Override
	public CommonMap getInventoryDetailView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getInventoryDetailView(cmap);
	}

	@Override
	public int updateTargetYn(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.updateTargetYn(cmap);
	}

	@Override
	public int updateTargetYnChk(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("invYear", cmap.getString("invYear"));
    		param.put("invNo", cmap.getString("invNo"));
    		param.put("sAssetSeq", param.getString("assetSeq"));
    		param.put("invTargetYn", cmap.getString("invTargetYn"));

    		int updateCnt = inventoryMapper.updateTargetYn(param);

    		if (updateCnt == 0 && "Y".equals(param.getString("invTargetYn"))) {
    			inventoryMapper.insertInventoryDetail(param);
    		}

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateTargetYnAll(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
        String invTargetYn = cmap.getString("invTargetYn", "N");
    	
    	cmap.put("invTargetYn", "N");
    	inventoryMapper.insertInventoryDetail(cmap);    	
    	
    	cmap.put("invTargetYn", invTargetYn);
    	return inventoryMapper.updateTargetYn(cmap);
	}

	@Override
	public void updateInventoryPrint(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		inventoryMapper.updateInventoryPrint(cmap);
	}

	@Override
	public void insertPrintHistory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("print_type", cmap.getString("print_type", cmap.getString("printType")));
		cmap.put("print_dt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
		cmap.put("ss_org_no", cmap.getString("ss_org_no", "1"));
		inventoryMapper.insertPrintHistory(cmap);
	}

	@Override
	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.getAssetHistoryList(cmap);
	}

	@Override
	public int updateRequest(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.updateRequest(cmap);
	}

	@Override
	public int updateRequestCon(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.updateRequestCon(cmap);
	}

	@Override
	public CommonMap selectRequest(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inventoryMapper.selectRequest(cmap);
	}

	@Override
	public CommonList selectRequestCon(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inventoryMapper.selectRequestCon(cmap);
		return list;
	}

	@Override
	public int deleteRequestCon(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		param.put("invYear", cmap.getString("invYear"));
    		param.put("invNo", cmap.getString("invNo"));
    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("confirmDt", cmap.getString("confirmDt"));
    		resultCnt += inventoryMapper.deleteRequestCon(param);
    	}

    	return resultCnt;
	}

}
