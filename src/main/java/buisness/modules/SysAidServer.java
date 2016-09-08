package buisness.modules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Pattern;

import buisness.db.DBInstaller;
import buisness.modules.SysAid.InstallType;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;
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
	private static String sysAidLogsPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\logs\\sysaid.log";
	private static String upgradeToNewReportsPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\logs\\upgradeToNewReports.log";
	private static String qschedulerLogPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\logs\\q-scheduler.log";
	
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
		verifyLoginBrowser();
		verifyDirectories();
		verifyConfigurationFiles();
		verifySysAidLog();
		verifyupgradeToNewReports();
		verifyQschedulerLog();
	}
	
	//Verify Browser opened with 2 tabs
	public static void verifyLoginBrowser(){
		LogManager.debug("Verify Browser opened with 2 tabs ..");
		SystemUtils.Processes.verify("chrome.exe", true);
		AutoItAPI.activateWindow("SysAid Help Tree","");
		AutoItAPI.verifyWinActivate("SysAid Help Tree", true);
		TestManager.sleep(1000);
		SystemUtils.Keyboard.switchBrowserTab();
		AutoItAPI.verifyWinActivate("SysAid Help Desk Software",true);
		
		
	}
	 
	private static void verifyQschedulerLog() {
		SystemUtils.Files.scanByLine(qschedulerLogPath, "Error", "Exception");
	}

	private static void verifyupgradeToNewReports() {
		LogManager.debug("Verify upgradeToNewReports.log ..");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(upgradeToNewReportsPath)));
			String line;
			Stack<String> stack = new Stack<String>();
			while ((line = br.readLine()) != null) {
				LogManager.debug("Line = " + line);
				if(line.contains("ERROR")){
					LogManager.assertSoft(false, "upgradeToNewReports.log file contains error : " + line);
				}else if(line.contains("Start")){
					stack.push("Start");
				}else if(line.contains("End")){
					if(stack.isEmpty()){
						LogManager.assertSoft(false, "Incorrect Start -End Order. see debug log");
						break;
					}else{
						stack.pop();
					}
				}
			}
			// after reading all lines , stack should be empty
			LogManager.verify(stack.isEmpty(), "Verify upgradeToNewReports.log");
		} catch (Exception e) {
			LogManager.error("Verify upgradeToNewReports.log - Error : " + e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
			}
		}
	}

	// verify sysaid.log file
	public static void verifySysAidLog() {
		LogManager.debug("Verify sysaid.log ..");
		boolean pass = true;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(sysAidLogsPath)));
			String line;
			Pattern p = Pattern.compile(".*]\\s*ERROR.*");
			while ((line = br.readLine()) != null) {
				if(p.matcher(line).matches()){
					// search exception line
					while ((line = br.readLine()) != null){
						if(line.contains("Exception"))
							break;
					}
					// on line with the exception details
					//TODO: Check Known Exception
					LogManager.error("Found Error on sysaid.log file , Exception: " + line);
					pass = false;
				}
			}
		} catch (Exception e) {
			LogManager.error("Verify sysaid.log - Error : " + e.getMessage());
		} finally {
			LogManager.verify(pass, "Verify sysaid.log");
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
			}
		}
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
