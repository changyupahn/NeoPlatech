package boassoft.mapper;

import java.util.List;

import egovframework.com.cmm.service.FileVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("FileManageMapper")
public interface FileManageMapper {

	public String insertFileInfs(List<?> fileList) throws Exception;
	
	public void insertFileInf(FileVO vo) throws Exception;
	
	public void updateFileInfs(List<?> fileList) throws Exception;
	
	public void deleteFileInfs(List<?> fileList) throws Exception;
	
	public void deleteFileInf(FileVO fvo) throws Exception;
	
	public List<FileVO> selectFileInfs(FileVO vo) throws Exception;
	
	public int getMaxFileSN(FileVO fvo) throws Exception;
	
	public FileVO selectFileInf(FileVO fvo) throws Exception;
	
	public void deleteAllFileInf(FileVO fvo) throws Exception;
	
	public List<FileVO> selectFileListByFileNm(FileVO fvo) throws Exception;
	
	public int selectFileListCntByFileNm(FileVO fvo) throws Exception;
	
	public List<FileVO> selectImageFileList(FileVO vo) throws Exception;
}
