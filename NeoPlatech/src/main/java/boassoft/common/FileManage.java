package boassoft.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import boassoft.common.ImageUtil;
import boassoft.util.DateUtil;
import boassoft.util.FileUtil;
import boassoft.util.Thumbnail;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.Globals;

@Component("FileManage")
public class FileManage {
	 public static final int BUFF_SIZE = 2048;

	    private static final Logger LOG = Logger.getLogger(FileManage.class.getName());

	    public static String EXTSN_TYPE_FILE = "file";
	    public static String EXTSN_TYPE_IMAGE = "image";
	    public static String EXTSN_TYPE_NONE = "none";

	    /**
	     * 첨부파일에 대한 목록 정보를 취득한다.
	     * @param files 첨부파일 목록
	     * @param extsnType 확장자 구분에 따라 업로드 할 수 있는 확장자가 정해짐(EXTSN_TYPE_FILE, EXTSN_TYPE_IMAGE, EXTSN_TYPE_DOCUMENT)
	     * @param keyStr 업로드될 파일명 앞에 붙여질 문자열 값
	     * @param storePath 저장 경로에 대한 프로퍼티명
	     * @param thumbWidth 이미지의 경우 썸네일 최대 가로사이즈
	     * @param thumbHeight 이미지의 경우 썸네일 최대 세로사이즈
	     * @return
	     * @throws Exception
	     */
	    public List<FileVO> parseFileInf(MultiValueMap<String, MultipartFile> files, String extsnType, String keyStr, String storePath, int thumbWidth, int thumbHeight, String orgNo, String assetNo, String fileDt, String inputKey2) throws Exception {
			int fileKey = 0;

			String storePathString = "";

			if ("".equals(storePath) || storePath == null) {
			    storePathString = EgovProperties.getProperty("Globals.fileStorePath");
			} else {
			    storePathString = EgovProperties.getProperty(storePath);
			}

			if (orgNo != null && !"".equals(orgNo)) {
				storePathString = storePathString.replaceAll("[{]orgNo[}]", orgNo);
			}

			storePathString = storePathString + DateUtil.getFormatDate("yyyy");

			if (!"none".equals(extsnType)) {
				File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));

				if (!saveFolder.exists() || saveFolder.isFile()) {
				    saveFolder.mkdirs();
				}
			}

			Iterator<Entry<String, List<MultipartFile>>> itr = files.entrySet().iterator();
			List<MultipartFile> fileList;
			MultipartFile file;
			String inputKey = "";
//			String tmpKey = "";
			String filePath = "";
			String filePathThumb = "";
			List<FileVO> result  = new ArrayList<FileVO>();
			FileVO fvo;

			while (itr.hasNext()) {
			    Entry<String, List<MultipartFile>> entry = itr.next();

			    fileList = entry.getValue();
			    inputKey = entry.getKey();

			    if ("".equals(inputKey2) || inputKey.equals(inputKey2)) {
				    for( int i=0; i<fileList.size(); i++ ){
				    	file = (MultipartFile) fileList.get(i);
					    String orginFileName = file.getOriginalFilename();

					    //--------------------------------------
					    // 원 파일명이 없는 경우 처리
					    // (첨부가 되지 않은 input file type)
					    //--------------------------------------
					    if ("".equals(orginFileName))
					    	continue;
					    ////------------------------------------

					    int index = orginFileName.lastIndexOf(".");
					    String fileExt = orginFileName.substring(index + 1);
					    //String strTimeStamp = getTimeStamp();

					    if ("".equals(keyStr)) {
					    	keyStr = getTimeStamp();
					    }

					    String newName = keyStr + fileKey + "." + fileExt;
					    String newThumbName = "tn" + keyStr + fileKey + "." + fileExt;
					    long _size = file.getSize();

					    if (!"".equals(assetNo) && !"".equals(fileDt)) {
					    	newName = String.format("%s_%s.%s", assetNo, fileDt, fileExt);
					    	newThumbName = String.format("tn%s_%s.%s", assetNo, fileDt, fileExt);
					    }

					    filePath = storePathString + File.separator + newName;

					    //업로드 확장자 체크
					    if ("file".equals(extsnType)) {
						    if( FileUtil.isAllowFile(fileExt)){
								file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
						    }
					    } else if ("excel".equals(extsnType)) {
						    if( FileUtil.isAllowExcel(fileExt)){
								file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
						    }
					    } else if ("xml".equals(extsnType)) {
						    if( FileUtil.isAllowXml(fileExt)){
								file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
						    }
					    } else if ("image".equals(extsnType) || "zeus".equals(extsnType)) {
					    	if( FileUtil.isAllowImage(fileExt)){
								file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));

								//이미지 파일인 경우 썸네일 생성
								filePathThumb = storePathString + File.separator + newThumbName;

								HashMap<String,Object> rotateMap = boassoft.common.ImageUtil.rotate(filePath);
								Thumbnail.createThumbnail(filePath, filePathThumb, 500, 500, rotateMap);
								if ("image".equals(extsnType)) {
									Thumbnail.createThumbnail2(filePath, filePath, 1200, 1200, rotateMap);
								}
						    }
					    } else if ("none".equals(extsnType)) {
					    	//TODO 파일 저장 없이 진행
					    } else {
					    	continue;
					    }

		//				if( !tmpKey.equals(inputKey)){
		//					fileKey = 0;
		//				}
		//				tmpKey = inputKey;

					    fvo = new FileVO();
					    fvo.setInputKey(inputKey);
					    fvo.setFileExtsn(fileExt);
					    fvo.setFileStreCours(storePathString);
					    fvo.setFileMg(Long.toString(_size));
					    fvo.setOrignlFileNm(orginFileName);
					    fvo.setStreFileNm(newName);
					    //fvo.setAtchFileId(atchFileIdString);
					    fvo.setFileSn(String.valueOf(fileKey));

					    result.add(fvo);

					    fileKey++;
				    }
			    }
			}

			return result;
	    }

	    /**
	     * 첨부파일을 서버에 저장한다.
	     *
	     * @param file
	     * @param newName
	     * @param stordFilePath
	     * @throws Exception
	     */
	    protected void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
			InputStream stream = null;
			OutputStream bos = null;

			try {
			    stream = file.getInputStream();
			    File cFile = new File(stordFilePath);

			    if (!cFile.isDirectory()) {
				boolean _flag = cFile.mkdir();
				if (!_flag) {
				    throw new IOException("Directory creation Failed ");
				}
			    }

			    bos = new FileOutputStream(stordFilePath + File.separator + newName);

			    int bytesRead = 0;
			    byte[] buffer = new byte[BUFF_SIZE];

			    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			    }
			} catch (Exception e) {
			    //e.printStackTrace();
			    LOG.error("IGNORE:", e);	// 2011.10.10 보안점검 후속조치
			} finally {
			    if (bos != null) {
				try {
				    bos.close();
				} catch (Exception ignore) {
				    LOG.debug("IGNORED: " + ignore.getMessage());
				}
			    }
			    if (stream != null) {
				try {
				    stream.close();
				} catch (Exception ignore) {
				    LOG.debug("IGNORED: " + ignore.getMessage());
				}
			    }
			}
	    }

	    /**
	     * 서버의 파일을 다운로드한다.
	     *
	     * @param request
	     * @param response
	     * @throws Exception
	     */
	    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

			String downFileName = "";
			String orgFileName = "";

			if ((String)request.getAttribute("downFile") == null) {
			    downFileName = "";
			} else {
			    downFileName = (String)request.getAttribute("downFile");
			}

			if ((String)request.getAttribute("orgFileName") == null) {
			    orgFileName = "";
			} else {
			    orgFileName = (String)request.getAttribute("orginFile");
			}

			orgFileName = orgFileName.replaceAll("\r", "").replaceAll("\n", "");

			File file = new File(EgovWebUtil.filePathBlackList(downFileName));

			if (!file.exists()) {
			    throw new FileNotFoundException(downFileName);
			}

			if (!file.isFile()) {
			    throw new FileNotFoundException(downFileName);
			}

			byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(), "UTF-8"));
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");

			BufferedInputStream fin = null;
			BufferedOutputStream outs = null;

			try {
				fin = new BufferedInputStream(new FileInputStream(file));
			    outs = new BufferedOutputStream(response.getOutputStream());
			    int read = 0;

				while ((read = fin.read(b)) != -1) {
				    outs.write(b, 0, read);
				}
			} finally {
			    if (outs != null) {
					try {
					    outs.close();
					} catch (Exception ignore) {
					    //System.out.println("IGNORED: " + ignore.getMessage());
					    LOG.debug("IGNORED: " + ignore.getMessage());
					}
				}
			    if (fin != null) {
					try {
					    fin.close();
					} catch (Exception ignore) {
					    //System.out.println("IGNORED: " + ignore.getMessage());
					    LOG.debug("IGNORED: " + ignore.getMessage());
					}
			    }
			}
	    }

	    /**
	     * 첨부로 등록된 파일을 서버에 업로드한다.
	     *
	     * @param file
	     * @return
	     * @throws Exception
	     */
	    public static HashMap<String, String> uploadFile(MultipartFile file) throws Exception {

			HashMap<String, String> map = new HashMap<String, String>();
			//Write File 이후 Move File????
			String newName = "";
			String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
			String orginFileName = file.getOriginalFilename();

			int index = orginFileName.lastIndexOf(".");
			//String fileName = orginFileName.substring(0, _index);
			String fileExt = orginFileName.substring(index + 1);
			long size = file.getSize();

			//newName 은 Naming Convention에 의해서 생성
			newName = getTimeStamp() + "." + fileExt;
			writeFile(file, newName, stordFilePath);
			//storedFilePath는 지정
			map.put(Globals.ORIGIN_FILE_NM, orginFileName);
			map.put(Globals.UPLOAD_FILE_NM, newName);
			map.put(Globals.FILE_EXT, fileExt);
			map.put(Globals.FILE_PATH, stordFilePath);
			map.put(Globals.FILE_SIZE, String.valueOf(size));

			return map;
	    }

	    /**
	     * 파일을 실제 물리적인 경로에 생성한다.
	     *
	     * @param file
	     * @param newName
	     * @param stordFilePath
	     * @throws Exception
	     */
	    protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
			InputStream stream = null;
			OutputStream bos = null;

			try {
			    stream = file.getInputStream();
			    File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));

			    if (!cFile.isDirectory())
				cFile.mkdir();

			    bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + newName));

			    int bytesRead = 0;
			    byte[] buffer = new byte[BUFF_SIZE];

			    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
				bos.write(buffer, 0, bytesRead);
			    }
			} catch (Exception e) {
			    //e.printStackTrace();
			    //throw new RuntimeException(e);	// 보안점검 후속조치
				Logger.getLogger(FileManage.class).debug("IGNORED: " + e.getMessage());
			} finally {
			    if (bos != null) {
				try {
				    bos.close();
				} catch (Exception ignore) {
				    Logger.getLogger(FileManage.class).debug("IGNORED: " + ignore.getMessage());
				}
			    }
			    if (stream != null) {
				try {
				    stream.close();
				} catch (Exception ignore) {
				    Logger.getLogger(FileManage.class).debug("IGNORED: " + ignore.getMessage());
				}
			    }
			}
	    }

	    /**
	     * 서버 파일에 대하여 다운로드를 처리한다.
	     *
	     * @param response
	     * @param streFileNm
	     *            : 파일저장 경로가 포함된 형태
	     * @param orignFileNm
	     * @throws Exception
	     */
	    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws Exception {
			String downFileName = streFileNm;
			String orgFileName = orignFileNm;

			File file = new File(downFileName);
			//log.debug(this.getClass().getName()+" downFile downFileName "+downFileName);
			//log.debug(this.getClass().getName()+" downFile orgFileName "+orgFileName);

			if (!file.exists()) {
			    throw new FileNotFoundException(downFileName);
			}

			if (!file.isFile()) {
			    throw new FileNotFoundException(downFileName);
			}

			//byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
			int fSize = (int)file.length();
			if (fSize > 0) {
			    BufferedInputStream in = null;

			    try {
				in = new BufferedInputStream(new FileInputStream(file));

		    	    	String mimetype = "text/html"; //"application/x-msdownload"

		    	    	response.setBufferSize(fSize);
				response.setContentType(mimetype);
				response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);
				response.setContentLength(fSize);
				//response.setHeader("Content-Transfer-Encoding","binary");
				//response.setHeader("Pragma","no-cache");
				//response.setHeader("Expires","0");
				FileCopyUtils.copy(in, response.getOutputStream());
			    } finally {
				if (in != null) {
				    try {
					in.close();
				    } catch (Exception ignore) {

					Logger.getLogger(FileManage.class).debug("IGNORED: " + ignore.getMessage());
				    }
				}
			    }
			    response.getOutputStream().flush();
			    response.getOutputStream().close();
			}
	    }

	    /**
	     * 2011.08.09
	     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
	     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	     *
	     * @param
	     * @return Timestamp 값
	     * @exception MyException
	     * @see
	     */
	    private static String getTimeStamp() {

			String rtnStr = null;

			// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
			String pattern = "yyyyMMddhhmmssSSS";

			try {
			    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
			    Timestamp ts = new Timestamp(System.currentTimeMillis());

			    rtnStr = sdfCurrent.format(ts.getTime());
			} catch (Exception e) {
			    //e.printStackTrace();

			    //throw new RuntimeException(e);	// 보안점검 후속조치
			    LOG.debug("IGNORED: " + e.getMessage());
			}

			return rtnStr;
	    }
}
