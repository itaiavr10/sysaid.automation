package buisness.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import buisness.sr.IncidentSR;
import buisness.sr.RequestSR;
import buisness.sr.ServiceRequestTables;

import com.core.base.LogManager;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class RequestsPage extends DashboardPage{//TODO duplicate code! need table abstraction!
	
	
	protected RequestsPage(PageDriver driver) {
		super(driver);
	}
	
	public void verifyTable(){
		try {
			LogManager.debug("Verify Request table");
			//add default Incident
			//table.addDefaultIncident();
			switchToFrame(Elements.Table_IFrame);
			//int expectedSize = IncidentSrTable.get().getCurrentTotoal();
			int expectedSize = ServiceRequestTables.get().requests().size();
			verifyTableSize(Elements.Table_rows, expectedSize);
			//pass on each one and validate:
			
			List<WebElement> rows = getElements(Elements.Table_rows.getBy());
			
				
			List<RequestSR> list = ServiceRequestTables.get().requests();
			for (RequestSR request : list) {
				String id = request.getID();
				for (WebElement row : rows) {
					String xpath = "//td[2]/div[text()='"+id+"']";
					List<WebElement> idCol =  row.findElements(By.xpath(xpath));
					if(idCol != null && idCol.size() == 1){
						investigateRowContent(idCol.get(0),request);
						rows.remove(row);
						break;
					}else
						LogManager.error("Failed to find row for request id:" + id);
				}
			}
			
			//return to default
			switchToMainWin();
		} catch (Exception e) {
			LogManager.error("Verify Request table - Error :" + e.getMessage());
		}
	}
	
	private void investigateRowContent(WebElement webElement, RequestSR request) {
		LogManager.debug("Verify Request content for request id: " + request.getID());
		try{
			boolean pass= true;
			WebElement td = webElement.findElement(By.xpath("./.."));
			String cat = td.findElement(By.xpath("../td[4]")).getText();
			pass = pass & LogManager.assertSoft(cat.equals(request.getCategory().toString()), String.format("Verify Request - Category: Expected= %s , Actual= %s",request.getCategory().toString(),cat));
			String sub_cat = td.findElement(By.xpath("../td[5]")).getText();
			pass = pass & LogManager.assertSoft(sub_cat.equals(request.getSub_category()), String.format("Verify Request - Sub Category: Expected= %s , Actual= %s",request.getSub_category().toString(),sub_cat));
			String title = td.findElement(By.xpath("../td[6]")).getText();
			pass = pass & LogManager.assertSoft(title.equals(request.getTitle()), String.format("Verify Request - Title: Expected= %s , Actual= %s",request.getTitle().toString(),title));
			String status = td.findElement(By.xpath("../td[7]")).getText();
			pass = pass & LogManager.assertSoft(status.equals(request.getStatus().toString()), String.format("Verify Request - Status: Expected= %s , Actual= %s",request.getStatus().toString(),status));
			String reqUser = td.findElement(By.xpath("../td[8]")).getText();
			pass = pass & LogManager.assertSoft(reqUser.equals("sysaid"), String.format("Verify Request - RequestUser: Expected= %s , Actual= %s","sysaid",reqUser));
			
			String priority = td.findElement(By.xpath("../td[10]")).getText();
			pass = pass & LogManager.assertSoft(priority.equals(request.getPriority().toString()), String.format("Verify Request - Priority: Expected= %s , Actual= %s",request.getPriority().toString(),priority));

			LogManager.verify(pass, "Verify Request Content With ID = " + request.getID());
		}catch(Exception e){
			LogManager.error("Verify Request content - Error: " + e.getMessage());
		}
		
	}
	
	
	
	
	private enum Elements implements PageElement {
		Table_IFrame(By.id("contentFrame")),
		Table_rows(By.cssSelector("#t tbody tr[sr_type='10']"))
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
