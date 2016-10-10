package buisness.pages;

import org.openqa.selenium.By;




import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.driver.BasePage;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class DashboardPage extends BasePage{
	
	
	public DashboardPage(PageDriver driver) {
		super(driver);
	}
	
	
	
	
	//public void goToHelpDesk(HelpDeskType helpDeskType){
	public void goToIncidentsTable(){ //go to incidents table
		LogManager.info("Go To Incidents table");
		clickTab(Tabs.ServiceDesk);
		//click on Incidents option
		click(Elements.Incidents_Option);
	}
	
	public void clickTab(Tabs tab){
		LogManager.info("Click on Tab: " + tab);
		click(tab);
		TestManager.sleep(500);
	}
	
	
	
	private enum Tabs implements PageElement{
		ServiceDesk(By.cssSelector(".main-items span.service-desk")),
		
		 ;
		   By bySelector;

		    public By getBy() {
		        return bySelector;
		    }


		    private Tabs(By selector) {
		        this.bySelector = selector;
		    }
	}
	
	
	private enum Elements implements PageElement{
		Incidents_Option(By.cssSelector(".menu_column_items  a[href*=Incidents]"))
		//Incidents_Option(By.cssSelector("span:contains('Incidents')"))
		
		 ;
		   By bySelector;

		    public By getBy() {
		        return bySelector;
		    }


		    private Elements(By selector) {
		        this.bySelector = selector;
		    }
	}

}
