package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import base.LogManager;

public class ConfigProperties {

	private static Properties properties;
	private static Map<String, String> propertiesMap;

	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("config.properties"));
			propertiesMap = new HashMap<String, String>();
			Enumeration<?> e = properties.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = properties.getProperty(key);
				//LogManager.info("Key : " + key + ", Value : " + value);
				propertiesMap.put(key, value);
			}
		} catch (IOException e) {
			LogManager.error("Failed to init config.properties file : " + e.getMessage());
		}
	}

	public static String getValue(String key) {
		if (propertiesMap.containsKey(key))
			return propertiesMap.get(key);
		else
			LogManager.error(key + " - Key is missing in config.properties file");
		return "Unknown";
	}

	public static void print() {
		LogManager.bold("Configuration:");
		Iterator<?> it = propertiesMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,String> pair = (Map.Entry) it.next();
			LogManager.info(pair.getKey() + " = " + pair.getValue());
		}

	}

}
