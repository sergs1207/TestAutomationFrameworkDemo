		
		// -создать чистый эксель файл с таким же колличеством листов как и у файла с тест кейсами
		// -копируем содержимое с тест кейсов в пустой файл
		// -теперь есть рабочий файл для репорта


package lesson4.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateEmptyTestReportFile
{
	public static final String DATE_FORMAT_NOW = "dd.MM.yyyy_HH-mm-ss";	
	//public String pathToTestReportFile ="D:/Git/TestGitRepo/AndyTEST/lesson4TestReport/UserStoriesChecklist_02_TestReport_06.02.2017_00-09-37.xlsx";		
	public static String pathToTestReportFile ;
	private String pathToTestCaseFile;
	private String pathToReportFolder;	
	private static List <String> sheetNames = new ArrayList<String>();
	
	public CreateEmptyTestReportFile()
	{		
	}
	
	public CreateEmptyTestReportFile(String pathToTestCaseFile, String pathToReportFolder) throws ClassNotFoundException, IOException, SQLException
	{
		this.pathToTestCaseFile = pathToTestCaseFile;
		this.pathToReportFolder = pathToReportFolder;		
	}
		
	public void readTestCaseFile() throws IOException, ClassNotFoundException, SQLException
	{
		System.out.println("Reading Test Case file...");
		File myFile = new File(pathToTestCaseFile);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook excelBook = new XSSFWorkbook(fis);			
			
			// Get names of Excel sheets
		for(int i = 0; i < excelBook.getNumberOfSheets(); i++) sheetNames.add(excelBook.getSheetName(i));			
						
		fis.close();		
	} 	//End of readTestCaseFile()
		
	public void createTestReportFile() throws IOException, ClassNotFoundException, SQLException
	{
		readTestCaseFile();  // Read Test Case file for create duplicate of it	
		
		System.out.println("Creating new Test Report file...");
		// -Get from pathToExcelFile only file name
		// - add output folder as prefix and "TestReport" + time stamp		
		pathToTestReportFile = pathToReportFolder + pathToTestCaseFile
				.substring(getLastCharacterIndex(pathToTestCaseFile, '\\'), getLastCharacterIndex(pathToTestCaseFile, '.')) + "_TestReport_" + timeStamp() + ".xlsx";		
		
		 XSSFWorkbook myWorkBook = new XSSFWorkbook(); 
		 for(String bufferSheetName: sheetNames)
		{
			System.out.println("Creating new sheet...");
			XSSFSheet mySheet = myWorkBook.createSheet(bufferSheetName);		 		
			System.out.println("New sheet was created.");		
		}	

		// open an OutputStream to save written data into XLSX file
		FileOutputStream os = new FileOutputStream(pathToTestReportFile);
		myWorkBook.write(os);
		// System.out.println("Writing on XLSX file Finished ...");

		// Close workbook, OutputStream and Excel file to prevent leak
		myWorkBook.close();
		os.close();
		System.out.println("New Test Report file created successefully!");
		
		ReadExcelAndCompareResults myInstance = new ReadExcelAndCompareResults(pathToTestReportFile);

	} // end of writeExcelCellsWithSQLQueryResult()
		

	private int getLastCharacterIndex(String myString, char myCharacter)
	{
		int position = 0;
		for (int i = 0; i < myString.length(); i++)
		{
			if (myString.charAt(i) == myCharacter)
				position = i;
		}
		return position;
	}
	
	public static String timeStamp() 
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}

