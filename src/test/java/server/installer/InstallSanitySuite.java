package server.installer;

import org.testng.annotations.Test;

import utils.AutoItAPI;
import base.AbstractSuite;
import base.LogManager;


public class InstallSanitySuite extends AbstractSuite{
	
	
	
	@Test
	public void flatInstall(){
		LogManager.info("Run Installer..");
		AutoItAPI.run("C:\\SA.exe");
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
		
		LogManager.info("Step4: set licnese file");
		AutoItAPI.waitWin("InstallShield Wizard" , "License File" , 20);
		AutoItAPI.clickButton("InstallShield Wizard", "License File", "2005");
		sleep(1500);
		//set activation & press Open button
		AutoItAPI.waitWin("Select the Activation File");
		AutoItAPI.setControlText("Select the Activation File", "1148", "c:\\SA\\activation.xml");
		sleep(500);
		AutoItAPI.clickButton("Select the Activation File", "", "Button1");
		
		//validate valid msg:
		LogManager.info("Step4: validate 'valid license' message");
		
		
	
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
