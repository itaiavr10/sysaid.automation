package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerWelcomeStep extends InstallerAbstractStep {
	
	InstallerWelcomeStep() {
		super("");
	}

	private String nextButtonID = "1";
	
	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/
	
	@Override
	public void waitTo() {
		waitTo("welcome");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle,visibleText, nextButtonID);
	}

}
