package boassoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.common.CommonXmlList;
import boassoft.mapper.GateMapper;
import boassoft.service.GateService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("gateService")
public class GateServiceImpl extends EgovAbstractServiceImpl implements GateService {

	@Resource(name="GateMapper")
    private GateMapper gateMapper;
	
	public CommonList getAssetInfoList(CommonMap cmap) throws Exception {
		return gateMapper.getAssetInfoList(cmap);
	}
	
	public int insertGateHistory(CommonMap cmap) throws Exception {
		return gateMapper.insertGateHistory(cmap);
	}
	
	@SuppressWarnings("unchecked")
	public CommonXmlList getAssetInfoXml(CommonMap cmap) throws Exception {
		CommonXmlList list = new CommonXmlList();
		list.addList( (List)gateMapper.getAssetInfoXml(cmap) );
		return list;
	}
}
