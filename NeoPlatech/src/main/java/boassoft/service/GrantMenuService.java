package boassoft.service;

import javax.servlet.http.HttpServletRequest;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface GrantMenuService {

	public CommonList getGrantMenuList(CommonMap cmap) throws Exception;
	
	public CommonMap getGrantMenuView(CommonMap cmap) throws Exception;
	
	public void setGrantMenu(CommonMap cmap, HttpServletRequest request) throws Exception;
	
	public String getGrantMenuRead(String grantNo, String menuNo) throws Exception;

	public String getGrantMenuWrite(String grantNo, String menuNo) throws Exception;
	
	public int insertGrantMenu(CommonMap cmap) throws Exception;
	
	public int insertGrantMenuAll(CommonMap cmap, CommonList list) throws Exception;
	
	public void updateGrantMenu(CommonMap cmap) throws Exception;
	
	public void deleteGrantMenu(CommonMap cmap) throws Exception;
	
	
}
