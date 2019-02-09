package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AddressMapper;
import boassoft.service.AddressService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("AddressService")
public class AddressServiceImple extends EgovAbstractServiceImpl implements AddressService {

	@Resource(name="addressMapper")
    private AddressMapper addressMapper;
	
	public int insertAddress(CommonMap cmap) throws Exception{
    	return addressMapper.insertAddress(cmap);
	}
	
	public void deleteAddress(CommonMap cmap) throws Exception{
		addressMapper.deleteAddress(cmap);
	}
	
	public CommonList getAddressList(CommonMap cmap) throws Exception {
		CommonList list = addressMapper.getAddressList(cmap);
		list.totalRow = addressMapper.getAddressListCnt(cmap);
		return list;
	}
	
	public CommonMap getAddressInfo(CommonMap cmap) throws Exception {
		return addressMapper.getAddressInfo(cmap);
	}
}
