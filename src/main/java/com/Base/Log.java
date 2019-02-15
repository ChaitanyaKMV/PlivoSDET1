package com.Base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {
	static{
		PropertyConfigurator.configure("./log4j/log4j.properties");
	}
	public static Logger log = Logger.getLogger(Log.class);
	
	public static void info(String message) {

		log.info(message);

	}

	public static void warn(String message) {

		log.warn(message);

	}

	public static void debug(String message) {

		log.debug(message);

	}

	public static void error(String message) {

		log.error(message);

	}

	public static void fatal(String message) {

		log.fatal(message);

	}

}
