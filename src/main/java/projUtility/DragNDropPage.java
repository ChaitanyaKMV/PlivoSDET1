package projUtility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.Base.BaseClass;
import com.Base.Log;

import junit.framework.Assert;

public class DragNDropPage {

	private static WebDriver driver;

	public DragNDropPage(WebDriver driver) {

		DragNDropPage.driver = driver;

		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[contains(text(),'Create an App')]")
	WebElement createAppBtn;

	@FindBy(xpath = "//button[contains(text(),\"Let's get started!\")]")
	WebElement letStartBtn;
	
	@FindBy(id = "add-page")
	WebElement addPageBtn;
	
	@FindBy(xpath = "//form[contains(@class,'unsubmittable')]//label[contains(text(),'Enter a new name for this page:')]")
	WebElement newPageDisplayMsg;
	
	@FindBy(xpath = "//form[contains(@class,'unsubmittable')]//input[contains(@name,'name')]")
	WebElement pageNameInput;
	
	@FindBy(xpath = "//div[@id='current-title']")
	WebElement currentPageName;
	
	@FindBy(xpath = "//a[contains(text(),'Messaging')]")
	WebElement messagingMenu;
	
	@FindBy(xpath = "//li[text()='Send an SMS']")
	WebElement sendSMSBtn;
	
	@FindBy(xpath = "//li[text()='Send an Email']")
	WebElement sendEmailBtn;
	
	@FindBy(xpath = "//div[@id='tabs-2']")
	WebElement draggableTarget;
	
	@FindBy(xpath = "//div[20]//div[3]//button[1]")
	WebElement createPageBtn;
	
	@FindBy(xpath = "//div[@id='node-740486']")
	WebElement startNodeDrag;
	
	@FindBy(xpath = "//div[@id='rec-184030']")
	WebElement sendSmsRecepNode;
	
	@FindBy(xpath = "//div[@id='node-173463']")
	WebElement sendSmsNotSentNode;
	
	@FindBy(xpath = "//div[@id='node-486974']")
	WebElement sendSmsSentNode;
	
	

	

	public void createApp() {
		Log.info("Clicking create an app button ");
		createAppBtn.click();
		
	}

	public void letsStart() {
		Log.info("Click on Let's get started ");
		letStartBtn.isDisplayed();
		letStartBtn.click();
		
	}

	public void addNewPage() {
		Log.info("Click on add new page ");
		addPageBtn.isDisplayed();
		addPageBtn.click();
	}

	public void enterPageName(String pageName) {
		
		if (newPageDisplayMsg.isDisplayed()) {
			pageNameInput.sendKeys(pageName);
			createPageBtn.click();
		}else{
			Log.warn("**------** New page name creation failed**------**");
		}
	}
	
	
	public void verifyCurrentPageName(String pageName){
		String currPage = currentPageName.getText();
		Assert.assertEquals(pageName, currPage);
	}
	
	public void selectMessagingMenu() {
		messagingMenu.isDisplayed();
		messagingMenu.click();
	}

	public void dragDrop(WebElement sourceLoc,WebElement targetLoc) {
		Actions act = new Actions(driver);
		act.dragAndDrop(sourceLoc, targetLoc).build().perform();
	}
	
	public void dragDropXY(WebElement sourceLoc,int xOffset,int yOffset) {
		Actions act = new Actions(driver);
		act.dragAndDropBy(sourceLoc, xOffset, yOffset).build().perform();
	}
	public void selectSendSMSAndDrop() {
		
		int x = 220;
		int y = 200;
		Log.info("Select send sms menu to drop");
		try{
			dragDropXY(sendSMSBtn, x, y);
		}catch(ElementNotVisibleException e){
			Log.warn(e.getMessage());
		}
		
		
	}
	
public void selectEmailAndDrop() {
	int x = 280;
	int y = 250;
		Log.info("Select email to drop ");
		try{
			dragDropXY(sendEmailBtn, x, y);
		}catch(ElementNotVisibleException e){
			Log.warn(e.getMessage());
		}
		
		
	}
	
	public boolean verifySignUp(){
		boolean status = false; 
		try{
	//	status= successAcctMsg.isDisplayed();
		}catch(ElementNotVisibleException e){
			Log.warn(e.getMessage());
		}
		
		return status;
	}

	public static String takeScreenshot(String screenshotName) {
		
		String fullPath = null;
		if (driver != null) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);

			try {
				fullPath = BaseClass.projFolPath + "/Snapshots/" + screenshotName;
				FileUtils.copyFile(src, new File(fullPath));
			} catch (IOException e) {
				Log.fatal("------------ Faild to copy screenshot to dest ---------- ");
				e.printStackTrace();
			}

		}

		return fullPath;

	}

}
