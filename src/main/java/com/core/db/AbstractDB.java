package com.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.core.base.LogManager;
import com.core.utils.SystemUtils.OS;

public abstract class AbstractDB {
	
	protected static  Connection conn = null;
	protected Statement statment = null;
	protected String user = "sa";
	protected String pass = "gSPc5a3a9p";
	protected String url = "jdbc:sqlserver://%s:1450;databaseName=ilient";
	public static String dbIP = "10.14.1.103";
	
	
	
	protected AbstractDB(String user, String pass, String url){
		//dbIP = OS.getCurrentIP();
		this.user = user;
		this.pass = pass;
		this.url = String.format(url, dbIP);
		//connect();
	}
	
	
	public ResultSet exec(String query) {
		connect();
		LogManager.debug("MS SQL - exec query : " + query);
		ResultSet rs = null;
		try {
			statment = conn.createStatement();
			rs = statment.executeQuery(query);
		} catch (SQLException e) {
			LogManager.error("Failed to execute DB query , Error : " + e.getMessage());
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
	
	public abstract void connect();

}
