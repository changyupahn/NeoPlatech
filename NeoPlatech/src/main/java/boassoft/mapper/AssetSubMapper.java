package boassoft.mapper;

import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AssetSubMapper")
public interface AssetSubMapper {

	public int updateAssetSub(CommonMap cmap) throws Exception;
	
	public int deleteAssetSub(CommonMap cmap) throws Exception;
}
