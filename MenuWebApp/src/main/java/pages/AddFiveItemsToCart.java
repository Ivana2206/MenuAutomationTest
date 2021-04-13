package pages;

import jxl.write.Label;
import test.TestPage;

import java.sql.Time;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.itextpdf.text.Paragraph;

public class AddFiveItemsToCart {

	static WebDriver driver;

	@FindBy(xpath = "//h3[contains(text(),'Margarita')]/../../..")
	WebElement selectMargarita;

	@FindBy(xpath = "//*[@aria-label='Increase quantity by 1']")
	WebElement increaseQuantity;

	@FindBy(xpath = "//div[text()='Add']/../..")
	WebElement selectAddButton;

	@FindBy(xpath = "//div[text()='Checkout']/..//div[@class='btn-info']")
	WebElement verifyValueInCheckoutSection;

	public AddFiveItemsToCart(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);

	}

	public void selectTestItemToIncreaseQuantity() throws Exception {

		TestPage.test.log(Status.PASS,
				"After clicking button that corresponds to 'Test item',increase quantity to 5 on modal that has just opened");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		Thread.sleep(3000);

		TestPage.printScreen("test8",
				"After clicking button that corresponds to 'Test item',increase quantity to 5 on modal that has just opened");
		// for excel
		TestPage.mysheet.addCell(new Label(3, 9,
				"After clicking button that corresponds to 'Test item',increase quantity to 5 on modal that has just opened"));
		TestPage.mysheet.addCell(new Label(4, 9, "OK"));
		TestPage.mysheet.addCell(new Label(5, 9,
				TestPage.formatterExcel.format(TestPage.date) + " " + new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 9, ""));

		selectMargarita.click();
		
		Thread.sleep(2000);
		
		TestPage.test.log(Status.PASS, "Click on '+' icon to Increase quantity");

		TestPage.printScreen("test9", "Click on '+' icon to Increase quantity\n\n\n");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 10, "Click on '+' icon to Increase quantity"));
		TestPage.mysheet.addCell(new Label(4, 10, "OK"));
		TestPage.mysheet.addCell(new Label(5, 10,
				TestPage.formatterExcel.format(TestPage.date) + " " + new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 10, ""));

		// To wait for element visible
		TestPage.implicitWait(increaseQuantity);

		for (int i = 1; i < 5; i++) {
			try {
				increaseQuantity.click();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void clickAddButton() throws InterruptedException {

		selectAddButton.click();

	}

	public void verifyTotalValueInCheckoutSectionForFiveItems() throws Exception {

		Thread.sleep(3000);

		String checkoutValue = verifyValueInCheckoutSection.getText();

		System.out.println("checkoutValue " + checkoutValue);

		Thread.sleep(2000);

		TestPage.test.log(Status.INFO, "Verify that the value of 'Total' in checkout section is 40.00 €");
		TestPage.printScreen("test10", "Verify that the value of 'Total' in checkout section is 40.00 €");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 11, "Verify that the value of 'Total' in checkout section is 40.00 €"));
		TestPage.mysheet.addCell(new Label(5, 11,
				TestPage.formatterExcel.format(TestPage.date) + " " + new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 11, ""));

		if (checkoutValue.contains("40.00")) {

			TestPage.test.log(Status.PASS, "CORRECT : 'Total' value in checkout section is NOT 40.00 € ");
			TestPage.document.add(new Paragraph("\n\n CORRECT : 'Total' value in checkout section is 40.00 € \n\n"));
			TestPage.mysheet.addCell(new Label(4, 11, "OK"));

		} else {

			TestPage.test.log(Status.ERROR, "'Total' value in checkout section is NOT 40.00 €");
			TestPage.document.add(new Paragraph("\n\n Wrong : 'Total' value in checkout section is NOT 40.00 € \n"));
			TestPage.mysheet.addCell(new Label(4, 11, "KO"));

		}

	}

}
