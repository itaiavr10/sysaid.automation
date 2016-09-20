package buisness.installer.steps;

import buisness.modules.SysAid.InstallType;

public abstract class InstallerAbstractStep {
	
	protected InstallType installType = InstallType.TYPICAL;
	
	
	public abstract void waitTo(String logInfo);
	
	public abstract void waitTo();

}
