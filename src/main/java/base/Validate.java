package base;

import org.testng.Assert;

public class Validate {

	
	/**
	 * report as info
	 * @param condition
	 * @param msg
	 */
	public void validate(boolean condition, String msg) {
		if (condition) {
			LogManager.pass(msg);
		} else {
			LogManager.error(msg);
			Assert.assertTrue(false);
		}
	}
	
	
	/**
	 * report as debug
	 * @param condition
	 * @param msg
	 */
	public void soft(boolean condition, String msg) {
		if (!condition) {
			LogManager.debug(msg);
		} else {
			LogManager.error(msg);
			Assert.assertTrue(false);
		}
	}

}
