package lesson4.framework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;



public class ExecuteQueryAndGenerateCSV
{
	private String databaseDriver;
	private String databaseURL;
	//private String security;	//For MSSQL
	private  String username; 
	private  String password;
		
	public ExecuteQueryAndGenerateCSV(String databaseDriver, String databaseURL, String username, String password)
	{
		this.databaseDriver = databaseDriver;
		this.databaseURL = databaseURL;
		//this.security = security;
		this.username = username;
		this.password = password;
		
	}	
	
	protected String executeSQLQuery(String resultOfReadCel) throws ClassNotFoundException, SQLException
	{			
		return resultSetToString(getResultSetFromQuery(resultOfReadCel));
	}	// end of Main method
	
	private ResultSet getResultSetFromQuery(String query) throws ClassNotFoundException, SQLException
	{
		ResultSet result;
		Class.forName(databaseDriver);
		Connection myConn = DriverManager.getConnection(databaseURL, username, password);
		Statement myStmt = myConn.createStatement();
		result = myStmt.executeQuery(query);
		return result;
	}	// end of getResultSetFromQuery()

	private String resultSetToString(ResultSet result) throws SQLException
	{
		String resultString = "";
		ResultSetMetaData rsmd = result.getMetaData();
		int columnCount = rsmd.getColumnCount();
		while (result.next())
		{
			// The column count starts from 1
			for (int i = 1; i <= columnCount; i++)			
				resultString += result.getString(rsmd.getColumnName(i)) + ";";			
		}
		
		return resultString;
	}	// end of ResultSetToString()
}	// end of class
