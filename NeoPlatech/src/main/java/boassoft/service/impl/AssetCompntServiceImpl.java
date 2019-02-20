package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import boassoft.mapper.AssetCompntMapper;
import boassoft.service.AssetCompntService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;


@Service("assetCompntService")
public class AssetCompntServiceImpl implements  AssetCompntService {

	@Resource(name="AssetCompntMapper")
    private AssetCompntMapper assetCompntMapper;
	
	@Override
	public CommonList getAssetCompntList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = assetCompntMapper.getAssetCompntList(cmap);
		list.totalRow = assetCompntMapper.getAssetCompntListCnt(cmap);
		return list;
	}

	@Override
	public int getAssetCompntListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.getAssetCompntListCnt(cmap);
	}

	@Override
	public CommonMap getAssetCompntView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.getAssetCompntView(cmap);
	}

	@Override
	public int insertAssetCompnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.insertAssetCompnt(cmap);
	}

	@Override
	public int updateAssetCompnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.updateAssetCompnt(cmap);
	}

	@Override
	public int deleteAssetCompnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.deleteAssetCompnt(cmap);
	}

	@Override
	public int deleteAssetCompnt2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetCompntMapper.deleteAssetCompnt2(cmap);
	}

}
