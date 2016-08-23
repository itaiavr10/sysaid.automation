package server.installer;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import utils.AutoItAPI;
import utils.SystemUtils;
import base.AbstractSuite;
import base.LogManager;
import buisness.InstallServer;
import buisness.InstallValidation;


public class InstallSanitySuite extends AbstractSuite{
	
	
	
	@Test
	public void flatInstall(){
		InstallServer.defaultInstallation();
		sleep(10,TimeUnit.SECONDS); //Wait for finish to deploy // TODO : should be a smart sleep
		InstallValidation.validateInstallation();
	}


	
/*	@Test
	public void validateCancelOption(){
	
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
