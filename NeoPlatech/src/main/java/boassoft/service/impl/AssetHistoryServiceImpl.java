package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.service.AssetHistoryService;
import boassoft.service.SystemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.DateUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.mapper.AssetHistoryMapper;

@Service("AssetHistoryService")
public class AssetHistoryServiceImpl extends EgovAbstractServiceImpl implements  AssetHistoryService{

	@Resource(name="assetHistoryMapper")
    private AssetHistoryMapper assetHistoryMapper;
	
	@Resource(name = "histSeqIdGnrService")
    private EgovIdGnrService histSeqIdGnrService;
	
	@Resource(name = "SystemService")
    private SystemService systemService;
	
	public CommonList getAssetHistoryList(CommonMap cmap) throws Exception {
		CommonList list = assetHistoryMapper.getAssetHistoryList(cmap);
		list.totalRow = assetHistoryMapper.getAssetHistoryListCnt(cmap);
		return list;
	}
	
	public int getAssetHistoryListCnt(CommonMap cmap) throws Exception {
		return assetHistoryMapper.getAssetHistoryListCnt(cmap);
	}
	
	public CommonMap getAssetHistoryView(CommonMap cmap) throws Exception{
		return assetHistoryMapper.getAssetHistoryView(cmap);
	}
	
	public int insertAssetHistory(CommonMap cmap) throws Exception{

    	cmap.put("histSeq", histSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    	cmap.put("histDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));

    	return assetHistoryMapper.insertAssetHistory(cmap);
	}
	
	 public int insertAssetHistory(CommonMap oriAsset, CommonMap cmap) throws Exception{
	    	return insertAssetHistoryDetail(oriAsset, cmap, "ASSET_HIST", true);
	 }	 	
	 
	 public int insertAssetHistoryDetail(CommonMap oriAsset, CommonMap cmap, String dispType, boolean allowNullValue) throws Exception{
	    	int resultCnt = 1;
	    	cmap.put("histTypeCd", "3");

	    	//화면표시관리 (자산목록)
	    	cmap.put("dispType", dispType);
			CommonList dispMngList = systemService.getDispMngList(cmap);

			for (int i=0; i<dispMngList.size(); i++) {
				CommonMap dispMng = dispMngList.getMap(i);

				String logicalName = dispMng.getString("logical_name");
				String physicalName = dispMng.getString("physical_name");

		    	if (!oriAsset.getString(physicalName).equals(cmap.getString(physicalName))) {
		    		if (allowNullValue || (allowNullValue == false && !"".equals(cmap.getString(physicalName)))) {
			    		cmap.put("histSeq", histSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
			    		cmap.put("assetSeq", oriAsset.getString("assetSeq"));
			    		cmap.put("histDt", DateUtil.getFormatDate("yyyyMMddHHmmss"));
			    		cmap.put("histContent", String.format("%s (을)를 [%s] 에서 [%s] (으)로 변경함"
			    									, logicalName
			    									, oriAsset.getString(physicalName)
			    									, cmap.getString(physicalName)));
			    		this.insertAssetHistory(cmap);
			    		resultCnt++;
		    		}
		    	}
			}

	    	return resultCnt;
		}
}
