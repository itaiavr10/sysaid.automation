package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerFolderDestinationStep extends InstallerAbstractStep{

	private String nextButtonID = "1"; 
	
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin("InstallShield Wizard" , "Setup will install the SysAid Server in the following folder");

	}
	
	@Override
	public void waitTo() {
		waitTo("folder destination");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton("InstallShield Wizard", "Setup will install the SysAid Server in the following folder", nextButtonID);
	}

	
	
	

}
