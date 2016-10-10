package buisness.modules;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Incident {
	
	
	private String id;
	//
	private String problem_type;
	private String title;
	private String description;
	private String status;
	private String priority;
	private String request_user;
	
	private Category category;
	private String sub_category;
	private String third_category;
	public Incident(Category category,String sub_category,String third_category, String title,String description){
		this.problem_type = String.format("%s_%s_%s", category,sub_category,third_category);
		this.category = category;
		this.sub_category = sub_category;
		this.third_category = third_category;
		this.title = title;
		this.description = description;
		this.status = "1"; //New
		this.priority = "1"; //Highest
		this.request_user = "1"; //sysaid
	}
	
	@SuppressWarnings("unchecked")
	public String getJson(){
		
		JSONObject srJson = new JSONObject();
		JSONArray infoArray = new JSONArray();
		
		JSONObject jsoninfo = new JSONObject();
		jsoninfo.put("key", "problem_type");
		jsoninfo.put("value", this.problem_type);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "title");
		jsoninfo.put("value", this.title);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "description");
		jsoninfo.put("value", this.description);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "status");
		jsoninfo.put("value", this.status);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "priority");
		jsoninfo.put("value", this.priority);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "request_user");
		jsoninfo.put("value", this.request_user);
		infoArray.add(jsoninfo);
	
		srJson.put("info", infoArray);
		
		return srJson.toJSONString();
		
	/*	String srJson = 
		"{\"info\":[{\"key\":\"problem_type\",\"value\":\"" + this.problem_type + "\"},"
		+ "{\"key\":\"title\",\"value\":\"" + this.title + "\"},"
		+ "{\"key\":\"description\",\"value\":\"" + this.description + "\"},"
		+ "{\"key\":\"status\",\"value\":\"" + this.status + "\"},"
		+ "{\"key\":\"priority\",\"value\":\"" + this.priority + "\"},"
		+ "{\"key\":\"request_user\",\"value\":\"" + this.request_user + "\"}]}"
		;
		return srJson;*/
		
	}


	public Incident setID(String id){
		this.id = id;
		return this;
	}
	
	public String getID(){
		return this.id;
	}
	

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getStatus() {
		return status;
	}

	public String getPriority() {
		return priority;
	}

	public String getRequest_user() {
		return request_user;
	}


	public Category getCategory() {
		return category;
	}

	public String getSub_category() {
		return sub_category;
	}

	public String getThird_category() {
		return third_category;
	}


	public enum Category{
		APPLICATION_ABC("Application ABC"),
		BASIC_SOFTWARE("Basic Software"),
		MOBILE_DEVICES("Mobile Devices"),
		;
		
		private String name;
		
		public String toString(){
			return name;
		}
		private Category(String name){
			this.name = name;
		}
	}
	

}
