package buisness.modules;


public abstract class SysAid {
	
	
	private static InstallType installType = InstallType.TYPICAL;
	private static DataBaseType dbType = DataBaseType.MsSQL;
	
	public static InstallType getInstallType() {
		return installType;
	}

	public static void setInstallType(InstallType installType) {
		SysAid.installType = installType;
	}

	public static DataBaseType getDbType() {
		return dbType;
	}

	public static void setDbType(DataBaseType dbType) {
		SysAid.dbType = dbType;
	}



	
	
	
	
	
	
	public enum DataBaseType{
		Oracle("Oracle") {
			@Override
			public String getDriver() {
				return "oracle.jdbc.driver.OracleDriver";
			}

			@Override
			public String getURL() {
				return "jdbc:oracle:thin:@localhost:1521:ILIENT"; //TODO : Ask Alex localhost?
			}
		},
		MsSQL("Microsoft QSL Server") {
			@Override
			public String getDriver() {
				return "net.sourceforge.jtds.jdbc.Driver";
			}

			@Override
			public String getURL() {
				return "jdbc:jtds:sqlserver://localhost:1433/ilient;useCursorsAlways=true"; //TODO : Ask Alex localhost?
			}
		},
		MySQL("MySql") {
			@Override
			public String getDriver() {
				return "com.mysql.jdbc.Driver";
			}

			@Override
			public String getURL() {
				return "jdbc:mysql://localhost/ilient"; //TODO : Ask Alex localhost?
			}
		};
		
		
		
		public String name;
	
		DataBaseType(String name){
			this.name = name;
		}
		
		
		public abstract String getDriver();
		public abstract String getURL();
		
	}
	
	
	
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
