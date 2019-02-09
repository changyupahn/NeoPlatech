/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.rte.fdl.excel.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

//import com.ibatis.sqlmap.client.SqlMapClient;

import egovframework.com.utl.sim.service.EgovFileTool;
import egovframework.rte.fdl.cmmn.exception.BaseException;
import egovframework.rte.fdl.excel.EgovExcel2007Service;
import egovframework.rte.fdl.excel.EgovExcel2007Mapping;
import egovframework.rte.fdl.string.EgovObjectUtil;

/**
 * 엑셀 서비스를 처리하는 비즈니스 구현 클래스
 * <p>
 * <b>NOTE:</b> 엑셀 서비스를 제공하기 위해 구현한 클래스이다.
 * @author 실행환경 개발팀 윤성종
 * @since 2009.06.01
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.06.01  윤성종           최초 생성
 * 
 * </pre>
 */

public class EgovExcel2007ServiceImpl implements EgovExcel2007Service {

    // @Resource(name = "egovExcelServiceDAO")
    private EgovExcelServiceDAO dao;

    protected Log log = LogFactory.getLog(this.getClass());

    private MessageSource messageSource;
    private String propertyPath;
    private String mapClass;
   // private SqlMapClient sqlMapClient;

    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.messageSource =
            (MessageSource) applicationContext.getBean("messageSource");
    }

    /**
     * @return the messageSource
     */
    protected MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * @param path
     * @throws Exception
     */
    public void setPropertyPath(String path) throws BaseException {
        this.propertyPath = path;
        log.debug("setPropertyPath : " + path);

        // FileUtil의 cd 기능으로 propertyPath 위치로 현재 디렉토리
        // 위치 이동
        // this.baseDir = "d:/workspace/tmp";
    }

   /* public void setSqlMapClient(SqlMapClient sqlMapClient) throws Exception {
        this.sqlMapClient = sqlMapClient;
        dao = new EgovExcelServiceDAO(this.sqlMapClient);
        // dao = EgovExcelServiceDAO.getInstance();
    }*/

    /**
     * Excel Cell과 VO를 mapping 구현 클래스
     * @param mapClass
     * @throws Exception
     */
    public void setMapClass(String mapClass) throws BaseException {
        this.mapClass = mapClass;
        log.debug("mapClass : " + mapClass);

    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #loadExcelTemplate(java.lang.String)
     */
    public XSSFWorkbook loadExcelTemplate(String templateName)
            throws BaseException, FileNotFoundException, IOException {

        // FileInputStream fileIn = new
        // FileInputStream(FilenameUtils.concat(this.baseDir,
        // templateName));
        FileInputStream fileIn = new FileInputStream(templateName);
        XSSFWorkbook wb = null;

        log.debug("EgovExcelServiceImpl.loadExcelTemplate : templatePath is "
            + templateName);

        try {
            log.debug("ExcelServiceImpl loadExcelTemplate ...");

//            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            wb = new XSSFWorkbook(fileIn);

        } catch (Exception e) {
            log.error(getMessageSource().getMessage(
                "error.excel.runtime.error",
                new Object[] {"EgovExcelServiceImpl loadExcelTemplate" },
                Locale.getDefault()), e);
            /*
             * if (e instanceof Exception) {
             * log.error(e.toString()); throw
             * (Exception) e; } else {
             * log.error(e.toString()); throw new
             * Exception(e); }
             */
        } finally {
            log.debug("ExcelServiceImpl loadExcelTemplate end...");
            fileIn.close();
        }
        return wb;

    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #loadWorkbook(java.lang.String)
     */
    public XSSFWorkbook loadWorkbook(String filepath) throws BaseException,
            FileNotFoundException, IOException {

        FileInputStream fileIn = new FileInputStream(filepath);
        XSSFWorkbook wb = loadWorkbook(fileIn);
        fileIn.close();

        return wb;
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #loadWorkbook(java.io.InputStream)
     */
    public XSSFWorkbook loadWorkbook(InputStream fileIn) throws BaseException {
        XSSFWorkbook wb = new XSSFWorkbook();

        try {
            log.debug("ExcelServiceImpl loadWorkbook ...");

//            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            wb = new XSSFWorkbook(fileIn);

        } catch (Exception e) {
            log.error(getMessageSource().getMessage(
                "error.excel.runtime.error", new Object[] {"Roles and Url" },
                Locale.getDefault()), e);
            /*
             * if (e instanceof Exception) {
             * log.error(e.toString()); throw
             * (Exception) e; } else {
             * log.error(e.toString()); throw new
             * Exception(e); }
             */
        }

        return wb;
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #writeWorkbook
     * (org.apache.poi.hssf.usermodel.XSSFWorkbook)
     */
    public void writeWorkbook(XSSFWorkbook workbook) throws BaseException,
            IOException {

        workbook.write(null);

        // FileOutputStream out = new
        // FileOutputStream(sb.toString());
        // workbook.write(out);
        // out.close();
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #createWorkbook
     * (org.apache.poi.hssf.usermodel.XSSFWorkbook,
     * java.lang.String)
     */
    public XSSFWorkbook createWorkbook(XSSFWorkbook wb, String filepath)
            throws BaseException, FileNotFoundException, IOException, Exception {
        // String fullFileName =
        // FilenameUtils.concat(this.baseDir,
        // filepath);
        String fullFileName = filepath;

        log.debug("EgovExcelServiceImpl.createWorkbook : templatePath is "
            + FilenameUtils.getFullPath(fullFileName));

        // 작업 디렉토리 생성
        if (!EgovFileTool.getExistDirectory(FilenameUtils.getFullPath(fullFileName))) {
            log.debug("make dir " + FilenameUtils.getFullPath(fullFileName));

            try {
                FileUtils.forceMkdir(new File(FilenameUtils
                    .getFullPath(fullFileName)));
            } catch (IOException e) {
                throw new IOException("Cannot create directory for path: "
                    + FilenameUtils.getFullPath(fullFileName));
            }
        }

        FileOutputStream fileOut = new FileOutputStream(fullFileName);

        log.debug("EgovExcelServiceImpl.createWorkbook : templatePath is "
            + fullFileName);

        try {
            log.debug("ExcelServiceImpl loadExcelObject ...");
            // this.workbook = new XSSFWorkbook();

            wb.write(fileOut);

        } catch (Exception e) {
            log.error(getMessageSource().getMessage(
                "error.excel.runtime.error", new Object[] {"Roles and Url" },
                Locale.getDefault()), e);
            /*
             * if (e instanceof Exception) { throw
             * (Exception) e; } else { throw new
             * Exception(e); }
             */
        } finally {
            log.debug("ExcelServiceImpl loadExcelObject end...");
            fileOut.close();
        }

        return wb;
    }

    /**
     * 엑셀Sheet을 읽어서 DB upload 한다.
     * @param String
     *        queryId
     * @param XSSFSheet
     *        sheet
     * @param int start
     * @param long commitCnt
     * @return
     * @throws Exception
     */
    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * org.apache.poi.hssf.usermodel.XSSFSheet, int,
     * long)
     */
    public Integer uploadExcel(String queryId, XSSFSheet sheet, int start,
            long commitCnt) throws BaseException, Exception {

        log.debug("sheet.getPhysicalNumberOfRows() : "
            + sheet.getPhysicalNumberOfRows());

        Integer rowsAffected = 0;

        try {

            long rowCnt = sheet.getPhysicalNumberOfRows();
            long cnt = (commitCnt == 0) ? rowCnt : commitCnt;

            log.debug("Runtime.getRuntime().totalMemory() : "
                + Runtime.getRuntime().totalMemory());
            log.debug("Runtime.getRuntime().freeMemory() : "
                + Runtime.getRuntime().freeMemory());

            long startTime = System.currentTimeMillis();

            for (int idx = start, i = start; idx < rowCnt; idx = i) {
                List<Object> list = new ArrayList<Object>();

                log.debug("before Runtime.getRuntime().freeMemory() : "
                    + Runtime.getRuntime().freeMemory());
                EgovExcel2007Mapping mapping =
                    (EgovExcel2007Mapping) EgovObjectUtil.instantiate(mapClass);

                for (i = idx; i < rowCnt && i < (cnt + idx); i++) {
                    XSSFRow row = sheet.getRow(i);
                    list.add(mapping.mappingColumn(row));
                }

                // insert
                // 현재 spring 연계 ibatis 의 batch 형식으로 작성
                // 후 중간에 exception 발생시켜도 rollback 이 불가한
                // 문제가 있음.
                // ibatis 의 batch 관련하여서는
                // sqlMapClient.startTransaction() 이하의
                // 코드 등 추가 작업이 필요한지 확인 필요!

                rowsAffected += dao.batchInsert(queryId, list);

                log.debug("after Runtime.getRuntime().freeMemory() : "
                    + Runtime.getRuntime().freeMemory());

                log.debug("\n\n\n" + rowsAffected);
            }

            log.debug("batchInsert time is "
                + (System.currentTimeMillis() - startTime));

        } catch (Exception e) {
            throw new Exception(e);
        }

        log.debug("uploadExcel result count is " + rowsAffected);

        return rowsAffected;
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * org.apache.poi.hssf.usermodel.XSSFSheet, int)
     */
    public Integer uploadExcel(String queryId, XSSFSheet sheet, int start)
            throws BaseException, Exception {
        return uploadExcel(queryId, sheet, start, 0);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * org.apache.poi.hssf.usermodel.XSSFSheet, long)
     */
    public Integer uploadExcel(String queryId, XSSFSheet sheet, long commitCnt)
            throws BaseException, Exception {
        return uploadExcel(queryId, sheet, 0, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * org.apache.poi.hssf.usermodel.XSSFSheet)
     */
    public Integer uploadExcel(String queryId, XSSFSheet sheet)
            throws BaseException, Exception {
        return uploadExcel(queryId, sheet, 0, 0);
    }

    /**
     * 엑셀파일을 읽어서 DB upload 한다.
     * @param String
     *        queryId
     * @param InputStream
     *        fileIn
     * @param int start
     * @param long commitCnt
     * @return
     * @throws Exception
     */
    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, int)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn, int start,
            long commitCnt) throws BaseException, Exception {
        XSSFWorkbook wb = loadWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(0);

        return uploadExcel(queryId, sheet, start, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, int)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn, int start)
            throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, start, 0);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            long commitCnt) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, 0, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn)
            throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, 0, 0);
    }

    /**
     * 엑셀파일을 읽어서 DB upload 한다. sheet의 인덱스값으로 upload할
     * sheet를 지정한다.
     * @param String
     *        queryId
     * @param InputStream
     *        fileIn
     * @param short sheetIndex
     * @param int start
     * @param long commitCnt
     * @return
     * @throws Exception
     */
    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, short, int)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            short sheetIndex, int start, long commitCnt) throws BaseException,
            Exception {
        XSSFWorkbook wb = loadWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);

        return uploadExcel(queryId, sheet, start, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, short, int)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            short sheetIndex, int start) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetIndex, start, 0);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, short, long)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            short sheetIndex, long commitCnt) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetIndex, 0, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, short)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            short sheetIndex) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetIndex, 0, 0);
    }

    /**
     * 엑셀파일을 읽어서 DB upload 한다. sheet의 명으로 upload할
     * sheet를 지정한다.
     * @param String
     *        queryId
     * @param InputStream
     *        fileIn
     * @param String
     *        sheetName
     * @param int start
     * @param long commitCnt
     * @return
     * @throws Exception
     */
    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, java.lang.String, int,
     * long)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            String sheetName, int start, long commitCnt) throws BaseException,
            Exception {
        XSSFWorkbook wb = loadWorkbook(fileIn);
        XSSFSheet sheet = wb.getSheet(sheetName);

        return uploadExcel(queryId, sheet, start, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, java.lang.String, int)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            String sheetName, int start) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetName, start, 0);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, java.lang.String, long)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            String sheetName, long commitCnt) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetName, 0, commitCnt);
    }

    /*
     * (non-Javadoc)
     * @see
     * egovframework.rte.fdl.excel.EgovExcelService
     * #uploadExcel(java.lang.String,
     * java.io.InputStream, java.lang.String)
     */
    public Integer uploadExcel(String queryId, InputStream fileIn,
            String sheetName) throws BaseException, Exception {
        return uploadExcel(queryId, fileIn, sheetName, 0, 0);
    }
    
    public boolean checkWorkbook(String filepath) throws BaseException, FileNotFoundException, IOException {
		boolean result = false;
		
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			loadWorkbook(fileIn);
			fileIn.close();
			result = true;
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}

}
