package buisness.installer.steps;

import com.core.utils.AutoItAPI;

public class InstallerLanguageStep extends InstallerAbstractStep {

	private String nextButtonId = "Button1";

	InstallerLanguageStep() {
		super("Language");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	@Override
	public void waitTo() {
		waitTo("Language setting");
	}

	public void clickNext() {
		AutoItAPI.clickButton(installerTitle, "", nextButtonId);
	}

}
