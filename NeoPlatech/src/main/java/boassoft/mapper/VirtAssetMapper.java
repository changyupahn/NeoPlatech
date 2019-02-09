package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("VirtAssetMapper")
public interface VirtAssetMapper {

	public CommonMap getVirtAssetNum(CommonMap cmap) throws Exception;
	
	public CommonList getVirtAssetList(CommonMap cmap) throws Exception;
	
	public int getVirtAssetListCnt(CommonMap cmap) throws Exception;
	
	public int insertVirtAsset(CommonMap cmap) throws Exception;
	
	public int deleteVirtAsset(CommonMap cmap) throws Exception;
	
	public int updateVirtAssetConfirm(CommonMap cmap) throws Exception;
	
	public int updateVirtAssetCancel(CommonMap cmap) throws Exception;
	
	
}
