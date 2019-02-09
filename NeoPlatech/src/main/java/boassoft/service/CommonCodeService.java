package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface CommonCodeService {

	
	public CommonList getCommonCodeList(CommonMap cmap) throws Exception;
	
	public CommonMap getCommonCodeView(String codeId, String code) throws Exception;
	
	public String getCommonCodeName(String codeId, String code) throws Exception;
	
	public String getCommonCodeJqGridStr(String codeId) throws Exception;
	
	public int insertCommonCode(CommonMap cmap) throws Exception;
	
	public int updateCommonCode(CommonMap cmap) throws Exception;
	
	public int deleteCommonCode(CommonMap cmap) throws Exception;
	
	public int deleteCommonCode2(CommonMap cmap) throws Exception;
		
	
}
