package buisness.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import buisness.tools.usermanagement.Company;

import com.core.base.LogManager;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class CompaniesPage  extends DashboardPage{
	
	protected CompaniesPage(PageDriver driver) {
		super(driver);
	}
	
	
	
	public void verifyTable(){
		try {
			LogManager.debug("Verify Company table");
			switchToFrame(Elements.Table_IFrame);
			
			verifyTableSize(Elements.Table_rows,  Company.getTotal());
			//pass on each one and validate:
			
			List<WebElement> rows = getElements(Elements.Table_rows.getBy());
			
			
			List<Company> list = Company.getAll();
			for (Company company : list) {
				String name = company.getName();
				for (WebElement row : rows) {
					String xpath = "//td[2]/div[text()='"+name+"']";
					List<WebElement> idCol =  row.findElements(By.xpath(xpath));
					if(idCol != null && idCol.size() == 1){
						investigateRowContent(idCol.get(0),company);
						rows.remove(row);
						break;
					}else
						LogManager.error("Failed to find row for Company name:" + name);
				}
			}
			
			//return to default
			switchToMainWin();
		} catch (Exception e) {
			LogManager.error("Verify Company table - Error :" + e.getMessage());
		}
	}
	
	
	
	private void investigateRowContent(WebElement webElement, Company company) {
		LogManager.debug("Verify Company content for company: " + company.getName());
		try{
			boolean pass= true;
			WebElement td = webElement.findElement(By.xpath("./.."));
			String address = td.findElement(By.xpath("../td[3]")).getText();
			pass = pass & LogManager.assertSoft(address.equals(company.getAddress()), String.format("Verify Company - Address: Expected= %s , Actual= %s",company.getAddress(),address));
			String city = td.findElement(By.xpath("../td[5]")).getText();
			pass = pass & LogManager.assertSoft(city.equals(company.getCity()), String.format("Verify Company - City: Expected= %s , Actual= %s",company.getCity(),city));
			String zip = td.findElement(By.xpath("../td[7]")).getText();
			pass = pass & LogManager.assertSoft(zip.equals(company.getZip()), String.format("Verify Company - Zip: Expected= %s , Actual= %s",company.getZip(),zip));
			String country = td.findElement(By.xpath("../td[8]")).getText();
			pass = pass & LogManager.assertSoft(country.equals(company.getCountry()), String.format("Verify Company - Country: Expected= %s , Actual= %s",company.getCountry(),country));
			String phone = td.findElement(By.xpath("../td[9]")).getText();
			pass = pass & LogManager.assertSoft(phone.equals(company.getPhone()), String.format("Verify Company - Phone: Expected= %s , Actual= %s",company.getPhone(),phone));
			String fax = td.findElement(By.xpath("../td[11]")).getText();
			pass = pass & LogManager.assertSoft(fax.equals(company.getFax()), String.format("Verify Company - Fax: Expected= %s , Actual= %s",company.getFax(),fax));

			LogManager.verify(pass, "Verify Company content with Name = " + company.getName());
		}catch(Exception e){
			LogManager.error("Verify Company content - Error: " + e.getMessage());
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
