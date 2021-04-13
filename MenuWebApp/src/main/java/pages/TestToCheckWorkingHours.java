package pages;

import jxl.write.Label;
import test.TestPage;

import java.sql.Time;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.itextpdf.text.Paragraph;

public class TestToCheckWorkingHours {

	static WebDriver driver;

	@FindBy(xpath= "//h3[contains(text(),'Petricevic One')]")
	WebElement selectTestLocation;

	@FindBy(xpath = "//button[text()='More info']")
	WebElement selectMoreInfo;

	@FindBy(xpath = "//span[contains(text(), 'Sunday')]/..//span[@class='pr4 opening-hour-text']")
	WebElement workingHours;

	@FindBy(xpath = "//span[text()='Petricevic One']/../..//button[@class='close-button']")
	WebElement closePopUpWindowOpeningHours;

	public TestToCheckWorkingHours(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	public void selectTestLocation() throws Exception {

		// To wait for element visible
		TestPage.implicitWait(selectTestLocation);
		
		TestPage.test.log(Status.PASS,
				"From the list of locations on the left, click on the one that corresponds to venue 'Test venue'");

		selectTestLocation.click();

		Thread.sleep(3000);

		TestPage.printScreen("test3",
				"From the list of locations on the left, click on the one that corresponds to venue 'Test venue'");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 3,
				"From the list of locations on the left, click on the one that corresponds to venue 'Test venue'"));
		TestPage.mysheet.addCell(new Label(4, 3, "OK"));
		TestPage.mysheet.addCell(new Label(5, 3, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 3, ""));
	}

	public void selectMoreInfoButton() throws Exception {

		TestPage.test.log(Status.PASS, "Click on 'More info' button");

		// adding img into pdf

		TestPage.document.add(new Paragraph("\n\nClick on 'More info' button \n\n"));
		// for excel
		TestPage.mysheet.addCell(new Label(3, 4, "Click on 'More info' button"));
		TestPage.mysheet.addCell(new Label(4, 4, "OK"));
		TestPage.mysheet.addCell(new Label(5, 4, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 4, ""));

		selectMoreInfo.click();
		Thread.sleep(2000);

	}

	public void getWorkingHours() throws Exception {

		WebElement Element = driver
				.findElement(By.xpath("//div[@class='opening-hour']/span[contains(text(), 'Sunday')]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Scrolling down the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);

		String workingHour = workingHours.getText();

		System.out.println("workingHour " + workingHour);

		Thread.sleep(2000);

		TestPage.test.log(Status.INFO, "Verify that the working hours for Sunday are from '8:00 AM - 8:00 PM'");

		TestPage.printScreen("test4", "Verify that the working hours for Sunday are  '8:00 AM - 8:00 PM'");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 5, "Verify that the working hours for Sunday are  '8:00 AM - 8:00 PM'"));
		TestPage.mysheet.addCell(new Label(5, 5, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 5, ""));

		String am = "";
		String pm = "";
		String[] number = workingHour.split("-");

		for (String hour : number) {

			if (hour.contains("AM")) {
				am = hour;
			} else {
				pm = hour;
			}

		}

		if (am.contains("8:00 AM") && pm.contains("8:00 PM")) {

			TestPage.test.log(Status.PASS, "Correct : Working hours for Sunday are from '8:00 AM - 8:00 PM'");
			TestPage.document
					.add(new Paragraph("\n Correct : Working hours for Sunday are from '8:00 AM - 8:00 PM' \n\n"));
			TestPage.mysheet.addCell(new Label(4, 5, "OK"));

		} else {

			TestPage.test.log(Status.ERROR, "Wrong : Working hours for Sunday are not from '8:00 AM - 8:00 PM'");
			TestPage.document
					.add(new Paragraph("\n Wrong : Working hours for Sunday are not from '8:00 AM - 8:00 PM' \n\n"));
			TestPage.mysheet.addCell(new Label(4, 5, "KO"));
		}

		TestPage.implicitWait(closePopUpWindowOpeningHours);

		closePopUpWindowOpeningHours.click();

	}

}
