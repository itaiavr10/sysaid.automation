package server.installer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Description;
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
import buisness.modules.SysAidAPI;
import buisness.modules.SysAidAgent;
import buisness.modules.SysAidLog;
import buisness.modules.SysAidServer;
import buisness.sr.AbstractSR;
import buisness.sr.AbstractSR.Category;
import buisness.sr.IncidentSR;
import buisness.sr.RequestSR;
import buisness.tools.usermanagement.Company;
import buisness.tools.usermanagement.Group;
import buisness.tools.usermanagement.Group.GroupType;


public class InstallSanitySuite extends AbstractSuite{
	
	
	
	@Test
	@TestCase(number = 14 , description = "Upgrade Process After Typical Installation with MsSQL Embedded")
	public void typicalUpgrade(){
		InstallServer.typicalInstallation();
		
		//wait for process to finish installation
		SystemUtils.Processes.waitForProcessStop(SysAidServer.exeName, 60 * 1000, 3000);
		
		//Create new SRs (Incdients & Requests)
		List<AbstractSR> newSRs = new ArrayList<AbstractSR>();
		newSRs.add(new IncidentSR(Category.APPLICATION_ABC,"Administration","Error Message","Failed Patches","WTF"));
		newSRs.add(new IncidentSR(Category.MOBILE_DEVICES,"Smartphone","Error Message","itai test","WTF"));
		newSRs.add(new RequestSR(Category.APPLICATION_ABC,"Administration","Basic Rquest Process","request 1 - title","WTF"));
		newSRs.add(new RequestSR(Category.DATA_CENTER,"Avilability","Basic Rquest Process","request 2 - title","WTF"));
		SysAidAPI.get().createNewSRs(newSRs);
		
		//Create Companies:
		Company.createNewCompany("company1", "address1","TA1" , "70101", "Israel1", "972-3-5333671", "972-3-7617201");
		Company.createNewCompany("company2", "address1","TA2" , "70102", "Israel2", "972-3-5333672", "972-3-7617202");
		Company.createNewCompany("company3", "address1","TA3" , "70103", "Israel3", "972-3-5333673", "972-3-7617203");
		//Create Groups:
		Group.createNewGroup("Group1", GroupType.Administrators, 1);
		Group.createNewGroup("Group2", GroupType.EndUser, 2);
		Group.createNewGroup("Group3", GroupType.Administrators, 3);
		Group.createNewGroup("Group4", GroupType.EndUser, 4);
		
		
		InstallServer.upgradeMe();
		
		InstallServer.verify();
		
		admin.launch();
		admin.login().loginWith("sysaid", "changeit");
		
		//Incident table verification
		admin.dashboard().goToIncidentsTable();
		admin.incidents().verifyTable();
		
		//Request table verification
		admin.incidents().goToRquestsTable();
		admin.requests().verifyTable();
		
		//Company table verification
		admin.dashboard().goToCompaniesTable();
		admin.companies().verifyTable();
		
		//Group table verification
		admin.dashboard().goToGroupsTable();
		admin.groups().verifyTable();
	}
	
	
	
	@Test()
	@TestCase(number = 8 , description = "SysAid Server installation - Customized MySQL")
	public void customizedMySqlInstall(){
		InstallServer.customizedMySqlInstallation();
		
		InstallServer.verify();
		
	}
	
	
	
	
	
	
	
	@Test()
	@TestCase(number = 1 , description = "Typical Installation with MsSQL Embedded")
	public void typicalInstall(){
		InstallServer.typicalInstallation();
		
		InstallServer.verify();
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
