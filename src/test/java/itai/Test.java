package itai;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.core.db.DBQuery;
import com.core.utils.SystemUtils;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException {
		List<String> list = Arrays.asList("Exception,out");
		System.out.println(list);
		String regex = String.format("(?i:.*%s.*)", list.toString().replace("[", "(").replace(",", "|").replace("]", ")"));
		System.out.println(regex);
		String line = "GetRequest  Code 1000 Description The operation has timed out"; 
		if(line.matches("(?i:.*(error|exception).*)")){
			System.out.println("found..");
		}
		if(line.matches(regex)){
			System.out.println("found..");
		}
			
	}

}
