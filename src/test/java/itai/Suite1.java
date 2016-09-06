package itai;

import org.testng.annotations.Test;

import com.core.db.DBQuery;

import base.AbstractSuite;
import base.LogManager;
import buisness.db.DBInstaller;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		DBInstaller.verifyTableContents();
		//LogManager.validate(true, "PASS MSG");
		
	}

}
