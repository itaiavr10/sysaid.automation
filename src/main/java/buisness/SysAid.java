package buisness;

import java.util.ArrayList;
import java.util.List;

public class SysAid {
	
	
	private static List<String> filesList;
	private static String serverPath = "C:\\Program Files\\SysAidServer";
	private static String agentPath = "C:\\Program Files\\SysAid";
	private static String msSqlPath = "C:\\Program Files\\SysAidMsSql";
	
	
	static{
		init();
	}

	private static void init() {
		filesList = new ArrayList<String>();
		//add
		filesList.add(serverPath);
		filesList.add(agentPath);
		filesList.add(msSqlPath);
	}
	
	
	public static List<String> getFiles(){
		return filesList;
	}

}
