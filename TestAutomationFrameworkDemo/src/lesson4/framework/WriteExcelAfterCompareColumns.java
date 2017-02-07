package lesson4.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelAfterCompareColumns
{
	protected void writeExcelCell(String pathToTestReportFile, String resultOfcompare, int sh2, int i) throws IOException, ClassNotFoundException, SQLException
	{
		File myFile = new File(pathToTestReportFile);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);		
		XSSFSheet mySheet = myWorkBook.getSheetAt(sh2);	//sh - number of excel sheet
		XSSFRow row = mySheet.getRow(i);
		if (row != null)
		{
			XSSFCell cell = row.getCell(5);
			if (cell == null)			
				cell = row.createCell(5);
						
			if(resultOfcompare.equals("Pass"))
			{
				cell.setCellValue(resultOfcompare);	
				XSSFCellStyle style = myWorkBook.createCellStyle();
				XSSFFont font = myWorkBook.createFont();
					font.setColor(IndexedColors.GREEN.getIndex());					
					style.setFont(font);
				 cell.setCellStyle(style);		 
			}
			else if(resultOfcompare.equals("Fail"))
			{
				cell.setCellValue(resultOfcompare);	
				XSSFCellStyle style = myWorkBook.createCellStyle();
				XSSFFont font = myWorkBook.createFont();
					 font.setColor(IndexedColors.RED.getIndex());					 
					 style.setFont(font);					 
				cell.setCellStyle(style);
			}			
		}		
		fis.close();

		// open an OutputStream to save written data into XLSX file
		FileOutputStream os = new FileOutputStream(myFile);
		myWorkBook.write(os);
		System.out.println("Writing on XLSX file Finished ...");

		// Close workbook, OutputStream and Excel file to prevent leak
		myWorkBook.close();
		os.close();
	}// end of writeExcelCell() method
}// end of Class

