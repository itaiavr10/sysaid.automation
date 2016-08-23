package utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;

import base.LogManager;
import base.TestManager;

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

		public static void scan(String filePath, String... searchQuery) {
			LogManager.info("Scan File: " + filePath);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
				String line;
				while ((line = br.readLine()) != null) {
					for (String reg : searchQuery) {
						if (line.toLowerCase().contains(reg.trim().toLowerCase()))
							LogManager.error("Found Line: " + line);
					}
				}
			} catch (Exception e) {
				LogManager.error("Scan File - Error : " + e.getMessage());
			} finally {
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

		public static void createFile(String filePath, final String fileContent) throws IOException {
			File newFile = new File(filePath);
			FileUtils.write(newFile, fileContent);
		}

		public static void validateExist(String fileName, String direcoryPath, boolean shouldExist) {
			validateExist(direcoryPath + "\\" + fileName, shouldExist, null);
		}

		public static void validateExist(String filePath, final boolean shouldExist) {
			validateExist(filePath, shouldExist, 5000);
		}

		public static void validateExist(String filePath, final boolean shouldExist, Integer maxTimeOutMs) {
			final ValueRef<Boolean> isExist = new ValueRef<Boolean>(false);
			final File file = new File(filePath);
			boolean ispass = Utils.tryUntil(new ActionWrapper("Validat File Exist : " + filePath, maxTimeOutMs) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = file.exists();
					isExist.setValue(results);
					return shouldExist == results;
				}
			});
			LogManager.validate(ispass, String.format("Validate File Exist : %s . Expected = %s , Actual = %s", filePath, shouldExist, isExist.value));

			/*File file = new File(filePath);
			boolean isExist = file.exists();
			TestManager.validator().validate(shouldExist == isExist, String.format("Validate File Exist : %s . Expected = %s , Actual = %s",filePath, shouldExist, isExist));*/
			//////////////////////////////////////////////////////////////
			/*File file = new File(filePath);//TODO Should be in Retry Mech
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
			TestManager.validator().validate(isok, String.format("Validate File Exist : %s . Expected = %s , Actual = %s",filePath, shouldExist, isok));*/
		}

		public static void replaceLine(String filePath, String originalLine, String newLine) {
			LogManager.debug(String.format("Replace File Content, Original line= %s , new line=%s",originalLine,newLine));
			validateExist(filePath,true);
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
					throw new Exception("Failed to find line");
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
		
		public static void moveFile(String srcPath,String destPath) throws IOException {
			FileUtils.moveFile(new File(srcPath), new File(destPath));
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//												Process														   //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Processes {

		public static synchronized void validate(final String processName, final boolean shouldRun) {
			final ValueRef<Boolean> isRun = new ValueRef<Boolean>(false);
			boolean pass = Utils.tryUntil(new ActionWrapper("Validate Process : " + processName) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = isProcessRunning(processName);
					isRun.setValue(results);
					return results == shouldRun;
				}
			});
			LogManager.validate(pass, String.format("Validate Process : %s . Expected = %s , Actual = %s", processName, shouldRun, isRun.value));

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

			if (!pass) {
				LogManager.validate(false,"Wait For Process Stop - Failed ");
			}

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

		public static void validate(final String serviceName, final boolean shouldRun) {
			final ValueRef<Boolean> isRun = new ValueRef<Boolean>(false);
			boolean ispass = Utils.tryUntil(new ActionWrapper("Validate Service : " + serviceName) {
				@Override
				public boolean invoke() throws Exception {
					boolean results = isServiceRunning(serviceName);
					isRun.setValue(results);
					return shouldRun == results;
				}
			});
			LogManager.assertTrue(ispass, String.format("Validate Service is running : %s . Expected = %s , Actual = %s", serviceName, shouldRun, isRun));
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
	
	public static class ScreenShooter{
		
		public static void main(String[] args) throws AWTException, Exception {
			Thread.sleep(5000);
			OutputStream out = null;
			String path = "./screenShot/itai.jpg";
			byte[] bytes = regularScreenShot();
			try {
			    out = new BufferedOutputStream(new FileOutputStream(path));
			    out.write(bytes);
			} finally {
			    if (out != null) out.close();
			}
			System.out.println("DONE");
		}

		public static byte[] regularScreenShot() throws AWTException, IOException {
			BufferedImage capture = captureScreen();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(capture, "jpg", baos);
			baos.flush();
			capture.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}

		public static BufferedImage captureScreen() throws AWTException {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			Robot robotic = new Robot();
			BufferedImage capture = robotic.createScreenCapture(screenRect);
			robotic.waitForIdle();
			return capture;
		}
	}
}
