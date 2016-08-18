package buisness;

import java.util.ArrayList;
import java.util.List;

public class SysAid {
	
	//TODO : separate to server , agent
	
	private static List<String> filesList;
	private static String serverPath = "C:\\Program Files\\SysAidServer";
	private static String agentPath = "C:\\Program Files\\SysAid";
	private static String msSqlPath = "C:\\Program Files\\SysAidMsSql";
	private static String tomcatPath = "C:\\Program Files\\SysAidServer\\tomcat";
	private static String webInfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF";
	private static String sysaidAgentLogPath = "C:\\Program Files\\SysAid\\logs";
	
	
	static{
		init();
	}

	private static void init() {
		filesList = new ArrayList<String>();
		//add
		filesList.add(serverPath);
		filesList.add(agentPath);
		filesList.add(msSqlPath);
		filesList.add(tomcatPath);
		filesList.add(webInfPath);
		filesList.add(sysaidAgentLogPath);
	}
	
	
	public static List<String> getFiles(){
		return filesList;
	}
	
	
	public static class Agent{
		public static String configFilePath = "C:\\Program Files\\SysAid\\Configuration\\AgentConfigurationFile.xml"; 
		public static String logFilePath = "C:\\Program Files\\SysAid\\logs\\SysAidAgentLog.txt"; 
	}

}
