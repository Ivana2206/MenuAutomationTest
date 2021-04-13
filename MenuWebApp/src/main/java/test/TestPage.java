package test;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.itextpdf.text.Document;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import pages.*;

public class TestPage {

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static Document document = new Document(PageSize.A4, 20, 20, 20, 20);;
	public static SimpleDateFormat formatter;
	public static Date date = new Date();
	public static File fos;
	public static File fis;
	public static WritableWorkbook myexcel;
	public static WritableSheet mysheet;
	public static String format = ".png";
	Date date1 = new Date();
	SimpleDateFormat forma = new SimpleDateFormat("ddMMyy");

	public static SimpleDateFormat formatterExcel = new SimpleDateFormat("dd.MM.yyyy");;
	
	public static WebDriver driver;
	private static String PAGE_URL = "https://webapp-playground-leadqa.menu.app";

	TestCheckNumberOfLocations testCheckNumberOfLocations;
	TestToCheckWorkingHours checkWorkingHours;
	AddThreeItemsToCart addThreeItemsToCart;
	AddFiveItemsToCart addFiveItemsToCart;

	@BeforeSuite
	public void setExtent() throws Exception {

		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "./Reports/" + forma.format(date1) + "/MenuTechologies.html");
		htmlReporter.config().setDocumentTitle("Menu Techologies Report");// title of the report
		htmlReporter.config().setReportName("Menu Techologies Report");// name of the report
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("environment", "test");
		extent.setSystemInfo("Browser", "Chrome");

	}

	@BeforeTest
	public void setPDFAndXML() throws Exception {

		// create PDF document
		formatter = new SimpleDateFormat("yyyyddMMHHmm");
		System.out.println(formatter.format(date));
		Font boldFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		PdfWriter.getInstance(document, new FileOutputStream("./Reports/" + forma.format(date1) + "/"
				+ "/NRT_TestAutomationTask-" + formatter.format(date) + ".pdf"));
		document.open();
		document.add(new Paragraph(date.toString()));
		document.add(new Paragraph("MENU Technologies - performing automatic test", boldFont));

		// create folder for print-screens
		fis = new File("./Screenshots/");
		fis.mkdir();

		// create csv file
		fos = new File("./Reports/" + forma.format(date1) + "/" + "/NRT_TestAutomationTask-" + formatter.format(date)
				+ ".csv");

		myexcel = Workbook.createWorkbook(fos);
		mysheet = myexcel.createSheet("mySheet", 0);
		// first right - second down
		mysheet.setColumnView(1, 40);
		mysheet.setColumnView(2, 12);
		mysheet.setColumnView(3, 125);
		mysheet.setColumnView(4, 20);
		mysheet.setColumnView(5, 30);
		mysheet.setColumnView(6, 30);
		mysheet.addCell(new Label(1, 0, "TEST NAME"));
		mysheet.addCell(new Label(2, 0, "TEST STEP"));
		mysheet.addCell(new Label(3, 0, "NRT"));
		mysheet.addCell(new Label(4, 0, "RESULT"));
		mysheet.addCell(new Label(5, 0, "EXECUTION TIME"));
		mysheet.addCell(new Label(6, 0, "EROR LOG"));

		for (int i = 1; i < 12; i++) {

			mysheet.addCell(new Label(1, i, "MENU Web Automation Task"));
			mysheet.addCell(new Label(2, i, String.valueOf(i)));

		}

	}

	@Test(priority = 0)
	public void runingMethod() throws Exception {

		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to(PAGE_URL);

		driver.manage().window().maximize();

		testCheckNumberOfLocations = new TestCheckNumberOfLocations(driver);
		checkWorkingHours = new TestToCheckWorkingHours(driver);
		addThreeItemsToCart = new AddThreeItemsToCart(driver);
		addFiveItemsToCart = new AddFiveItemsToCart(driver);
	}

	@Test(priority = 1)
	public void checkNumberOfLocation() throws Exception {

		test = extent.createTest("Check number of locations");

		testCheckNumberOfLocations.clickDineInButton();
		testCheckNumberOfLocations.setinsertlocation("Novi Sad");
		testCheckNumberOfLocations.setselectLocation();
		testCheckNumberOfLocations.numberOfLocation();

	}

	@Test(priority = 2)
	public void checkWorkingHoursForSunday() throws Exception {

		TestPage.test = TestPage.extent.createTest("Check working hours for Sunday");

		checkWorkingHours.selectTestLocation();
		checkWorkingHours.selectMoreInfoButton();
		checkWorkingHours.getWorkingHours();

	}

	@Test(priority = 3)
	public void checkCanThreeItemsBeAdded() throws Exception {

		TestPage.test = TestPage.extent.createTest("Check can items (3) be added to cart");

		addThreeItemsToCart.acceptCookies();
		addThreeItemsToCart.selectTestItem();
		addThreeItemsToCart.selectAddButton();
		addThreeItemsToCart.verifyTotalValueInCheckoutSection();

	}

	@Test(priority = 4)
	public void checkCanFiveItemsBeAdded() throws Exception {

		TestPage.test = TestPage.extent.createTest("Check can items (5) be added to cart");

		driver.navigate().to(PAGE_URL);

		testCheckNumberOfLocations.clickDineInButton();
		testCheckNumberOfLocations.setinsertlocation("Novi Sad");
		testCheckNumberOfLocations.setselectLocation();
		checkWorkingHours.selectTestLocation();
		addThreeItemsToCart.acceptCookies();
		addFiveItemsToCart.selectTestItemToIncreaseQuantity();
		addFiveItemsToCart.clickAddButton();
		addFiveItemsToCart.verifyTotalValueInCheckoutSectionForFiveItems();

	}

	public static void printScreen(String printScreenName, String pdfText) throws Exception {

		String fileName = printScreenName + format;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("./screenshots/" + fileName));

		// adding img into pdf
		Image img = Image.getInstance("./Screenshots/" + fileName);
		img.scaleToFit(500f, 500f);
		document.add(new Paragraph("\n\n" + pdfText + "\n\n"));
		document.add(img);
		document.add(new Paragraph("\n\n"));
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

		String dateName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "\\Reports\\ScreenshotFailed" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);

		return destination;
	}

	public static void implicitWait(WebElement webElement) throws IOException {

		WebDriverWait wait = new WebDriverWait(driver, 2);
		wait.until(ExpectedConditions.visibilityOf(webElement));

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, InterruptedException {

		if (result.getStatus() == result.FAILURE) {

			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName());
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable() + ExtentColor.RED);

			String screenPath = TestPage.getScreenshot(driver, result.getName());

			// System.out.println("screenPath " + screenPath);
			test.addScreenCaptureFromPath(screenPath);

		} else if (result.getStatus() == result.SKIP) {
			test.log(Status.SKIP, "Test case SKIPPED IS " + result.getName());

		}
	}

	@AfterTest
	public void closePDFAndXML() throws IOException {

		extent.flush();
		document.close();
		myexcel.write();
		myexcel.close();

		File newfile = new File("./Reports/" + forma.format(date1) + "/" + "/NRT_TestAutomationTask_KO-"
				+ formatter.format(date) + ".csv");

		boolean exist = false;

		for (int m = 0; m < mysheet.getRows(); m++) {
			if (mysheet.getCell(4, m).getContents().equals("KO")) {
				exist = true;

				break;
			}
		}
		System.out.println("exist " + exist);
		if (exist) {

			fos.renameTo(newfile);

			System.out.println("File renamed successfully");
		} else if (!exist) {
			System.out.println("The file works correctly");
		}

		// for deleting screenshots folder
		File index = new File(fis.getCanonicalPath());
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			currentFile.delete();
			index.delete();
		}

		driver.close();
		driver.quit();
	}

}
