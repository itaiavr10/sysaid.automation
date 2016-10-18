package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

import buisness.modules.SysAid.InstallType;

public abstract class InstallerAbstractStep {
	
	protected String installerTitle = "InstallShield Wizard";
	protected String visibleText = ""; // visible text of current page
	private Integer waifForPage = 60;
	
	
	InstallerAbstractStep(String visibleText){
		this.visibleText = visibleText;
	}
	
	//public abstract void waitTo(String logInfo);
	public void waitTo(String logInfo) {
		waitTo(logInfo,waifForPage);
	}
	
	public void waitTo(String logInfo ,Integer MaxTimeOut) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText,MaxTimeOut);
	}
	
	public abstract void waitTo();
	
	
	
	protected void click(String debugInfo , String controlID){
		
		
	}
	
	protected void setText(String debugInfo, String controlID , String text){
		
	}
	

}
