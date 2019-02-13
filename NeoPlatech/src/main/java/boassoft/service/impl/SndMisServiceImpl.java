package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.SndMisMapper;
import boassoft.service.SndMisService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("SndMisService")
public class SndMisServiceImpl extends EgovAbstractServiceImpl implements SndMisService {

	@Resource(name="SndMisMapper")
    private SndMisMapper sndMisMapper;
	
	@Override
	public CommonList getSndMisList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = sndMisMapper.getSndMisList(cmap);
		list.totalRow = sndMisMapper.getSndMisListCnt(cmap);
		return list;
	}

	@Override
	public int getSndMisListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.getSndMisListCnt(cmap);
	}

	@Override
	public CommonMap getSndMisView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.getSndMisView(cmap);
	}

	@Override
	public int insertSndMis(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.insertSndMis(cmap);
	}

	@Override
	public int updateSndMis(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.updateSndMis(cmap);
	}

	@Override
	public int deleteSndMis(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.deleteSndMis(cmap);
	}

	@Override
	public int deleteSndMis2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return sndMisMapper.deleteSndMis2(cmap);
	}

}
