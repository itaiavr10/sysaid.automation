package buisness.installer.steps;

import buisness.modules.SysAid.InstallType;

public abstract class InstallerAbstractStep {
	
	protected InstallType installType = InstallType.TYPICAL;
	
	protected String installerTitle = "InstallShield Wizard";
	protected String visibleText = ""; // visible text of current page
	
	
	InstallerAbstractStep(String visibleText){
		this.visibleText = visibleText;
	}
	
	public abstract void waitTo(String logInfo);
	
	public abstract void waitTo();

}
