package buisness.modules;


public abstract class SysAid {
	
	
	public static InstallType type = InstallType.TYPICAL;
	
	
	
	
	
	public enum InstallType{
		TYPICAL,Customized;
		;
	}
	
	/*//TODO : separate to server , agent
	
	private static List<String> filesList;
	
	
	
	
	
	
	
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
		
		
		
	}*/
	
	

}
