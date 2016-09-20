package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

public class InstallerLicenseAgreementStep extends InstallerAbstractStep {
	
	InstallerLicenseAgreementStep() {
		super("license agreement");
		// TODO Auto-generated constructor stub
	}

	private String acceptCheckBoxId = "1000";
	private String nextButtonID = "1";
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}
	
	@Override
	public void waitTo() {
		waitTo("license agreement");
	}
	
	
	public void acceptAgreement(){
		AutoItAPI.check(installerTitle, acceptCheckBoxId);
		TestManager.sleep(1000);
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText , nextButtonID);
	}

}
