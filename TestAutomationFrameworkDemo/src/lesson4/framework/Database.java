package lesson4.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Database
{
	private String pathToDatabaseAndTablesCreationFile;
	private String databaseDriver;
	private String databaseURL;
	//private String security;
	private  String username;
	private  String password;
	
	
	
	public Database(String pathToDatabaseAndTablesCreationFile, String databaseDriver, String databaseURL, String username, String password)
	{
		this.pathToDatabaseAndTablesCreationFile = pathToDatabaseAndTablesCreationFile;
		this.databaseDriver = databaseDriver;
		this.databaseURL = databaseURL;
		//this.security = security;
		this.username = username;
		this.password = password;
	}
	
	protected void createTablesAndFillData() throws ClassNotFoundException, SQLException, IOException
	{
		Connection conn = null;
		Statement stmt = null;		

		// Register JDBC driver
		Class.forName(databaseDriver);

		// Open a connection
		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(databaseURL, username, password);
		System.out.println("Connection to database is successeful!");
		
		//  Execute a query		
		stmt = conn.createStatement();	   				
		System.out.println("Executing query ...");			
		stmt.executeUpdate(readFileForCreateDatabaseAndTables(pathToDatabaseAndTablesCreationFile));
		
		// Close  connections		
		stmt.close();
		conn.close();	
		System.out.println("Database and tables was created and filled by the data successfully...BINGO!!!");	
	}
		
	private String readFileForCreateDatabaseAndTables(String pathToFile) throws IOException
	{
		String createDbAndTables = "";
		
		//Specify path to your file
		File file = new File(pathToFile);				
						
		//Create special input stream for reading data
		FileInputStream fis = new FileInputStream(file);
				
		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				
		//Print all existing lines from file to the console
		String line = "";				
		while ((line = br.readLine()) != null) 
				createDbAndTables += line + " ";
				
		br.close();			
		
		return createDbAndTables;
	}
	
}
