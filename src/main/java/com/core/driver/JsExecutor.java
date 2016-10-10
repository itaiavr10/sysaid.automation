package com.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsExecutor {
	private WebDriver driver;
	
	 public JsExecutor(WebDriver driver){
		 this.driver = driver;
	 }
	
	
	 public  JavascriptExecutor getJsExecutor() throws Exception
	 {
		 return (JavascriptExecutor) this.driver;
	 }	 

	 public  String executeScript(String command) throws Exception
	 {            
	     Object execute = getJsExecutor().executeScript(command);
	
	     if (execute == null)
	         return "null";
	
	     return execute.toString();
	 }
	 
	 public  String executeScript(String command,WebElement element) throws Exception
	 {            
	     Object execute = null;
		try {
			execute = getJsExecutor().executeScript(command,element);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	     if (execute == null)
	         return "null";
	
	     return execute.toString();
	 }
	 
	 public String injectJS(String command) throws Exception
	 {            
		 return executeScript("javascript:" + command);	     
	 }

}
