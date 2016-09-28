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
	private static InstallerVerification installerVerification;
	
	
	static{
		installer = new InstallerApi();
		installerVerification = new InstallerVerification();
	}
	
	
	public static void verify(){
		installerVerification.verify();
	}
	
	private static void exec(){
		LogManager.info("Run Installer..");
		SystemUtils.Processes.executeAndCheckProcess(SysAidServer.exePath,SysAidServer.exeName);
		TestManager.sleep(10000);
	}
	
	public static void closeInstaller(){ // TODO : move to installer step
		LogManager.info("Close Installer..");
		AutoItAPI.waitWin("InstallShield Wizard");
		TestManager.sleep(1000);
		LogManager.info("Click Cancel button");
		AutoItAPI.clickButton("InstallShield Wizard", "", "9"); //Cancel Button
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
		installer.LicenseFileStep.selectLicenseFile(SysAidServer.getActivationFilePath());
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
		
		AutoItAPI.waitWinClosed("InstallShield Wizard");
		
		
	}
	
	
	
	public static void upgradeMe(){
		SysAidServer.initUpgrade();
		exec();
		
		installer.WelcomeStep.waitTo("Welcome.. click next");
		installer.WelcomeStep.clickNext();
		
		installer.LicenseAgreementStep.waitTo("License agreement - check + Click Next");
		installer.LicenseAgreementStep.acceptAgreement();
		installer.LicenseAgreementStep.clickNext();
		
		//Continue to select Patch management destination
		installer.FolderDestinationStep.waitTo("Accept default destination folder and click Next to continue");
		installer.FolderDestinationStep.clickNext();
		//Continue to select Start menu Program folder
		installer.PatchMngRepositoryStep.waitTo("Accept default patch managment destination folder and click Next to continue");
		installer.PatchMngRepositoryStep.clickNext();
		//
		installer.CompletedStep.waitTo("completed page",300);
		installer.CompletedStep.clickFinish();
		
	}
	
	
	public static void typicalInstallation(){
		exec();
		
		installer.WelcomeStep.waitTo("Welcome.. click next");
		installer.WelcomeStep.clickNext();
		
		installer.LicenseAgreementStep.waitTo("License agreement - check + Click Next");
		installer.LicenseAgreementStep.acceptAgreement();
		installer.LicenseAgreementStep.clickNext();
		
		installer.SetupTypeStep.waitTo("Setup type- use default Typical and click Next");
		installer.SetupTypeStep.clickNext();
		
		installer.LicenseFileStep.waitTo("Set activation file");
		installer.LicenseFileStep.selectLicenseFile(SysAidServer.getActivationFilePath());

		installer.LicenseFileStep.verifyLicneseMsg(true);
		installer.LicenseFileStep.clickNext();
		
		installer.UserCredentialsStep.waitTo("Set credentials");
		installer.UserCredentialsStep.setCredentials("sysaid", "changeit");
		installer.UserCredentialsStep.clickNext();
		installer.UserCredentialsStep.handlePopUp();
		
		installer.CompletedStep.waitTo("completed page");
		installer.CompletedStep.clickFinish();
		
		AutoItAPI.waitWinClosed("InstallShield Wizard");
	}
	

}
