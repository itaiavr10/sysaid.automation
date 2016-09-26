package itai;

import org.testng.annotations.Test;

import buisness.db.DBInstaller;
import buisness.db.DBconst.TableContent;
import buisness.modules.SysAid;
import buisness.modules.SysAidAgent;
import buisness.modules.SysAidServer;
import buisness.modules.SysAid.DataBaseType;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.db.DBQuery;

public class Suite1 extends AbstractSuite{
	
	@Test()
	@TestCase(number = 0 , description = "TestA - For debug")
	public void A(){
		LogManager.error("hello - A");
		LogManager.info("hello again - A");
	}
	
	
	@Test()
	@TestCase(number = 1 , description = "TestB - For debug")
	public void B(){
		LogManager.info("hello - B");		
	}


}
