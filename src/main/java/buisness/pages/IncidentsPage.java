package buisness.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import buisness.sr.IncidentSR;
import buisness.sr.ServiceRequestTables;

import com.core.base.LogManager;
import com.core.driver.BasePage;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class IncidentsPage extends DashboardPage {

	protected IncidentsPage(PageDriver driver) {
		super(driver);
	}
	
	
	
	public void verifyTable(){
		try {
			LogManager.debug("Verify Incident table");
			//add default Incident
			//table.addDefaultIncident();
			switchToFrame(Elements.Table_IFrame);
			//int expectedSize = IncidentSrTable.get().getCurrentTotoal();
			int expectedSize = ServiceRequestTables.get().incidents().size();
			verifyTableSize(Elements.Table_rows, expectedSize);
			//pass on each one and validate:
			
			List<WebElement> rows = getElements(Elements.Table_rows.getBy());
			
				
			//List<IncidentSR> list = IncidentSrTable.get().getList();
			List<IncidentSR> list = ServiceRequestTables.get().incidents();
			for (IncidentSR incident : list) {
				String id = incident.getID();
				for (WebElement row : rows) {
					String xpath = "//td[2]/div[text()='"+id+"']";
					List<WebElement> idCol =  row.findElements(By.xpath(xpath));
					if(idCol != null && idCol.size() == 1){
						investigateRowContent(idCol.get(0),incident);
						rows.remove(row);
						break;
					}else
						LogManager.error("Failed to find row for incident id:" + id);
				}
			}
			
			//return to default
			switchToMainWin();
		} catch (Exception e) {
			LogManager.error("Verify Incident table - Error :" + e.getMessage());
		}
	}
	
	
	
	private void investigateRowContent(WebElement webElement, IncidentSR incident) {
		LogManager.debug("Verify Incidnet content for incidnet id: " + incident.getID());
		try{
			boolean pass= true;
			WebElement td = webElement.findElement(By.xpath("./.."));
			String cat = td.findElement(By.xpath("../td[4]")).getText();
			pass = pass & LogManager.assertSoft(cat.equals(incident.getCategory().toString()), String.format("Verify Incident - Category: Expected= %s , Actual= %s",incident.getCategory().toString(),cat));
			String sub_cat = td.findElement(By.xpath("../td[5]")).getText();
			pass = pass & LogManager.assertSoft(sub_cat.equals(incident.getSub_category()), String.format("Verify Incident - Sub Category: Expected= %s , Actual= %s",incident.getSub_category().toString(),sub_cat));
			String title = td.findElement(By.xpath("../td[6]")).getText();
			pass = pass & LogManager.assertSoft(title.equals(incident.getTitle()), String.format("Verify Incident - Title: Expected= %s , Actual= %s",incident.getTitle().toString(),title));
			String status = td.findElement(By.xpath("../td[7]")).getText();
			pass = pass & LogManager.assertSoft(status.equals(incident.getStatus().toString()), String.format("Verify Incident - Status: Expected= %s , Actual= %s",incident.getStatus().toString(),status));
			String reqUser = td.findElement(By.xpath("../td[8]")).getText();
	
			if(incident.getID().equals("6"))
				;	//workaround for default incident , no need to verify req_user , it's empty
			else
				pass = pass & LogManager.assertSoft(reqUser.equals("sysaid"), String.format("Verify Incident - RequestUser: Expected= %s , Actual= %s","sysaid",reqUser));
			
			String priority = td.findElement(By.xpath("../td[10]")).getText();
			pass = pass & LogManager.assertSoft(priority.equals(incident.getPriority().toString()), String.format("Verify Incident - Priority: Expected= %s , Actual= %s",incident.getPriority().toString(),priority));

			LogManager.verify(pass, "Verify Incidnet Content With ID = " + incident.getID());
		}catch(Exception e){
			LogManager.error("Verify Incidnet content - Error: " + e.getMessage());
		}
		
	}



	/*public void verifyTable(IncidentTable table){
		try {
			LogManager.debug("Verify Incident table");
			switchToFrame(Elements.Table_IFrame);
			int expectedSize = table.total();
			verifyTableSize(Elements.Table_rows, expectedSize);
			//pass on each one and validate:
			List<Incident> list = table.getList();
			List<WebElement> currenRows = getElements(By.cssSelector("#t > tbody"));
			currenRows.remove(0);//this is the title row
			for (Incident incident : list) {
				String id = incident.getID();
				//find the row:
				//WebElement row = findRowAccordingToID(currenRows,"td > div:contains(%s)", id);
				WebElement row = findRowAccordingToID(currenRows,"//td/div[text()=%s]", id);
				//List<WebElement> found = currenRows.get(0).findElements(By.cssSelector(String.format("td: > div:contains(%s)",id)));
				if(row == null)
					LogManager.error("Failed to find Incident with ID= " +id);
				else{
					//verify row content
					//category
					List<WebElement> category = row.findElements(By.cssSelector("td:nth-child(4)"));
					if(category == null || category.size() == 0)
						LogManager.error("Failed to get category");
					String cat = category.get(0).getText();
					System.out.println(cat);
				}
				
				
			}	
			//return to default
			switchToMainWin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private WebElement findRowAccordingToID(List<WebElement> currenRows , String rowSelector, String id){
		
		
		for (WebElement webElement : currenRows) {
			List<WebElement> found = webElement.findElements(By.xpath(String.format(rowSelector,id)));
			if(found != null && found.size() == 1)
				return webElement;
				//return found.get(0);
		}
		
		return null;
	}
	
	
	
	

	private enum Elements implements PageElement {
		Table_IFrame(By.id("contentFrame")),
		Table_rows(By.cssSelector("#t tbody tr[sr_type='1']"))

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
