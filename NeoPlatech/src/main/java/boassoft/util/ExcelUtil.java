package boassoft.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelUtil {

	private static final Logger log = Logger.getLogger("woman");

	/*
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		CommonMap commonMap = (CommonMap) model.get("commonList");
		CommonList headerList = (CommonList) commonMap.get("HeaderItem");
		CommonList rowList = (CommonList) commonMap.get("RowItem");

		HSSFSheet sheet = createSheet(workbook, commonMap.getString("PLATE"), headerList);

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		int rowNum = 1;
		for (int i=0; i<rowList.size(); i++) {
			CommonMap rowMap = (CommonMap) rowList.get(i);
			rowNum = addRantRow(sheet, cellStyle, rowNum, rowMap);
		}
	}*/

	public static HSSFSheet createSheet(HSSFWorkbook workbook, String plateNumber, String[] headerList) {
		HSSFSheet sheet = workbook.createSheet(plateNumber);

		HSSFRow header = sheet.createRow(0);
		HSSFCell cell = null;

		for (int i=0; i<headerList.length; i++) {
			cell = header.createCell(i);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(headerList[i]);
		}

		return sheet;
	}

	public static HSSFSheet createSheet2(HSSFWorkbook workbook, String plateNumber, String[] headerList1, String[] headerList2, HSSFCellStyle cellStyle1, short height) {
		HSSFSheet sheet = workbook.createSheet(plateNumber);

		HSSFRow header = sheet.createRow(0);
		header.setHeight(height);
		HSSFCell cell = null;

		for (int i=0; i<headerList1.length; i++) {
			cell = header.createCell(i);
			cell.setCellValue(headerList1[i]);
			cell.setCellStyle(cellStyle1);
		}

		if (headerList2 != null) {
			header = sheet.createRow(1);
			header.setHeight(height);

			for (int i=0; i<headerList2.length; i++) {
				cell = header.createCell(i);
				cell.setCellValue(headerList2[i]);
				cell.setCellStyle(cellStyle1);
			}
		}

		return sheet;
	}

	public static int addRow(HSSFSheet sheet, HSSFCellStyle cellStyle3, int rowNum, CommonMap rowMap, String[] headerList, String[] headerListTyp) {
		HSSFRow row = sheet.createRow(rowNum+1);
		HSSFCell cell = null;

		for (int i=0; i<headerList.length; i++) {
			cell = row.createCell(i);

			if ("NUMBER".equals(headerListTyp[i])) {
				cell.setCellValue(rowMap.getDouble(headerList[i]));
				cell.setCellStyle(cellStyle3);
			} else {
				cell.setCellValue(rowMap.getString(headerList[i], false));
			}
		}

		return rowNum;
	}

	public static int addRow2(HSSFSheet sheet, HSSFCellStyle cellStyleDefault, HSSFCellStyle cellStyleText, HSSFCellStyle cellStyleNumber, HSSFCellStyle cellStyleDate, int rowNum, CommonMap rowMap, String[] headerList, String[] headerListTyp, short height) {
		HSSFRow row = sheet.createRow(rowNum+1);
		row.setHeight(height);
		HSSFCell cell = null;

		for (int i=0; i<headerList.length; i++) {
			cell = row.createCell(i);

			if ("DEFAULT".equals(headerListTyp[i])) {
				cell.setCellValue(rowMap.getString(headerList[i], false));
				cell.setCellStyle(cellStyleDefault);
			} else if ("TEXT".equals(headerListTyp[i])) {
				cell.setCellValue(rowMap.getString(headerList[i], false));
				cell.setCellStyle(cellStyleText);
			} else if ("NUMBER".equals(headerListTyp[i])) {
				cell.setCellValue(rowMap.getDouble(headerList[i]));
				cell.setCellStyle(cellStyleNumber);
			} else if ("DATE".equals(headerListTyp[i])) {
				cell.setCellValue(DateUtil.formatDateTime(rowMap.getString(headerList[i], false), "-", ":", 14));
				cell.setCellStyle(cellStyleDate);
			} else {
				cell.setCellValue(rowMap.getString(headerList[i], false));
				cell.setCellStyle(cellStyleDefault);
			}
		}

		return rowNum;
	}

	public static String getCell(HSSFRow hsr, int cellNum) {
		String result = "";

		if (hsr.getCell(cellNum) == null)
			return "";

		if (hsr.getCell(cellNum).getCellType() == 0) {

			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(hsr.getCell(cellNum))) {
				//날짜형
				Date date = hsr.getCell(cellNum).getDateCellValue();
				result = new SimpleDateFormat("yyyy-MM-dd").format(date);
			} else {
				//숫자형
				hsr.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
				result = hsr.getCell(cellNum).toString();
			}
		} else {
			hsr.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
			result = hsr.getCell(cellNum).toString();
		}

		return result;
	}

	public static String getCell(XSSFRow xsr, int cellNum) {
		String result = "";

		if (xsr.getCell(cellNum) == null)
			return "";

		if (xsr.getCell(cellNum).getCellType() == 0) {

			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(xsr.getCell(cellNum))) {
				//날짜형
				Date date = xsr.getCell(cellNum).getDateCellValue();
				result = new SimpleDateFormat("yyyy-MM-dd").format(date);
			} else {
				//숫자형
				xsr.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
				result = xsr.getCell(cellNum).toString();
			}
		} else {
			xsr.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
			result = xsr.getCell(cellNum).toString();
		}

		return result;
	}

	public static void write(HttpServletRequest request, HttpServletResponse response, CommonList dataList, String excelFileName, String[] headerListLgc, String[] headerListPhc, String[] headerListTyp) throws Exception {
		excelFileName = excelFileName + "_"+ DateUtil.getFormatDate() +".xls";
    	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);

    	HSSFWorkbook hwb = new HSSFWorkbook();
    	HSSFSheet sheet = ExcelUtil.createSheet(hwb, "0", headerListLgc);

    	HSSFCellStyle cellStyle3 = hwb.createCellStyle();
    	HSSFDataFormat dataFormat = hwb.createDataFormat();
		cellStyle3.setDataFormat(dataFormat.getFormat("#,##0"));

    	for (int k=0; k<dataList.size(); k++) {
			CommonMap data = dataList.getMap(k);
			ExcelUtil.addRow(sheet, cellStyle3, k, data, headerListPhc, headerListTyp);
		}

    	hwb.write(response.getOutputStream());
	}

	public static void write2(HttpServletRequest request, HttpServletResponse response, CommonList dataList, String excelFileName, String[] headerListLgc1, String[] headerListLgc2, String[] headerListPhc, String[] headerListTyp, String[][] mergedRegion, String[] headerListWidth, int height) throws Exception {
		excelFileName = excelFileName + "_"+ DateUtil.getFormatDate() +".xls";
    	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);

        HSSFWorkbook hwb = new HSSFWorkbook();

        HSSFCellStyle cellStyleHeader = hwb.createCellStyle();
        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle cellStyleDefault = hwb.createCellStyle();
        cellStyleDefault.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle cellStyleText = hwb.createCellStyle();
        cellStyleText.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

    	HSSFCellStyle cellStyleNumber = hwb.createCellStyle();
    	HSSFDataFormat dataFormat = hwb.createDataFormat();
    	cellStyleNumber.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setDataFormat(dataFormat.getFormat("#,##0"));
    	cellStyleNumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

    	HSSFCellStyle cellStyleDate = hwb.createCellStyle();
    	cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	cellStyleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		CommonMap cmap = new CommonMap();
		cmap.put("height", Math.round(height * 1000 / 50));
		short h = (short)cmap.getInt("height");

    	HSSFSheet sheet = ExcelUtil.createSheet2(hwb, "0", headerListLgc1, headerListLgc2, cellStyleHeader, h);

    	for (int k=0; k<dataList.size(); k++) {
			CommonMap data = dataList.getMap(k);
			int rownum = headerListLgc2 == null ? k : (k+1);
			ExcelUtil.addRow2(sheet, cellStyleDefault, cellStyleText, cellStyleNumber, cellStyleDate, rownum, data, headerListPhc, headerListTyp, h);
		}

    	if (mergedRegion != null) {
        	for (int k=0; k<mergedRegion.length; k++) {
        		String[] data = mergedRegion[k];
        		sheet.addMergedRegion(new Region((int)Integer.parseInt(data[0])
        				, (short)Integer.parseInt(data[1])
        				, (int)Integer.parseInt(data[2])
        				, (short)Integer.parseInt(data[3])));
        	}
    	}

    	for (int i=0;i<headerListWidth.length;i++) {
    		CommonMap temp = new CommonMap();
    		temp.put("wid", Math.round(Integer.parseInt(headerListWidth[i]) * 1000 / 3.14));
    		int wid = temp.getInt("wid");

	    	sheet.setColumnWidth(i, wid);
    	}

    	hwb.write(response.getOutputStream());
	}

	/** ZEUS현황 엑셀다운로드 전용 */
	public static void write3(HttpServletRequest request, HttpServletResponse response, CommonList dataList, String excelFileName, String[] headerListLgc1, String[] headerListLgc2, String[] headerListPhc, String[] headerListTyp, String[][] mergedRegion, String[] headerListWidth, int height) throws Exception {
		excelFileName = excelFileName + "_"+ DateUtil.getFormatDate() +".xls";
    	excelFileName = new String(excelFileName.getBytes("euc-kr"),"8859_1");
    	response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFileName);

        HSSFWorkbook hwb = new HSSFWorkbook();

        HSSFCellStyle cellStyleHeader = hwb.createCellStyle();
        cellStyleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle cellStyleDefault = hwb.createCellStyle();
        cellStyleDefault.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleDefault.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleDefault.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle cellStyleText = hwb.createCellStyle();
        cellStyleText.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyleText.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyleText.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

    	HSSFCellStyle cellStyleNumber = hwb.createCellStyle();
    	HSSFDataFormat dataFormat = hwb.createDataFormat();
    	cellStyleNumber.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	cellStyleNumber.setDataFormat(dataFormat.getFormat("#,##0"));
    	cellStyleNumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

    	HSSFCellStyle cellStyleDate = hwb.createCellStyle();
    	cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    	cellStyleDate.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	cellStyleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		CommonMap cmap = new CommonMap();
		cmap.put("height", Math.round(height * 1000 / 50));
		short h = (short)cmap.getInt("height");

    	HSSFSheet sheet = ExcelUtil.createSheet2(hwb, "0", headerListLgc1, headerListLgc2, cellStyleHeader, h);

    	for (int k=0; k<dataList.size(); k++) {
			CommonMap data = dataList.getMap(k);
			int rownum = headerListLgc2 == null ? k : (k+1);
			ExcelUtil.addRow2(sheet, cellStyleDefault, cellStyleText, cellStyleNumber, cellStyleDate, rownum, data, headerListPhc, headerListTyp, h);
		}

    	if (mergedRegion != null) {
        	for (int k=0; k<mergedRegion.length; k++) {
        		String[] data = mergedRegion[k];
        		sheet.addMergedRegion(new Region((int)Integer.parseInt(data[0])
        				, (short)Integer.parseInt(data[1])
        				, (int)Integer.parseInt(data[2])
        				, (short)Integer.parseInt(data[3])));
        	}
    	}

    	// 두행씩 셀합치기
    	for (int k=0; k<dataList.size(); k++) {

    		int rownumtmp = 0;
    		int rownum = dataList.getMap(k).getInt("rnum", 99999);

    		if (k % 2 == 0) {
	    		for (int j=0; j<4; j++) {
		    		sheet.addMergedRegion(new Region(
		    				k+2
		    				, (short)Integer.parseInt("" + j)
		    				, k+3
		    				, (short)Integer.parseInt("" + j)
		    				));
	    		}
    		}
    	}

    	for (int i=0;i<headerListWidth.length;i++) {
    		CommonMap temp = new CommonMap();
    		temp.put("wid", Math.round(Integer.parseInt(headerListWidth[i]) * 1000 / 3.14));
    		int wid = temp.getInt("wid");

	    	sheet.setColumnWidth(i, wid);
    	}

    	hwb.write(response.getOutputStream());
	}
}
