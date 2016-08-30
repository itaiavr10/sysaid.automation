package server.installer;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import common.annotation.TestCase;
import utils.AutoItAPI;
import utils.SystemUtils;
import base.AbstractSuite;
import base.LogManager;
import base.TestManager;
import buisness.InstallServer;
import buisness.SysAidAgent;
import buisness.SysAidServer;


public class InstallSanitySuite extends AbstractSuite{
	
	
	
	/**
	 * Test should failed!
	 * Verify License Agreement - page : Next button
	 */
	@Test(priority = 1)
	public void licenseAgreementFailureCase(){
	/*	InstallServer.exec();
		LogManager.info("Step1: Click Next");
		AutoItAPI.waitWin("InstallShield Wizard");
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		AutoItAPI.waitWin("InstallShield Wizard" , "license agreement");
		LogManager.info("Step2: license agreement - validate 'Next' button is Disabled");
		AutoItAPI.validateElementEnable("InstallShield Wizard", "1", true); // Fail

		InstallServer.closeInstaller();	*/
	}
	
	@Test(priority = 0)
	public void licenseAgreementPassCase(){
	/*	InstallServer.exec();
		LogManager.info("Step1: Click Next");
		AutoItAPI.waitWin("InstallShield Wizard");
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		AutoItAPI.waitWin("InstallShield Wizard" , "license agreement");
		LogManager.info("Step2: license agreement - validate 'Next' button is Disabled By Default!");
		AutoItAPI.validateElementEnable("InstallShield Wizard", "1", false); // OK

		InstallServer.closeInstaller();	*/
	}
	
	
	
	/**
	 * This is flat installation
	 */
	@Test(priority = 1)
	@TestCase(number = 1)
	public void flatInstall(){
		InstallServer.defaultInstallation();
		sleep(10,TimeUnit.SECONDS); //Wait for finish to deploy // TODO : should be a smart sleep
		
		//wait for process to finish installstion
		SystemUtils.Processes.waitForProcessStop("SA.exe", 60 * 1000, 3000);
		
		SysAidServer.validateInstallation();
		SysAidAgent.validateInstallation();
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
