package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerWelcomeStep extends InstallerAbstractStep {
	
	private String nextButtonID = "1";
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin("InstallShield Wizard" , "");
	}
	
	@Override
	public void waitTo() {
		waitTo("welcome");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton("InstallShield Wizard", "", nextButtonID);
	}

}
