package itai;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import buisness.modules.SysAidAPI;
import buisness.sr.AbstractSR;
import buisness.sr.AbstractSR.Category;
import buisness.sr.IncidentSR;
import buisness.sr.RequestSR;
import buisness.tools.usermanagement.Company;
import buisness.tools.usermanagement.Group;
import buisness.tools.usermanagement.Group.GroupType;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;

public class Suite1 extends AbstractSuite{
	
	//@Test(groups = "bug")
	@Test(enabled=true)
	@TestCase(number = 0 , description = "TestA - For debug")
	public void A(){
		Group.createNewGroup("Group1", GroupType.Administrators, 1);
		Group.createNewGroup("Group2", GroupType.EndUser, 2);
		Group.createNewGroup("Group2", GroupType.EndUser, 2);
	
		
		admin.launch();
		admin.login().loginWith("sysaid", "changeit");
		admin.dashboard().goToGroupsTable();
		admin.groups().verifyTable();
		
	}
	
	
	
	//@Test()
	@TestCase(number = 0 , description = "TestB - For debug")
	public void B(){
		List<AbstractSR> newSRs = new ArrayList<AbstractSR>();

		newSRs.add( new RequestSR(Category.MOBILE_DEVICES,"new req","Error Message","itai test","WTF"));
		
		SysAidAPI.get().createNewSRs(newSRs);
		admin.launch();
		admin.login().loginWith("sysaid", "changeit");
		admin.dashboard().goToRquestsTable();
		admin.requests().verifyTable();
	}


}
