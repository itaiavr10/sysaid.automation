package com.core.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.core.base.LogManager;

public class MySqlDB extends AbstractDB{
	
	private String mySqlDriver = "com.mysql.jdbc.Driver";
	private String dbName ="ilient";
	
	
	protected MySqlDB() {
		super("root", "Password1", "jdbc:mysql://%s:3306/");
	}
	
	public void connect() { //TODO: might be a singleton , connect only 1 time!
		try {
			//url = "jdbc:sqlserver://10.14.1.103:1450;databaseName=ilient";
			LogManager.debug("MS SQL - connecting..");
			Class.forName(mySqlDriver).newInstance();
			// Setup the connection with the DB
			conn = DriverManager.getConnection(url + dbName, user, pass);
			LogManager.debug("MS SQL - connection created");

		} catch (ClassNotFoundException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		} catch (SQLException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		} catch (InstantiationException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		} catch (IllegalAccessException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		}
		
	}

}
