package selenium_api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_DropdownList {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor javaExecutor;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		javaExecutor = (JavascriptExecutor) driver;
		explicitWait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test
	public void TC_01_HTMLDropDown() throws InterruptedException {
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		Select select = new Select(driver.findElement(By.xpath("//select[@id='job1']")));

		/*
		 * <select id="job1" name="user_job1"> <option value="automation">Automation Tester</option> <option value="manual">Manual Tester</option> <option value="website">Website Tester</option> <option value="mobile">Mobile Tester</option> <option value="disabled" disabled="disabled">Dropdown
		 * disable</option> </select>
		 */

		Assert.assertFalse(select.isMultiple());
		select.selectByVisibleText("Automation Tester");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Automation Tester");
		Thread.sleep(3000);

		select.selectByValue("manual");
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Manual Tester");
		Thread.sleep(3000);

		select.selectByIndex(3);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "Mobile Tester");
		Thread.sleep(3000);

		Assert.assertEquals(select.getOptions().size(), 5);
	}

	@Test
	public void TC_02_CustomDropdown() throws Exception {

		// Iquery
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		
		selectItemInCustomDropdown("//div[@class='demo']", "//span[@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "19");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text() ='19']")).isDisplayed());
		Thread.sleep(1000);

		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdown("//div[@class='demo']", "//span[@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "15");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text() ='15']")).isDisplayed());
		Thread.sleep(1000);

		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdown("//div[@class='demo']", "//span[@id='number-button']", "//ul[@id='number-menu']//li[@class='ui-menu-item']/div", "5");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text() ='5']")).isDisplayed());
		Thread.sleep(1000);

		// Kendo UI
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		
		selectItemInCustomDropdown("//div[@id='cap-view']", "//span[@aria-owns='color_listbox']", "//ul[@id='color_listbox']/li", "Orange");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input' and text()='Orange']")).isDisplayed());
		Thread.sleep(1000);

		selectItemInCustomDropdown("//div[@id='cap-view']", "//span[@aria-owns='color_listbox']", "//ul[@id='color_listbox']/li", "Grey");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input' and text()='Grey']")).isDisplayed());
		Thread.sleep(1000);

		selectItemInCustomDropdown("//div[@id='cap-view']", "//span[@aria-owns='color_listbox']", "//ul[@id='color_listbox']/li", "Black");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@aria-owns='color_listbox']//span[@class='k-input' and text()='Black']")).isDisplayed());
		Thread.sleep(1000);

		// Agular
		driver.get("https://material.angular.io/components/select/examples");
		
		selectItemInCustomDropdown("//div[text()='Select with reset option']","//mat-select[@placeholder='State']", "//mat-option/span", "Maine");
		Assert.assertTrue(driver.findElement(By.xpath("//mat-select[@placeholder='State']//span[text()='Maine']")).isDisplayed());
		Thread.sleep(1000);
		
		selectItemInCustomDropdown("//div[text()='Select with reset option']","//mat-select[@placeholder='State']", "//mat-option/span", "New York");
		Assert.assertTrue(driver.findElement(By.xpath("//mat-select[@placeholder='State']//span[text()='New York']")).isDisplayed());
		Thread.sleep(1000);

		// VueJS 
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		
		selectItemInCustomDropdown("//div[@id='app']", "//div[@id='app']/div/li", "//ul[@class='dropdown-menu']/li", "Second Option"); 
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='app']/div/li[contains(text(),'Second Option')]")).isDisplayed()); Thread.sleep(3000);
		Thread.sleep(1000);
		
		selectItemInCustomDropdown("//div[@id='app']", "//div[@id='app']/div/li", "//ul[@class='dropdown-menu']/li", "Third Option"); 
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='app']/div/li[contains(text(),'Third Option')]")).isDisplayed()); Thread.sleep(3000);
		Thread.sleep(1000);
		
		//Edit Table
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		
		driver.findElement(By.xpath("//div[@id='default-place']/input")).sendKeys("Ford");
		driver.findElement(By.xpath("//div[@id='default-place']/input")).sendKeys(Keys.TAB);
		Assert.assertEquals(driver.findElement(By.xpath("//*[@id='default-place']//li[text()='Ford']")).getAttribute("class"), "es-visible selected");

	}

	public void selectItemInCustomDropdown(String scrollToXpath, String parentXpath, String childXpath, String expectedItem) throws Exception {
		// Scroll to view element dropdown
		javaExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(scrollToXpath)));

		// Click vào dropdown
		WebElement element = driver.findElement(By.xpath(parentXpath));
		element.click();
		Thread.sleep(1000);

		// Get tất cả item trong dropdown vào 1 list element (List <WebElement>)
		List<WebElement> childList = driver.findElements(By.xpath(childXpath));

		// Wait để tất cả phần tử trong dropdown được hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfAllElements(childList));

		// Dùng vòng lặp for duyệt qua từng phần tử sau đó getText
		/*
		 * Cach 1: for(int i = 0; i < childList.size(); i++) {
		 * 
		 * }
		 */

		// Cach 2: dung for each
		for (WebElement child : childList) {
			String textItem = child.getText().trim();
			
			//Hàm trim mục đích xóa khoảng trắng /tab/ \n  ở đầu và ở cuối câu
			
			System.out.println("Text in dropdown = " + textItem);

			// Nếu actual text = expected text thì click vào phần tử đó và break khỏi vòng lặp
			if (textItem.equals(expectedItem)) {

				// Scroll to expected item de click (item khong visible)
				javaExecutor.executeScript("arguments[0].scrollIntoView(true);", child);
				Thread.sleep(1000);
				child.click();
				break;
			}
		}
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
