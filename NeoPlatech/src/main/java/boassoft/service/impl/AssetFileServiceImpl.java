package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AssetFileMapper;
import boassoft.service.AssetFileService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("AssetFileService")
public class AssetFileServiceImpl extends EgovAbstractServiceImpl implements AssetFileService{

	@Resource(name="AssetFileMapper")
    private AssetFileMapper assetFileMapper;
	
	@Override
	public CommonList getAssetFileList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = assetFileMapper.getAssetFileList(cmap);
		list.totalRow = assetFileMapper.getAssetFileListCnt(cmap);
		return list;
	}

	@Override
	public int getAssetFileListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.getAssetFileListCnt(cmap);
	}

	@Override
	public CommonMap getAssetFileView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.getAssetFileView(cmap);
	}

	@Override
	public int insertAssetFile(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.insertAssetFile(cmap);
	}

	@Override
	public int updateAssetFile(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.updateAssetFile(cmap);
	}

	@Override
	public int deleteAssetFile(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.deleteAssetFile(cmap);
	}

	@Override
	public int deleteAssetFile2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return assetFileMapper.deleteAssetFile2(cmap);
	}

}
 