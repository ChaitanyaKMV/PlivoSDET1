package com.Base;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	static String snapID = BaseClass.addDate();
	static String ReportPath;
	private static ExtentReports reports;

	public synchronized static ExtentReports getReporter() {
		if (reports == null) {
			// Set HTML reporting file location
			ReportPath = System.getProperty("user.dir") + "/Reports/Report_" + snapID + "_" + System.currentTimeMillis()
					+ ".html";
			reports = new ExtentReports(ReportPath, false);
			reports.loadConfig(new File(System.getProperty("user.dir") + "/Configurations/extent-config.xml"));
			reports.addSystemInfo("Created By", "Chaitanya Kumar").addSystemInfo("Project", "Plivo SDET UI Test")
					.addSystemInfo("User Name", System.getProperty("user.name"))
					.addSystemInfo("Browser", PropertyManager.getInstance().browser)
					.addSystemInfo("Environment", "Windows")
					.addSystemInfo("Time Zone", System.getProperty("user.timezone"));

		}
		return reports;
	}

}
