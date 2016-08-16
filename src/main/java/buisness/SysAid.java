package buisness;

import java.util.ArrayList;
import java.util.List;

public class SysAid {
	
	
	private static List<String> filesList;
	private static String serverPath = "C:\\Program Files\\SysAidServer";
	private static String agentPath = "C:\\Program Files\\SysAid";
	private static String msSqlPath = "C:\\Program Files\\SysAidMsSql";
	private static String tomcatPath = "C:\\Program Files\\SysAidServer\\tomcat";
	private static String webInfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF";
	
	
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
	}
	
	
	public static List<String> getFiles(){
		return filesList;
	}

}
