package itai;

import java.io.Serializable;
import java.net.UnknownHostException;

import com.core.driver.PageDriver;
import com.core.utils.HttpSender;
import com.google.gson.Gson;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException {
			//Driver d = new Driver();
			//d.navigate("http://10.14.1.103:8080/Login.jsp");
			String loginJson = "{\"user_name\":\"sysaid\",\"password\":\"changeit\"}";
			try {
				report(loginJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void report(String jsonAsString) throws Exception {
		System.out.println("message= " + jsonAsString);
		String POST_URL = "http://10.14.1.103:8080/api/v1/login";
		HttpSender.getInstance().postJson(POST_URL , jsonAsString);
	}
	
	
	public void report(Message message) throws Exception {
		Object jsonContent = toJson(message);
		System.out.println("message= " + jsonContent.toString());
		String POST_URL = "";
		HttpSender.getInstance().postJson(POST_URL , jsonContent);
	}
	
	public static Object toJson(Object toConvert) {
		Gson gson = new Gson();
		Object jsonObject = gson.toJson(toConvert);
		return jsonObject;
	}
	
	
	public static class Message implements Serializable{
		
		private static final long serialVersionUID = 7648535369189206863L;
		private String key;
		private String value;
		public Message(String k,String v){
			this.key = k;
			this.value =v;
		}
		
	}
}
