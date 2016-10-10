package com.core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {
	private WebDriver driver;
	
	
	public Driver(){
		driver = new FirefoxDriver();
	}
	
	public void navigate(String to){
		driver.get(to);
	}
	
	

}
