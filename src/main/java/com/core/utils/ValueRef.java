package com.core.utils;

public class ValueRef<T> {
	
	public T value;
	
	//public T getValue() {return value;}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	
	public ValueRef(T value){
		this.value = value;
	}

	public void addOne(){
		Integer i = (Integer)value + 1;
		T t = (T)i;
		setValue(t);
	}

}
