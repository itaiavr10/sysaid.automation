package com.core.db;

public class DbFactory {
	
	public static DBconnector get(){ // should be by DB TYpe , ip
		return new DBconnector();
	}

}
