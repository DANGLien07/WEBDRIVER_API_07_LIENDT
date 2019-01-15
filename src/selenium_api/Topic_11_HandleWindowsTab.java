package selenium_api;

import java.util.List;
import java.util.Set;
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

public class Topic_11_HandleWindowsTab {

	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		// Chrome
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();

		// Firefox
		// driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void TC_01_Window_ID() throws Exception {
		driver.get("https://daominhdam.github.io/basic-form/index.html");

		String parentWindowID = driver.getWindowHandle();

		// Click "Opening a new windown"
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();

		switchToWindowByID(parentWindowID);

		// Verify navigate to Google page success
		Assert.assertEquals(driver.getTitle(), "Google");

		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

		switchToWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");

		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}

	public void TC_02_Window_Title() throws Exception {
		driver.get("http://www.hdfcbank.com/");
		String parentID = driver.getWindowHandle();

		// Kiểm tra popup xuất hiện
		List<WebElement> notification = driver.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		System.out.println("Number of element = " + notification.size());
		if (notification.size() > 0) {
			// Switch to iframe (notification)
			driver.switchTo().frame(notification.get(0));
			clickElementByJavascript(driver.findElement(By.cssSelector("#div-close")));
			System.out.println("Close popup success!");

			// Switch top windown (parent)
			driver.switchTo().defaultContent();
		}

		driver.findElement(By.xpath("//a[text()='Agri']")).click();
		// Switch qua agri tab
		switchToWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		Assert.assertEquals(driver.getTitle(), "HDFC Bank Kisan Dhan Vikas e-Kendra");

		driver.findElement(By.xpath("//a[contains(., 'Account Details')]")).click();
		// Switch qua account detail tab
		switchToWindowByTitle("Welcome to HDFC Bank NetBanking");
		Assert.assertEquals(driver.getTitle(), "Welcome to HDFC Bank NetBanking");

		// Switch qua frame chứa Privacy Policy link
		driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name ='footer']")));

		driver.findElement(By.xpath("//a[text() = 'Privacy Policy']")).click();
		//Switch qua Privacy Policy tab
		switchToWindowByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		Assert.assertEquals(driver.getTitle(), "HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		
		driver.findElement(By.xpath("//a[text() = 'CSR']")).click();
		//Switch qua CSR tab
		Assert.assertEquals(driver.getTitle(), "HDFC Bank - CSR - Homepage");
		
		closeAllWindowsWithoutParentWindow(parentID);

	}
	// Đúng trong trường hợp có duy nhất 2 cửa sổ
	
	@Test
	public void TC_03_AddToCompare() throws Exception {
		driver.get("http://live.guru99.com/index.php/");
		  String parentID = driver.getWindowHandle();
		  
		  //Open Mobile Page
		  driver.findElement(By.xpath("//a[text() ='Mobile']")).click();
		  
		  //Add Sony Xperia and Samsung to compare
		  driver.findElement(By.xpath("//a[text() ='Sony Xperia']/parent::h2/following-sibling::div[@class ='actions']//a[text() ='Add to Compare']")).click();
		  driver.findElement(By.xpath("//a[text() ='Samsung Galaxy']/parent::h2/following-sibling::div[@class ='actions']//a[text() ='Add to Compare']")).click();
		  
		  driver.findElement(By.xpath("//button[@title = 'Compare']")).click();
		  switchToWindowByTitle("Products Comparison List - Magento Commerce");
		  Assert.assertEquals(driver.getTitle(), "Products Comparison List - Magento Commerce");
		  
		  closeAllWindowsWithoutParentWindow(parentID);
		
	}
	public void switchToWindowByID(String parentID) {
		// Get ra tất cả các cửa sổ đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp để kiểm tra
		for (String runWindow : allWindows) {

			// Mỗi lần duyệt kiểm tra điều kiện nếu: ID mà khác với parent ID thì nó sẽ switch qua
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);

				// Break khỏi vòng lặp for không kiểm tra nữa
				break;
			}
		}
	}

	// Đúng trong trường hợp từ 2 cửa sổ trở lên
	public void switchToWindowByTitle(String expectedTitle) {
		// Get ra tất cả các cửa sổ đang có
		Set<String> allWindows = driver.getWindowHandles();

		// Dùng vòng lặp duyệt qua tất cả các ID
		for (String runWindows : allWindows) {

			// Switch qua từng cửa sổ trước sau đó mới kiểm tra
			driver.switchTo().window(runWindows);

			// Get ra title của page mới Switch
			String actualTitle = driver.getTitle();

			// Nếu title của page = title truyền vào
			if (actualTitle.equals(expectedTitle)) {

				// Thoát khỏi vòng lặp - không kiểm tra những thằng khác nữa
				break;
			}
		}
	}

	public boolean closeAllWindowsWithoutParentWindow(String parentWindowID) {
		//Get ra tất cả ID của các cửa sổ	
		Set<String> allWindows = driver.getWindowHandles();
		
		//Dùng vòng lặp for để duyệt qua từng ID
		for (String runWindows : allWindows) {
			
			//Nếu ID mà không bằng parentID
			if (!runWindows.equals(parentWindowID)) {
				
				//Nó sẽ Switch qua
				driver.switchTo().window(runWindows);
				
				//Đóng cái tab hiện tại
				driver.close();
			}
		}
		
		//Chỉ còn lại 1 cửa sổ duy nhất (Parent)
		driver.switchTo().window(parentWindowID);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	public void clickElementByJavascript(WebElement element) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click();", element);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
