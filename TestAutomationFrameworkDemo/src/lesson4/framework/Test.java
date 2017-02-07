package lesson4.framework;

import java.io.IOException;
import java.sql.SQLException;


public class Test
{
	private String pathToTestCaseFile;
	private String pathToReportFolder;
	private String databaseDriver;
	private String databaseURL;
	//private String security;	// For MSSQL
	protected  String username; 
	protected  String password;
	
	public Test(String pathToTestCaseFile, String pathToReportFolder, String databaseDriver, String databaseURL, String username, String password) 
	{
		this.pathToTestCaseFile = pathToTestCaseFile;
		this.pathToReportFolder = pathToReportFolder;
		this.databaseDriver = databaseDriver;
		this.databaseURL = databaseURL;
		//this.security = security;
		this.username  = username;
		this.password  = password;
	}

	public void executeTestCases() throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		CreateEmptyTestReportFile myInstance = new CreateEmptyTestReportFile(pathToTestCaseFile, pathToReportFolder);
		myInstance.createTestReportFile();
					
		System.out.println("Starting to copy data from Test Case to Test Result file...");
		CopyTestCaseFileToTestReportFile myInstance2 = new CopyTestCaseFileToTestReportFile(pathToTestCaseFile);		
		myInstance2.copyAllDataFromTestCaseToTestReportFile();
		System.out.println("Data copied from Test Case to Test Result file...successefully!");		
		
		ReadExcelAndCompareResults myInstance3 = new ReadExcelAndCompareResults(databaseDriver, databaseURL, username, password);		
		myInstance3.writeDataFromExcelToArrayList();		
		myInstance3.addSQLresultToArrayList();			
	}
	
}
