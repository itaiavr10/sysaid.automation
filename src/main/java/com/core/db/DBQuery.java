package com.core.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.core.base.LogManager;
import com.core.utils.ActionWrapper;
import com.core.utils.StringRef;
import com.core.utils.Utils;
import com.core.utils.ValueRef;

import buisness.db.DBconst.TableContent;

public class DBQuery {
	
	
	public static void verifyTableRetry(final TableContent tableContent , Integer timeOutMs, Integer delay){
		final StringRef info = new StringRef("");
		boolean ispass = Utils.tryUntil(new ActionWrapper("Verify Table Content: "+ tableContent , timeOutMs, delay) {
			@Override
			public boolean invoke() throws Exception {
				ResultSet rs = DbFactory.get().exec(tableContent.getQuery());
				try {
					List<List<String>> actualTable = new ArrayList<List<String>>();
					List<List<String>> expectedTable = tableContent.getExpected();
					int rowIndex = 0;
					while(rs.next()){
						List<String> row = new ArrayList<String>();
						for(int i = 1; i<=tableContent.col;i++){ //Read each row and add to actual table
							row.add(rs.getString(i));
						}
						actualTable.add(row);
						List<String> expectedRow = expectedTable.get(rowIndex);
						if(expectedRow.equals(row)){ // verify current row
							LogManager.debug("Found same table row content: " +row.toString());
						}else{
							 return failure( String.format("Verify row : Expected = %s , Actual = %s" ,expectedRow.toString(), row.toString()) );
						}
						if(rowIndex>= expectedTable.size()){ // verify current table is not bigger the expected
							return failure("Current Table is bigger then expexted"); 
						}
						rowIndex++;
					}
					
					if(expectedTable.size() != actualTable.size()){
						return failure(String.format("Incorrect Size , Expected Rows = %s , Actual Rows = %s" , 
								tableContent.getDescription(),expectedTable.size(),actualTable.size())); 
					}
					
				} catch (Exception e) {
					return failure (e.getMessage()); 
				}
				return true;
			}
			
			private boolean failure(String msg){
				info.setValue(msg); 
				LogManager.debug(msg);
				return false;
			}
		});
		if(ispass)
			LogManager.verify(true, "Verify Table Content: "+ tableContent.getDescription());
		else
			LogManager.error(tableContent.getDescription() + " Verify Table Content Failed : " + info.value);
	}
	
	
	public static void verifyTable(TableContent tableContent){
		LogManager.debug("Verify Table Content: "+ tableContent);
		ResultSet rs = DbFactory.get().exec(tableContent.getQuery());
		boolean passed = true;
		try {
			List<List<String>> actualTable = new ArrayList<List<String>>();
			List<List<String>> expectedTable = tableContent.getExpected();
			int rowIndex = 0;
			while(rs.next()){
				List<String> row = new ArrayList<String>();
				for(int i = 1; i<=tableContent.col;i++){ //Read each row and add to actual table
					row.add(rs.getString(i));
				}
				actualTable.add(row);
				List<String> expectedRow = expectedTable.get(rowIndex);
				if(expectedRow.equals(row)){ // verify current row
					LogManager.debug("Found same table row content: " +row.toString());
				}else{
					LogManager.verify(false,String.format("Verify %s - Table Content: Expected = %s , Actual = %s" , tableContent.getDescription(), expectedRow.toString(), row.toString()));
					passed = false;
				}
				if(rowIndex>= expectedTable.size()){ // verify current table is not bigger the expected
					passed = false;
					throw new Exception(tableContent + " : Current Table is bigger then expexted");
				}
				rowIndex++;
			}
			
			if(expectedTable.size() != actualTable.size()){
				LogManager.verify(false,String.format("Verify Table Content: %s  - Incorrect Size , Expected Rows = %s , Actual Rows = %s" , 
						tableContent.getDescription(),expectedTable.size(),actualTable.size()));
				passed = false;
			}
			
		} catch (Exception e) {
			LogManager.verify(false, tableContent + " - Verify Table Content: - Error : " + e.getMessage());
		}finally{
			if(passed)
				LogManager.verify(true,"Verify Table Content: "+ tableContent.getDescription());
		}
	}
	
	
	
	
	/*public static void validateRowResult(String query , List<String> expectedList){
		LogManager.debug("Validate Query results..");
		ResultSet rs = DbFactory.get().exec(query);
		try {
			if(rs.next()){
				List<String> actualList = new ArrayList<String>();
				actualList.add(rs.getString(1));
				actualList.add(rs.getString(2));
				actualList.add(rs.getString(3));
				LogManager.validate(expectedList.equals(actualList),String.format("Validate DB Query=%s , Expected = %s , Actual = %s" , query,expectedList.toString(),actualList.toString()));
				if(rs.next())
					LogManager.warn("More then 1 query results..");
			}else
				throw new Exception("No results from DB");
		} catch (Exception e) {
			LogManager.validate(false, "validate DB Result - Error : " + e.getMessage());
		}finally{
			
		}
	}*/
	
	public static void verifyResult(String query , int expected){
		verifyResult(query , Integer.toString(expected));
	}
	
	
	public static void verifyResult(String query , String expected){
		LogManager.debug("Verify Query results..");
		ResultSet rs = DbFactory.get().exec(query);
		//ResultSet rs = DBconnector.get().exec(query);
		try {
			if(rs.next()){
				String actual = rs.getString(1);
				LogManager.verify(expected.equals(actual),String.format("Verify DB Query=%s , Expected = %s , Actual = %s" , query,expected,actual));
				if(rs.next())
					LogManager.warn("More then 1 query results..");
			}else
				throw new Exception("No results from DB");
		} catch (Exception e) {
			LogManager.verify(false, "Verify DB Result - Error : " + e.getMessage());
		}finally{
			
		}
	}
	
	public static void verifyNumOfRows(String query , int expectedRows){
		LogManager.debug("Verify DB - Num Of Rows..");
		ResultSet rs = DbFactory.get().exec(query);
		try {
			int actualRows = 0;
			while(rs.next()){
				actualRows++;
			}
			LogManager.verify(expectedRows == actualRows ,String.format("Verify DB - Num Of Rows, Query=%s , Expected = %s , Actual = %s" , query,expectedRows,actualRows));
		} catch (Exception e) {
			LogManager.verify(false, "Verify Num Of Rows - Error : " + e.getMessage());
		}finally{
			
		}
	}

}
