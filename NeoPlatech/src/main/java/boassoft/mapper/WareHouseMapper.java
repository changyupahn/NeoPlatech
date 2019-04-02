package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("WareHouseMapper")
public interface WareHouseMapper {

	public CommonList getWareHouseList(CommonMap cmap) throws Exception;

	public int getWareHouseListCnt(CommonMap cmap) throws Exception;

	public CommonList getWareHouseDetailList(CommonMap cmap) throws  Exception;

	public int getWareHouseDetailListCnt(CommonMap cmap) throws  Exception;

}