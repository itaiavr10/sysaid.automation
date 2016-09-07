package buisness.modules;

import java.util.concurrent.TimeUnit;

import buisness.modules.SysAid.InstallType;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;
import com.core.utils.SystemUtils.Keyboard;


public class InstallServer {
	
	
	
	
	public static void exec(){
		LogManager.info("Run Installer..");
		AutoItAPI.run(SysAidServer.exePath);
		TestManager.sleep(10000);
	}
	
	public static void closeInstaller(){
		LogManager.info("Close Installer..");
		AutoItAPI.waitWin("InstallShield Wizard");
		TestManager.sleep(1000);
		LogManager.info("Click Cancel button");
		AutoItAPI.clickButton("InstallShield Wizard", "", "9"); //Cancel Button
		//Keyboard.ClickEsc();
		//Exit Setup
		AutoItAPI.waitWin("Exit Setup");
		AutoItAPI.clickButton("Exit Setup", "", "6");
		TestManager.sleep(1000);
		//Press Finish
		LogManager.info("Click Finish button");
		AutoItAPI.waitWin("InstallShield Wizard","Finish");
		AutoItAPI.clickButton("InstallShield Wizard", "Finish", "1");
		AutoItAPI.waitWinClosed("InstallShield Wizard");
		
		
	}
	
	
	public static void typicalInstallation(){
		exec();
		LogManager.info("Step1: Click Next");
		AutoItAPI.waitWin("InstallShield Wizard");
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		LogManager.info("Step2: license agreement - check + Click Next");
		AutoItAPI.waitWin("InstallShield Wizard" , "license agreement");
		AutoItAPI.check("InstallShield Wizard", "1000");
		TestManager.sleep(1000);
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");
		
		LogManager.info("Step3: setup type");
		TestManager.sleep(1000);
		AutoItAPI.waitWin("InstallShield Wizard" , "Typical");
		AutoItAPI.clickButton("InstallShield Wizard", "Typical", "Button3");
		
		LogManager.info("Step4: set activation file");
		TestManager.sleep(4,TimeUnit.MINUTES);
		AutoItAPI.waitWin("InstallShield Wizard" , "License File" , 60);
		AutoItAPI.clickButton("InstallShield Wizard", "License File", "2005");
		TestManager.sleep(1500);
		//set activation & press Open button
		AutoItAPI.waitWin("Select the Activation File");
		AutoItAPI.setControlText("Select the Activation File", "1148", "c:\\SA\\activation.xml");
		TestManager.sleep(5, TimeUnit.SECONDS);
		AutoItAPI.clickButton("Select the Activation File", "", "Button1");
		
		//verify valid msg:
		LogManager.info("Step5: verify 'valid license' message");
		TestManager.sleep(5,TimeUnit.SECONDS);
		AutoItAPI.waitWin("InstallShield Wizard","License File");
		//Valid License Msg id=2007 , Invalid id= 2008
		AutoItAPI.verifyVisibility("InstallShield Wizard", "2007", true);
		
		LogManager.info("Step6: click Next"); 
		AutoItAPI.waitWin("InstallShield Wizard","");
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button2");
		
		LogManager.info("Step7: Set credentials");
		TestManager.sleep(6,TimeUnit.MINUTES);
		AutoItAPI.waitWin("InstallShield Wizard","Serial Number",120);
		AutoItAPI.setControlText("InstallShield Wizard", "2702", "sysaid"); // Set User
		TestManager.sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2703", "changeit"); //Set Pass
		TestManager.sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2704", "changeit"); //Re'enter Password
		TestManager.sleep(500);
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button1"); //Click next
		
		LogManager.info("Step8: 'SysAid Enterprise' popup - click OK");
		AutoItAPI.waitWin("SysAid Enterprise","Account initialized successfully.",30);
		TestManager.sleep(2,TimeUnit.SECONDS);
		AutoItAPI.clickButton("SysAid Enterprise", "OK", "2"); //Click OK
		
		LogManager.info("Step9: 'installShield - completing page");
		AutoItAPI.waitWin("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server",30);
		AutoItAPI.clickButton("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server","Button1");
		
		SysAid.type = InstallType.TYPICAL;
		
	}
	
	
	

}
