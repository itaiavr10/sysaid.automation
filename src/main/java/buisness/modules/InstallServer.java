package buisness.modules;

import buisness.installer.steps.InstallerApi;
import buisness.modules.SysAid.DataBaseType;
import buisness.modules.SysAid.InstallType;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;
import com.core.utils.SystemUtils;


public class InstallServer {
	
	private static InstallerApi installer;
	
	
	static{
		installer = new InstallerApi();
	}
	
	
	public static void exec(){
		LogManager.info("Run Installer..");
		//AutoItAPI.run(SysAidServer.exePath);
		SystemUtils.Processes.executeAndCheckProcess(SysAidServer.exePath,SysAidServer.exeName);
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
	
	public static void customizedMySqlInstallation(){
		exec();
		installer.WelcomeStep.waitTo("Welcome.. click next");
		installer.WelcomeStep.clickNext();
		
		installer.LicenseAgreementStep.waitTo("License agreement - check + Click Next");
		installer.LicenseAgreementStep.acceptAgreement();
		installer.LicenseAgreementStep.clickNext();
		//step 5 : select customized
		installer.SetupTypeStep.waitTo("Setup type - Select Customized and click Next");
		installer.SetupTypeStep.selectType(InstallType.CUSTOMIZED);
		installer.SetupTypeStep.clickNext();
		//step 6: Continue to select Patch management destination
		installer.FolderDestinationStep.waitTo("Accept default destination folder and click Next to continue");
		installer.FolderDestinationStep.clickNext();
		//Step 7: Continue to select Start menu Program folder
		installer.PatchMngRepositoryStep.waitTo("Accept default patch managment destination folder and click Next to continue");
		installer.PatchMngRepositoryStep.clickNext();
		//Step 8: Continue to installing
		installer.StartMenuProgramStep.waitTo("Accept default Program folder and click Next to continue");
		installer.StartMenuProgramStep.clickNext();
		//Step 9: Select license file
		installer.LicenseFileStep.waitTo("set activation file");
		installer.LicenseFileStep.selectLicenseFile("c:\\SA\\activation.xml");
		installer.LicenseFileStep.verifyLicneseMsg(true);
		installer.LicenseFileStep.clickNext();
		//Step 10: select DB  - Choose 'Use external database (Oracle, MS SQL, MySQL) and click Next
		installer.SelectDbStep.waitTo("Choose 'Use external database (Oracle, MS SQL, MySQL) and click Next");
		installer.SelectDbStep.selectExternal();
		installer.SelectDbStep.clickNext();
		//Step 11: Setting Database Connection
		installer.DbSettingStep.waitTo("DB Setting: select MySQL");
		installer.DbSettingStep.setDB(DataBaseType.MySQL);
		installer.DbSettingStep.setCredentials("root", "Password1");
		installer.DbSettingStep.checkConnection();
		installer.DbSettingStep.clickNext();
		//Step 12: skip email setting
		installer.EmailSettingStep.waitTo("Email setting: Skip");
		installer.EmailSettingStep.clickSkip();
		//Step 13: HTTP port setting
		installer.HttpPortStep.waitTo("HTTP port setting: Accept default port 8080 and click Next");
		installer.HttpPortStep.clickNext();
		//Step 14: LDAP integration settings
		installer.LdapStep.waitTo("LDAP Integration: Skip");
		installer.LdapStep.clickSkip();
		//Step 15: Language - default
		installer.LanguageStep.waitTo("Language setting: default , click next");
		installer.LanguageStep.clickNext();
		//Step 16: Initializing account 
		installer.UserCredentialsStep.waitTo("Set credentials");
		installer.UserCredentialsStep.setCredentials("sysaid", "changeit");
		installer.UserCredentialsStep.clickNext();
		installer.UserCredentialsStep.handlePopUp();
		//Step 18
		installer.CompletedStep.waitTo("completed page");
		installer.CompletedStep.clickFinish();
		/*LogManager.info("Step9: 'installShield - completing page");
		AutoItAPI.waitWin("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server",30);
		AutoItAPI.clickButton("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server","Button1");*/
		
		AutoItAPI.waitWinClosed("InstallShield Wizard");
		
		
	}
	
	
	public static void typicalInstallation(){
		exec();
		/*LogManager.info("Step1: Click Next");
		AutoItAPI.waitWin("InstallShield Wizard");
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");*/
		installer.WelcomeStep.waitTo("Welcome.. click next");
		installer.WelcomeStep.clickNext();
		
		/*LogManager.info("Step2: license agreement - check + Click Next");
		AutoItAPI.waitWin("InstallShield Wizard" , "license agreement");
		AutoItAPI.check("InstallShield Wizard", "1000");
		TestManager.sleep(1000);
		AutoItAPI.clickButton("InstallShield Wizard", "", "1");*/
		installer.LicenseAgreementStep.waitTo("License agreement - check + Click Next");
		installer.LicenseAgreementStep.acceptAgreement();
		installer.LicenseAgreementStep.clickNext();
		
		installer.SetupTypeStep.waitTo("Setup type- use default Typical and click Next");
		installer.SetupTypeStep.clickNext();
		//AutoItAPI.waitWin("InstallShield Wizard" , "Typical");
		//AutoItAPI.clickButton("InstallShield Wizard", "Typical", "Button3");
		
		//LogManager.info("Step4: set activation file");
		installer.LicenseFileStep.waitTo("Set activation file");
		installer.LicenseFileStep.selectLicenseFile("c:\\SA\\activation.xml");
		/*AutoItAPI.waitWin("InstallShield Wizard" , "License File" , 200);
		AutoItAPI.clickButton("InstallShield Wizard", "License File", "2005");
		//set activation & press Open button
		AutoItAPI.waitWin("Select the Activation File");
		TestManager.sleep(1500);
		AutoItAPI.setControlText("Select the Activation File", "1148", "c:\\SA\\activation.xml");
		TestManager.sleep(5, TimeUnit.SECONDS);
		AutoItAPI.clickButton("Select the Activation File", "", "Button1");*/
		
		//verify valid msg:
		//LogManager.info("Step5: verify 'valid license' message");
		installer.LicenseFileStep.verifyLicneseMsg(true);
		installer.LicenseFileStep.clickNext();
		//TestManager.sleep(5,TimeUnit.SECONDS);
		//AutoItAPI.waitWin("InstallShield Wizard","License File");
		//Valid License Msg id=2007 , Invalid id= 2008
		//AutoItAPI.verifyVisibility("InstallShield Wizard", "2007", true);
		
		/*LogManager.info("Step6: click Next"); 
		AutoItAPI.waitWin("InstallShield Wizard","");
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button2");*/
		
		installer.UserCredentialsStep.waitTo("Set credentials");
		installer.UserCredentialsStep.setCredentials("sysaid", "changeit");
		installer.UserCredentialsStep.clickNext();
		installer.UserCredentialsStep.handlePopUp();
		
		/*LogManager.info("Step7: Set credentials");
		TestManager.sleep(6,TimeUnit.MINUTES);
		AutoItAPI.waitWin("InstallShield Wizard","Serial Number",250);
		AutoItAPI.setControlText("InstallShield Wizard", "2702", "sysaid"); // Set User
		TestManager.sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2703", "changeit"); //Set Pass
		TestManager.sleep(500);
		AutoItAPI.setControlText("InstallShield Wizard", "2704", "changeit"); //Re'enter Password
		TestManager.sleep(500);
		AutoItAPI.clickButton("InstallShield Wizard", "", "Button1"); //Click next
		
		LogManager.info("Step8: 'SysAid Enterprise' popup - click OK");
		AutoItAPI.waitWin("SysAid Enterprise","Account initialized successfully.",50);
		TestManager.sleep(2,TimeUnit.SECONDS);
		AutoItAPI.clickButton("SysAid Enterprise", "OK", "2"); //Click OK
		*/

		installer.CompletedStep.waitTo("completed page");
		installer.CompletedStep.clickFinish();
		/*LogManager.info("Step9: 'installShield - completing page");
		AutoItAPI.waitWin("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server",30);
		AutoItAPI.clickButton("InstallShield Wizard","The InstallShield Wizard has successfully installed the SysAid Server","Button1");*/
		
		AutoItAPI.waitWinClosed("InstallShield Wizard");
		
	}
	
	
	

}
