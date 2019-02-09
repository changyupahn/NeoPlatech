package boassoft.service;

import boassoft.common.CommonXmlList;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface TabService {

	public CommonMap getRfidLoginView(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception;
	
	public CommonXmlList getAssetListXml(CommonMap cmap) throws Exception;
	
	public int getAssetListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getInventoryListXml(CommonMap cmap) throws Exception;
	
	public int getInventoryListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getInventoryDetailListXml(CommonMap cmap) throws Exception;
	
	public int getInventoryDetailListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getUserListXml(CommonMap cmap) throws Exception;
	
	public int getUserListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getDeptListXml(CommonMap cmap) throws Exception;
	
	public int getDeptListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getPosListXml(CommonMap cmap) throws Exception;
	
	public int getPosListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getOrgListXml(CommonMap cmap) throws Exception;
	
	public int getOrgListXmlCnt(CommonMap cmap) throws Exception;
	
	public CommonXmlList getImageListXml(CommonMap cmap) throws Exception;
	
	public int getImageListXmlCnt(CommonMap cmap) throws Exception;
	
	public void insertAssetImage(CommonMap cmap) throws Exception;
	
	public void deleteAssetImage(CommonMap cmap) throws Exception;
	
	public void updateInventoryDetail(CommonMap cmap) throws Exception;
	
	public CommonXmlList getNewAssetListXml(CommonMap cmap) throws Exception;
	
	public void insertNewAsset(CommonMap cmap) throws Exception;
	
	public CommonList getUserDeptList(CommonMap cmap) throws Exception;
}
