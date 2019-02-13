package boassoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.common.CommonXmlList;
import boassoft.mapper.AssetMapper;
import boassoft.mapper.InventoryMapper;
import boassoft.mapper.PrintMapper;
import boassoft.service.PrintService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Service("PrintService")
public class PrintServiceImpl extends EgovAbstractServiceImpl implements PrintService {

	
	@Resource(name="PrintMapper")
    private PrintMapper printMapper;

	@Resource(name="InventoryMapper")
    private InventoryMapper inventoryMapper;

	@Resource(name="AssetMapper")
    private AssetMapper assetMapper;

	@Resource(name = "printSeqIdGnrService")
    private EgovIdGnrService printSeqIdGnrService;

	@Override
	public CommonXmlList getPrintTargetListXml(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonXmlList list = new CommonXmlList();
		list.addList( (List)printMapper.getPrintTargetListXml(cmap) );

		printMapper.updatePrintTargetListXml(cmap);

		return list;
	}

	@Override
	public int updatePrintTargetSuccess(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		//태그 인쇄 회수 증가
		assetMapper.updateAssetPrintCnt(cmap);

//		//태그 인쇄 회수 증가 (재물조사)
//		if (!"".equals(cmap.getString("inv_year"))
//				&& !"".equals(cmap.getString("inv_no"))) {
//			inventoryDAO.updateInventoryPrint(cmap);
//		}

		//인쇄성공Update
		int result = printMapper.updatePrintTargetSuccess(cmap);

		return result;
	}

	@Override
	public int updatePrintTargetFail(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printMapper.updatePrintTargetFail(cmap);
	}

	@Override
	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = printMapper.getPrintHistoryList(cmap);
		list.totalRow = printMapper.getPrintHistoryListCnt(cmap);
		return list;
	}

	@Override
	public void insertHistory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("print_type", cmap.getString("print_type", cmap.getString("printType")));
    	cmap.put("print_dt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    	printMapper.insertHistory(cmap);
	}

	@Override
	public int insertHistory2(CommonMap cmap, CommonList list) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("printType", cmap.getString("printType", "N"));
    	cmap.put("printDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));

    	int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("printSeq", printSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		param.put("printDt", cmap.getString("printDt"));
    		param.put("ssUserNo", cmap.getString("ssUserNo"));
    		param.put("tmpTitle", "");
    		param.put("printType", cmap.getString("printType"));
    		param.put("reqSystem", cmap.getString("reqSystem"));

    		printMapper.insertHistory2(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int insertHistory3(CommonMap cmap, CommonList list) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("printType", cmap.getString("printType", "N"));
    	cmap.put("printDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));

    	int resultCnt = 0;
    	
    	//임시부여코드 생성
		cmap.put("tmpCode", DateUtil.getFormatDate("yyyyMMddHHmmssSSS"));

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("tmpCode", cmap.getString("tmpCode"));
    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("printSeq", printSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		param.put("printDt", cmap.getString("printDt"));
    		param.put("ssUserNo", cmap.getString("ssUserNo"));
    		param.put("tmpTitle", "");
    		param.put("printType", cmap.getString("printType"));
    		param.put("reqSystem", cmap.getString("reqSystem"));

    		printMapper.insertHistoryTmp(param);

    		resultCnt++;
    		
    	}
    	
    	printMapper.insertHistoryTmpApply(cmap);

    	return resultCnt;
	}

	@Override
	public void updatePrintYn(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		printMapper.updatePrintYn(cmap);
	}

	@Override
	public void insertRePrint(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("print_dt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
		printMapper.insertRePrint(cmap);
	}
	
	
}
