package itai;

import org.testng.annotations.Test;

import com.core.base.AbstractSuite;
import com.core.base.LogManager;

public class Suite2 extends AbstractSuite {
	
	
	@Test(groups={"integration"})
	public void A(){
		LogManager.info("Suite2 - A");
	}
	
	@Test
	public void B(){
		LogManager.info("Suite2 - B");
	}

}
