package buisness.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;




import buisness.sr.IncidentSR;
import buisness.sr.ServiceRequestTables;
import buisness.tools.usermanagement.Group;

import com.core.base.LogManager;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class GroupsPage extends DashboardPage {

	protected GroupsPage(PageDriver driver) {
		super(driver);
	}
	
	
	
	public void verifyTable(){
		LogManager.debug("Verify Group table");
		try {
			switchToFrame(Elements.Table_IFrame);
			verifyTableSize(Elements.Table_rows, Group.getTotal());
			//pass on each one and validate:
			List<WebElement> rows = getElements(Elements.Table_rows.getBy());
			List<Group> list = Group.getAll();
			for (Group group : list) {
				for (WebElement row : rows) {
					String xpath = "//td[2]/div[text()='"+group.getName()+"']";
					List<WebElement> idCol =  row.findElements(By.xpath(xpath));
					if(idCol != null && idCol.size() == 1){
						investigateRowContent(idCol.get(0),group);
						rows.remove(row);
						break;
					}else
						LogManager.error("Failed to find row for Group name:" + group.getName());
				}
			}
			//return to default
			switchToMainWin();
		} catch (Exception e) {
			LogManager.error("Verify Incident table - Error :" + e.getMessage());
		}
	}
	
	
	
	private void investigateRowContent(WebElement webElement, Group group) { 
		LogManager.debug("Verify Group content for group name: " + group.getName());
		try{
			boolean pass= true;
			WebElement td = webElement.findElement(By.xpath("./.."));
			//Type
			String type = td.findElement(By.xpath("../td[3]")).getText();
			pass = pass & LogManager.assertSoft(type.equals(group.getType()), String.format("Verify Group - Type: Expected= %s , Actual= %s",group.getType(),type));
			//Support Level
			String supportLevel = td.findElement(By.xpath("../td[4]")).getText();
			pass = pass & LogManager.assertSoft(supportLevel.equals(group.getSupportLevel()), String.format("Verify Group - Support level: Expected= %s , Actual= %s",group.getSupportLevel(),supportLevel));

			LogManager.verify(pass, "Verify Group Content With name = " + group.getName());
		}catch(Exception e){
			LogManager.error("Verify Group content - Error: " + e.getMessage());
		}
		
	}
	
	private enum Elements implements PageElement {
		Table_IFrame(By.id("contentFrame")),
		Table_rows(By.cssSelector("#t tbody tr[onmouseout *='style.cursor']"))

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
