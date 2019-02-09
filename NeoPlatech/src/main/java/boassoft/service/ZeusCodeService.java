package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ZeusCodeService {

	public CommonList getZeusCodeList(CommonMap cmap) throws Exception;
	
	public int getZeusCodeListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusCodeView(CommonMap cmap) throws Exception;
	
	public CommonMap getZeusCodeView2(CommonMap cmap) throws Exception;
	
	public int insertZeusCode(CommonMap cmap) throws Exception;
	
	public int updateZeusCode(CommonMap cmap) throws Exception;
	
	public int deleteZeusCode(CommonMap cmap) throws Exception;
	
	public int deleteZeusCode2(CommonMap cmap) throws Exception;
	
	public int deleteZeusCodeAll(CommonMap cmap) throws Exception;
	
	
}
