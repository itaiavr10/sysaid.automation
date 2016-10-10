package itai;

import org.testng.annotations.Test;

import buisness.db.DBInstaller;
import buisness.db.DBconst.TableContent;
import buisness.modules.Incident;
import buisness.modules.Incident.Category;
import buisness.modules.IncidentTable;
import buisness.modules.SysAid;
import buisness.modules.SysAidAPI;
import buisness.modules.SysAidAgent;
import buisness.modules.SysAidServer;
import buisness.modules.SysAid.DataBaseType;

import com.core.annotation.TestCase;
import com.core.base.AbstractSuite;
import com.core.base.LogManager;
import com.core.db.DBQuery;
import com.core.driver.PageDriver;
import com.core.utils.HttpSender;
import com.core.utils.SystemUtils;

public class Suite1 extends AbstractSuite{
	
	//@Test(groups = "bug")
	//@Test(enabled=true)
	@TestCase(number = 0 , description = "TestA - For debug")
	public void A(){
		Incident incident = new Incident(Category.APPLICATION_ABC,"Administration","Other","itai title","WTF");
		SysAidAPI.get().createNewSR(incident);
		System.out.println(incident.getID());
		/*String cat = "Haim the GFY";
		String POST_URL = "http://10.14.1.225:8080/api/v1/login";
		String loginJson = "{\"user_name\":\"sysaid\",\"password\":\"changeit\"}";
		String srJson = "{\"info\":[{\"key\":\"problem_type\",\"value\":\""+cat+"\"},{\"key\":\"title\",\"value\":\"from Advanced REST Client\"},{\"key\":\"description\",\"value\":\"test SR from ARC\"},"
				+ "{\"key\":\"status\",\"value\":\"2\"},{\"key\":\"priority\",\"value\":\"3\"},{\"key\":\"impact\",\"value\":\"2\"},{\"key\":\"urgency\",\"value\":\"2\"},"
				+ "{\"key\":\"request_user\",\"value\":\"2\"},{\"key\":\"responsibility\",\"value\":\"1\"},{\"key\":\"assigned_group\",\"value\":\"1\"},{\"key\":\"due_date\",\"value\":\"1507932485000\"}]}";
		try {
			report(POST_URL,loginJson,false);
			POST_URL = "http://10.14.1.225:8080/api/v1/sr";
			report(POST_URL,srJson,true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static void report(String url , String jsonAsString, boolean setCookie) throws Exception {
		System.out.println("message= " + jsonAsString);
		HttpSender.getInstance().postJson(url , jsonAsString,setCookie);
	}
	
	
	@Test()
	@TestCase(number = 0 , description = "TestB - For debug")
	public void B(){
		IncidentTable table = new IncidentTable();
		table.add(new Incident(Category.BASIC_SOFTWARE,"Other","How to?","Welcome to SysAid!","WTF").setID("6"));
		table.add(new Incident(Category.APPLICATION_ABC,"Administration","Error Message","Failed Patches","WTF").setID("25"));
		table.add(new Incident(Category.MOBILE_DEVICES,"Smartphone","Error Message","itai test","WTF").setID("26"));
		admin.launch();
		admin.login().loginWith("sysaid", "changeit");
		admin.dashboard().goToIncidentsTable();
		admin.incidents().verifyTable(table);
	}


}
