package buisness.installer.steps;

import java.util.concurrent.TimeUnit;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

public class InstallerLicenseFileStep  extends InstallerAbstractStep{
	
	InstallerLicenseFileStep() {
		super("License File");
		// TODO Auto-generated constructor stub
	}


	private String browseButtonId = "2005";
	private String openButtonId = "Button1";
	private String fileNameTextBoxID = "1148";
	private String validLicenseMsgID = "2007";
	private String inValidLicenseMsgID = "2008";
	private String nextButtonID = "Button2"; 
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText , 360);
		
	}
	
	@Override
	public void waitTo() {
		waitTo("license file");
	}
	
	public void selectLicenseFile(String filePath){
		//click Browser button
		AutoItAPI.clickButton(installerTitle, visibleText, browseButtonId);
		//set activation & press Open button
		AutoItAPI.waitWin("Select the Activation File");
		TestManager.sleep(1500);
		AutoItAPI.setControlText("Select the Activation File", fileNameTextBoxID, filePath);
		TestManager.sleep(5, TimeUnit.SECONDS);
		AutoItAPI.clickButton("Select the Activation File", "", openButtonId);
		TestManager.sleep(5,TimeUnit.SECONDS);
	}
	
	
	public void verifyLicneseMsg(boolean isValid){
		if(isValid)
			AutoItAPI.verifyVisibility(installerTitle, validLicenseMsgID, true);
		else
			AutoItAPI.verifyVisibility(installerTitle, inValidLicenseMsgID, true);
	}

	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
		//TODO: How much time to wait for
	}
}
