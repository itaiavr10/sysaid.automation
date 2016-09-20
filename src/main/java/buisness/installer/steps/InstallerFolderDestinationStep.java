package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerFolderDestinationStep extends InstallerAbstractStep{

	InstallerFolderDestinationStep() {
		super("Setup will install the SysAid Server in the following folder");
		// TODO Auto-generated constructor stub
	}

	private String nextButtonID = "1"; 
	
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);

	}
	
	@Override
	public void waitTo() {
		waitTo("folder destination");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}

	
	
	

}
