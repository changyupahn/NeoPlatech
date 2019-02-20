package boassoft.service.impl;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AppMapper;
import boassoft.service.AppService;
import boassoft.util.AppCommonList;
import boassoft.util.CommonMap;
import boassoft.util.CommonList;

@Service("appService")
public class AppServiceImpl extends EgovAbstractServiceImpl implements AppService{

	@Resource(name="AppMapper")
    private AppMapper appMapper;
	
	public CommonMap getRfidAdminView(CommonMap cmap) throws Exception {
		return appMapper.getRfidAdminView(cmap);		
	}
	
	public CommonMap getRfidDeviceView(CommonMap cmap) throws Exception {
		return appMapper.getRfidDeviceView(cmap);				
	}
	
	public CommonMap getRfidOrgView(CommonMap cmap) throws Exception {
		return appMapper.getRfidOrgView(cmap);
	}
	
	public int updateRfidDeviceToken(CommonMap cmap) throws Exception {
		return appMapper.updateRfidDeviceToken(cmap);
	}
	
	public CommonList getRfidsysAstcolMngList(CommonMap cmap) throws Exception {
		return appMapper.getRfidsysAstcolMngList(cmap);		
	}
	
	public CommonList getRfidsysInvencolMngList(CommonMap cmap) throws Exception {
		return appMapper.getRfidsysInvencolMngList(cmap);		
	}
	
	public int updateRfidsysAstcolMng(CommonMap cmap) throws Exception {
		return appMapper.updateRfidsysAstcolMng(cmap);
	}
	
	public CommonMap getInventoryLast(CommonMap cmap) throws Exception{
		return appMapper.getInventoryLast(cmap);
	}
	
	public CommonList getCmcodeList(CommonMap cmap) throws Exception {
		return appMapper.getCmcodeList(cmap);
	}
	
	public AppCommonList getInventoryDetailList(CommonMap cmap) throws Exception {
		return appMapper.getInventoryDetailList(cmap);
	}
	
	public CommonMap getAssetImg(CommonMap cmap) throws Exception {
		return appMapper.getAssetImg(cmap);
	}
	public CommonList getAssetImgList(CommonMap cmap) throws Exception {
		return appMapper.getAssetImgList(cmap);
	}
	
	public void insertAssetImage(CommonMap cmap) throws Exception {
		appMapper.insertAssetImage(cmap);
	}
	
	public int deleteAssetImage(CommonMap cmap) throws Exception {
		return appMapper.deleteAssetImage(cmap);
	}
	
	public void insertRfidInventoryCheck(CommonMap cmap) throws Exception {
		appMapper.insertRfidInventoryCheck(cmap);
	}
}
