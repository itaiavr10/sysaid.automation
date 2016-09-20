package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerCompletedStep extends InstallerAbstractStep{
	
	private String finishButtonId = "Button1"; 
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin("InstallShield Wizard" , "The InstallShield Wizard has successfully installed the SysAid Server",30);
	}
	
	@Override
	public void waitTo() {
		waitTo("completed step");
	}
	
	public void clickFinish(){
		AutoItAPI.clickButton("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server",finishButtonId);
	}

}
