package selenium_api;

import java.util.Random;

public class commons {
	
	public static int randomEmail() {
		Random random = new Random(); // tạo thư viện radom
		int number = random.nextInt(999999); // tạo biến number
		System.out.println("Random number = " + number);
		return number;
	}
}
