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

public class AddThreeItemsToCart {

	static WebDriver driver;

	@FindBy(xpath = "//button[text()='Okay']")
	WebElement acceptCookies;

	@FindBy(xpath = "//h3[contains(text(),'Margarita')]/../../..")
	WebElement selectMargarita;

	@FindBy(xpath = "//*[@aria-label='Increase quantity by 1']")
	WebElement increaseQuantity;

	@FindBy(xpath = "//div[text()='Add']/../..")
	WebElement selectAddButton;

	@FindBy(xpath = "//div[text()='Checkout']/..//div[@class='btn-info']")
	WebElement verifyValueInCheckoutSection;

	public AddThreeItemsToCart(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	public void acceptCookies() throws InterruptedException {

		try {
			acceptCookies.click();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void selectTestItem() throws Exception {

		TestPage.test.log(Status.PASS,
				"After clicking button that corresponds to 'Test item',increase quantity to 3 on modal that has just opened");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		Thread.sleep(3000);

		TestPage.printScreen("test5",
				"After clicking button that corresponds to 'Test item',increase quantity to 3 on modal that has just opened");
		// for excel
		TestPage.mysheet.addCell(new Label(3, 6,
				"After clicking button that corresponds to 'Test item',increase quantity to 3 on modal that has just opened"));
		TestPage.mysheet.addCell(new Label(4, 6, "OK"));
		TestPage.mysheet.addCell(new Label(5, 6, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 6, ""));

		selectMargarita.click();

		Thread.sleep(2000);

		TestPage.test.log(Status.PASS, "Click on '+' icon to Increase quantity");
		TestPage.printScreen("test6", "\n\n\nClick on '+' icon to Increase quantity");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 7, "Click on '+' icon to Increase quantity"));
		TestPage.mysheet.addCell(new Label(4, 7, "OK"));
		TestPage.mysheet.addCell(new Label(5, 7, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 7, ""));

		// To wait for element visible
		TestPage.implicitWait(increaseQuantity);

		for (int i = 1; i < 3; i++) {
			try {
				increaseQuantity.click();
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void selectAddButton() {

		selectAddButton.click();

	}

	public void verifyTotalValueInCheckoutSection() throws Exception {

		Thread.sleep(3000);

		String checkoutValue = verifyValueInCheckoutSection.getText();

		System.out.println("checkoutValue " + checkoutValue);

		TestPage.test.log(Status.INFO, "Verify that the value of 'Total' in checkout section is 24.00 €");
		TestPage.printScreen("test7", "Verify that the value of 'Total' in checkout section is 24.00 €");

		// for excel
		TestPage.mysheet.addCell(new Label(3, 8, "Verify that the value of 'Total' in checkout section is 24.00 €"));
		TestPage.mysheet.addCell(new Label(5, 8, TestPage.formatterExcel.format(TestPage.date) + " " +new Time(System.currentTimeMillis())));
		TestPage.mysheet.addCell(new Label(6, 8, ""));

		if (checkoutValue.contains("24.00")) {

			TestPage.test.log(Status.PASS, "CORRECT : 'Total' value in checkout section is NOT 24.00 € ");
			TestPage.document.add(new Paragraph("\n CORRECT : 'Total' value in checkout section is 24.00 € \n\n"));
			TestPage.mysheet.addCell(new Label(4, 8, "OK"));

		} else {

			TestPage.test.log(Status.ERROR, "'Total' value in checkout section is NOT 24.00 €");
			TestPage.document.add(new Paragraph("\n Wrong : 'Total' value in checkout section is NOT 24.00 € \n"));
			TestPage.mysheet.addCell(new Label(4, 8, "KO"));

		}

	}

}
