package com.core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Cloner;
import utils.SystemUtils.OS;
import base.LogManager;

public class DBconnector {

	private Connection conn = null;
	private Statement statment = null;
	private String user = "sa";
	private String pass = "gSPc5a3a9p";
	private String url = "jdbc:sqlserver://%s:1450;databaseName=ilient";
	public static String dbIP = "10.14.1.103";

	public DBconnector() {
		//connect();
		//dbIP = OS.getCurrentIP();
	}

	private void connect() { //TODO: might be a singleton , connect only 1 time!
		try {
			//url = String.format(url, dbIP);
			url = "jdbc:sqlserver://10.14.1.103:1450;databaseName=ilient";
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

	public ResultSet exec(String query) {
		connect();
		LogManager.debug("MS SQL - exec query : " + query);
		ResultSet rs = null;
		try {
			statment = conn.createStatement();
			rs = statment.executeQuery(query);
		} catch (SQLException e) {
			LogManager.assertTrue(false, "Failed to execute DB query , Error : " + e.getMessage());
		} finally {
			
			//closeConnection();
		}
		return rs;
	}

	public void closeConnection() {
		if (statment != null) {
			try {
				statment.close();
			} catch (SQLException e1) {}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	}

}
