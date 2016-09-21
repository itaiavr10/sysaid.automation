package buisness.installer.steps;

import buisness.modules.SysAid;
import buisness.modules.SysAid.InstallType;

import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

public class InstallerSetupTypeStep extends InstallerAbstractStep {

	private InstallType installType = InstallType.TYPICAL;

	InstallerSetupTypeStep() {
		super("Typical");
		// TODO Auto-generated constructor stub
	}

	private String typicalOptionID = "504";
	private String customizedOptionID = "503";
	private String nextButtonID = "Button3";

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	/**
	 * Wait for page to be ready
	 */
	public void waitTo() {
		waitTo("Setup Type");
	}

	public void selectType(InstallType type) {
		switch (type) {
		case TYPICAL:
			AutoItAPI.check(installerTitle, typicalOptionID);
			break;
		case CUSTOMIZED:
			AutoItAPI.check(installerTitle, customizedOptionID);
			installType = InstallType.CUSTOMIZED;
			break;
		}
		TestManager.sleep(1000);

	}

	public void clickNext() {
		SysAid.setInstallType(installType);
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}

}
