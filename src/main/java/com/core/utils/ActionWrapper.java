package com.core.utils;

public abstract class ActionWrapper {
	
	final public static int defaultTimeOut = 20000;
	private String name;
	private int timeoutMs = defaultTimeOut;
	private int delayMs = 500;
	
	public ActionWrapper() {
		this("");
	}
	
	public ActionWrapper(String name) {
		this.name = name; 
	}
	
	public ActionWrapper(String name , Integer timeOutMs) {
		this(name,timeOutMs,null); 
	}
	
	/*public ActionWrapper(int timeOut) {
		this("",timeOut,null); 
	}
	
	public ActionWrapper(Integer timeOut, Integer delay) {
		this("",timeOut,delay); 
	}*/
	
	public ActionWrapper(String name, Integer timeOutMs, Integer delay) {
		this.name = name; 
		if(timeOutMs != null)
			timeoutMs = timeOutMs;
		if(delay != null)
			delayMs = delay;
	}
	
	public abstract boolean invoke() throws Exception;
	public int TimeoutMs() { return timeoutMs; } ;
	public int Delay() { return delayMs; } ;
	public String GetName() { return this.name; } ;

}
