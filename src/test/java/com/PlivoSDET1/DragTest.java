package com.PlivoSDET1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Base.BaseClass;
import com.Base.Log;



public class DragTest extends BaseClass {

	 //WebDriver driver;
	// Testdata file path
	String filePath = System.getProperty("user.dir") + "/ExcelData/";
	String fileName = "Data1.xlsx";
	String fullPathOfFile = filePath + fileName;
	String sheetName = "Sheet1";
	
	String pageTitle = "ChaitanyaTest";
	
	
	
	@Test(priority=0)
	public void createAppTest() throws InterruptedException{
		
		signupPage.createApp();
		Thread.sleep(2000);
		Log.info("------------------------------------ test1");
	}
	
	
	
		
	@Test(priority=1)
	public void validSignup() throws InterruptedException{
		signupPage.letsStart();
		signupPage.addNewPage();
		Thread.sleep(2000);
		signupPage.enterPageName(pageTitle);
		Thread.sleep(2000);
		signupPage.verifyCurrentPageName(pageTitle);
		Thread.sleep(2000);
		signupPage.selectMessagingMenu();
		Thread.sleep(2000);
		signupPage.selectSendSMSAndDrop();
		Thread.sleep(2000);
		signupPage.selectEmailAndDrop();
		Thread.sleep(2000);
		
		
	}

	

}
