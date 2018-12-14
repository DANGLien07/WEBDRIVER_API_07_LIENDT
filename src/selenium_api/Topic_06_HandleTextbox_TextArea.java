package selenium_api;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_HandleTextbox_TextArea {
	WebDriver driver;
	private String newName, newDob, newAddress, newCity, newState, newPin, newMobile, newEmail, password;
	private String editAddress, editCity, editState, editPin, editMobile, editEmail, customerID;

	By nameByTextbox = By.xpath("//input[@name='name']");
	By doBByTextbox = By.xpath("//input[@name='dob']");
	By addressByTextarea = By.xpath("//textarea[@name='addr']");
	By cityByTextbox = By.xpath("//input[@name='city']");
	By stateByTextbox = By.xpath("//input[@name='state']");
	By pinByTextbox = By.xpath("//input[@name='pinno']");
	By mobileByTextbox = By.xpath("//input[@name='telephoneno']");
	By emailByTextbox = By.xpath("//input[@name='emailid']");
	By passwordByTextbox = By.xpath("//input[@name='password']");
	By sumitByTextbox = By.xpath("//input[@name='sub']");

	@BeforeClass
	public void beforeClass() {

		// System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		// driver = new ChromeDriver();

		driver = new FirefoxDriver();
		driver.get("http://demo.guru99.com/v4");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		/*------------input data------------*/
		newName = "Automation Test";
		newDob = "2012-07-28";
		newAddress = "Nghe An";
		newCity = "Vinh";
		newState = "Ben Thuy";
		newPin = "111111";
		newMobile = "0977865671";
		newEmail = "newtest" + commons.randomEmail() + "@gmail.com";
		password = "123123";

		editAddress = "Phu Yen";
		editCity = "Tuy Hoa";
		editState = "Phuong 7";
		editPin = "654321";
		editMobile = "0998776512";
		editEmail = "edittest" + commons.randomEmail() + "@gmail.com";

	}

	@Test
	public void TC_01_Newcustomer() {

		// Step 02 - Đăng nhập với thông tin: User = mngr167318 | Pass = jubUhyz
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys("mngr167318");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("jubUhyz");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		// Verify HomePage được hiển thị thành công
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());

		// Step 03 - Chọn menu New Customer
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		/*-----------New Customer User input data-----------*/
		driver.findElement(nameByTextbox).sendKeys(newName);
		driver.findElement(doBByTextbox).sendKeys(newDob);
		driver.findElement(addressByTextarea).sendKeys(newAddress);
		driver.findElement(cityByTextbox).sendKeys(newCity);
		driver.findElement(stateByTextbox).sendKeys(newState);
		driver.findElement(pinByTextbox).sendKeys(newPin);
		driver.findElement(mobileByTextbox).sendKeys(newMobile);
		driver.findElement(emailByTextbox).sendKeys(newEmail);
		driver.findElement(passwordByTextbox).sendKeys(password);
		driver.findElement(sumitByTextbox).click();

		/*-----------Get CustomerID-----------*/
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println("Customer ID = " + customerID);

		/*-----------Verify input Data Maching vs Output Data after creater customer success-----------*/
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), newName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), newDob);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), newAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), newCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), newState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), newPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), newMobile);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), newEmail);

		System.out.println("New = " + driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText());

	}

	@Test
	public void TC_02_Editcustomer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(customerID);
		driver.findElement(By.xpath("//input[@name='AccSubmit']")).click();

		/*-----------verify @Name/ Address matching vs ipput data at new Customer-----------*/
		Assert.assertEquals(driver.findElement(nameByTextbox).getAttribute("value"), newName);
		Assert.assertEquals(driver.findElement(addressByTextarea).getText(), newAddress);

		/*-----------Edit Customer User input data-----------*/
		driver.findElement(addressByTextarea).clear();
		driver.findElement(cityByTextbox).clear();
		driver.findElement(stateByTextbox).clear();
		driver.findElement(pinByTextbox).clear();
		driver.findElement(mobileByTextbox).clear();
		driver.findElement(emailByTextbox).clear();
		driver.findElement(addressByTextarea).sendKeys(editAddress);
		driver.findElement(cityByTextbox).sendKeys(editCity);
		driver.findElement(stateByTextbox).sendKeys(editState);
		driver.findElement(pinByTextbox).sendKeys(editPin);
		driver.findElement(mobileByTextbox).sendKeys(editMobile);
		driver.findElement(emailByTextbox).sendKeys(editEmail);
		driver.findElement(sumitByTextbox).click();

		/*-----------Verify input Data Maching vs Output Data after edit customer success-----------*/
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editMobile);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);

		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText());
		System.out.println("Edit =" + driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
