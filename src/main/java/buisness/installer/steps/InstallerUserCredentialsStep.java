package buisness.installer.steps;

import java.util.concurrent.TimeUnit;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

public class InstallerUserCredentialsStep extends InstallerAbstractStep{
	
	InstallerUserCredentialsStep() {
		super("Serial Number");
		// TODO Auto-generated constructor stub
	}

	private String userFieldId = "2702";
	private String passFieldId = "2703";
	private String repassFieldId = "2704";
	
	private String nextButtonID = "Button1"; 
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText, 250); //TODO: in typical it will take another 6 Min
	}
	
	@Override
	public void waitTo() {
		waitTo("user credentials");
	}
	
	
	public void setCredentials(String user, String pass){
		AutoItAPI.setControlText(installerTitle, userFieldId, user); // Set User
		TestManager.sleep(500);
		AutoItAPI.setControlText(installerTitle, passFieldId, pass); //Set Pass
		TestManager.sleep(500);
		AutoItAPI.setControlText(installerTitle, repassFieldId, pass); //Re'enter Password
		TestManager.sleep(500);
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}
	
	public void handlePopUp(){
		LogManager.info("'SysAid Enterprise' popup - click OK");
		AutoItAPI.waitWin("SysAid Enterprise","Account initialized successfully.",50);
		TestManager.sleep(2,TimeUnit.SECONDS);
		AutoItAPI.clickButton("SysAid Enterprise", "OK", "2"); //Click OK
	}
	

}
