package buisness.sr;


public class IncidentSR extends AbstractSR{
	
	
	
	
	
	public IncidentSR(Category category,String sub_category,String third_category, String title,String description , PriorityType priority, String request_user){
		super(category, sub_category, third_category,  title, description, priority, request_user );
	}
	
	
	
	public IncidentSR(Category category,String sub_category,String third_category, String title,String description){
		super(category, sub_category, third_category,  title, description, PriorityType.Highest, "1" );
	}



	@Override
	public void addToTable() {
		ServiceRequestTables.get().addIncident(this);
	}



	@Override
	public String getSuffix() {
		return "sr";
	}
	
	
	
	
/*	@SuppressWarnings("unchecked")
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
		
		String srJson = 
		"{\"info\":[{\"key\":\"problem_type\",\"value\":\"" + this.problem_type + "\"},"
		+ "{\"key\":\"title\",\"value\":\"" + this.title + "\"},"
		+ "{\"key\":\"description\",\"value\":\"" + this.description + "\"},"
		+ "{\"key\":\"status\",\"value\":\"" + this.status + "\"},"
		+ "{\"key\":\"priority\",\"value\":\"" + this.priority + "\"},"
		+ "{\"key\":\"request_user\",\"value\":\"" + this.request_user + "\"}]}"
		;
		return srJson;
		
	}*/


	

}
