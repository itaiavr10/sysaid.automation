package itai;

import org.testng.annotations.Test;

import com.core.base.AbstractSuite;
import com.core.base.LogManager;

public class Suite3 extends AbstractSuite{
	
	@Test(groups="test1")
	public void A(){
		LogManager.info("Suite3 - A");
	}
	
	@Test()
	public void B(){
		System.out.println("Suite3 - B");
	}

}
