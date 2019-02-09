package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;


public interface AddressService {

	public int insertAddress(CommonMap cmap) throws Exception;

	public void deleteAddress(CommonMap cmap) throws Exception;
	
	public CommonList getAddressList(CommonMap cmap) throws Exception;
	
	public CommonMap getAddressInfo(CommonMap cmap) throws Exception;
}
