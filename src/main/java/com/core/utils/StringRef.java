package com.core.utils;

public class StringRef extends ValueRef<String>
{

	public StringRef(String value) {
		super(value);
	}
	
	public void addValue(String addedValue){
		value += addedValue;
	}
	
	public void addLine(String addedValue){
		value += "\n"+addedValue;
	}
	
	public boolean isEmpty(){
		return (value == null || value.isEmpty());
	}
		
}
