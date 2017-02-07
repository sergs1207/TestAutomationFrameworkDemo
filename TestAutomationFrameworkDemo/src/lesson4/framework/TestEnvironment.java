package lesson4.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class TestEnvironment 
{	
	public static final String DATA_FILE = "D:\\others\\Новая папка\\TestGitRepo\\AndyTEST\\ini\\FrameworkForMySQL.ini";
	protected  String pathToTestCaseFile = "";
	protected  String pathToReportFolder = "";
	protected  String pathToDatabaseAndTablesCreationFile = "";
	protected  String databaseDriver = "";
	protected  String databaseURL = "";
	protected  String username = ""; // For Oracle, MySQL
	protected  String password = ""; // For Oracle, MySQL
	//protected  String databaseSecurity  = ""; // For MS SQL Server
	

	public void initialiseEnvironmentVariables() throws IOException, ClassNotFoundException
	{
		this.pathToTestCaseFile = getValueFromFile(DATA_FILE, "pathToTestCaseFile");
		this.pathToReportFolder = getValueFromFile(DATA_FILE, "pathToReportFolder");
		this.pathToDatabaseAndTablesCreationFile = getValueFromFile(DATA_FILE, "pathToDatabaseAndTablesCreationFile");
		this.databaseDriver = getValueFromFile(DATA_FILE, "databaseDriver");
		this.databaseURL = getValueFromFile(DATA_FILE, "databaseURL");
		this.username  = getValueFromFile(DATA_FILE, "username");
		this.password  = getValueFromFile(DATA_FILE, "password");
		//this.databaseSecurity   = getValueFromFile(DATA_FILE, "databaseSecurity");

	}

	private String getValueFromFile(String DATA_FILE, String myParam) throws IOException, ClassNotFoundException 
	{
		String resultValue = "";		
		
		// Specify path to your file
		File file = new File(DATA_FILE);

		// Create special input stream for reading data
		FileInputStream fis = new FileInputStream(file);

		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		// Get all existing lines from file to the String	
		String line = "";
		while ((line = br.readLine()) != null)
		{
			String bufferArray[] = line.split(" ");	//разделяю строку на масив из слов			
			
			if(bufferArray[0].equalsIgnoreCase(myParam) )
			{
				resultValue = line.substring(line.indexOf("=")+2);	// read part of string beginning after "=" . 2  means that the "=" + "space"				
				break;
			}			
		}
		 br.close();

		return resultValue;
	}

	public void  createEnvironment() throws ClassNotFoundException, SQLException, IOException
	{
		Database myDatabase = new Database(pathToDatabaseAndTablesCreationFile, databaseDriver, databaseURL, username, password);		
		myDatabase.createTablesAndFillData();		
	}

	public void  runTests() throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		Test myTest = new Test(pathToTestCaseFile, pathToReportFolder, databaseDriver, databaseURL, username,  password);		
		myTest.executeTestCases();		
	}	

}
