package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

import buisness.modules.SysAid.InstallType;

public abstract class InstallerAbstractStep {
	
	protected String installerTitle = "InstallShield Wizard";
	protected String visibleText = ""; // visible text of current page
	
	
	InstallerAbstractStep(String visibleText){
		this.visibleText = visibleText;
	}
	
	//public abstract void waitTo(String logInfo);
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}
	
	public abstract void waitTo();
	
	
	
	public void click(String debugInfo , String controlID){
		
		
	}
	
	public void setText(String debugInfo, String controlID , String text){
		
	}
	

}
