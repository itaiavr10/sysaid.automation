package buisness.modules;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.core.base.LogManager;
import com.core.utils.HttpSender;
import com.core.utils.SystemUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SysAidAPI {
	
	private static SysAidAPI api;
	private String pre_url;
	
	private SysAidAPI(){
		//pre_url = "http://"+SystemUtils.OS.getCurrentIP()+":8080/api/v1/";
		pre_url = "http://10.14.1.225:8080/api/v1/";
	}
	
	
	
	public static SysAidAPI get(){
		if(api == null){
			api = new SysAidAPI();
			api.login();
		}
		return api;
	}
	
	private void login(){
		LogManager.debug("ServerAPI - login...");
		String suffix_url = "login";
		String loginJson = "{\"user_name\":\"sysaid\",\"password\":\"changeit\"}";
		try {
			HttpSender.getInstance().postJson(pre_url+suffix_url , loginJson,false);
			LogManager.debug("ServerAPI - logged in successfully.");
		} catch (Exception e) {
			LogManager.error("ServerAPI - Login error: " + e.getMessage());
		}
	}
	
	public void createNewSR(Incident incident){
		/*String cat = "helloAutomation";
		String srJson = "{\"info\":[{\"key\":\"problem_type\",\"value\":\""+cat+"\"},{\"key\":\"title\",\"value\":\"from Advanced REST Client\"},{\"key\":\"description\",\"value\":\"test SR from ARC\"},"
				+ "{\"key\":\"status\",\"value\":\"2\"},{\"key\":\"priority\",\"value\":\"3\"},{\"key\":\"impact\",\"value\":\"2\"},{\"key\":\"urgency\",\"value\":\"2\"},"
				+ "{\"key\":\"request_user\",\"value\":\"2\"},{\"key\":\"responsibility\",\"value\":\"1\"},{\"key\":\"assigned_group\",\"value\":\"1\"},{\"key\":\"due_date\",\"value\":\"1507932485000\"}]}";*/
		
		
		LogManager.debug("ServerAPI - create new SR...");
		String suffix_url = "sr";
		try {
			//HttpSender.getInstance().postJson(pre_url+suffix_url , srJson,true);
			String response = HttpSender.getInstance().postJson(pre_url+suffix_url , incident.getJson(),true);
			String id = extractID(response);
			incident.setID(id);
		} catch (Exception e) {
			LogManager.error("ServerAPI - Login error: " + e.getMessage());
		}
	}
	
	private String extractID(String response){
		String id = null;
		
		try{
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(response);
				JSONObject json = (JSONObject)obj;
				id = (String) json.get("id");
			//JSONObject jObject  = new JSONObject(output); // json
			//JsonParser parser = new JsonParser();
			//JsonElement json =  parser.parse(response);
			// json.get
			//JSONParser parser = new JSONParser();
			//JSONObject json = (JSONObject) parser.parse(stringToParse);
		}catch(Exception e){
			e.getMessage();
		}
		
		return id;
	}
	
	
	
	

}
