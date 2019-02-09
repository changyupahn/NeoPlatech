package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("AddressMapper")
public interface AddressMapper {

	public int insertAddress(CommonMap cmap) throws Exception;
	
	public void deleteAddress(CommonMap cmap) throws Exception;
	
	public CommonList getAddressList(CommonMap cmap) throws Exception;
	
	public int getAddressListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getAddressInfo(CommonMap cmap) throws Exception;
}
