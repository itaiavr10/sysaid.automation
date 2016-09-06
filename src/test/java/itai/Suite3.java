package itai;

import org.testng.annotations.Test;

import com.core.base.AbstractSuite;
import com.core.base.LogManager;

public class Suite3 extends AbstractSuite{
	
	@Test
	public void A(){
		LogManager.info("A-Start...");
		System.out.println("Suite3 - A");
		LogManager.info("A-End...");
	}
	
	/*@Test
	public void B(){
		System.out.println("Suite3 - B");
	}*/

}
