package selenium_api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_UploadFile {
	WebDriver driver;

	/*
	 * Get ra đường dẫn của thư mục đang đứng D:\AT_TestOnline_07\Online_Class_07\WebDriverAPI
	 */
	String rootFolder = System.getProperty("user.dir");

	String fileName01 = "01.jpg";
	String fileName02 = "02.png";
	String fileName03 = "03.jpg";
	String filePath01 = rootFolder + "\\uploadFile\\" + fileName01;
	String filePath02 = rootFolder + "\\uploadFile\\" + fileName02;
	String filePath03 = rootFolder + "\\uploadFile\\" + fileName03;

	@BeforeClass
	public void beforeClass() {
		// Chrome
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();

		// Firefox
		// driver = new FirefoxDriver();

		// IE
		// System.setProperty("webdriver.chrome.driver", ".\\lib\\IEDriverServer.exe");
		// driver = new InternetExplorerDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println(driver.toString().toLowerCase());
	}

	public void TC_01_SingleUpload() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		WebElement addFileButton = driver.findElement(By.xpath("//input[@type='file']"));
		addFileButton.sendKeys(filePath01);
		Thread.sleep(4000);
	}

	public void TC_02_MultipleUploadFile() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		Thread.sleep(5000);

		WebElement addFileButton = driver.findElement(By.xpath("//input[@type='file']"));
		addFileButton.sendKeys(filePath01 + "\n" + filePath02 + "\n" + filePath03);
		Thread.sleep(4000);

		/* Verify file sendkey success */
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + fileName01 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + fileName02 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + fileName03 + "']")).isDisplayed());

		/* Click to start button to Upload file */
		List<WebElement> startButtons = driver.findElements(By.xpath("//table//button[@class='btn btn-primary start']"));
		for (WebElement startBtn : startButtons) {
			startBtn.click();
			Thread.sleep(3000);
		}
		/* Verify file upload success */
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@href and @title='" + fileName01 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@href and @title='" + fileName02 + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@href and @title='" + fileName03 + "']")).isDisplayed());
	}

	@Test
	public void TC_03_AutoIT() throws Exception {
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");

		// Click to addFile Button -> Open file dialog

		driver.findElement(By.xpath("//span[contains(text(),'Add files...')]")).click();

		if (driver.toString().toLowerCase().contains("chrome")) {
			driver.findElement(By.cssSelector(".fileinput-button")).click();
		} else if (driver.toString().toLowerCase().contains("firefox")) {
			clickToElementByJS(driver.findElement(By.xpath("//input[@type='file']")));
		} else if (driver.toString().toLowerCase().contains("internet explorer")) {
			driver.findElement(By.xpath("//span[contains(text(),'Add files...')]")).click();

		}
		Thread.sleep(3000);

		// Excute 1 file exe
		if (driver.toString().toLowerCase().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] { ".\\uploadFile\\chrome.exe", filePath01 });
		} else if (driver.toString().toLowerCase().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { ".\\uploadFile\\firefox.exe", filePath01 });
		} else if (driver.toString().toLowerCase().contains("internet explorer")) {
			Runtime.getRuntime().exec(new String[] { ".\\uploadFile\\ie.exe", filePath01 });

		}
		Thread.sleep(5000);

		/* Verify file sendkey success */
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + fileName01 + "']")).isDisplayed());

		/* Click to start button to Upload file */
		List<WebElement> startButtons = driver.findElements(By.xpath("//table//button[@class='btn btn-primary start']"));
		for (WebElement startBtn : startButtons) {
			startBtn.click();
			Thread.sleep(3000);
		}
		
		/* Verify file upload success */
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[@href and @title='" + fileName01 + "']")).isDisplayed());
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public Object clickToElementByJS(WebElement element) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

}
