package com.core.base;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import buisness.modules.SysAidServer;

public class AbstractSuite {

	//protected Validate validate;

	@BeforeSuite
	public void beforeSuite() { // RUN BEFURE each testng.xml - suite
		// System.out.println("@BeforeSuite");
		//validate = TestManager.validator();
		//System.setProperty("upgradeFrom", "16.1.25.b28"); //TODO : Debug!!
		SysAidServer.initInstaller();
	}

	@BeforeClass
	public void beforeClass() { // RUN BEFURE each suite Class
		// System.out.println("@BeforeClass");

	}

	@BeforeMethod
	public void beforeMethod() { // RUN BEFURE each test
		// System.out.println("@BeforeMethod - RUN BEFURE each test");
	}

	@BeforeTest
	public void beforeTest() { // RUN BEFURE each testng.xml - test
		// System.out.println("@BeforeTest");
	}

	// -------------------------------------------
	@AfterSuite
	public void afterSuite() { // RUN AFTER each testng.xml - suite
		// System.out.println("@AfterSuite");
	}

	@AfterClass
	public void afterClass() { // RUN After each suite Class
		// System.out.println("@AfterClass");
	}

	@AfterMethod
	public void afterMethod(ITestResult tr) { // RUN AFTER each test
		//Assert.assertTrue(SuiteReporter.isTestPassed()); 
		//LogManager.bold("Test Execution time = " + (tr.getEndMillis() - tr.getStartMillis()));
		
	}
	@AfterMethod
	public void getRunTime(ITestResult tr) {
	    long time = tr.getEndMillis() - tr.getStartMillis();
	}

	@AfterTest
	public void afterTest() { // RUN AFTER each testng.xml - test
		// System.out.println("@AfterTest");
		// System.out.println("----------");
	}
	
	protected void sleep(long time , TimeUnit unit) {
		sleep(unit.toMillis(time));
	}

	protected void sleep(long time) {
		TestManager.sleep(time);

	}

}
