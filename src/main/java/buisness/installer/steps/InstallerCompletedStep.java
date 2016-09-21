package buisness.installer.steps;

import com.core.utils.AutoItAPI;

public class InstallerCompletedStep extends InstallerAbstractStep {

	InstallerCompletedStep() {
		super("The InstallShield Wizard has successfully installed the SysAid Server");
		// TODO Auto-generated constructor stub
	}

	private String finishButtonId = "Button1";

	/*	@Override
		public void waitTo(String logInfo) {
			LogManager.info("Step: " + logInfo);
			AutoItAPI.waitWin(installerTitle , visibleText, 30);
		}*/

	@Override
	public void waitTo() {
		waitTo("completed step");
	}

	public void clickFinish() {
		AutoItAPI.clickButton(installerTitle, visibleText, finishButtonId);
	}

}
