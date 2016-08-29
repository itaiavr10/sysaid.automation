package itai;

import java.net.InetAddress;
import java.net.UnknownHostException;

import utils.SystemUtils;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException {
		String computername=InetAddress.getLocalHost().getHostName();
		System.out.println(computername);
	}

}
