package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerHttpPortStep extends InstallerAbstractStep{
	
	private String nextButtonId = "Button1";
	
	InstallerHttpPortStep() {
		super("Server HTTP Port");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	@Override
	public void waitTo() {
		waitTo("HTTP port");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, "", nextButtonId);
	}

}
