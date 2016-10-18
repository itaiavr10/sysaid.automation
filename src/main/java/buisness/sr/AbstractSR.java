package buisness.sr;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public abstract class AbstractSR {
	
	
	protected String id;
	//
	protected String problem_type;
	protected String title;
	protected String description;
	protected StatusType status;
	protected PriorityType priority;
	protected String request_user;
	
	protected Category category;
	protected String sub_category;
	protected String third_category;
	
	
	protected AbstractSR(Category category,String sub_category,String third_category, String title,String description , PriorityType priority, String request_user){
		this.problem_type = String.format("%s_%s_%s", category,sub_category,third_category);
		this.category = category;
		this.sub_category = sub_category;
		this.third_category = third_category;
		this.title = title;
		this.description = description;
		this.status = StatusType.New;
		this.priority = priority; 
		this.request_user = request_user; //sysaid
	}
	
	public AbstractSR(Category category,String sub_category,String third_category, String title,String description){
		this(category, sub_category, third_category,  title, description, PriorityType.Highest, "1" );
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
		jsoninfo.put("value", this.status.id);
		infoArray.add(jsoninfo);
		
		jsoninfo = new JSONObject();
		jsoninfo.put("key", "priority");
		jsoninfo.put("value", this.priority.id);
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
	
	
	public abstract void addToTable(); 
	public abstract String getSuffix();
	
	public <T extends AbstractSR> T setID(String id){
		this.id = id;
		return (T)this;
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

	public StatusType getStatus() {
		return status;
	}

	public PriorityType getPriority() {
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
	
	public enum PriorityType{
		Highest("1","Highest"),
		VeryHigh("2","Very High"),
		High("3","High"),
		Normal("4","Normal"),
		Low("5","Low")
		;
		
		String id;
		String name;
		
		public String toString(){
			return name;
		}
		
		public String getID(){
			return id;
		}
		
		PriorityType(String id, String name){
			this.id = id;
			this.name = name;
		}
		
	}
	
	public enum StatusType{
		New("1"),Open("2"),Closed("3");
		
		String id;
		public String getID(){
			return id;
		}
		
		StatusType(String id){
			this.id = id;
		}
		
	}

}
