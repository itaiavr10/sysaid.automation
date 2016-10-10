package buisness.pages;

import com.core.base.LogManager;
import com.core.driver.PageDriver;
import com.core.utils.SystemUtils;

public class Admin {
	
	
	private PageDriver driver;
	private String URL =null;
	
	
	private LoginPage login = null;
	private DashboardPage dashboard = null;
	private IncidentsPage incidents = null;


	public void launch() {
		LogManager.info("Launch Admin");
		driver = new PageDriver();
		URL = "http://10.14.1.103:8080"; // TODO : Current ip
		//URL = String.format("http://%s:8080",SystemUtils.OS.getCurrentIP());
		driver.navigate(URL);
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
