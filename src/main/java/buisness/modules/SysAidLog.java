package buisness.modules;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.core.base.LogManager;

public class SysAidLog {
	
	
	private static String sysAidLogsPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\logs\\sysaid.log";
	
	private static List<String> knownErros = new ArrayList<String>();
	
	static{
		initKnownErrors();
	}
	
	
	// verify sysaid.log file
		public static void verifyLog() {
			LogManager.info("Verify sysaid.log ..");
			boolean pass = true;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(sysAidLogsPath)));
				String line;
				Pattern p = Pattern.compile(".*]\\s*ERROR.*"); // by log4j error level 
				while ((line = br.readLine()) != null) {
					if(p.matcher(line).matches()){ // found log4j error
						String errorMsg = line;
						// search exception (max 3) line under error
						for(int i=1;i<=3 && (line = br.readLine()) != null ;i++){
							if(line.contains("Exception")){
								errorMsg = line;
								break;
							}
						}
					
						/*if ((line = br.readLine()) != null){ 
							if(line.contains("Exception"))
								errorMsg = line;
						}*/
						//Check Known Exception
						boolean isKnownError = false;
						for(String error : knownErros){
							if(line.contains(error)){
								knownErros.remove(error);
								isKnownError = true;
								break;
							}
						}
						
						if(isKnownError){
							LogManager.warn("Found Error on sysaid.log file , Exception: " + errorMsg);
						}else{
							LogManager.error("Found Error on sysaid.log file , Exception: " + errorMsg);
							pass = false;
						}
						
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


	private static void initKnownErrors() {
		//below error appears in log 2 times & defined as known bugs
		knownErros.add("SocketException: Connection reset by peer: socket write error");
		knownErros.add("SocketException: Connection reset by peer: socket write error");
		
		
	}

}
