package com.mercury.org.framework.utility;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Property {

	public Property() {
		
	}
	
	/**
	 * Returns the properties file instance
	 * @param path
	 * @return
	 */
	public static Properties getPropertyInstance(String path) {
		Properties  prop = new Properties();
		InputStream input = null;
		try{
			input = new FileInputStream(path);
			prop.load(input);
		}catch(IOException e){
			e.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	/**
	 * Returns the value present in the properties file
	 * @param properties
	 * @param key
	 * @return
	 */
	public static String getPropertyValue(Properties properties, String key){
		return properties.getProperty(key);
	}
	
	
}
