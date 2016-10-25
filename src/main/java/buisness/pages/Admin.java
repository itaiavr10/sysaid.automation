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
	private RequestsPage requests = null;
	private CompaniesPage companies = null;
	private GroupsPage groups = null;

	
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
		//URL = "http://10.14.1.33:8080"; // TODO : Current ip
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
	
	public RequestsPage requests() {
		if(requests == null)
			requests = new RequestsPage(driver);
		return requests;
	}
	
	public CompaniesPage companies() {
		if(companies == null)
			companies = new CompaniesPage(driver);
		return companies;
	}
	
	public GroupsPage groups() {
		if(groups == null)
			groups = new GroupsPage(driver);
		return groups;
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
