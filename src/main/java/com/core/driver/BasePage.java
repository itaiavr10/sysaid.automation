package com.core.driver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.core.base.LogManager;

public abstract class BasePage {
	
	protected PageDriver driver;
	protected JsExecutor js;
	
	
	protected BasePage(PageDriver driver){
		this.driver = driver;
		js = new JsExecutor(driver.get());
	}
	
	
	protected void click(PageElement element){
		driver.click(element);
	}
	
	
	protected void setValue(PageElement element, String value){
		driver.setValue(element, value);
	}
	
	
	protected void switchToFrame(PageElement element){
		driver.switchToFrame(element);
	}
	
	protected void switchToMainWin(){
		driver.switchToMainWin();
	}
	
	
	protected boolean isExist(PageElement element){
		int total = driver.totalElements(element);
		if(total == 0)
			return false;
		if(total > 1)
			LogManager.warn("Elment exist, but more than 1");
		return true;
	}
	
	protected boolean isElementExist(PageElement element){
		return driver.isDisplayed(element);
	}
	
	
	protected List<WebElement> getElements(By by){
		return driver.get().findElements(by);
	}
	
	protected void verifyTableSize(PageElement rows , int expectedRows){
		int actualrows = driver.totalElements(rows);
		LogManager.verify(expectedRows == actualrows, String.format("Verify table size, Expected= %s, Actual= %s",expectedRows, actualrows));
	}
	
	
	
	/**
     * verify if Element is displayed
     * @param by
     * @param timeOutInSeconds
     */
	protected void verifyIsDisplayed(PageElement element,Integer... timeOutInSeconds) {
        boolean isDisplayed = driver.waitForIsDisplayed(element,timeOutInSeconds);
        LogManager.verify(isDisplayed, "Verify Element is displayed : " + element.toString());
    }
	
	
	
	

}
