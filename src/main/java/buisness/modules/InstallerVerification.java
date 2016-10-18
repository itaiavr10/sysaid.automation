package buisness.modules;

import com.core.utils.SystemUtils;

public class InstallerVerification {

	public void verify() {
		
		//wait for process to finish installation
		SystemUtils.Processes.waitForProcessStop(SysAidServer.exeName, 90 * 1000, 3000);

		SysAidServer.verifyInstallation();
		//TODO : RDS Internal
		SysAidAgent.verifyInstallation();

		SysAidServer.verifyDB();

		SysAidLog.verifyLog();
	}

}
