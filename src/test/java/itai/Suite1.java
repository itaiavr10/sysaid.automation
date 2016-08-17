package itai;

import org.testng.annotations.Test;

import utils.SystemUtils;
import base.AbstractSuite;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		SystemUtils.validateProcess("calc.exe", true);
	}

}
