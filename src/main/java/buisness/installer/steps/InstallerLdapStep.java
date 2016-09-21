package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerLdapStep extends InstallerAbstractStep{
	
	private String nextButtonId = "Button1";
	private String skipButtonId = "Button4";
	
	InstallerLdapStep() {
		super("Server Type");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	@Override
	public void waitTo() {
		waitTo("LDAP Integration");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, "", nextButtonId);
	}
	
	public void clickSkip(){
		AutoItAPI.clickButton(installerTitle, "", skipButtonId);
	}

}
