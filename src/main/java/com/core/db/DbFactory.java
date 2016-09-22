package com.core.db;

import com.core.base.LogManager;

import buisness.modules.SysAid;

public class DbFactory {
	
	/*public static DBconnector get(){ // should be by DB TYpe , ip
		return DBconnector.get();
	}*/
	
	private static AbstractDB current;
	
	static{
		switch (SysAid.getDbType()) {
		case MsSQL:
			current = new MsSqlDB();
			break;
			
		case MySQL:
			current = new MySqlDB();
			break;

		default:
			LogManager.error("Failed to init DB Object");
			break;
		}
	}
	
	public static AbstractDB get(){
		return current;
	}

}
