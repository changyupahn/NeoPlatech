package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AssetSubMapper;
import boassoft.service.AssetSubService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("AssetSubService")
public class AssetSubServiceImpl extends EgovAbstractServiceImpl implements AssetSubService{

	@Resource(name="assetSubMapper")
    private AssetSubMapper assetSubMapper;
	
	@Override
	public int updateAssetSub(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetSubMapper.updateAssetSub(cmap);
	}

	@Override
	public int updateAssetSubAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		resultCnt += assetSubMapper.updateAssetSub(param);
    	}

    	return resultCnt;
	}

	@Override
	public int deleteAssetSub(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetSubMapper.deleteAssetSub(cmap);
	}

	@Override
	public int deleteAssetSubAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		resultCnt += assetSubMapper.deleteAssetSub(param);
    	}

    	return resultCnt;
	}

}
