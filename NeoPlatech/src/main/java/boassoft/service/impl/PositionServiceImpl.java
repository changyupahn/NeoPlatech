package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.PositionMapper;
import boassoft.service.PositionService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;


@Service("PositionService")
public class PositionServiceImpl extends EgovAbstractServiceImpl implements  PositionService{

	@Resource(name="positionMapper")
    private PositionMapper positionMapper;
	
	@Override
	public CommonList getPositionList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("searchKeyword"))) {
			CommonList posNoList = positionMapper.getPositionCdList(cmap);
			String listStr = "";
			for (int i=0; i<posNoList.size(); i++) {
				if (!"".equals(posNoList.getMap(i).getString("posNo01"))) listStr += posNoList.getMap(i).getString("posNo01") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo02"))) listStr += posNoList.getMap(i).getString("posNo02") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo03"))) listStr += posNoList.getMap(i).getString("posNo03") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo04"))) listStr += posNoList.getMap(i).getString("posNo04") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo05"))) listStr += posNoList.getMap(i).getString("posNo05") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo06"))) listStr += posNoList.getMap(i).getString("posNo06") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo07"))) listStr += posNoList.getMap(i).getString("posNo07") + ",";
				if (!"".equals(posNoList.getMap(i).getString("posNo08"))) listStr += posNoList.getMap(i).getString("posNo08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "0";
			}
			cmap.put("listStr", listStr);
			cmap.put("sPosNoArr", cmap.getArray("listStr"));
		}
		
		CommonList list = positionMapper.getPositionList(cmap);
		list.totalRow = positionMapper.getPositionListCnt(cmap);
		return list;
	}

	@Override
	public int getPositionListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.getPositionListCnt(cmap);
	}

	@Override
	public CommonMap getPositionView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.getPositionView(cmap);
	}

	@Override
	public int insertPosition(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.insertPosition(cmap);
	}

	@Override
	public int updatePosition(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.updatePosition(cmap);
	}

	@Override
	public int deletePosition(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.deletePosition(cmap);
	}

	@Override
	public int deletePosition2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return positionMapper.deletePosition2(cmap);
	}

}
