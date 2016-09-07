package buisness.modules;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import buisness.db.DBInstaller;
import buisness.modules.SysAid.InstallType;

import com.core.base.LogManager;
import com.core.utils.SystemUtils;
import com.core.utils.XmlUtils;

public class SysAidServer {
	
	private static String server_ver = "unknown";
	private static String server_build = "unknown";
	public static String exeName;
	public static String exePath;
	
	
	private static List<String> filesList;
	private static String serverPath = "C:\\Program Files\\SysAidServer";
	private static String tomcatPath = "C:\\Program Files\\SysAidServer\\tomcat";
	private static String webInfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF";
	private static String log4jPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\log4j.properties";
	private static String accountConfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\conf\\accountConf.xml";
	
	static{
		initFiles();
	}
	
	public static void initInstaller(){
		boolean getDefaultExe = false;
		server_ver = System.getProperty("version");
		server_build = System.getProperty("build");
		if(server_ver == null || server_ver.equals("unknown")){
			LogManager.warn("sysaid version didn't defined via system properties! install default exe : SysAidServer64_default.exe");
			getDefaultExe = true;
		}
		else if(server_build == null || server_build.equals("unknown")){
			LogManager.warn("sysaid build version didn't defined via system properties! install default exe : SysAidServer64_default.exe ");
			getDefaultExe = true;
		}
		
		//TODO : should check OS bit
		if(getDefaultExe)
			exeName = "SysAidServer64_default.exe";
		else{
			LogManager.info(String.format("SysAid Version:%s , Build: %s",server_ver,server_build));
			exeName = String.format("C:\\SA\\SysAidServer64_%s_b%s.exe",server_ver.replace(".", "_"),server_build);
		}
		exePath = "C:\\SA\\" + exeName;
		SystemUtils.Files.verifyExist(exePath, true,2000);
		
	}

	private static void initFiles() {
		filesList = new ArrayList<String>();
		//add
		filesList.add(serverPath);
		filesList.add(tomcatPath);
		filesList.add(webInfPath);
		
		
	}
	
	
	/**
	 *  Test #242
	 */
	public static void verifyInstallation(){
		LogManager.bold("SysAid Server : Verify Installation");
		changeLog4J();
		verifyServices();
		verifyProcesses();
		verifyDesktopIcon();
		verifyDirectories();
		//TODO: step 4 Verify Browser opened with 2 tabs
		//TODO: step 6 Verify configuration files in C:\Program Files\SysAidServer\root\WEB-INF\conf 
		
		verifyConfigurationFiles();
		//TODO: step 7 Logs (sysaid.log) C:\Program Files\SysAidServer\root\WEB-INF\logs
		//TODO: step 8 Logs (upgradeToNewReports.log)
		//TODO: step 9 Logs Logs (q-scheduler.log)
	}
	
	//Verify configuration files in C:\Program Files\SysAidServer\root\WEB-INF\conf 
	public static void verifyConfigurationFiles() {
		LogManager.debug("Verify configurations file..");
		LogManager.debug("verify sysaid.ver file..");
		// verify sysaid.ver file:
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("C:\\Program Files\\SysAidServer\\root\\WEB-INF\\conf\\sysaid.ver"));
			String rdsVer = properties.getProperty("rdsVersion");
			String ver = properties.getProperty("version");
			String clientVersion = properties.getProperty("clientVersion");
			LogManager.assertSoft(rdsVer.equals(server_ver), String.format("Incorrect Server Version! Expected : %s , Actual: %s" ,server_ver , rdsVer ));
			LogManager.assertSoft(ver.equals(server_ver), String.format("Incorrect Server Version! Expected : %s , Actual: %s" ,server_ver , ver ));
			LogManager.assertSoft(clientVersion.equals(server_ver), String.format("Incorrect Server Version! Expected : %s , Actual: %s" ,server_ver , clientVersion ));
		
		}catch (Exception e) {
			LogManager.error("Verify configurations file - Error : " + e.getMessage());
		}
		
		//verify serverconf.xml file exist.. // TODO: Should check content instead on DBInstaller class! (Discussion with Alex)
		SystemUtils.Files.verifyExist(DBInstaller.serverConfPath, true);
		
		// Verify accountConf file exist , add TODO TBD (content)
		SystemUtils.Files.verifyExist(accountConfPath, true);
		
	}

	/**
	 * Test# 251 , #252
	 */
	public static void verifyDB(){
		LogManager.bold("Verification - MSSQL embedded");
		if(SysAid.type == InstallType.TYPICAL)
			DBInstaller.verifyMsSqlEmbedded();
		DBInstaller.verifyTablesCount();
		DBInstaller.verifyTableContents();
	}
	
	
	
	public static void verifyProcesses(){ 
		SystemUtils.Processes.verify("Wrapper.exe", true); //TODO : Should be an enum  ?Is a RDS Process?
	}
	
	public static void verifyServices(){
		SystemUtils.Services.verify("SysAid Server", true); //TODO : Should be an enum
	}
	
	public static void verifyDesktopIcon(){
		SystemUtils.Files.verifyExist("SysAid Login.lnk", SystemUtils.Files.getPublicDesktopPath(), true); //TODO : Should be an enum
	}
	
	public static void verifyDirectories(){
		for (String file : filesList) {
			SystemUtils.Files.verifyExist(file, true);
		}
	}
	
	public static void changeLog4J(){
		SystemUtils.Files.replaceLine(log4jPath, "log4j.logger.com.ilient=INFO, sysaidLogFile", "log4j.logger.com.ilient=DEBUG, sysaidLogFile");
	}
	
	

}
