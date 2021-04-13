package pages;

import jxl.write.Label;
import test.TestPage;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import com.aventstack.extentreports.Status;
import com.itextpdf.text.Paragraph;

public class TestCheckNumberOfLocations {

	static WebDriver driver;

	public static int countLocation;

	@FindBy(xpath = "//button[@alt='Dine-in Pickup']")
	WebElement DineInButton;

	@FindBy(xpath = "//input[@aria-label='Select location']")
	WebElement insertlocation;

	@FindBy(xpath = "//p[text()='Novi Sad, Serbia']")
	WebElement selectLocation;

	@FindBy(xpath = "//*[@aria-label='List of locations']/button")
	static List<WebElement> numberOfLocation; // should be 2 numbers of location

	public TestCheckNumberOfLocations(WebDriver driver) {

		this.driver = driver;

		// This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	public void clickDineInButton() throws IOException {

		TestPage.test.log(Status.PASS, "Click Order Type 'Dine-In' button");

		// To wait for element visible
		TestPage.implicitWait(DineInButton);
		DineInButton.click();

	}

	public void setinsertlocation(String location) throws Exception {

		// To wait for element visible
		TestPage.implicitWait(insertlocation);

		insertlocation.clear();
		insertlocation.sendKeys(location);

		Thread.sleep(2000);

		TestPage.test.log(Status.PASS, "Enter <Test location> into Select location field");
		TestPage.printScreen("test",
				"Click Order Type 'Dine-In' button -> Enter location -> Select the one that corresponds to the test location");

	}

	public void setselectLocation() throws Exception {

		Thread.sleep(2000);

		TestPage.test.log(Status.PASS,
				"Select the one that corresponds to the Test location. Map with locations and their details will be shown.");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 1,
				"Click Order Type “Dine-In” button ->Enter <Test location -> Select the one that corresponds to the <Test location>"));
		TestPage.mysheet.addCell(new Label(4, 1, "OK"));
		TestPage.mysheet.addCell(new Label(5, 1, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 1, ""));

		selectLocation.click();
	}

	public static int numberOfLocation() throws Exception {

		Thread.sleep(3000);

		TestPage.test.log(Status.INFO, "Verify that number of avaliable locations (venues) is 2");
		TestPage.printScreen("test1", "Verify that number of avaliable locations (venues) is 2");
		// for excel
		TestPage.mysheet.addCell(new Label(3, 2, "Verify that number of avaliable locations (venues) is 2 "));
		TestPage.mysheet.addCell(new Label(5, 2, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 2, ""));

		countLocation = numberOfLocation.size();

		System.out.println("the number of avaliable locations (venues) is :" + countLocation + "\n");

		if (countLocation == 2) {

			TestPage.test.log(Status.PASS, "Correct : the number of avaliable locations (venues) is 2");
			TestPage.document.add(new Paragraph("\n\nCorrect : the number of avaliable locations (venues) is 2 \n\n"));
			TestPage.mysheet.addCell(new Label(4, 2, "OK"));

		} else {

			TestPage.test.log(Status.ERROR, "ERROR : the number of avaliable locations (venues) is NOT 2");
			TestPage.document.add(new Paragraph("\n ERROR : the number of avaliable locations (venues) is NOT 2 \n\n"));
			TestPage.mysheet.addCell(new Label(4, 2, "KO"));
		}

		return countLocation;
	}

}
