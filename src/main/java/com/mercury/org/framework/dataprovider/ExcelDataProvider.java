package com.mercury.org.framework.dataprovider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import com.mercury.org.framework.constants.FrameWorkConstants;

public class ExcelDataProvider {

	public static final String DATA_PROVIDER = "excelDataProvider";

	public ExcelDataProvider() {
		
	}
	
	/**
	 * Excel Data Provider 
	 * Sheet Name is Class Name 
	 * 
	 * @param method
	 * @return
	 */
	@DataProvider(name = "excelDataProvider")
	public static Object[][] getExcelData(Method method) {
		Object[][] dataProvider = getData(method.getDeclaringClass().getSimpleName());
		return dataProvider;
	}

	/**
	 * This method reads the data in excel and adds to hash table
	 * 
	 * @param sheetName
	 * @return
	 */
	public static Object[][] getData(String sheetName) {
		String FilePath = FrameWorkConstants.EXCEL_PATH;
		File inputStream = new File(FilePath);
		int countForObj = 0;
		Hashtable<String, String> excelData = new Hashtable<String, String>();
		Object[][] obj = null;
		try {
			Workbook wb = new XSSFWorkbook(inputStream);
			Sheet sheet = wb.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			Row rowHeader = sheet.getRow(0);
			List<String> headerSet = new ArrayList<String>();
			obj = new Object[rowCount][1];
			for (int j = 0; j < rowHeader.getLastCellNum(); j++) {
				headerSet.add(rowHeader.getCell(j).getStringCellValue());
			}

			for (int i = 1; i < rowCount + 1; i++) {

				Row row = sheet.getRow(i);
				int count = 0;
				for (int j = 0; j < row.getLastCellNum(); j++) {
					excelData.put(headerSet.get(count), row.getCell(j).getStringCellValue());
					count = count + 1;
				}
				obj[countForObj][0] = excelData;
				countForObj = countForObj + 1;
				excelData = new Hashtable<>();
			}
			wb.close();
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

}
