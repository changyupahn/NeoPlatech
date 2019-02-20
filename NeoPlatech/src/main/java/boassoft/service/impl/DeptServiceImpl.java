package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.DeptMapper;
import boassoft.service.DeptService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("deptService")
public class DeptServiceImpl extends EgovAbstractServiceImpl implements DeptService {

	@Resource(name="DeptMapper")
    private DeptMapper deptMapper;
	
	@Override
	public CommonList getDeptList(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		if (!"".equals(cmap.getString("searchKeyword"))) {
			CommonList deptNoList = deptMapper.getDeptNoList(cmap);
			String listStr = "";
			for (int i=0; i<deptNoList.size(); i++) {
				if (!"".equals(deptNoList.getMap(i).getString("deptNo01"))) listStr += deptNoList.getMap(i).getString("deptNo01") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo02"))) listStr += deptNoList.getMap(i).getString("deptNo02") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo03"))) listStr += deptNoList.getMap(i).getString("deptNo03") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo04"))) listStr += deptNoList.getMap(i).getString("deptNo04") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo05"))) listStr += deptNoList.getMap(i).getString("deptNo05") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo06"))) listStr += deptNoList.getMap(i).getString("deptNo06") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo07"))) listStr += deptNoList.getMap(i).getString("deptNo07") + ",";
				if (!"".equals(deptNoList.getMap(i).getString("deptNo08"))) listStr += deptNoList.getMap(i).getString("deptNo08") + ",";
			}
			if ("".equals(listStr)) {
				listStr = "00000";
			}
			cmap.put("listStr", listStr);
			cmap.put("sDeptNoArr", cmap.getArray("listStr"));
		}

		CommonList list = deptMapper.getDeptList(cmap);
		list.totalRow = deptMapper.getDeptListCnt(cmap);
		return list;
	}

	@Override
	public int getDeptListCnt(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.getDeptListCnt(cmap);
	}

	@Override
	public CommonMap getDeptView(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.getDeptView(cmap);
	}

	@Override
	public int insertDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.insertDept(cmap);
	}

	@Override
	public int updateDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.updateDept(cmap);
	}

	@Override
	public int deleteDept(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.deleteDept(cmap);
	}

	@Override
	public int deleteDept2(CommonMap cmap) throws Exception {
		// TODO Auto-generated method stub
		return deptMapper.deleteDept2(cmap);
	}

}
