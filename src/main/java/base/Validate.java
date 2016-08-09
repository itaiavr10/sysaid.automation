package base;

import org.testng.Assert;

public class Validate {

	public void validate(boolean condition, String msg) {
		if (condition) {
			LogManager.pass(msg);
		} else {
			LogManager.error(msg);
			Assert.assertTrue(false);
		}
	}

}
