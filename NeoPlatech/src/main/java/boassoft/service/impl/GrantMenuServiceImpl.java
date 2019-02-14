package boassoft.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import boassoft.service.GrantMenuService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import boassoft.util.RequestUtil;
import boassoft.util.SessionUtil;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.GrandMenuMapper;

@Service("grantMenuService")
public class GrantMenuServiceImpl extends EgovAbstractServiceImpl implements GrantMenuService {

	@Resource(name="GrandMenuMapper")
	private GrandMenuMapper grandMenuMapper;
	
	public CommonList getGrantMenuList(CommonMap cmap) throws Exception {
		CommonList list = grandMenuMapper.getGrantMenuList(cmap);
		list.totalRow = grandMenuMapper.getGrantMenuListCnt(cmap);
		return list;
	}
	
	public CommonMap getGrantMenuView(CommonMap cmap) throws Exception{
		return grandMenuMapper.getGrantMenuView(cmap);
	}
	
	public void setGrantMenu(CommonMap cmap, HttpServletRequest request) throws Exception{
		CommonMap param = new CommonMap();
		param.put("grantNo", SessionUtil.getString("grantNo"));
		param.put("menuNo", RequestUtil.getMenuNo(request));

		CommonMap viewData = grandMenuMapper.getGrantMenuView(param);

		//읽기 권환
		if ("Y".equals(viewData.getString("grantManagerYn"))) {
			cmap.put("ssGrantRead", "GRANT_MGR");
		} else if ("Y".equals(viewData.getString("grantHreadYn"))) {
			cmap.put("ssGrantRead", "GRANT_DHD");
		} else if ("Y".equals(viewData.getString("grantReadYn"))) {
			cmap.put("ssGrantRead", "GRANT_USR");
		} else {
			cmap.put("ssGrantRead", "GRANT_NONE");
		}

		//쓰기 권한
		if ("Y".equals(viewData.getString("grantManagerYn"))) {
			cmap.put("ssGrantWrite", "GRANT_MGR");
		} else if ("Y".equals(viewData.getString("grantHwriteYn"))) {
			cmap.put("ssGrantWrite", "GRANT_DHD");
		} else if ("Y".equals(viewData.getString("grantWriteYn"))) {
			cmap.put("ssGrantWrite", "GRANT_USR");
		} else {
			cmap.put("ssGrantWrite", "GRANT_NONE");
		}
	}
	
	public String getGrantMenuRead(String grantNo, String menuNo) throws Exception{
		
		String result = "";
		CommonMap cmap = new CommonMap();
		cmap.put("grantNo", grantNo);
		cmap.put("menuNo", menuNo);
		
		CommonMap viewData = grandMenuMapper.getGrantMenuView(cmap);
		
		if ("Y".equals(viewData.getString("grantManagerYn"))) {
			result = "GRANT_MGR";
		} else if ("Y".equals(viewData.getString("grantHreadYn"))) {
			result = "GRANT_DHD";
		} else if ("Y".equals(viewData.getString("grantReadYn"))) {
			result = "GRANT_USR";
		} else {
			result = "GRANT_NONE";
		}

		return result;
	}
	
	public String getGrantMenuWrite(String grantNo, String menuNo) throws Exception{
		
		String result = "";
		CommonMap cmap = new CommonMap();
		cmap.put("grantNo", grantNo);
		cmap.put("menuNo", menuNo);
		
		CommonMap viewData = grandMenuMapper.getGrantMenuView(cmap);

		if ("Y".equals(viewData.getString("grantManagerYn"))) {
			result = "GRANT_MGR";
		} else if ("Y".equals(viewData.getString("grantHwriteYn"))) {
			result = "GRANT_DHD";
		} else if ("Y".equals(viewData.getString("grantWriteYn"))) {
			result = "GRANT_USR";
		} else {
			result = "GRANT_NONE";
		}
		
		return result;
	}
	
	public int insertGrantMenu(CommonMap cmap) throws Exception{
    	return grandMenuMapper.insertGrantMenu(cmap);
	}

	 public int insertGrantMenuAll(CommonMap cmap, CommonList list) throws Exception{
		 
		 int resultCnt = 0;

	    	//삭제 후 저장
		 grandMenuMapper.deleteGrantMenuAll(cmap);
		 
		//저장
	    	for (int i=0; i<list.size(); i++) {
	    		CommonMap param = list.getMap(i);

	    		param.put("grantNo", cmap.getString("grantNo"));
	    		param.put("frstRegisterId", cmap.getString("ssUserNo"));
	    		param.put("lastUpdusrId", cmap.getString("ssUserNo"));

	    		grandMenuMapper.insertGrantMenu(param);

	    		resultCnt++;
	    	}
	    	
	    	return resultCnt;
	 }
	 
	 public void updateGrantMenu(CommonMap cmap) throws Exception{
		 grandMenuMapper.updateGrantMenu(cmap);
	}
	 
	 public void deleteGrantMenu(CommonMap cmap) throws Exception{
		 grandMenuMapper.deleteGrantMenu(cmap);
	} 
}
