package lesson4.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.SQLException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteSQLQueryResult
{	
	public void writeExcelCellsWithSQLQueryResult(String pathToTestReportFile,
			String resultOfSQLQueryExecut, int sheetNumber, int rowCounter) throws IOException, ClassNotFoundException, SQLException
	{				
		File myFile = new File(pathToTestReportFile);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(sheetNumber);
		XSSFRow row = mySheet.getRow(rowCounter);
		if (row != null)
		{
			XSSFCell cell = row.getCell(4);
			if (cell == null)
				cell = row.createCell(4);

			cell.setCellValue(resultOfSQLQueryExecut);
		}
		fis.close();

		// open an OutputStream to save written data into XLSX file
		FileOutputStream os = new FileOutputStream(myFile);
		myWorkBook.write(os);
		// System.out.println("Writing on XLSX file Finished ...");

		// Close workbook, OutputStream and Excel file to prevent leak
		myWorkBook.close();
		os.close();
	} // end of writeExcelCellsWithSQLQueryResult()	

} // end of class

