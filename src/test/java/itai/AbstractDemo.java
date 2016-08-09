/*package itai;


import org.testng.annotations.*;

public class AbstractDemo {
	
	@BeforeSuite
	public void beforeSuite(){ //RUN BEFURE each testng.xml  - suite
		System.out.println("@BeforeSuite");
	}
	
	
	@BeforeClass 
	public void beforeClass(){ //RUN BEFURE each suite Class
		System.out.println("@BeforeClass");
	}
	
	
	@BeforeMethod 
	public void beforeMethod(){ //RUN BEFURE each test
		System.out.println("@BeforeMethod - RUN BEFURE each test");
	}
	
	@BeforeTest
	public void beforeTest(){ //RUN BEFURE each testng.xml - test
		System.out.println("@BeforeTest");
	}
	
	
	//-------------------------------------------
	@AfterSuite
	public void afterSuite(){ //RUN AFTER each testng.xml  - suite
		System.out.println("@AfterSuite");
	}
	
	
	@AfterClass 
	public void afterClass(){ //RUN After each suite Class
		System.out.println("@AfterClass");
	}
	
	
	@AfterMethod 
	public void afterMethod(){ //RUN AFTER each test
		System.out.println("@AfterMethod RUN AFTER each test");
	}
	
	@AfterTest
	public void afterTest(){ //RUN AFTER each testng.xml - test
		System.out.println("@AfterTest");
		System.out.println("----------");
	}
	

}
*/