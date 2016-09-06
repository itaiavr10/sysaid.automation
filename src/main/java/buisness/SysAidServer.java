package buisness;

import java.util.ArrayList;
import java.util.List;

import base.LogManager;
import utils.SystemUtils;

public class SysAidServer {
	
	private static String version = "unknown";
	private static String build = "unknown";
	public static String exeName;
	public static String exePath;
	
	private static List<String> filesList;
	private static String serverPath = "C:\\Program Files\\SysAidServer";
	private static String msSqlPath = "C:\\Program Files\\SysAidMsSql";
	private static String tomcatPath = "C:\\Program Files\\SysAidServer\\tomcat";
	private static String webInfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF";
	
	private static String log4jPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\log4j.properties"; 
	
	static{
		initFiles();
	}
	
	public static void initInstaller(){
		boolean getDefaultExe = false;
		version = System.getProperty("version");
		build = System.getProperty("build");
		if(version == null || version.equals("unknown")){
			LogManager.warn("sysaid version didn't defined via system properties! install default exe : SysAidServer64_default.exe");
			getDefaultExe = true;
		}
		else if(build == null || build.equals("unknown")){
			LogManager.warn("sysaid build version didn't defined via system properties! install default exe : SysAidServer64_default.exe ");
			getDefaultExe = true;
		}
		
		//TODO : should check OS bit
		if(getDefaultExe)
			exeName = "SysAidServer64_default.exe";
		else{
			LogManager.info(String.format("SysAid Version:%s , Build: %s",version,build));
			exeName = String.format("C:\\SA\\SysAidServer64_%s_b%s.exe",version.replace(".", "_"),build);
		}
		exePath = "C:\\SA\\" + exeName;
		SystemUtils.Files.validateExist(exePath, true,2000);
		
	}

	private static void initFiles() {
		filesList = new ArrayList<String>();
		//add
		filesList.add(serverPath);
		filesList.add(msSqlPath);
		filesList.add(tomcatPath);
		filesList.add(webInfPath);
		
		
	}
	
	public static void verifyInstallation(){
		LogManager.bold("SysAid Server : Validate Installation");
		changeLog4J();
		validateProcesses();
		validateServices();
		validateDesktopIcon();
		validateDirectories();
	}
	
	public static void validateProcesses(){ 
		SystemUtils.Processes.validate("Wrapper.exe", true); //TODO : Should be an enum  ?Is a RDS Process?
	}
	
	public static void validateServices(){
		SystemUtils.Services.validate("SysAid Server", true); //TODO : Should be an enum
		SystemUtils.Services.validate("SQL Server (SYSAIDMSSQL)", true); //TODO : Should be an enum
	}
	
	public static void validateDesktopIcon(){
		SystemUtils.Files.validateExist("SysAid Login.lnk", SystemUtils.Files.getPublicDesktopPath(), true); //TODO : Should be an enum
	}
	
	public static void validateDirectories(){
		for (String file : filesList) {
			SystemUtils.Files.validateExist(file, true);
		}
	}
	
	public static void changeLog4J(){
		SystemUtils.Files.replaceLine(log4jPath, "log4j.logger.com.ilient=INFO, sysaidLogFile", "log4j.logger.com.ilient=DEBUG, sysaidLogFile");
	}
	
	

}
