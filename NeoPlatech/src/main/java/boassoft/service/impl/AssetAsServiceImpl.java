package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AssetAsMapper;
import boassoft.service.AssetAsService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("AssetAsService")
public class AssetAsServiceImpl extends EgovAbstractServiceImpl implements AssetAsService {

	@Resource(name="AssetAsMapper")
    private AssetAsMapper assetAsMapper;
	
	@Override
	public CommonList getAssetAsList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = assetAsMapper.getAssetAsList(cmap);
		list.totalRow = assetAsMapper.getAssetAsListCnt(cmap);
		return list;
	}

	@Override
	public int getAssetAsListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.getAssetAsListCnt(cmap);
	}

	@Override
	public CommonMap getAssetAsView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.getAssetAsView(cmap);
	}

	@Override
	public int insertAssetAs(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.insertAssetAs(cmap);
	}

	@Override
	public int updateAssetAs(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.updateAssetAs(cmap);
	}

	@Override
	public int deleteAssetAs(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.deleteAssetAs(cmap);
	}

	@Override
	public int deleteAssetAs2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetAsMapper.deleteAssetAs2(cmap);
	}

}
