package com.core.driver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.base.LogManager;
import com.core.base.TestManager;

public class PageDriver {
	private WebDriver driver;
	private String mainWinHandle;
	private long implicitWait = 30; //default implicit wait in Seconds
	private long findWaitDelayInMili = 500; // for test stable flow

	public PageDriver() {
		LogManager.debug("init web driver");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void navigate(String to) {
		driver.get(to);
	}
	
	public WebDriver get(){
		return driver;
	}
	
	
	protected void switchToFrame(PageElement element){
		driver.switchTo().defaultContent();
		mainWinHandle = driver.getWindowHandle();
		driver.switchTo().frame(getElement(element));
	}
	
	protected void switchToMainWin(){
		driver.switchTo().window(mainWinHandle);
	}
	


	void click(PageElement element) {
		LogManager.debug("click on element : " + element.toString());
		waitForIsClickable(element);
		getElement(element).click();
	}
	
	void setValue(PageElement element , String value){
		LogManager.debug("set value on element : " + element.toString());
		getElement(element).sendKeys(value);
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
	
	

	boolean isDisplayed(PageElement element) {
		try {
			return getElement(element).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public int totalElements(PageElement element){
		List<WebElement> list = driver.findElements(element.getBy());
		if(list == null)
			return 0;
		return list.size();
	}

	private WebElement getElement(PageElement element) {
		LogManager.debug("get element : " + element.toString());
		TestManager.sleep(findWaitDelayInMili);
		List<WebElement> list = driver.findElements(element.getBy());
		// check if found
		if(list == null || list.isEmpty())
			LogManager.assertTrue(false, "Failed to find element : " + element.toString());
		//check that we found only 1
		if (list.size() != 1)
			LogManager.warn("get element found : " + list.size());

		//wait for is displayed
		waitForIsDisplayed(element);

		return list.get(0); //TODO wait for displayed!
	}

	// Explicit Wait for Element to be clickable!
	boolean waitForIsClickable(PageElement element, Integer... timeout) {
		LogManager.debug("wait for element is clickable : " + element.toString());
		try {
			waitFor(ExpectedConditions.elementToBeClickable(element.getBy()), timeout.length > 0 ? timeout[0] : null);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	 // Explicit Wait for Element Displayed!
	boolean waitForIsDisplayed(PageElement element, Integer... timeout) {
		LogManager.debug("wait for element is displayed : " + element.toString());
		try {
			waitFor(ExpectedConditions.visibilityOfElementLocated(element.getBy()), timeout.length > 0 ? timeout[0] : null);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	private void waitFor(ExpectedCondition<WebElement> condition, Integer timeout) throws TimeoutException {
		timeout = timeout != null ? timeout : (int) implicitWait;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(condition);
	}

}
