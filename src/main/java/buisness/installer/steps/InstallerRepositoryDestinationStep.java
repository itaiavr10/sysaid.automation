package buisness.installer.steps;

import com.core.utils.AutoItAPI;

/**
 * Patch Management Data - Destination
 * 
 * @author itai.avrahami
 *
 */
public class InstallerRepositoryDestinationStep extends InstallerAbstractStep {

	InstallerRepositoryDestinationStep() {
		super("Patch Management repository folder");
		// TODO Auto-generated constructor stub
	}

	private String nextButtonID = "1";

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
		
	}*/

	@Override
	public void waitTo() {
		waitTo("repository destination");
	}

	public void clickNext() {
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}

}
