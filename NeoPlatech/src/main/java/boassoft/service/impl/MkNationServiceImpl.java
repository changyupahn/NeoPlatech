package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.MkNationMapper;
import boassoft.service.MkNationService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("MkNationService")
public class MkNationServiceImpl extends EgovAbstractServiceImpl implements MkNationService {

	@Resource(name="MkNationMapper")
    private MkNationMapper mkNationMapper;
	
	@Override
	public CommonList getMkNationList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("searchKeyword"))) {
			CommonList mkNationCdList = mkNationMapper.getMkNationCdList(cmap);
			String listStr = "";
			for (int i=0; i<mkNationCdList.size(); i++) {
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq01"))) listStr += mkNationCdList.getMap(i).getString("natnSeq01") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq02"))) listStr += mkNationCdList.getMap(i).getString("natnSeq02") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq03"))) listStr += mkNationCdList.getMap(i).getString("natnSeq03") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq04"))) listStr += mkNationCdList.getMap(i).getString("natnSeq04") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq05"))) listStr += mkNationCdList.getMap(i).getString("natnSeq05") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq06"))) listStr += mkNationCdList.getMap(i).getString("natnSeq06") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq07"))) listStr += mkNationCdList.getMap(i).getString("natnSeq07") + ",";
				if (!"".equals(mkNationCdList.getMap(i).getString("natnSeq08"))) listStr += mkNationCdList.getMap(i).getString("natnSeq08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "0";
			}
			cmap.put("listStr", listStr);
			cmap.put("sNatnSeqArr", cmap.getArray("listStr"));
		}

		CommonList list = mkNationMapper.getMkNationList(cmap);
		list.totalRow = mkNationMapper.getMkNationListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getMkNationList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = mkNationMapper.getMkNationList(cmap);
		list.totalRow = mkNationMapper.getMkNationListCnt(cmap);
		return list;
	}

	@Override
	public int getMkNationListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.getMkNationListCnt(cmap);
	}

	@Override
	public CommonMap getMkNationView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.getMkNationView(cmap);
	}

	@Override
	public int insertMkNation(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.insertMkNation(cmap);
	}

	@Override
	public int updateMkNation(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.updateMkNation(cmap);
	}

	@Override
	public int deleteMkNation(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.deleteMkNation(cmap);
	}

	@Override
	public int deleteMkNation2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkNationMapper.deleteMkNation2(cmap);
	}

}
