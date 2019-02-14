package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.InspAssetMapper;
import boassoft.mapper.VirtAssetMapper;
import boassoft.service.AssetHistoryService;
import boassoft.service.InspAssetService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("inspAssetService")
public class InspAssetServiceimpl extends EgovAbstractServiceImpl implements InspAssetService {

	@Resource(name="InspAssetMapper")
    private InspAssetMapper inspAssetMapper;

	@Resource(name="VirtAssetMapper")
    private VirtAssetMapper virtAssetMapper;
	
	@Resource(name="assetHistoryService")
    private AssetHistoryService assetHistoryService;

	@Override
	public CommonList getInspAssetList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inspAssetMapper.getInspAssetList(cmap);
		list.totalRow = inspAssetMapper.getInspAssetListCnt(cmap);
		return list;
	}

	@Override
	public int getInspAssetListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.getInspAssetListCnt(cmap);
	}

	@Override
	public CommonMap getInspAssetView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.getInspAssetView(cmap);
	}

	@Override
	public int insertInspAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.insertInspAsset(cmap);
	}

	@Override
	public int insertInspAssetAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		//삭제 후 저장
    		inspAssetMapper.deleteInspAsset2(param);

    		param.put("assetSeq", param.getString("assetSeq"));
    		param.put("purno", param.getString("purno"));
    		param.put("rqstno", param.getString("rqstno"));
    		param.put("prodno", param.getString("prodno"));
    		param.put("inspDt", cmap.getString("inspDt"));
    		param.put("aqusitDt", cmap.getString("aqusitDt"));
    		param.put("inspUserName", cmap.getString("inspUserName"));
    		param.put("inspRemark", cmap.getString("inspRemark"));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		//검수정보 저장
    		inspAssetMapper.insertInspAsset(param);

    		//자산확정 처리
    		virtAssetMapper.updateVirtAssetConfirm(param);

    		//자산 히스토리 등록
    		param.put("histTypeCd", "1");
    		param.put("histContent", "자산취득");
    		assetHistoryService.insertAssetHistory(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateInspAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.updateInspAsset(cmap);
	}

	@Override
	public int deleteInspAsset(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.deleteInspAsset(cmap);
	}

	@Override
	public int deleteInspAsset2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspAssetMapper.deleteInspAsset2(cmap);
	}

	@Override
	public int deleteInspAsset2All(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);

    		//삭제
    		inspAssetMapper.deleteInspAsset2(param);

    		//자산확정 처리 취소
    		virtAssetMapper.updateVirtAssetCancel(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	
}
