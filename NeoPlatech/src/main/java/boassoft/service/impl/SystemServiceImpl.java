package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.service.SystemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.mapper.SystemMapper;

@Service("systemService")
public class SystemServiceImpl extends EgovAbstractServiceImpl implements SystemService {

	@Resource(name="SystemMapper")
    private SystemMapper systemMapper;
	
	public CommonList getAddcolMngList(CommonMap cmap) throws Exception {
		CommonList list = systemMapper.getAddcolMngList(cmap);
		return list;
	}
	
	public CommonList getDispMngList(CommonMap cmap) throws Exception {
		CommonList list = systemMapper.getDispMngList(cmap);
		return list;
	}
}
