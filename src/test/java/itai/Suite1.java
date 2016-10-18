package itai;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import buisness.modules.SysAidAPI;
import buisness.sr.AbstractSR;
import buisness.sr.AbstractSR.Category;
import buisness.sr.IncidentSR;
import buisness.sr.RequestSR;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;

public class Suite1 extends AbstractSuite{
	
	//@Test(groups = "bug")
	//@Test(enabled=true)
	@TestCase(number = 0 , description = "TestA - For debug")
	public void A(){
		
	}
	
	
	
	@Test()
	@TestCase(number = 0 , description = "TestB - For debug")
	public void B(){
		List<AbstractSR> newSRs = new ArrayList<AbstractSR>();
		newSRs.add( new IncidentSR(Category.APPLICATION_ABC,"incA","Error Message","Failed Patches","WTF"));
		newSRs.add( new IncidentSR(Category.MOBILE_DEVICES,"incB","Error Message","itai test","WTF"));
		newSRs.add( new RequestSR(Category.MOBILE_DEVICES,"proB","Error Message","itai test","WTF"));
		
		SysAidAPI.get().createNewSRs(newSRs);
		
		
		
		
	
		/*
		
		admin.login().loginWith("sysaid", "changeit");
		admin.dashboard().goToIncidentsTable();
		admin.incidents().verifyTable(table);*/
	}


}
