package selenium_api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Frame_Iframe {

	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		// Chrome
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();

		// Firefox
		// driver = new FirefoxDriver();
		driver.manage().window().maximize();

		// Ảnh hưởng trực tiếp đến 2 thằng: findElement/ findElements
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Iframe() {
		driver.get("http://www.hdfcbank.com/");

		// Issue 01: Check element not displayed
		List<WebElement> notification = driver.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		System.out.println("Number of element = " + notification.size());
		// Case 01: có hiển thị popup
		// Case 02: Không hiển thị popup
		if (notification.size() > 0) {
			// Switch to iframe (notification)
			driver.switchTo().frame(notification.get(0));
			clickElementByJavascript(driver.findElement(By.cssSelector("#div-close")));
			System.out.println("Close popup success!");
			
			//Issue 03: Switch to default content
			// Switch top windown (parent)
			driver.switchTo().defaultContent();
		}
		// Step 03 - Verify đoạn text được hiển thị: What are you looking for? (switch qua iframe nếu có)
		// Switch to iframe (notification)
		System.out.println("Switch to looking from iframe");

		// Issue 02: Iframe ramdom ID
		WebElement lookingForIframe = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(lookingForIframe);
		String messageText = driver.findElement(By.xpath("//span[@id='messageText']")).getText();
		Assert.assertEquals(messageText, "What are you looking for?");

		driver.switchTo().defaultContent();
		
		//Switch to iframe (sliding image)
		WebElement slidingIframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(slidingIframe);
		
		List <WebElement> bannerImg = driver.findElements(By.xpath("//div[@id='bannercontainer']//img[@class='bannerimage']"));
		int bannerImgNumber = bannerImg.size();
		
		//Verify banner có đúng 6 images
		Assert.assertEquals(bannerImgNumber, 6);
		
		driver.switchTo().defaultContent();
		
		//Verify flipper banner được hiển thị và có 8 items
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='flipBanner']")).isDisplayed());
		
		List <WebElement> flipBannerImg = driver.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		Assert.assertEquals(flipBannerImg.size(), 8);
		
		//Check displayed 8 Images
		for (WebElement image: flipBannerImg) {
			System.out.println("Flipping banner = " + image.isDisplayed());
			Assert.assertTrue(image.isDisplayed());
		}
		}


	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void clickElementByJavascript(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click();", element);
	}
}
