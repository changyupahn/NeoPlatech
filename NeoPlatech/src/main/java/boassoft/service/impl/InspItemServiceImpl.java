package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import boassoft.mapper.InspItemMapper;
import boassoft.service.InspItemService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("inspItemService")
public class InspItemServiceImpl extends EgovAbstractServiceImpl implements InspItemService{

	@Resource(name="InspItemMapper")
    private InspItemMapper inspItemMapper;

	@Resource(name = "inspItemSeqIdGnrService")
    private EgovIdGnrService inspItemSeqIdGnrService;
	
	@Override
	public CommonList getInspItemList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inspItemMapper.getInspItemList(cmap);
		list.totalRow = inspItemMapper.getInspItemListCnt(cmap);
		return list;
	}

	@Override
	public int getInspItemListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.getInspItemListCnt(cmap);
	}

	@Override
	public CommonList getInspItemList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = inspItemMapper.getInspItemList2(cmap);
		list.totalRow = inspItemMapper.getInspItemListCnt2(cmap);
		return list;
	}

	@Override
	public int getInspItemListCnt2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.getInspItemListCnt2(cmap);
	}

	@Override
	public CommonMap getInspItemView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.getInspItemView(cmap);
	}

	@Override
	public int insertInspItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.insertInspItem(cmap);
	}

	@Override
	public int insertInspItemAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//기존 데이터 삭제
		inspItemMapper.deleteInspItemAll(cmap);

    	//저장
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("inspItemSeq", inspItemSeqIdGnrService.getNextStringId().replaceAll("^[0]+", ""));
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		inspItemMapper.insertInspItem(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int updateInspItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.updateInspItem(cmap);
	}

	@Override
	public int updateInspItemAll(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//수정
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		inspItemMapper.updateInspItem2(param);

    		resultCnt++;
    	}

    	return resultCnt;
	}

	@Override
	public int deleteInspItem(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.deleteInspItem(cmap);
	}

	@Override
	public int deleteInspItem2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.deleteInspItemAll(cmap);
	}

	@Override
	public int deleteInspItemAll(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return inspItemMapper.deleteInspItemAll(cmap);
	}

	@Override
	public int deleteInspItem3All(CommonMap cmap, CommonList list)
			throws Exception {
		// TODO Auto-generated method stub
		int resultCnt = 0;

    	//삭제
    	for (int i=0; i<list.size(); i++) {
    		CommonMap param = list.getMap(i);
    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

    		if (!"".equals(param.getString("inspItemSeq"))) {
    			inspItemMapper.deleteInspItem3(param);
    		}

    		resultCnt++;
    	}

    	return resultCnt;
	}

}
 