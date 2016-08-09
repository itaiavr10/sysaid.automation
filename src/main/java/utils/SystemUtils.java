package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
//OSUtils
public class SystemUtils {

	//TODO: Check
	public static String readFile(final File srcFile) throws IOException {
		String input;
		input = FileUtils.readFileToString(srcFile);
		return input;
	}
	
	
	public static String getProjectPath() {
		//return Paths.get(Strings.EMPTY).toAbsolutePath().toString();
		return System.getProperty("user.dir");
	}
	
	public static String getResourcesDirectoryPath() {
		return getProjectPath() + "\\src\\main\\resources";
	}
	
	
	public static void createFile(String filePath , final String fileContent) throws IOException {
		File newFile = new File(filePath);
		FileUtils.write(newFile, fileContent);
	}
	
	
	
	
}
