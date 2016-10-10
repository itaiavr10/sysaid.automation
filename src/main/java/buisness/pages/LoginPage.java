package buisness.pages;

import org.openqa.selenium.By;

import com.core.base.LogManager;
import com.core.driver.BasePage;
import com.core.driver.PageDriver;
import com.core.driver.PageElement;

public class LoginPage extends BasePage{
	
	
	public LoginPage(PageDriver driver) {
		super(driver);
	}


	public void loginWith(String user, String pass){
		setUser(user);
		setPassword(pass);
		clickLogin();
	}
	

	public void setUser(String user){
		LogManager.info("Set User: " + user);
		setValue(Elements.UserNameFiled, user);
	}
	
	public void setPassword(String password){
		LogManager.info("Set Password: " + password);
		setValue(Elements.PasswordFiled, password);
	}
	
	public void clickLogin(){
		LogManager.info("Click Login");
		click(Elements.LoginButton);
	}
	
	
	
	
	private enum Elements implements PageElement{
		 UserNameFiled(By.cssSelector("input[name='userName']")),
		 PasswordFiled(By.cssSelector("input[name='password']")),
		 LoginButton(By.className("ButtonLabel"))
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
