package boassoft.service.impl;

import javax.annotation.Resource;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.BatchMysqlMapper;
import boassoft.service.BatchMysqlService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public class BatchMysqlServiceImpl extends EgovAbstractServiceImpl implements BatchMysqlService {

	@Resource(name="batchMysqlMapper")
    private BatchMysqlMapper batchMysqlMapper;
	
	@Override
	public int loadDataFile(String tableName, CommonList list) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlMapper.loadDataFile(null);
		return 1;
	}

	@Override
	public void runScript(String tableName, CommonList list) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int loadDataFile(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		batchMysqlMapper.loadDataFile(cmap);
		return 1;
	}

}
