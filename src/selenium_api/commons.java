package selenium_api;

import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class commons {
	
	public static int randomEmail() {
		Random random = new Random(); // tạo thư viện radom
		int number = random.nextInt(999999); // tạo biến number
		System.out.println("Random number = " + number);
		return number;
	}
	
	//Topic_08
	public static void clickElementByJavascript(WebDriver driver, WebElement element) {
	    JavascriptExecutor je = (JavascriptExecutor) driver;
	    je.executeScript("arguments[0].click();", element);
	}
}
