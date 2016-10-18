package buisness.pages;

import com.core.base.LogManager;
import com.core.driver.PageDriver;
import com.core.utils.SystemUtils;

public class Admin {
	
	static Admin admin;
	private PageDriver driver;
	private String URL =null;
	
	
	private LoginPage login = null;
	private DashboardPage dashboard = null;
	private IncidentsPage incidents = null;

	
	private Admin(){
	}
	
	public static Admin get(){
		if(admin == null)
			admin = new Admin();
		return admin;
	}

	public void launch() {
		LogManager.info("Launch Admin");
		driver = new PageDriver();
		//URL = "http://10.14.1.103:8080"; // TODO : Current ip
		URL = String.format("http://%s:8080",SystemUtils.OS.getCurrentIP());
		driver.navigate(URL);
	}
	
	public void teardown(){
		LogManager.debug("Admin - tear down");
		if(driver != null)
			driver.get().quit();
	}


	public IncidentsPage incidents() {
		if(incidents == null)
			incidents = new IncidentsPage(driver);
		return incidents;
	}


	public LoginPage login() {
		if(login == null)
			login = new LoginPage(driver);
		return login;
	}





	public DashboardPage dashboard() {
		if(dashboard == null)
			dashboard = new DashboardPage(driver);
		return dashboard;
	}
	
	
	

}
