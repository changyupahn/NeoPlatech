package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.mapper.LogMapper;
import boassoft.service.LogService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("logService")
public class LogServiceImpl extends EgovAbstractServiceImpl implements LogService {

	@Resource(name="LogMapper")
    private LogMapper logMapper;
	
	@Resource(name = "logSeqIdGnrService")
    private EgovIdGnrService logSeqIdGnrService;

	
	@Override
	public CommonList getLogList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = logMapper.getLogList(cmap);
		list.totalRow = logMapper.getLogListCnt(cmap);
		return list;
	}

	@Override
	public int getLogListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return logMapper.getLogListCnt(cmap);
	}

	@Override
	public CommonMap getLogView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return logMapper.getLogView(cmap);
	}

	@Override
	public int insertLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		cmap.put("logSeq", logSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));

    	return logMapper.insertLog(cmap);
	}

	@Override
	public int updateLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return logMapper.updateLog(cmap);
	}

	@Override
	public int deleteLog(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return logMapper.deleteLog(cmap);
	}

	@Override
	public int deleteLog2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return logMapper.deleteLog2(cmap);
	}

}
