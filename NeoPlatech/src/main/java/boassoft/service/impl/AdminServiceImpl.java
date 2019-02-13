package boassoft.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import boassoft.mapper.AdminMapper;
import boassoft.service.AdminService;
import boassoft.util.CommonList;
import boassoft.util.CommonMap;

@Service("AdminService")
public class AdminServiceImpl extends EgovAbstractServiceImpl implements AdminService{

	@Resource(name="AdminMapper")
	private AdminMapper adminMapper;
	
	public CommonMap getValidationQuery(CommonMap cmap) throws Exception{
		return adminMapper.getValidationQuery(cmap);
	}
	
	public CommonMap getAdminLoginView(CommonMap cmap) throws Exception{
		return adminMapper.getAdminLoginView(cmap);
	}
	
	public CommonMap getAdminView(CommonMap cmap) throws Exception{
		return adminMapper.getAdminView(cmap);
	}
	
	public CommonList getAdminList(CommonMap cmap) throws Exception {
		CommonList list = adminMapper.getAdminList(cmap);
		list.totalRow = adminMapper.getAdminListCnt(cmap);
		return list;
	}
	
	public CommonList getAdminPrintList(CommonMap cmap) throws Exception{
		return adminMapper.getAdminPrintList(cmap);
	}
	
	public CommonMap getAdminPrintN(CommonMap cmap) throws Exception{
		return adminMapper.getAdminPrintN(cmap);
	}
	
	public CommonMap getAdminPrintS(CommonMap cmap) throws Exception{
		return adminMapper.getAdminPrintS(cmap);
	}
	
	public void insertAdmin(CommonMap cmap) throws Exception{
		adminMapper.insertUser(cmap);
	}
	
	public void updateAdmin(CommonMap cmap) throws Exception{
		adminMapper.updateUser(cmap);
	}
	
	public void deleteAdmin(CommonMap cmap) throws Exception{
		adminMapper.deleteUser(cmap);
	}
}
