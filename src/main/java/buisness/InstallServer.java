package buisness;

import java.util.List;

import utils.SystemUtils;
import utils.XmlUtils;

public class InstallServer {
	
	
	
	
	public static void validateInstallation(){
		
		SystemUtils.Files.replaceLine("C:\\log4j.properties", "log4j.logger.com.ilient=INFO, sysaidLogFile", "log4j.logger.com.ilient=DEBUG, sysaidLogFile");
		
		//wait for process to finish installstion
		SystemUtils.Processes.waitForProcessStop("SA.exe", 60 * 1000 , 3000);
		//validate SA.exe is finished to run
		SystemUtils.Processes.validate("SA.exe", false); //TODO : Should be an enum
		//validate SA Process start to run
		SystemUtils.Processes.validate("Wrapper.exe", true); //TODO : Should be an enum
		SystemUtils.Processes.validate("SysAidSM.exe", true); //TODO : Should be an enum
		SystemUtils.Processes.validate("SysAidWorker.exe", true); //TODO : Should be an enum
		
		validateSysAidFiles();
		
		//validate SysAidAgent & SysAidServer services are running
		SystemUtils.Services.validate("SysAid Agent", true); //TODO : Should be an enum
		SystemUtils.Services.validate("SysAid Server", true); //TODO : Should be an enum
		SystemUtils.Services.validate("SQL Server (SYSAIDMSSQL)", true); //TODO : Should be an enum
		
		//validate icons on desktop:
		SystemUtils.Files.validateExist("SysAid Login.lnk", SystemUtils.Files.getPublicDesktopPath(), true); //TODO : Should be an enum
		SystemUtils.Files.validateExist("SysAid.lnk", SystemUtils.Files.getPublicDesktopPath(), true); //TODO : Should be an enum
				
				
		// verify Agent send inventory to server - successfully
		XmlUtils.validteNodeValue(SysAid.Agent.configFilePath, "FirstTime", "N", 180000, 3000);
		
		//log verification
		SystemUtils.Files.scan(SysAid.Agent.logFilePath, "Error","Exception");
		
		
	}
	
	
	public static void validateSysAidFiles(){
		List<String> fileList = SysAid.getFiles();
		for (String file : fileList) {
			SystemUtils.Files.validateExist(file, true);
		}
	}

}
