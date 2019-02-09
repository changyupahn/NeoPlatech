package boassoft.mapper;

import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("BatchMysqlMapper")
public interface BatchMysqlMapper {

	public void loadDataFile(CommonMap cmap) throws Exception;
	
	
}
