package boassoft.service;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface BatchMysqlService {

	public int loadDataFile(String tableName, CommonList list) throws Exception;
	
	public int loadDataFile(CommonMap cmap) throws Exception;
	
	public void runScript(String tableName, CommonList list) throws Exception;
	
	
}
