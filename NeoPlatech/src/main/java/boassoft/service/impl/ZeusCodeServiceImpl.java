package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import boassoft.mapper.ZeusCodeMapper;
import boassoft.service.ZeusCodeService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("zeusCodeService")
public class ZeusCodeServiceImpl extends EgovAbstractServiceImpl implements ZeusCodeService {

	@Resource(name="ZeusCodeMapper")
    private ZeusCodeMapper zeusCodeMapper;

	
	@Override
	public CommonList getZeusCodeList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = zeusCodeMapper.getZeusCodeList(cmap);
		list.totalRow = zeusCodeMapper.getZeusCodeListCnt(cmap);
		return list;
	}

	@Override
	public int getZeusCodeListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.getZeusCodeListCnt(cmap);
	}

	@Override
	public CommonMap getZeusCodeView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.getZeusCodeView(cmap);
	}

	@Override
	public CommonMap getZeusCodeView2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.getZeusCodeView2(cmap);
	}

	@Override
	public int insertZeusCode(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.insertZeusCode(cmap);
	}

	@Override
	public int updateZeusCode(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.updateZeusCode(cmap);
	}

	@Override
	public int deleteZeusCode(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.deleteZeusCode(cmap);
	}

	@Override
	public int deleteZeusCode2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.deleteZeusCode2(cmap);
	}

	@Override
	public int deleteZeusCodeAll(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return zeusCodeMapper.deleteZeusCodeAll(cmap);
	}

}
