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
		//AutoItAPI.clickButton("InstallShield Wizard", "Typical", "Button3");
	
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
	 * 
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
