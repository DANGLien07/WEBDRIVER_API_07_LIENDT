package selenium_api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sun.javafx.scene.paint.GradientUtils.Point;

public class Topic_05_WebBrowser_WebElement {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		//Khởi tạo browser
		// Chrome, set property trỏ đến file chromedriver.exe
		System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		driver = new ChromeDriver();

		// Firefox
		//driver = new FirefoxDriver();
		driver.get("https://www.google.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_WebBrowser() {
		/*------ WEB BROWSER ------*/
		// Đóng tab mà nó đang active
		driver.close();

		// Đóng trình duyệt
		driver.quit(); /*Hay dùng*/

		// Open Url
		driver.get("Ulr"); /*Hay dùng*/

		// Get ra title của page hiện tại
		String loginPageUrl = driver.getCurrentUrl(); /*Hay dùng*/

		// Get ra source code của page hiện tại
		String homePageSource = driver.getPageSource();
		// Dùng kiểm tra một cách tương đối - perfomance chạy nhanh
		Assert.assertTrue(homePageSource.contains("SELENIUM WEBDRIVER FROM DEMO"));

		// Get ra title của page hiện tại
		String homePageTitle = driver.getTitle(); /*Hay dùng*/
		Assert.assertEquals(homePageTitle, "SELENIUM WEBDRIVER FROM DEMO");

		/* sẽ học trong phần Windown popup */
		// Get ra windows id của page hiện tại (GUIID - Uniqe)
		String homePageId = driver.getWindowHandle(); /*Hay dùng*/

		// Get ra tất cả windows id của tất cả các tab
		driver.getWindowHandles();

		/* sẽ học trong phần WebdriverWait */
		// Wait cho element (findelement/ findelements)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); /*Hay dùng*/

		// Wait cho page được load thành công
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		// Set timeout cho scrip
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

		// F11 - GUI
		driver.manage().window().fullscreen();
		
		//Phóng ta browser ra hết cỡ
		driver.manage().window().maximize(); /*Hay dùng*/

		driver.manage().window().setSize(new Dimension(1024, 768));
		driver.manage().window().getPosition(new Point(300, 300));

		Dimension initial_size = driver.manage().window().getSize();
		int height = initial_size.getHeight();
		int width = initial_size.getWidth();
		
		//
		driver.navigate().back();
		driver.navigate().forward();
		driver.navigate().refresh();
		
		//Tracking history (back/foward)
		driver.navigate().to("Url");
		
		//Alert/ iframe/ windows
		driver.switchTo().alert(); /*Hay dùng*/
		
		driver.switchTo().frame(""); /*Hay dùng*/
		driver.switchTo().defaultContent(); /*Hay dùng*/
		
		driver.switchTo().windows("GUIID");
		
	}

	@Test
	public void TC_01_WebElement() {
		/*------ WEB ELEMENT ------*/
		//Cách 1: Action trực tiếp lên step (click/ senkey/ gettext/...)
		driver.findElement(By.id("email")).sendKeys("");
		
		//Cách 2: Dùng lại element
		WebElement emailTextbox = driver.findElement(By.id("email"));
		emailTextbox.clear(); /**/
		emailTextbox.sendKeys("AT"); /**/
		emailTextbox.click(); /**/
		
		
		//Xóa data trước khi senkey (textbox/ textaria/ dropdown)
		emailTextbox.clear();
		emailTextbox.sendKeys("AT");
		
		//Nếu không tìm thấy element nào hết: throw exception và đánh fail testcase										
		emailTextbox.findElement(By.xpath("//input[@id='password']"));
		List <WebElement> homePageLink = driver.findElement(By.xpath("//a"));
		//Thao tác với Element của list
		homePageLink.get(0).click(); //nếu muốn thao tác với phần tử đầu tiên /**/
		homePageLink.get(1).getText(); //nếu muốn thao tác với phần tử thứ 2 /**/
		
		int homePageNumber = homePageLink.size(); /**/
		//Nếu không tìm thấy element nào hết: return list rỗng và chạy step tiếp theo chứ không đánh fail testcase
		
		/*<input id="email" class="input-text required-entry validate-email" 
		 * type="email" title="Email Address" value="" name="login[username]" 
		 * spellcheck="false" autocorrect="off" autocapitalize="off"/>*/
		
		//Giá trị mình cần đang nằm trong 1 attribute của thẻ
		WebElement emailTxt = driver.findElement(By.xpath("//input[@id='email']"));
		String emailValueName = emailTxt.getAttribute("value"); /**/
		// Textbox is disable
		
		//<td>Custommer Namer</td> -> nằm bên ngoài thẻ
		emailTxt.getText(); /**/
		
		//GUI
		String emailBackground = emailTxt.getCssValue("background-color");
		String emailFontSize = emailTxt.getCssValue("font-size");
		
		emailTxt.getLocation();
		emailTxt.getSize();
		
		String emailTagname = emailTxt.getTagName();
		//Dynamic locator
		//Element đầu tiên get ra phần tử cho element sau sử dụng
		
		//Kiểm tra cho 1 element có hiển thị ở trên Page hay không
		Assert.assertTrue(emailTxt.isDisplayed()); /**/
		
		//Kiểm tra cho 1 element có bị disabled ở trên Page hay không
		Assert.assertTrue(emailTxt.isEnabled()); /**/
		
		Assert.assertFalse(emailTxt.isEnabled()); /**/
		
		//Kiểm tra cho 1 element có được chọn thành công ở trên Page hay không (radio button/checkbox)
		Assert.assertTrue(emailTxt.isSelected()); /**/
		Assert.assertFalse(emailTxt.isSelected());
		
		//Click => button/ check box/ radio/ link
		emailTxt.click();
		
		//From search/ login/ register (cho phép nhận sự kiện enter)
		driver.findElement(By.xpath("//input[@id='pass']")).submit();
		emailTxt.submit();				
		
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
