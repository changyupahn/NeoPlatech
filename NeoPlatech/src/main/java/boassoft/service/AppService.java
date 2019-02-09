package boassoft.service;

import boassoft.util.AppCommonList;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

public interface AppService {

	public CommonMap getRfidAdminView(CommonMap cmap) throws Exception;
	
	public CommonMap getRfidDeviceView(CommonMap cmap) throws Exception;
	
	public CommonMap getRfidOrgView(CommonMap cmap) throws Exception;
	
	public int updateRfidDeviceToken(CommonMap cmap) throws Exception;
	
	public CommonList getRfidsysAstcolMngList(CommonMap cmap) throws Exception;
	
	public CommonList getRfidsysInvencolMngList(CommonMap cmap) throws Exception;
	
	public int updateRfidsysAstcolMng(CommonMap cmap) throws Exception;
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception;
	
	public CommonList getCmcodeList(CommonMap cmap) throws Exception;
	
	public AppCommonList getInventoryDetailList(CommonMap cmap) throws Exception;
	
	public CommonMap getAssetImg(CommonMap cmap) throws Exception;
	
	public CommonList getAssetImgList(CommonMap cmap) throws Exception;
	
	public void insertAssetImage(CommonMap cmap) throws Exception;
	
	public int deleteAssetImage(CommonMap cmap) throws Exception;
	
	public void insertRfidInventoryCheck(CommonMap cmap) throws Exception;
	
	
	
}
