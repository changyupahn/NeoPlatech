package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.mapper.DepreMapper;
import boassoft.service.DepreService;

@Service("depreService")
public class DepreServiceImpl extends EgovAbstractServiceImpl implements  DepreService {

	@Resource(name="DepreMapper")
    private DepreMapper depreMapper;

	@Override
	public CommonMap getMaxDepreView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.getMaxDepreView(cmap);
	}

	@Override
	public CommonMap getMinAqusitDt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.getMinAqusitDt(cmap);
	}

	@Override
	public CommonList getDepreList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = depreMapper.getDepreList(cmap);
		list.totalRow = depreMapper.getDepreListCnt(cmap);
		return list;
	}

	@Override
	public int getDepreListCnt(CommonMap cmap)
			throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.getDepreListCnt(cmap);
	}

	@Override
	public CommonMap getDepreView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.getDepreView(cmap);
	}

	@Override
	public int insertDepre(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.insertDepre(cmap);
	}

	@Override
	public int updateDepre(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.updateDepre(cmap);
	}

	@Override
	public int deleteDepre(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.deleteDepre(cmap);
	}

	@Override
	public int deleteDepre2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return depreMapper.deleteDepre2(cmap);
	}

	@Override
	public CommonList getDepreYearList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = depreMapper.getDepreYearList(cmap);
		list.totalRow = list.size();
		return list;
	}
	
	
}
