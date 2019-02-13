package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.MkCompanyMapper;
import boassoft.service.MkCompanyService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("MkCompanyService")
public class MkCompanyServiceImpl extends EgovAbstractServiceImpl implements MkCompanyService {

	@Resource(name="MkCompanyMapper")
    private MkCompanyMapper mkCompanyMapper;
	
	@Override
	public CommonList getMkCompanyList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("searchKeyword"))) {
			CommonList mkCompanyCdList = mkCompanyMapper.getMkCompanyCdList(cmap);
			String listStr = "";
			for (int i=0; i<mkCompanyCdList.size(); i++) {
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq01"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq01") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq02"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq02") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq03"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq03") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq04"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq04") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq05"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq05") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq06"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq06") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq07"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq07") + ",";
				if (!"".equals(mkCompanyCdList.getMap(i).getString("compSeq08"))) listStr += mkCompanyCdList.getMap(i).getString("compSeq08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "0";
			}
			cmap.put("listStr", listStr);
			cmap.put("sCompSeqArr", cmap.getArray("listStr"));
		}

		CommonList list = mkCompanyMapper.getMkCompanyList(cmap);
		list.totalRow = mkCompanyMapper.getMkCompanyListCnt(cmap);
		return list;
	}

	@Override
	public CommonList getMkCompanyList2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		CommonList list = mkCompanyMapper.getMkCompanyList(cmap);
		list.totalRow = mkCompanyMapper.getMkCompanyListCnt(cmap);
		return list;
	}

	@Override
	public int getMkCompanyListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.getMkCompanyListCnt(cmap);
	}

	@Override
	public CommonMap getMkCompanyView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.getMkCompanyView(cmap);
	}

	@Override
	public CommonMap getMkCompanyView2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.getMkCompanyView2(cmap);
	}

	@Override
	public int insertMkCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.insertMkCompany(cmap);
	}

	@Override
	public int updateMkCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.updateMkCompany(cmap);
	}

	@Override
	public int deleteMkCompany(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.deleteMkCompany(cmap);
	}

	@Override
	public int deleteMkCompany2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return mkCompanyMapper.deleteMkCompany2(cmap);
	}

}
