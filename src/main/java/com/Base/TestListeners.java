package com.Base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import projUtility.DragNDropPage;

public class TestListeners implements ITestListener {
	
	protected static WebDriver driver;
//	protected static ExtentReports reports;
//	protected static ExtentTest test;
	static String ReportPath;
    String snapID=BaseClass.addDate();
    
	@Override
	public void onTestStart(ITestResult result) {
		Log.info("\n \n");
		Log.info("***********************************************************");
		Log.info("***********************************************************");
		Log.info("************** On Test starts *************** " + result.getName());
		Log.info("***********************************************************");
		Log.info("***********************************************************");
		
		 ExtentTestManager.startTest(result.getMethod().getMethodName(),"");
		 ExtentTestManager.getTest().log(LogStatus.INFO, result.getMethod().getMethodName() + "Test is started");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info(" ########## On Test success ########## " + result.getName());
		Log.info("\n \n");
		ExtentTestManager.getTest().log(LogStatus.PASS, result.getMethod().getMethodName() + "Test is passed");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.info("\n \n");
		Log.info("----------------------------------------- ");
		Log.info("---------- On Test fails Start ------------ " + result.getName());
		ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName() + "Test is failed");
		String fileName = result.getMethod().getMethodName()+snapID + ".png";

		String filePath = DragNDropPage.takeScreenshot(fileName);
		String file = ExtentTestManager.getTest().addScreenCapture(filePath);
		ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName() + "------- test is failed -------", file);
		ExtentTestManager.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName() + "------- test is failed -------",
				result.getThrowable().getMessage());
		Log.info("---------- On Test fails Ends ----------");
		Log.info("----------------------------------------- ");
		Log.info("\n \n");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("=========== On Test skipped ============= " + result.getName());
		ExtentTestManager.getTest().log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");

	}

	@Override
	public void onStart(ITestContext context) {
		Log.info("\n \n");
		Log.info("********* Test class starts ********** " + context.getClass());
		Log.info("\n \n");
		

	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info("===============================================================");
		Log.info("=====================Tests finished===========================");
		Log.info("===== Test Passed ===== " + context.getPassedTests()+" ===========");
		Log.info("===== Test Failed ===== " + context.getFailedTests()+" ===========");
		Log.info("===============================================================");
		Log.info("\n \n");

		ExtentTestManager.endTest();
		ExtentManager.getReporter().flush();

		Log.info("######################## Web Report URL: ########################");
		Log.info(ExtentManager.ReportPath);
		Log.info("######################## Web Report URL: ########################");
		Log.info("\n \n");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

}
