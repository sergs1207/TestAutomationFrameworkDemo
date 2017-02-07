package lesson4.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CopyTestCaseFileToTestReportFile
{
	private  String[] myArrayForEachObjectOfArrayList;
	private static List<String[]> arrayListOfTestCases = new ArrayList<String[]>();
	private String pathToTestCaseFile;
	private String pathToTestReportFile;	
	
	CopyTestCaseFileToTestReportFile(){};
	
	CopyTestCaseFileToTestReportFile(String pathToTestCaseFile)
	{
		this.pathToTestCaseFile = pathToTestCaseFile;		
	}	
	
	public void copyAllDataFromTestCaseToTestReportFile() throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		copyTestCaseFileToArrayList();
	}	
	
	private void copyTestCaseFileToArrayList() throws IOException, ClassNotFoundException, SQLException, InterruptedException
	{			
		System.out.println("Reading Test Case file...");
		File myFile = new File(pathToTestCaseFile);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook excelBook = new XSSFWorkbook(fis);	
		
		for (int i = 0; i < excelBook.getNumberOfSheets(); i++)
		{			
			XSSFSheet excelSheet = excelBook.getSheetAt(i);			
			
			for (Row row : excelBook.getSheetAt(i))
			{				
				myArrayForEachObjectOfArrayList = new String[6];
				for (int j = 0; j <= 5; j++)
				{
					Cell cell = row.getCell(j);
					if (cell != null) 
						myArrayForEachObjectOfArrayList[j] = cell.getStringCellValue();					
				}
				arrayListOfTestCases.add(myArrayForEachObjectOfArrayList);
				
			}
			System.out.println("Sheet was read.");
			readArrayList(i);		
			arrayListOfTestCases.clear(); // I M P O R T A N T !!! clear Arraylist for use it for next excel sheet ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! !!
		}
		fis.close();
	} // end of copyTestCaseFileToArrayList
	
	private void readArrayList(int sheetNumber) throws FileNotFoundException, IOException, InterruptedException
	{
		String resultOfReadCell = "";		
		int counterOfRows = 0;
		
		for (String[] buferArry : arrayListOfTestCases)
		{			
			for(int j = 0; j < buferArry.length; j++)
			{				
				if (buferArry[j] != null)
				{					
					resultOfReadCell = buferArry[j].toString();					
					fillTestReportFile(sheetNumber, resultOfReadCell, counterOfRows, j);																					
				}
			}
			System.out.println("Row was read.");
			counterOfRows++;
		}			
	}	
	
	private void fillTestReportFile(int sheetNumber, String resultOfReadCell, int counterOfRows, int numberOfCell) throws FileNotFoundException, IOException, InterruptedException
	{		
		CreateEmptyTestReportFile myInstance = new CreateEmptyTestReportFile();
		File myFile = new File(myInstance.pathToTestReportFile);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(sheetNumber);
		XSSFRow row = mySheet.getRow(counterOfRows);
		
		if(row == null)
			row = mySheet.createRow(counterOfRows);				
		if (row != null)
		{
			XSSFCell cell = row.getCell(numberOfCell);
			if (cell == null) 			
				cell = row.createCell(numberOfCell);							
			cell.setCellValue(resultOfReadCell);			
		}	
		
		fis.close();
		
		// open an OutputStream to save written data into XLSX file
		FileOutputStream os = new FileOutputStream(myFile);
		myWorkBook.write(os);
		// System.out.println("Writing on XLSX file Finished ...");

		// Close workbook, OutputStream and Excel file to prevent leak
		myWorkBook.close();
		os.close();				
	}
		
}//End of main 
