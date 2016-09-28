package com.core.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

import com.core.base.LogManager;
import com.core.base.TestManager;

//OSUtils
public class SystemUtils {

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//												Files														   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Files {

		//TODO: Check
		public static String read(final File srcFile) throws IOException {
			String input;
			input = FileUtils.readFileToString(srcFile);
			return input;
		}

		/**
		 * Scan File - line by line
		 * @param filePath
		 * @param searchQuery
		 */
		public static void scanByLine(String filePath, String... searchQuery) {
			LogManager.info("Scan File: " + filePath);
			boolean pass =true;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				String line;
				String keys = Arrays.asList(searchQuery).toString();
				keys = keys.substring(1, keys.length() -1);
				//String keys = "START"+Arrays.asList(searchQuery).toString().replace(",", "|")+ "END";
				//keys = keys.replace("START[","(").replace("]END", ")");
				keys = keys.replaceAll("[\\<\\(\\[\\{\\\\\\^\\-\\=\\$\\!\\|\\]\\}\\)‌​\\?\\*\\+\\.\\>]", "\\\\$0");
				keys = "(" + keys + ")";
				keys = keys.replace(",", "|");
				String regex = String.format("(?i:.*%s.*)",keys);
				while ((line = br.readLine()) != null) {
					if(line.matches(regex)){
						LogManager.error("Found Line: " + line);
						pass = false;
					}
//					for (String reg : searchQuery) {
//						if (line.toLowerCase().contains(reg.trim().toLowerCase())){
//							LogManager.error("Found Line: " + line);
//							pass = false;
//						}
//					}
				}
				
				
			} catch (Exception e) {
				LogManager.error("Scan File - Error : " + e.getMessage());
				pass = false;
			} finally {
				LogManager.assertSoft(pass, "Scan File: " + filePath);
				try {
					if (br != null)
						br.close();
				} catch (Exception e) {
				}
			}
			/*Scanner scanner = null;
			try{
			    scanner = new Scanner(new File(filePath));
			    while (scanner.hasNextLine()){
			        String line = scanner.nextLine();
			        for (String reg : searchQuery) {
			        	 if (line.toLowerCase().contains(reg.trim().toLowerCase()))
			        		 LogManager.error("Found Line: " + line);
					}
			    }
			} catch (FileNotFoundException e) {
				LogManager.error("Scan File - Error : " + e.getMessage());
			}
			finally{
			    try{
			        if (scanner != null)
			            scanner.close();
			    }
			    catch (Exception e) {}
			}*/
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
		
		public static String getMediaPath() {
			return getProjectPath() + "\\test-output\\media\\";
		}
		
		public static boolean isFileExist(String path){
			return new File(path).exists();
		}

		public static void createFile(String filePath, final String fileContent) throws IOException {
			File newFile = new File(filePath);
			FileUtils.write(newFile, fileContent);
		}

		public static void verifyExist(String fileName, String direcoryPath, boolean shouldExist) {
			verifyExist(direcoryPath + "\\" + fileName, shouldExist, null);
		}

		public static void verifyExist(String filePath, final boolean shouldExist) {
			verifyExist(filePath, shouldExist, 5000);
		}

		public static void verifyExist(String filePath, final boolean shouldExist, Integer maxTimeOutMs) {
			final ValueRef<Boolean> isExist = new ValueRef<Boolean>(false);
			final File file = new File(filePath);
			boolean ispass = Utils.tryUntil(new ActionWrapper("Verify File Exist : " + filePath, maxTimeOutMs) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = file.exists();
					isExist.setValue(results);
					return shouldExist == results;
				}
			});
			LogManager.verify(ispass, String.format("Verify File Exist : %s . Expected = %s , Actual = %s", filePath, shouldExist, isExist.value));
		}

		public static void replaceLine(String filePath, String originalLine, String newLine) {
			LogManager.debug(String.format("Replace File Content, Original line= %s , new line=%s",originalLine,newLine));
			verifyExist(filePath,true);
			String tmpFileName = "tmp.dat";
			boolean lineFound = false;
			BufferedReader br = null;
			BufferedWriter bw = null;
			try {
				br = new BufferedReader(new FileReader(filePath));
				bw = new BufferedWriter(new FileWriter(tmpFileName));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains(originalLine)){
						lineFound=true;
						line = newLine;
					}
					bw.write(line + "\n");
				}
				if(!lineFound){
					throw new Exception("Failed to find line : " + originalLine);
				}
			} catch (Exception e) {
				LogManager.error("Replace File Content- Error : " + e.getMessage());
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException e) {}
				try {
					if (bw != null)
						bw.close();
				} catch (IOException e) {}
			}
			if(lineFound){
				try {
					FileUtils.forceDelete(new File(filePath));
					moveFile(tmpFileName , filePath);
				} catch (IOException e) {
					LogManager.error("Replace File Content- Error : " + e.getMessage());
				}
			}
			
		}
		
		public static void moveFile(File src,File dest) {
			try {
				FileUtils.moveFile(src, dest);
			} catch (IOException e) {
				LogManager.error("Failed to move File , Error: " + e.getMessage());
			}
		}
		
		public static void moveFile(String srcPath,String destPath) {
			moveFile(new File(srcPath), new File(destPath));
		}
		
		public static void copyFile(String srcPath, String destPath) {
			LogManager.debug(String.format("Copy File from:%s , to:%s", srcPath, destPath));
			File srcFile = new File(srcPath);
			File destFile = new File(destPath);
			InputStream in = null;
			OutputStream out = null;
			try {
				if (!srcFile.exists())
					throw new Exception(srcFile.getName() + " - Source File is missing");

				if (!destFile.exists()) 
					destFile.createNewFile();
				
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				if(!isFileExist(destPath))
					throw new Exception("Failed to copy file");

			} catch (Exception e) {
				LogManager.error("Copy file error: " + e.getMessage());
			} finally {
				try {
					in.close();
				} catch (IOException e) {
				}
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//												OS      													   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static class OS {
		
		private static boolean is64;
		private static boolean is32;
		
		
		static{
			if(System.getProperty("sun.arch.data.model").equals("64")){
				is64 = true;
				is32 = false;
			}else{
				is64 = false;
				is32 = true;
			}
		}
		
		public static boolean is64Bit(){
			return is64;
		}
		
		public static boolean is32Bit(){
			return is32;
		}
		
		
		public static String getCurrentIP(){
			try {
				return Inet4Address.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				LogManager.assertTrue(false, "Failed to get current IP , Error : " + e.getMessage());
			}
			return "";
		}
		
		public static String getComputerName(){
			try {
				return InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				LogManager.assertTrue(false, "Failed to get computer name , Error : " + e.getMessage());
			}
			return "";
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//												Keyboard													   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static class Keyboard {
		private static Robot get(){
			try {
				return new Robot();
			} catch (AWTException e) {
				LogManager.assertTrue(false, "Failed to init Robot");
			}
			return null;
		}
		
		public static void ClickEsc() {
			Robot robot = get();
			LogManager.debug("Click Esc");
			TestManager.sleep(500);
			robot.keyPress(KeyEvent.VK_ESCAPE);
			TestManager.sleep(500);
			robot.keyRelease(KeyEvent.VK_ESCAPE);
		}
		
		/**
		 * Switch browser tab by CTRL + ALT 
		 */
		public static void switchBrowserTab() {
			Robot robot = get();
			LogManager.debug("switch browser tab");
			TestManager.sleep(500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			TestManager.sleep(500);
			robot.keyPress(KeyEvent.VK_TAB);
			TestManager.sleep(500);
			robot.keyRelease(KeyEvent.VK_TAB);
			TestManager.sleep(500);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
		
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//												Process														   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Processes {
		
		
		public static void executeAndCheckProcess(final String exePath, final String processName){
			final ValueRef<Boolean> isRun = new ValueRef<Boolean>(false);
			boolean ispass = Utils.tryUntil(new ActionWrapper("Execute and Verify Process" , 30000 , 10000) {
				@Override
				public boolean invoke() throws Exception {
					execute(exePath);
					boolean results = isProcessRunning(processName);
					isRun.setValue(results);
					return results;
				}
			});
			LogManager.assertTrue(ispass, String.format("Execute and Verify Process : %s", processName));
		}
		
		
		public static void execute(String exePath){
			LogManager.debug("CMD Execute : " + exePath);
			try {
				Runtime.getRuntime().exec(exePath);
				LogManager.debug("executed Successfully..");
			} catch (IOException e) {
				LogManager.error("CD Execute - Error:" + e.getMessage());
			}
		}

		public static synchronized void verify(final String processName, final boolean shouldRun) {
			final ValueRef<Boolean> isRun = new ValueRef<Boolean>(false);
			boolean pass = Utils.tryUntil(new ActionWrapper("Verify Process : " + processName) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = isProcessRunning(processName);
					isRun.setValue(results);
					return results == shouldRun;
				}
			});
			LogManager.verify(pass, String.format("Verify Process : %s . Expected = %s , Actual = %s", processName, shouldRun, isRun.value));

			/*boolean isRun = isProcessRunning(processName);
			TestManager.validator().validate(shouldRun == isRun, String.format("Validate Process : %s . Expected = %s , Actual = %s",processName, shouldRun, isRun));*/
		}

		public static void waitForProcessStop(final String processName, int maxTimeoutInMiliSec, int waitInterval) {
			final ValueRef<Boolean> isStoped = new ValueRef<Boolean>(false);
			boolean pass = Utils.tryUntil(new ActionWrapper("Wait For Process Stop: " + processName, maxTimeoutInMiliSec, waitInterval) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = !isProcessRunning(processName);
					isStoped.setValue(results);
					return results;
				}
			});

			LogManager.verify(pass,"Wait For Process Stop : " + processName);

			/*
			
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
			}*/
		}

		public static boolean isProcessRunning(String processName) {
			List<String> processList = getProcessDetails(processName);
			if (processList == null || processList.isEmpty())
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
				InputStream procOutput = proc.getInputStream();

				input = new BufferedReader(new InputStreamReader(procOutput));
				String line;

				while ((line = input.readLine()) != null) {
					if (line.contains("No tasks")) {
						LogManager.debug("tasklist.exe - No results for :" + processName);
						return null;
					}
					if (line.contains("====="))
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
				LogManager.error("Error on  getProcessDetails utils : " + e.getMessage());
			} finally {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
			return processList;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//											Services														   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Services {

		public static void verify(final String serviceName, final boolean shouldRun) {
			final ValueRef<Boolean> isRun = new ValueRef<Boolean>(false);
			boolean ispass = Utils.tryUntil(new ActionWrapper("Verify Service : " + serviceName) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = isServiceRunning(serviceName);
					isRun.setValue(results);
					return shouldRun == results;
				}
			});
			LogManager.verifyAssert(ispass, String.format("Verify Service is running : %s . Expected = %s , Actual = %s", serviceName, shouldRun, isRun.value));
			//TestManager.validator().validate(ispass, String.format("Validate File Exist : %s . Expected = %s , Actual = %s", filePath, shouldExist, isExist.value));
			
			//boolean isRun = isServiceRunning(serviceName);
			//LogManager.assertTrue(shouldRun == isRun, String.format("Validate Service is running : %s . Expected = %s , Actual = %s", serviceName, shouldRun, isRun));
		}

		private static boolean isServiceRunning(String serviceName) {
			BufferedReader input = null;
			try {
				//String cmd = String.format("sc query \"%s\"   | FIND \"STATE\"", serviceName);
				//String cmd = String.format("net START | find \"%s\"", serviceName);
				String cmd = "net START";
				Process proc = Runtime.getRuntime().exec(cmd);
				InputStream procOutput = proc.getInputStream();

				input = new BufferedReader(new InputStreamReader(procOutput));
				String line;
				while ((line = input.readLine()) != null) {
					if (line.contains(serviceName))
						return true;
					//return false;
				}
			} catch (Exception e) {
				LogManager.error("Error on  getProcessDetails utils : " + e.getMessage());
			} finally {
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
	
	
	
	
}
