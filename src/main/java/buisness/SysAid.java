package buisness;

import java.util.ArrayList;
import java.util.List;

import utils.ActionWrapper;
import utils.StringRef;
import utils.Utils;
import utils.XmlUtils;
import base.LogManager;
import base.TestManager;

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
		
		/**
		 * verify Agent send inventory to server - successfully
		 */
		public static void validteInventorySent(){
			final String expectedValue = "N";
			final StringRef actualValue = new StringRef("");
			boolean ispass = Utils.tryUntil(new ActionWrapper("Validate Inventory Sent to Server.." , 180000 , 3000) {
				@Override
				public boolean invoke() throws Exception {
					actualValue.setValue(XmlUtils.getNodeValue(SysAid.Agent.configFilePath,"FirstTime"));
					return expectedValue.equals(actualValue.value);
				}
			});
			TestManager.validator().validate(ispass,"Validate Agent send inventory to server");
		}
	}
	
	public static class Server{
		public static String log4jPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\log4j.properties"; 
	}

}
