package com.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

	private static PropertyManager instance;
	Properties prop;
	static Object lock = new Object();
	String URL;
	String browser,repoName;
	int waitSec;

	String filePath = System.getProperty("user.dir") + "/Configurations/config.properties";

	public static PropertyManager getInstance() {

		if (instance == null) {

			synchronized (lock) {
				instance = new PropertyManager();
				instance.loadData();
			}
		}
		return instance;
	}
	
	public void loadPropertyFile(String file) {
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(file)));
		} catch (IOException e) {
			Log.fatal(" **** Failed to read Property file path: " + filePath);
			e.printStackTrace();
		}
	}

	public  void loadData() {
		PropertyManager.getInstance().loadPropertyFile(filePath);
		URL = prop.getProperty("testURL");
		browser = prop.getProperty("browser", "chrome");
		waitSec = Integer.parseInt(prop.getProperty("impWaitInSec"));
		
		
	}

}
