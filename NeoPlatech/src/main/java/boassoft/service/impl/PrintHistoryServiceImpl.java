package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.mapper.AssetMapper;
import boassoft.mapper.PrintHistoryMapper;
import boassoft.service.PrintHistoryService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;

@Service("PrintHistoryService")
public class PrintHistoryServiceImpl extends EgovAbstractServiceImpl implements PrintHistoryService {

	@Resource(name="PrintHistoryMapper")
    private PrintHistoryMapper printHistoryMapper;

	@Resource(name="AssetMapper")
    private AssetMapper assetMapper;

	@Resource(name = "printSeqIdGnrService")
    private EgovIdGnrService printSeqIdGnrService;
	
	@Override
	public CommonList getPrintHistoryList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = printHistoryMapper.getPrintHistoryList(cmap);
		list.totalRow = printHistoryMapper.getPrintHistoryListCnt(cmap);
		return list;
	}

	@Override
	public int getPrintHistoryListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.getPrintHistoryListCnt(cmap);
	}

	@Override
	public CommonMap getPrintHistoryView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.getPrintHistoryView(cmap);
	}

	@Override
	public int insertPrintHistory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.insertPrintHistory(cmap);
	}

	@Override
	public int insertPrintHistoryAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		//자산 조회
    		CommonMap vieaData = assetMapper.getAssetDetail(param);

    		param.put("printSeq", printSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("assetNo", vieaData.getString("assetNo"));
    		param.put("rfidNo", vieaData.getString("rfidNo"));
    		param.put("assetName", vieaData.getString("assetName"));
    		param.put("assetSize", vieaData.getString("assetSize"));
    		param.put("assetType", vieaData.getString("itemName"));
    		param.put("aqusitDt", vieaData.getString("aqusitDt"));
    		param.put("orgNo", cmap.getString("ssUserNo"));
    		param.put("invYear", "");
    		param.put("invNo", "");
    		param.put("reqSystem", cmap.getString("reqSystem"));
    		param.put("printDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
    		param.put("printType", cmap.getString("printType", "N"));
    		param.put("printYn", "N");
    		param.put("tmpTitle", "");
    		param.put("etc1", vieaData.getString("userName"));
    		param.put("etc2", "");
    		param.put("etc3", "");
    		param.put("etc4", "");
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		printHistoryMapper.insertPrintHistory(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updatePrintHistory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.updatePrintHistory(cmap);
	}

	@Override
	public int deletePrintHistory(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.deletePrintHistory(cmap);
	}

	@Override
	public int deletePrintHistory2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return printHistoryMapper.deletePrintHistory2(cmap);
	}

}
