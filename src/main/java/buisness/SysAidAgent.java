package buisness;

import java.util.ArrayList;
import java.util.List;

import utils.ActionWrapper;
import utils.StringRef;
import utils.SystemUtils;
import utils.Utils;
import utils.XmlUtils;
import base.LogManager;

public class SysAidAgent {
	
	private static List<String> filesList;
	private static String agentPath = "C:\\Program Files\\SysAid";
	private static String sysaidAgentLogPath = "C:\\Program Files\\SysAid\\logs";
	
	
	public static String configFilePath = "C:\\Program Files\\SysAid\\Configuration\\AgentConfigurationFile.xml"; 
	public static String logFilePath = "C:\\Program Files\\SysAid\\logs\\SysAidAgentLog.txt"; 
	
	static{
		init();
	}

	private static void init() {
		filesList = new ArrayList<String>();
		//add
		filesList.add(agentPath);
		filesList.add(sysaidAgentLogPath);
	}
	
	public static void verifyInstallation(){
		LogManager.bold("SysAid Agent : Verify Installation");
		verifyProcesses();
		verifyServices();
		verifyDesktopIcon();
		verifyDirectories();
		verifyInventorySent();
		logScan();
		
	}
	
	public static void verifyProcesses(){
		SystemUtils.Processes.verify("SysAidSM.exe", true); //TODO : Should be an enum
		SystemUtils.Processes.verify("SysAidWorker.exe", true); //TODO : Should be an enum
	}
	
	public static void verifyServices(){
		SystemUtils.Services.verify("SysAid Agent", true); //TODO : Should be an enum
	}
	
	public static void verifyDesktopIcon(){
		SystemUtils.Files.verifyExist("SysAid.lnk", SystemUtils.Files.getPublicDesktopPath(), true); //TODO : Should be an enum
	}
	
	public static void verifyDirectories(){
		for (String file : filesList) {
			SystemUtils.Files.verifyExist(file, true);
		}
	}
	
	/**
	 * verify Agent send inventory to server - successfully
	 */
	public static void verifyInventorySent(){
		final String expectedValue = "N";
		final StringRef actualValue = new StringRef("");
		boolean ispass = Utils.tryUntil(new ActionWrapper("Verify Inventory Sent to Server.." , 180000 , 3000) {
			@Override
			public boolean invoke() throws Exception {
				actualValue.setValue(XmlUtils.getNodeValue(configFilePath,"FirstTime"));
				return expectedValue.equals(actualValue.value);
			}
		});
		LogManager.verify(ispass,"Verify Agent send inventory to server");
	}
	
	//log verification
	public static void logScan(){
		SystemUtils.Files.scan(logFilePath, "Error", "Exception");
	}
	
	

}
