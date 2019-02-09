package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface ZeusService {

	public CommonList getZeusList(CommonMap cmap) throws Exception;
	
	public String write(CommonMap cmap, CommonList paramList, String filePath, String fileName) throws Exception;
	
	public String modify(CommonMap cmap, CommonList paramList, String equipId) throws Exception;
	
	public String delete(CommonMap cmap, String equipId) throws Exception;
	
	public String upload2(CommonMap cmap, CommonList paramList, String filePath, String fileName) throws Exception;
	
	public String upload3(CommonMap cmap, CommonList paramList, String filePath, String fileName, String url) throws Exception;
	
	
}
