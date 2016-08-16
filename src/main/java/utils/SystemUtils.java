package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

import base.LogManager;
import base.TestManager;
//OSUtils
public class SystemUtils {
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//												Files														   //
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//TODO: Check
	public static String readFile(final File srcFile) throws IOException {
		String input;
		input = FileUtils.readFileToString(srcFile);
		return input;
	}
	
	
	public static String getProjectPath() {
		return System.getProperty("user.dir");
	}
	
	public static String getPublicDesktopPath() {
		//return String.format("C:\\Users\\%s\\Desktop", System.getProperty("user.name"));
		return String.format("C:\\Users\\Public\\Desktop");
	}
	
	public static String getResourcesDirectoryPath() {
		return getProjectPath() + "\\src\\main\\resources";
	}
	
	
	public static void createFile(String filePath , final String fileContent) throws IOException {
		File newFile = new File(filePath);
		FileUtils.write(newFile, fileContent);
	}
	
	
	public static void validateFileExist(String fileName , String direcoryPath , boolean shouldExist){
		validateFileExist(direcoryPath + "\\" + fileName , shouldExist);
	}
	
	public static void validateFileExist(String filePath ,  boolean shouldExist){
		/*File file = new File(filePath);
		boolean isExist = file.exists();
		TestManager.validator().validate(shouldExist == isExist, String.format("Validate File Exist : %s . Expected = %s , Actual = %s",filePath, shouldExist, isExist));*/
		//////////////////////////////////////////////////////////////
		File file = new File(filePath);//TODO Should be in Retry Mech
		long waitInterval = 2000;
		long maxTimeoutInMiliSec = 20000;
		//boolean isExist = file.exists();
		long startTime = System.currentTimeMillis(); //TODO Should be in Retry Mech
		boolean isok = false;
		while (System.currentTimeMillis() - startTime < maxTimeoutInMiliSec) {
			if (file.exists() == shouldExist) {
				isok = true;
				break;
			}
			// interval to sleep between each iteration
			try {
				Thread.sleep(waitInterval);
			} catch (InterruptedException e) {}
		}
		TestManager.validator().validate(isok, String.format("Validate File Exist : %s . Expected = %s , Actual = %s",filePath, shouldExist, isok));
	}
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//												Process														   //
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void validateProcess(String processName , boolean shouldRun){
		boolean isRun = isProcessRunning(processName);
		TestManager.validator().validate(shouldRun == isRun, String.format("Validate Process : %s . Expected = %s , Actual = %s",processName, shouldRun, isRun));
	}
	
	public static void waitForProcessStop(String processName, long maxTimeoutInMiliSec , long waitInterval) {
		LogManager.debug("Wait For Process Stop: " + processName);
		long startTime = System.currentTimeMillis(); //TODO Should be in Retry Mech
		boolean isok = false;
		while (System.currentTimeMillis() - startTime < maxTimeoutInMiliSec) {
			if (!isProcessRunning(processName)) {
				isok = true;
				break;
			}
			// interval to sleep between each iteration
			try {
				Thread.sleep(waitInterval);
			} catch (InterruptedException e) {}
		}
		if(!isok){
			LogManager.error("Wait For Process Stop - Failed ");
		}
	}
	
	public static boolean isProcessRunning(String processName){
		List<String> processList = getProcessDetails(processName);
		if(processList == null || processList.isEmpty())
			return false;
		return true;
	}

	private static List<String> getProcessDetails(String processName) {
		LogManager.debug("Get Process Details for process: " + processName);
		List<String> processList = null;
		BufferedReader input = null;
		try {
			String cmd = String.format("tasklist.exe /fi \"Imagename eq %s\"", processName);
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream procOutput = proc.getInputStream ();
			
				input =new BufferedReader(new InputStreamReader(procOutput));
				String line;
				
				while ((line = input.readLine()) != null) {
					if(line.contains("No tasks"))
						return null;
					if(line.contains("====="))
						break;
			    }
				//Start reading table results
				processList = new ArrayList<String>();
				LogManager.debug("Found Processes :");
				while ((line = input.readLine()) != null) {
					LogManager.debug(line); //<-- Parse data here.
			        processList.add(line);
			    }
			    
			    
		} catch (Exception e) {
			TestManager.validator().assertFalse("Error on  getProcessDetails utils : " + e.getMessage());
		}finally{
			try {
				input.close();
			} catch (IOException e) {
			}
		}
		return processList;
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//											Services														   //
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void validateService(String serviceName , boolean shouldRun){
		boolean isRun = isServiceRunning(serviceName);
		TestManager.validator().validate(shouldRun == isRun, String.format("Validate Service is running : %s . Expected = %s , Actual = %s",serviceName, shouldRun, isRun));
	}
	
	private static boolean isServiceRunning(String serviceName) {
		BufferedReader input = null;
		try {
			//String cmd = String.format("sc query \"%s\"   | FIND \"STATE\"", serviceName);
			//String cmd = String.format("net START | find \"%s\"", serviceName);
			String cmd = "net START";
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream procOutput = proc.getInputStream ();
			
				input =new BufferedReader(new InputStreamReader(procOutput));
				String line;
				while ((line = input.readLine()) != null) {
					if(line.contains(serviceName))
						return true;
					//return false;
			    }
		} catch (Exception e) {
			TestManager.validator().assertFalse("Error on  getProcessDetails utils : " + e.getMessage());
		}finally{
			try {
				input.close();
			} catch (IOException e) {
			}
		}
		return false;
	}
	
	/*public static boolean isServiceRunning(String serviceName) {
		LogManager.debug("Is Service Running for service: " + serviceName);
		try {
			String cmd = String.format("sc query %s   | FIND \"STATE\"", serviceName);
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream procOutput = proc.getInputStream ();
			
				BufferedReader input =new BufferedReader(new InputStreamReader(procOutput));
				String line;
				if ((line = input.readLine()) != null) {
					LogManager.debug("Service status : " + line);
					if(line.contains("Running"))
						return true;
					return false;
			    }
		} catch (Exception e) {
			TestManager.validator().assertFalse("Error on  isServiceRunning utils : " + e.getMessage());
		}
		return false;
	}*/
}
