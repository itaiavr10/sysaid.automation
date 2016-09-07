package server.installer;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.db.DBQuery;
import com.core.utils.AutoItAPI;
import com.core.utils.SystemUtils;

import buisness.db.DBInstaller;
import buisness.modules.InstallServer;
import buisness.modules.SysAidAgent;
import buisness.modules.SysAidServer;


public class InstallSanitySuite extends AbstractSuite{
	
	
	

	
	/**
	 * This is flat installation
	 */
	@Test(priority = 1)
	@TestCase(number = 107)
	public void typicalInstall(){
		InstallServer.typicalInstallation();
		sleep(10,TimeUnit.SECONDS); //Wait for finish to deploy // TODO : should be a smart sleep
		
		//wait for process to finish installstion
		SystemUtils.Processes.waitForProcessStop(SysAidServer.exeName, 60 * 1000, 3000);
		
		SysAidServer.verifyInstallation();
		//TODO : RDS Internal
		SysAidAgent.verifyInstallation();
		
		
		SysAidServer.verifyDB();
		
		
	}
	
	


	
/*	@Test
	public void validateCancelOption(){
	System.out.println("jenkins test");
		LogManager.info("Validate Cancel option");
		AutoItAPI.run("C:\\Users\\itai.avrahami\\Desktop\\SA.exe");
		sleep(10000);
		LogManager.info("Step1: Click Cancel");
		AutoItAPI.waitWin("InstallShield Wizard","",1);
		
	}*/
	
	//@Test
	public void calc_Demo(){
		LogManager.info("Run Calc..");
		AutoItAPI.run("calc.exe");
		LogManager.info("Done");
		sleep(1000);
		AutoItAPI.waitWin("Calculator");
		//Enter 3
		AutoItAPI.clickButton("Calculator", "", "133") ;
		sleep(1000);
		//Enter +
		AutoItAPI.clickButton("Calculator", "", "93") ;
		sleep(1000);
		//Enter 3
		AutoItAPI.clickButton("Calculator", "", "133") ;
		sleep(1000);
		//Enter =
		AutoItAPI.clickButton("Calculator", "", "121");
		
	}

}
