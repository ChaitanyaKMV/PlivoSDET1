package com.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import projUtility.DragNDropPage;

/**
 * Base class is intend to support which Initialize WebDriver ,Launching browser
 * Common methods:Reading Excel, Random user utility Log4j Implementation Close
 * browser
 */
public class BaseClass {

	private WebDriver driver;
	public static String projFolPath = System.getProperty("user.dir");
	public static DragNDropPage signupPage;

	public WebDriver getDriver() {
		return driver;
	}

	private void setDriverLaunchURL() {

		PropertyManager pr = PropertyManager.getInstance();
		pr.loadData();

		if (driver == null) {
			switch (pr.browser) {
			case "chrome":
				driver = initChromeDriver();
				break;

			case "ie":

				driver = initIEDriver();
			default:
				Log.info("===== Browser: " + pr.browser + " is invalid.., Launching default chrome browser ");
				break;
			}

			driver.manage().window().maximize();
			Log.info("**** Navigating to URL ***** " + pr.URL);
			driver.get(pr.URL);
			driver.manage().timeouts().implicitlyWait(pr.waitSec, TimeUnit.SECONDS);
			// WebDriverWait wait = new WebDriverWait(driver, pr.waitSec);
			signupPage = new DragNDropPage(driver);

		}

	}

	@BeforeClass
	public void initializeTestBaseSetup() {
		try {
			setDriverLaunchURL();

		} catch (Exception e) {
			Log.fatal("Error....." + e.getStackTrace());
		}
	}

	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			Log.info("****************************  Closing browser **************************************** ");
			Log.info("\n \n");
			driver.close();
		}
	}

	private static WebDriver initChromeDriver() {
		System.setProperty("webdriver.chrome.driver", projFolPath + "/drivers/chromedriver.exe");
		Log.info("**** Browser Launching ***** ");
		WebDriver driver = new ChromeDriver();
		return driver;

	}

	private static WebDriver initIEDriver() {
		System.setProperty("webdriver.ie.driver", projFolPath + "/drivers/IEDriverServer.exe");
		Log.info("**** Browser Launching ***** ");
		WebDriver driver = new InternetExplorerDriver();
		return driver;
	}

	public static String addDate() {

		Date d = new Date();
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String newDate = df.format(d);
		return newDate;

	}

	public static String readCellFromExcel(String filePath, String fileName, String sheetName, int rowNo, int colNo)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "/" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook guru99Workbook = null;

		// Find the file extension by splitting file name in substring and
		// getting only extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			guru99Workbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			guru99Workbook = new HSSFWorkbook(inputStream);

		}

		// Read sheet inside the workbook by its name

		Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

		// Get row in excel file

		Row row = guru99Sheet.getRow(rowNo);
		Cell cell = row.getCell(colNo);

		// Get row in excel file
		String cellValue = cell.getStringCellValue();

		return cellValue;

	}

	public void writeExcel(String filePath, String fileName, String sheetName, int rowNo, int colNo, String enterValue)
			throws IOException {

		// Create an object of File class to open xlsx file

		File file = new File(filePath + "/" + fileName);

		// Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook guru99Workbook = null;

		// Find the file extension by splitting file name in substring and
		// getting only extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file

		if (fileExtensionName.equals(".xlsx")) {

			// If it is xlsx file then create object of XSSFWorkbook class

			guru99Workbook = new XSSFWorkbook(inputStream);

		}

		// Check condition if the file is xls file

		else if (fileExtensionName.equals(".xls")) {

			// If it is xls file then create object of XSSFWorkbook class

			guru99Workbook = new HSSFWorkbook(inputStream);

		}

		// Read excel sheet by sheet name

		Sheet sheet = guru99Workbook.getSheet(sheetName);

		// Get the current count of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Get the first row from the sheet

		Row newRow = sheet.createRow(rowNo);
		Cell cell = newRow.createCell(colNo);

		cell.setCellValue(enterValue);

		// Close input stream

		inputStream.close();

		// Create an object of FileOutputStream class to create write data in
		// excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file

		guru99Workbook.write(outputStream);

		// close output stream

		outputStream.close();

	}

	private static XSSFSheet ExcelWSheet;

	private static XSSFWorkbook ExcelWBook;

	private static XSSFCell Cell;

	private static XSSFRow Row;

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {

		String[][] tabArray = null;

		try {

			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startRow = 1;

			int startCol = 0;

			int ci, cj;

			int totalRows = ExcelWSheet.getLastRowNum();
			//totalRows = totalRows;

			// you can write a function as well to get Column count

			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			//totalCols = totalCols - 1;
			//Log.info("total no of rows: "+totalRows+" total columns: "+totalCols);
			tabArray = new String[totalRows][totalCols];

			ci = 0;

			for (int i = startRow; i <= totalRows; i++, ci++) {

				cj = 0;

				for (int j = startCol; j < totalCols; j++, cj++) {

					tabArray[ci][cj] = getCellData(i, j);

					//Log.info(ci + "==" + cj + "== " + tabArray[ci][cj]);

				}

			}

		}

		catch (FileNotFoundException e) {

			Log.warn("Could not read the Excel sheet");

			e.printStackTrace();

		}

		catch (IOException e) {

			Log.warn("Could not read the Excel sheet");

			e.printStackTrace();

		}

		return (tabArray);

	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		try {
			DataFormatter formatter = new DataFormatter();

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			int dataType = Cell.getCellType();

			if (dataType == 3) {

				return "";

			} else {

				// String CellData = Cell.getStringCellValue();
				String CellData = formatter.formatCellValue(Cell);

				return CellData;

			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

			throw (e);

		}

	}

}