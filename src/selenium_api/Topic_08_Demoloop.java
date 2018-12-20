package selenium_api;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Demoloop {
	WebDriver driver;
	private String firstname, lastname, emailaddress, password, randomNumber;
	private String sourceFolder = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// Chrome
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();

		// Firefox
		// driver = new FirefoxDriver();
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(invocationCount=10)
	public void TC_06_CreateAnAccount() throws Exception {
		randomNumber = randomEmail() + "";
		firstname = "DANG" + randomNumber;
		lastname = "Lien" + randomNumber;
		emailaddress = "seleniumonline" + randomNumber + "@gmail.com";
		password = "123456";
		
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		driver.findElement(By.id("firstname")).sendKeys(firstname);
		driver.findElement(By.id("lastname")).sendKeys(lastname);
		driver.findElement(By.id("email_address")).sendKeys(emailaddress);
		driver.findElement(By.className("validate-password")).sendKeys(password);
		driver.findElement(By.className("validate-cpassword")).sendKeys(password);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']")).isDisplayed());
		
		//Capture screenshot
		takeSnapShot(driver, sourceFolder +"\\screenshot\\"+ GetTimeStampValue()+ ".png");
		
		driver.findElement(By.xpath("//div[@class='page-header-container']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]")).isDisplayed());

	}

	public int randomEmail() {		
		Random random = new Random(); // tạo thư viện radom
		int number = random.nextInt(999999); // tạo biến number
		System.out.println("Random number = " + number);
		return number;
	}
	public  void takeSnapShot(WebDriver webdriver, String fileName) {
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File sourceFile = scrShot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(fileName);
		try {
			FileUtils.copyFile(sourceFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
				}

	public  String GetTimeStampValue()throws IOException{
	    Calendar cal = Calendar.getInstance();       
	    Date time=cal.getTime();
	    String timestamp=time.toString();
	    System.out.println(timestamp);
	    String systime=timestamp.replace(":", "-");
	    System.out.println(systime);
	    return systime;
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}