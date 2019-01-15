package selenium_api;

import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sun.glass.ui.Robot;

public class Topic_09_User_Interactions {
	WebDriver driver;
	Actions action;

	//Get ra đường dẫn: D:\\AT_TestOnline_07\\Online_Class_07\\WebDriverAPI - \\lib\\drag_and_drop_helper.js
	String workingDir = System.getProperty("user.dir");
	
	//Get js File	
	String jsFilePath = workingDir + "\\lib\\drag_and_drop_helper.js";
	String jQueryFilePath = workingDir + "\\lib\\jquery_load_helper.js";

	By numberRangeBy = By.xpath("//ol[@id='selectable']/li");

	@BeforeClass
	public void beforeClass() {
		// Chrome
		// System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
		// driver = new ChromeDriver();

		// Firefox
		driver = new FirefoxDriver();
		action = new Actions(driver);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_HoverMouse() {
		driver.get("https://www.myntra.com/");

		WebElement accountAvatar = driver.findElement(By.xpath("//div[@class='desktop-userIconsContainer']"));
		action.moveToElement(accountAvatar).perform();

		WebElement signUpLink = driver.findElement(By.xpath("//a[text()='Sign up']"));
		Assert.assertTrue(signUpLink.isDisplayed());
		signUpLink.click();

		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Signup with Myntra']")).isDisplayed());

	}

	@Test
	public void TC_02_ClickAndHold() throws Exception {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> numberRange = driver.findElements(numberRangeBy);
		action.clickAndHold(numberRange.get(0)).moveToElement(numberRange.get(3)).release().perform();
		Thread.sleep(3000);

		List<WebElement> numberRangeSelected = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		Assert.assertEquals(numberRangeSelected.size(), 4);

		// Chỉ chọn các số 1,3,5,7,9
		// Refresh lại app
		driver.navigate().refresh();

		List<WebElement> numberRangeRandom = driver.findElements(By.xpath("//*[@id='selectable']/li"));

		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).build().perform();
		numberRangeRandom.get(0).click();
		numberRangeRandom.get(2).click();
		numberRangeRandom.get(4).click();
		numberRangeRandom.get(6).click();
		numberRangeRandom.get(8).click();
		action.keyUp(Keys.CONTROL).build().perform();

		List<WebElement> numberRangeRandomSelected = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,'ui-selected')]"));
		Assert.assertEquals(numberRangeRandomSelected.size(), 5);
	}

	@Test
	public void TC_03_ClickAndHold() throws Exception {
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement doubleClick = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		action.doubleClick(doubleClick).perform();

		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(driver.switchTo().alert().getText(), "The Button was double-clicked.");
		alert.accept();
	}

	@Test
	public void TC_04_RightClick() {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement rightClick = driver.findElement(By.xpath("//span[text()='right click me']"));
		action.contextClick(rightClick).perform();

		WebElement quitBefore = driver.findElement(By.xpath("//li/span[text()='Quit']"));
		action.moveToElement(quitBefore).perform();

		Assert.assertTrue(driver.findElement(By.xpath("//li[contains(@class,'visible') and contains(@class,'hover')]/span[text()='Quit']")).isDisplayed());

		// Click to Quit
		action.click(quitBefore).perform();

		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(driver.switchTo().alert().getText(), "clicked: quit");
		alert.accept();
	}

	@Test
	public void TC_05_Drag_Drop() {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");

		WebElement sourceElement = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement targetElement = driver.findElement(By.xpath("//div[@id='droptarget']"));

		action.dragAndDrop(sourceElement, targetElement).perform();

		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='droptarget']")).getText().equals("You did great!"));
	}
	@Test
	public void TC_06_Drag_Drop_HTML5_Javascript_Jquery_CSS() throws Exception {
		driver.get("https://html5demos.com/drag/");

		// use Javascript
		String java_script = "";
		String text;

		// Read and close thread
		BufferedReader input = new BufferedReader(new FileReader(jsFilePath));
		StringBuffer buffer = new StringBuffer();

		while ((text = input.readLine()) != null)
			buffer.append(text + " ");
		java_script = buffer.toString();
		input.close();

		//
		String oneCss = "#one";
		String targetCss = "#bin";

		String jQueryLoader = readFile(jQueryFilePath);
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(jQueryLoader);

		java_script = java_script + "$(\"" + oneCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		System.out.println(java_script);
		je.executeScript(java_script);
		
		Thread.sleep(3000);

	}

	@Test
	public void TC_07_Drag_Drop_HTML5_Xpath() throws AWTException, InterruptedException {
		driver.get("https://html5demos.com/drag/");
		String oneXpath = "//a[@id='one']";
		String targetXpath = "//div[@id='bin']";

		drag_the_and_drop_html5_by_xpath(oneXpath, targetXpath);

		Thread.sleep(2000);
	}
	
	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 0 + xCentreSource;
		sourceLocation.y += 70 + yCentreSource;
		targetLocation.x += 0 + xCentreTarget;
		targetLocation.y += 70 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
		
		//hàm đọc file
		
		
		public String readFile(String file) throws IOException {
			Charset cs = Charset.forName("UTF-8");
			FileInputStream stream = new FileInputStream(file);
			try {
				Reader reader = new BufferedReader(new InputStreamReader(stream,cs));
				StringBuilder builder = new StringBuilder();
				char[] buffer = new char[8192];
				int read;
				while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
					builder.append(buffer, 0, read);
				}
				return builder.toString();
	 		}
			finally {
				stream.close();
			}
		}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}