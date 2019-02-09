package boassoft.mapper;

import java.util.List;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("TabMapper")
public interface TabMapper {

	public CommonMap getRfidLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception;
	
	public List getAssetListXml(CommonMap cmap) throws Exception;
	
	public int getAssetListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getInventoryListXml(CommonMap cmap) throws Exception;
	
	public int getInventoryListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getInventoryDetailListXml(CommonMap cmap) throws Exception;
	
	public int getInventoryDetailListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getUserListXml(CommonMap cmap) throws Exception;
	
	public int getUserListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getDeptListXml(CommonMap cmap) throws Exception;
	
	public int getDeptListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getPosListXml(CommonMap cmap) throws Exception;
	
	public int getPosListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getOrgListXml(CommonMap cmap) throws Exception;
	
	public int getOrgListXmlCnt(CommonMap cmap) throws Exception;
	
	public List getImageListXml(CommonMap cmap) throws Exception;
	
	public int getImageListXmlCnt(CommonMap cmap) throws Exception;
	
	public void insertAssetImage(CommonMap cmap) throws Exception;
	
	public void deleteAssetImage(CommonMap cmap) throws Exception;
	
	public void updateInventoryDetail(CommonMap cmap) throws Exception;
	
	public List getNewAssetListXml(CommonMap cmap) throws Exception;
	
	public void insertNewAsset(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptList(CommonMap cmap) throws Exception;
}
