package buisness;

import java.util.List;

import utils.SystemUtils;

public class InstallServer {
	
	
	
	
	public static void validateInstallation(){
		//wait for process to finish installstion
		SystemUtils.waitForProcessStop("SA.exe", 40 * 1000 , 3000);
		//validate SA.exe is finished to run
		SystemUtils.validateProcess("SA.exe", false); //TODO : Should be an enum
		//validate SA Process start to run
		SystemUtils.validateProcess("Wrapper.exe", true); //TODO : Should be an enum
		SystemUtils.validateProcess("SysAidSM.exe", true); //TODO : Should be an enum
		SystemUtils.validateProcess("SysAidWorker.exe", true); //TODO : Should be an enum
	
		//validate SysAidAgent & SysAidServer services are running
		SystemUtils.validateService("SysAid Agent", true); //TODO : Should be an enum
		SystemUtils.validateService("SysAid Server", true); //TODO : Should be an enum
		//validate icons on desktop:
		SystemUtils.validateFileExist("SysAid Login.lnk", SystemUtils.getPublicDesktopPath(), true); //TODO : Should be an enum
		SystemUtils.validateFileExist("SysAid.lnk", SystemUtils.getPublicDesktopPath(), true); //TODO : Should be an enum
		
		validateSysAidFiles();
	}
	
	
	public static void validateSysAidFiles(){
		List<String> fileList = SysAid.getFiles();
		for (String file : fileList) {
			SystemUtils.validateFileExist(file, true);
		}
	}

}
