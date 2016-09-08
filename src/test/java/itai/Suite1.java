package itai;

import org.testng.annotations.Test;

import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.db.DBQuery;

import buisness.db.DBInstaller;
import buisness.modules.SysAidAgent;
import buisness.modules.SysAidServer;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		SysAidServer.verifyLoginBrowser();
		
	}

}
