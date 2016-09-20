package itai;

import org.testng.annotations.Test;

import buisness.db.DBconst.TableContent;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.db.DBQuery;

public class Suite1 extends AbstractSuite{
	
	@Test()
	@TestCase(number = 0 , description = "TestA - For debug")
	public void A(){
		
		DBQuery.verifyTableRetry(TableContent.COMPUTER ,  60 * 1000 , 3 * 1000);
	}
	
	
	//@Test()
	@TestCase(number = 1 , description = "TestB - For debug")
	public void B(){
		LogManager.error("hello - B");		
	}


}
