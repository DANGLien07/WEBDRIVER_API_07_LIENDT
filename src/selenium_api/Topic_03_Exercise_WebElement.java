package selenium_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Exercise_WebElement {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		driver = new FirefoxDriver();
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// Phóng to trình duyệt
		driver.manage().window().maximize();

		// Wait element (findElement) trong 30s (WebDriverWait)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	// ========== Catche of elenments by Xpath/Locators ===========
	// Enabled
	By emailByTextbox = By.xpath("//input[@id='mail']");
	By ageUnder18ByRadio = By.xpath("//input[@id='under_18']");
	By educationByTextArea = By.xpath("//textarea[@id='edu']");
	By jobRole01BySelect = By.xpath("//select[@id='job1']");
	By interestsByCheckbox = By.xpath("//input[@id='development']");
	By slider01BySlider = By.xpath("//input[@id='slider-1']");
	By buttonByBtnenabled = By.xpath("//button[@id='button-enabled']");

	// Disabled
	By radiobuttondisabledByRadio = By.xpath("//input[@id='radio-disabled']");
	By addressByTextbox = By.xpath("//input[@id='address']");
	By passwordByTextbox = By.xpath("//input[@id='password']");
	By biographyByTextArea = By.xpath("//textarea[@id='bio']");
	By jobRole02BySelect = By.xpath("//select[@id='job2']");
	By checkboxDisabledByCheckbox = By.xpath("//input[@id='check-disbaled']");
	By slider02BySlider = By.xpath("//input[@id='slider-2']");
	By buttonDisabledByBtndisabled = By.xpath("//button[@id='button-disabled']");

	@Test
	public void TC_01_IsDisplayed() {
		// Test Script 01: Kiểm tra phần tử hiển thị trên trang
		// Cách 1
		/*
		 * if (driver.findElement(emailByTextbox).isDisplayed()) {
		 * driver.findElement(emailByTextbox).sendKeys("Automation Testing"); } else {
		 * System.out.println("Element [" + emailByTextbox + "] is not display!"); } if
		 * (driver.findElement(ageUnder18ByRadio).isDisplayed()) {
		 * driver.findElement(ageUnder18ByRadio).sendKeys("Automation Testing"); } else
		 * { System.out.println("Element [" + ageUnder18ByRadio + "] is not display!");
		 * } if (driver.findElement(educationByTextarea).isDisplayed()) {
		 * driver.findElement(educationByTextarea).click(); } else {
		 * System.out.println("Element [" + educationByTextarea + "] is not display!");
		 * } // if(driver.findElement(addressByTextbox).isDisplayed()){ //
		 * driver.findElement(addressByTextbox).sendKeys("Automation Testing"); // }
		 * else { // System.out.println("Element ["+ addressByTextbox +
		 * "] is not display!"); // }
		 */

		// Cách 2
		if (isControlDisplayed(emailByTextbox)) {
			driver.findElement(emailByTextbox).sendKeys("Automation Testing");
		}

		if (isControlDisplayed(ageUnder18ByRadio)) {
			driver.findElement(ageUnder18ByRadio).click();
		}

		if (isControlDisplayed(educationByTextArea)) {
			driver.findElement(educationByTextArea).sendKeys("Automation Testing");
		}
	}

	@Test
	public void TC_02_IsEnabled() {
		// Test Script 02: Kiểm tra phần tử enable/ disable trên trang

		// Enabled
		Assert.assertTrue(isControlEnabled(emailByTextbox));
		Assert.assertTrue(isControlEnabled(ageUnder18ByRadio));
		Assert.assertTrue(isControlEnabled(educationByTextArea));
		Assert.assertTrue(isControlEnabled(jobRole01BySelect));
		Assert.assertTrue(isControlEnabled(interestsByCheckbox));
		Assert.assertTrue(isControlEnabled(slider01BySlider));
		Assert.assertTrue(isControlEnabled(buttonByBtnenabled));

		// Disabled
		Assert.assertFalse(isControlEnabled(passwordByTextbox));
		Assert.assertFalse(isControlEnabled(radiobuttondisabledByRadio));
		Assert.assertFalse(isControlEnabled(biographyByTextArea));
		Assert.assertFalse(isControlEnabled(jobRole02BySelect));
		Assert.assertFalse(isControlEnabled(checkboxDisabledByCheckbox));
		Assert.assertFalse(isControlEnabled(slider02BySlider));
		Assert.assertFalse(isControlEnabled(buttonDisabledByBtndisabled));
	}

	@Test
	public void TC_03_IsSelected() {
		// Test Script 03: Kiểm tra phần tử được chọn trên trang

		// Step 02 - Click chọn Age (Under 18)/ Interests (Development)
		driver.findElement(ageUnder18ByRadio).click();
		driver.findElement(interestsByCheckbox).click();

		// Step 03 - Kiểm tra các phần tử tại Step 02 đã được chọn
		Assert.assertTrue(isControlSelected(ageUnder18ByRadio));
		Assert.assertTrue(isControlSelected(interestsByCheckbox));

		// Click để bỏ chọn development checkbox
		driver.findElement(interestsByCheckbox).click();
		Assert.assertFalse(isControlSelected(interestsByCheckbox));

		// Step 04 - Nếu chưa được chọn thì cho phép chọn lại 1 lần nữa
		if (!isControlSelected(interestsByCheckbox)) {
			driver.findElement(interestsByCheckbox).click();
			Assert.assertTrue(isControlEnabled(interestsByCheckbox));

		}

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public boolean isControlDisplayed(By by) {
		WebElement element = driver.findElement(by);
		if (element.isDisplayed()) {
			System.out.println("Element [" + by + "] is displayed!");
			return true;
		} else {
			System.out.println("Element [" + by + "] is not displayed!");
			return false;
		}
	}

	public boolean isControlSelected(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			System.out.println("Element [" + by + "] is selected!");
			return true;
		} else {
			System.out.println("Element [" + by + "] is not selected!");
			return false;
		}
	}

	public boolean isControlEnabled(By by) {
		WebElement element = driver.findElement(by);
		if (element.isEnabled()) {
			System.out.println("Element [" + by + "] is enabled!");
			return true;
		} else {
			System.out.println("Element [" + by + "] is not enabled!");
			return false;
		}
	}
}