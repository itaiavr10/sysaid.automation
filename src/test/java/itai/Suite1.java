package itai;

import org.testng.annotations.Test;

import base.AbstractSuite;
import base.LogManager;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		LogManager.info("Step1");
		LogManager.validate(true, "PASS MSG");
		
	}

}
