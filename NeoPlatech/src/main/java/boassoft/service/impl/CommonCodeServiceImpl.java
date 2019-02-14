package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.service.CommonCodeService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.mapper.CommonCodeMapper;

@Service("commonCodeService")
public class CommonCodeServiceImpl extends EgovAbstractServiceImpl implements CommonCodeService {

	@Resource(name="CommonCodeMapper")
    private CommonCodeMapper commonCodeMapper;
	
	public CommonList getCommonCodeList(CommonMap cmap) throws Exception {
		CommonList list = commonCodeMapper.getCommonCodeList(cmap);
		return list;
	}
	
	public CommonMap getCommonCodeView(String codeId, String code) throws Exception {
		CommonMap cmap = new CommonMap();
		cmap.put("codeId", codeId);
		cmap.put("code", code);
		return commonCodeMapper.getCommonCodeView(cmap);
	}
	
	public String getCommonCodeName(String codeId, String code) throws Exception {
		CommonMap cmap = new CommonMap();
		cmap.put("codeId", codeId);
		cmap.put("code", code);
		return commonCodeMapper.getCommonCodeView(cmap).getString("codeName");
	}
	
	public String getCommonCodeJqGridStr(String codeId) throws Exception {
		CommonMap cmap = new CommonMap();
		cmap.put("codeId", codeId);
		cmap.put("paramCodeId", codeId);
		CommonList list = commonCodeMapper.getCommonCodeList(cmap);

		//공통코드
		String str = "";
		if (list.size() > 0) {
			for (int i=0; i<list.size(); i++) {
				CommonMap view = list.getMap(i);
				str += String.format("<option value=\"%s\" '+(value==\"%s\"?\"selected\":\"\")+'>%s</option>"
										, view.getString("code")
										, view.getString("code")
										, view.getString("codeName")
										);
			}
		}

		return str;
	}
	
	public int insertCommonCode(CommonMap cmap) throws Exception{
    	return commonCodeMapper.insertCommonCode(cmap);
	}
	
	public int updateCommonCode(CommonMap cmap) throws Exception{
    	return commonCodeMapper.updateCommonCode(cmap);
	}

	public int deleteCommonCode(CommonMap cmap) throws Exception{
    	return commonCodeMapper.deleteCommonCode(cmap);
	}
	
	public int deleteCommonCode2(CommonMap cmap) throws Exception{
    	return commonCodeMapper.deleteCommonCode2(cmap);
	}
}
