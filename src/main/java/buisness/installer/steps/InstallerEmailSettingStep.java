package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerEmailSettingStep extends InstallerAbstractStep{
	
	private String skipButtonId ="Button3";
	
	InstallerEmailSettingStep() {
		super("Mail server");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	@Override
	public void waitTo() {
		waitTo("Email settings");
	}
	
	public void clickSkip(){
		AutoItAPI.clickButton(installerTitle, "", skipButtonId);
	}

}
