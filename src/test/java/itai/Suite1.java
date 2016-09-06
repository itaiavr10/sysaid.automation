package itai;

import org.testng.annotations.Test;

import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.db.DBQuery;

import buisness.db.DBInstaller;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		DBInstaller.verifyTableContents();
		//LogManager.validate(true, "PASS MSG");
		
	}

}
