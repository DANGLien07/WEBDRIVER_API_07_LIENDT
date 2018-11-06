package selenium_api;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_02_Exercise {
	// Khai báo biến của Selenium webDriver
	WebDriver driver;

	// Annotation của testNG -> Chạy trước tất cả testcase để khởi tạo browser
	@BeforeClass
	public void beforeClass() {

		// Khởi tạo firefox browser lên (Multi-Browser)
		driver = new FirefoxDriver();

		// Phóng to trình duyệt
		driver.manage().window().maximize();

		// Wait element (findElement) được tìm thấy trong 30s (WebDriverWait)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	// Annotation đại diện cho 1 testcase
	@Test
	public void TC_01_CheckUrlAndTitle() {

		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Kiểm tra title của page là: "Home page"
		String homePageTitle = driver.getTitle();

		// Equals (Input = Output)
		Assert.assertEquals(homePageTitle, "Home page");
		// Assert.assertTrue(homePageTitle.equals("Home page")); -> có thể dùng ntn
		// Assert.assertTrue(homePageTitle.contains("Home")); -> có thể dùng ntn
		// Text()='My Account' -> Check text tuyệt đối (Check bằng nguyên chuỗi My
		// Account)
		// contains(text)('My') -> Check text tương đối (Check bằng My cũng Passed mà
		// bằng Account cũng passed)

		// Step 03 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Step 04 - Click CREATE AN ACCOUNT button để tới trang đăng kí tài khoản
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

		// Step 05 - Back lại trang đăng nhập
		driver.navigate().back();

		// Step 06 - Kiểm tra url của page đăng nhập là:
		// http://live.guru99.com/index.php/customer/account/login/
		String loginUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginUrl, "http://live.guru99.com/index.php/customer/account/login/");

		// Step 07 - Forward tới trang tạo tài khoản
		driver.navigate().forward();

		// Step 08 - Kiểm tra url của page tạo tài khoản là:
		// http://live.guru99.com/index.php/customer/account/create/
		String createAnAccountUrl = driver.getCurrentUrl();
		Assert.assertEquals(createAnAccountUrl, "http://live.guru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_LoginEmpty() {
		
		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Step 03 - Để trống Username/ Password
		// driver.findElement(By.xpath("//input[@id='email']")).sendKeys(" ");
		// driver.findElement(By.name("login[password]")).sendKeys("");

		// Step 04 - Click Login button
		driver.findElement(By.id("send2")).click();

		// Step 05 - Verify error message xuất hiện tại 2 field: This is a required field.
		String emailErrorMessage = driver.findElement(By.id("advice-required-entry-email")).getText();
		Assert.assertEquals(emailErrorMessage, "This is a required field.");
		String passErrorMessage = driver.findElement(By.id("advice-required-entry-pass")).getText();
		Assert.assertEquals(passErrorMessage, "This is a required field.");
	}

	@Test
	public void TC_03_LoginWithEmailInvalid() {

		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Step 03 - Nhập email invalid: 123434234@12312.123123
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("123434234@12312.123123");

		// Step 04 - Click Login button
		driver.findElement(By.id("send2")).click();

		// Step 05 - Verify error message xuất hiện: Please enter a valid email address. For example johndoe@domain.com.
		String emailErrorMessage = driver.findElement(By.id("advice-validate-email-email")).getText();
		Assert.assertEquals(emailErrorMessage, "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_04_LoginWithPasswordLessThan6Character() {
		
		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Step 03 - Nhập email correct and password incorrect: automation@gmail.com/123
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123");

		// Step 04 - Click Login button
		driver.findElement(By.id("send2")).click();

		// Step 05 - Verify error message xuất hiện: Please enter 6 or more characters
		// without leading or trailing spaces.
		String passErrorMessage = driver.findElement(By.id("advice-validate-password-pass")).getText();
		Assert.assertEquals(passErrorMessage, "Please enter 6 or more characters without leading or trailing spaces.");

	}

	@Test
	public void TC_05_LoginWithPasswordIncorrect() {
		
		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		
		//Step 03 - Nhập email correct and password incorrect: automation@gmail.com/ 123123123
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123123123");

		// Step 04 - Click Login button
		driver.findElement(By.id("send2")).click();
		
		//Step 05 - Verify error message xuất hiện: Invalid login or password.
		String passErrorMessage = driver.findElement(By.xpath("//span[contains(.,'Invalid login or password.')]")).getText();
		Assert.assertEquals(passErrorMessage, "Invalid login or password.");
	}

	@Test
	public void TC_06_CreateAnAccount() {
		String firstname = "DANG", lastname = "Lien", emailaddress = "seleniumonline" + randomEmail() + "@gmail.com",
				password = "123456";
		System.out.println("Email random = " + emailaddress);

		// Step 01 - Truy cập vào trang: http://live.guru99.com
		driver.get("http://live.guru99.com/");

		// Step 02 - Click vào link "My Account" để tới trang đăng nhập
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Step 03 - Click CREATE AN ACCOUNT button để tới trang đăng kí tài khoản
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();

		// Step 04 - Nhập thông tin hợp lệ vào tất cả các field: First Name/ Last Name/
		// Email Address/ Password/ Confirm Password
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		driver.findElement(By.id("email_address")).sendKeys(emailaddress);
		driver.findElement(By.className("validate-password")).sendKeys(password);
		driver.findElement(By.className("validate-cpassword")).sendKeys(password);

		// Step 05 - Click REGISTER button
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		// Step 06 - Verify message xuất hiện khi đăng kí thành công: Thank you for
		// registering with Main Website Store.
		Assert.assertTrue(
				driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']"))
						.isDisplayed());

		// Step 07 - Logout khỏi hệ thống
		driver.findElement(By.xpath("//div[@class='page-header-container']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();

		// Step 08 - Kiểm tra hệ thống navigate về Home page sau khi logout thành công
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed());

	}

	// Annotation của TestNG -> chạy 1 lần cuối cùng cho tất cả test case
	@AfterClass
	public void afterClass() {
		// Tắt Browser
		driver.quit();
	}

	public int randomEmail() {
		Random random = new Random(); // tạo thư viện radom
		int number = random.nextInt(999999); // tạo biến number
		System.out.println("Random number = " + number);
		return number;
	}
}
