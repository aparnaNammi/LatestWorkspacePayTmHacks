package com.lsa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LSSPropertiesLoader {

	private static LSSPropertiesLoader instance = null;
	private Properties properties;

	protected LSSPropertiesLoader() throws IOException {

		properties = new Properties();
		properties.load(new FileInputStream(LSSUtil.getPath("lss.properties")));

	}

	public static LSSPropertiesLoader getInstance() {
		if (instance == null) {
			try {
				instance = new LSSPropertiesLoader();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		return instance;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

}
