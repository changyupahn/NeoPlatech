package boassoft.mapper;

import java.util.List;

import egovframework.com.cop.com.service.UserInfVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("EgovUserInfManageMapper")
public interface EgovUserInfManageMapper {

	public List<UserInfVO> selectUserList(UserInfVO userVO) throws Exception;
	
	public int selectUserListCnt(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectCmmntyUserList(UserInfVO userVO) throws Exception;
	
	public int selectCmmntyUserListCnt(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectCmmntyMngrList(UserInfVO userVO) throws Exception;
	
	public int selectCmmntyMngrListCnt(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectClubUserList(UserInfVO userVO) throws Exception;
	
	public int selectClubUserListCnt(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectClubOprtrList(UserInfVO userVO) throws Exception;
	
	public int selectClubOprtrListCnt(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectAllClubUser(UserInfVO userVO) throws Exception;
	
	public List<UserInfVO> selectAllCmmntyUser(UserInfVO userVO) throws Exception;
	
			
}
