package selenium_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Button_Radio_Checkbox_Alert {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		// Chrome
		// System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		// driver = new ChromeDriver();

		// Firefox
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_HandleBuutton() {
		driver.get("http://live.guru99.com/");

		// Click to My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		// Verify page URL
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");

		// Click Create an Account button
		WebElement createAnAccountButton = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		commons.clickElementByJavascript(driver, createAnAccountButton);

		// Verify page URL
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_Checkbox_RadioButton_Html() {
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		// Click under 18 Radio button + Development Checkbox
		WebElement under18Radio = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement developmentCheckbox = driver.findElement(By.xpath("//input[@id='development']"));
		under18Radio.click();
		developmentCheckbox.click();

		// Verify
		Assert.assertTrue(under18Radio.isSelected());
		Assert.assertTrue(developmentCheckbox.isSelected());

	}

	@Test
	public void TC_03_Checkbox_Custom() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");

		/*---------------------Cach 1--------------------------
		// Click to 'Dual-zone air conditioning' checkbox
		WebElement dualzoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']"));
		dualzoneCheckbox.click();
		
		// Verify dualzoneCheckbox selected
		Assert.assertTrue(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		
		// Uncheck
		dualzoneCheckbox.click();
		
		// Verify dualzoneCheckbox un-selected
		Assert.assertFalse(driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input")).isSelected());
		}
		 */

		// ---------------------Cach 2--------------------------
		// Click to 'Dual-zone air conditioning' checkbox
		WebElement dualZoneCheckbox = driver.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));

		// Click chon OK
		clickElementByJavascript(dualZoneCheckbox);

		// Verify dualzoneCheckbox selected
		Assert.assertTrue(dualZoneCheckbox.isSelected());

		// Uncheck
		clickElementByJavascript(dualZoneCheckbox);

		// Verify dualzoneCheckbox un-selected
		Assert.assertFalse(dualZoneCheckbox.isSelected());
	}

	@Test
	public void TC_04_RadioButton() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");

		// Click to petrolRadioButton
		WebElement petrolRadioButton = driver.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		WebElement dot8RadioButton = driver.findElement(By.xpath("//label[text()='1.8 Petrol, 118kW']/preceding-sibling::input"));

		// Click chon OK
		clickElementByJavascript(petrolRadioButton);

		// Verify petrolRadioButton selected
		Assert.assertTrue(petrolRadioButton.isSelected());

		// Uncheck
		clickElementByJavascript(dot8RadioButton);
		Assert.assertTrue(dot8RadioButton.isSelected());

		// Verify petrolRadioButton un-selected
		Assert.assertFalse(petrolRadioButton.isSelected());

	}

	@Test
	public void TC_05_Alert() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		By resultMessage = By.xpath("//p[@id='result']");
		Alert alert;
		String name = "dangthilien";

		// Practice 01
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		Thread.sleep(3000);

		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Thread.sleep(3000);

		// Message display
		Assert.assertTrue(driver.findElement(resultMessage).getText().equals("You clicked an alert successfully"));

		Assert.assertEquals(driver.findElement(resultMessage).getText(), "You clicked an alert successfully");

		// Practice 02
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		Thread.sleep(3000);

		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Thread.sleep(3000);

		// Message display
		Assert.assertTrue(driver.findElement(resultMessage).getText().equals("You clicked: Cancel"));
		Assert.assertEquals(driver.findElement(resultMessage).getText(), "You clicked: Cancel");

		// Practice 03
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		Thread.sleep(3000);

		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(name);
		alert.accept();
		Thread.sleep(3000);

		// Message display
		Assert.assertTrue(driver.findElement(resultMessage).getText().equals("You entered: " + name));
		Assert.assertEquals(driver.findElement(resultMessage).getText(), "You entered: " + name);
	}

	@Test
	public void TC_06_AuthenticationAlert() {
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");

		Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]")).isDisplayed());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// Không quan tâm element có visible hay không
	public void clickElementByJavascript(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click();", element);
	}
}
