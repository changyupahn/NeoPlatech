package boassoft.mapper;

import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("GrantMenuMapper")
public interface GrantMenuMapper {

	public CommonList getGrantMenuList(CommonMap cmap) throws Exception;
	
	public int getGrantMenuListCnt(CommonMap cmap) throws Exception;
	
	public CommonMap getGrantMenuView(CommonMap cmap) throws Exception;
	
	public CommonMap getGrantMenuView2(CommonMap cmap) throws Exception;
	
	public int insertGrantMenu(CommonMap cmap) throws Exception;
	
	public void updateGrantMenu(CommonMap cmap) throws Exception;
	
	public void deleteGrantMenu(CommonMap cmap) throws Exception;
	
	public void deleteGrantMenuAll(CommonMap cmap) throws Exception;
}
