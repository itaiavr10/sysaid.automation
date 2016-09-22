package com.core.db;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.core.base.LogManager;

public class MsSqlDB extends AbstractDB{
	
	
	
	

	protected MsSqlDB() {
		super("sa", "gSPc5a3a9p", "jdbc:sqlserver://%s:1450;databaseName=ilient");
	}

	
	public void connect() { //TODO: might be a singleton , connect only 1 time!
		try {
			//url = "jdbc:sqlserver://10.14.1.103:1450;databaseName=ilient";
			LogManager.debug("MS SQL - connecting..");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, pass);
			LogManager.debug("MS SQL - connection created");

		} catch (ClassNotFoundException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		} catch (SQLException e) {
			LogManager.assertTrue(false, "Failed to connect to DB , Error : " + e.getMessage());
		}
	}
	

}
