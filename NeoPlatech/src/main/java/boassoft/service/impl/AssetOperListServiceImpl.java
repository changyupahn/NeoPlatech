package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import boassoft.mapper.AssetOperListMapper;
import boassoft.service.AssetOperListService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("assetOperListService")
public class AssetOperListServiceImpl implements AssetOperListService {

	@Resource(name="AssetOperListMapper")
    private AssetOperListMapper assetOperListMapper;
	
	@Override
	public CommonList getAssetOperListList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = assetOperListMapper.getAssetOperListList(cmap);
		list.totalRow = assetOperListMapper.getAssetOperListListCnt(cmap);
		return list;
	}

	@Override
	public int getAssetOperListListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.getAssetOperListListCnt(cmap);
	}

	@Override
	public CommonMap getAssetOperListView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.getAssetOperListView(cmap);
	}

	@Override
	public int insertAssetOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.insertAssetOperList(cmap);
	}

	@Override
	public int updateAssetOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.updateAssetOperList(cmap);
	}

	@Override
	public int deleteAssetOperList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.deleteAssetOperList(cmap);
	}

	@Override
	public int deleteAssetOperList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetOperListMapper.deleteAssetOperList2(cmap);
	}

}
