package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

/**
 * Patch Management Data - Destination
 * @author itai.avrahami
 *
 */
public class InstallerRepositoryDestinationStep  extends InstallerAbstractStep{

	private String nextButtonID = "1";
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin("InstallShield Wizard" , "Patch Management repository folder");
		
	}
	
	@Override
	public void waitTo() {
		waitTo("repository destination");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton("InstallShield Wizard", "Setup will install SysAid Server in the following folder", nextButtonID);
	}

	

}
