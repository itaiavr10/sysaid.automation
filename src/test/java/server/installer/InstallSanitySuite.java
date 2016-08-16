package server.installer;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import utils.AutoItAPI;
import utils.SystemUtils;
import base.AbstractSuite;
import base.LogManager;
import buisness.InstallServer;


public class InstallSanitySuite extends AbstractSuite{
	
	
	
	@Test
	public void flatInstall(){
		LogManager.info("Run Installer..");
		AutoItAPI.run("C:\\SA\\SA.exe");
		sleep(10000);
		LogManager.info("Step1: Click Next");
		AutoItAPI.waitWin("InstallShield Wizard");
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		LogManager.info("Step2: license agreement - check + Click Next");
		AutoItAPI.waitWin("InstallShield Wizard" , "license agreement");
		AutoItAPI.check("InstallShield Wizard", "1000");
		sleep(1000);
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		LogManager.info("Step3: setup type");
		sleep(1000);
		AutoItAPI.waitWin("InstallShield Wizard" , "Typical");
		AutoItAPI.clickButton("InstallShield Wizard", "Typical", "Button3");
		
		LogManager.info("Step4: set activation file");
		sleep(4,TimeUnit.MINUTES);
		AutoItAPI.waitWin("InstallShield Wizard" , "License File" , 60);
		AutoItAPI.clickButton("InstallShield Wizard", "License File", "2005");
		sleep(1500);
		//set activation & press Open button
		AutoItAPI.waitWin("Select the Activation File");
		AutoItAPI.setControlText("Select the Activation File", "1148", "c:\\SA\\activation.xml");
		sleep(5, TimeUnit.SECONDS);
		AutoItAPI.clickButton("Select the Activation File", "", "Button1");
		
		//validate valid msg:
		LogManager.info("Step5: validate 'valid license' message");
		sleep(5,TimeUnit.SECONDS);
		AutoItAPI.waitWin("InstallShield Wizard","License File");
		//Valid License Msg id=2007 , Invalid id= 2008
		AutoItAPI.validateVisibility("InstallShield Wizard", "2007", true);
		
		LogManager.info("Step6: click Next"); 
		AutoItAPI.waitWin("InstallShield Wizard","");
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button2");
		
		LogManager.info("Step7: Set credentials");
		sleep(6,TimeUnit.MINUTES);
		AutoItAPI.waitWin("InstallShield Wizard","Serial Number",120);
		AutoItAPI.setControlText("InstallShield Wizard", "2702", "sysaid"); // Set User
		sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2703", "changeit"); //Set Pass
		sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2704", "changeit"); //Re'enter Password
		sleep(500);
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button1"); //Click next
		
		LogManager.info("Step8: 'SysAid Enterprise' popup - click OK");
		AutoItAPI.waitWin("SysAid Enterprise","Account initialized successfully.",30);
		sleep(2,TimeUnit.SECONDS);
		AutoItAPI.clickButton("SysAid Enterprise", "OK", "2"); //Click OK
		
		LogManager.info("Step9: 'installShield - completing page");
		AutoItAPI.waitWin("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server",30);
		AutoItAPI.clickButton("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server","Button1");
		
		sleep(5,TimeUnit.SECONDS); //Wait for finish to deploy
		InstallServer.validateInstallation();
		
		
	
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
	/*public void calc_Demo(){
	 * push via SourceTree
		LogManager.info("Run Calc..");
		validate.validate(true, "Calc Validate - true");
		AutoItAPI.run("calc.exe");
		validate.validate(true, "Calc Validate - false");
		LogManager.info("Done");
		sleep(1000);
		AutoItAPI.activateWindow("Calculator");
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
		
	}*/

}
