package base;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;

public class TestManager {
	
	/*private final static ThreadLocal<Validate> validator = new ThreadLocal<Validate>();
	
	 public static void setValidator(Validate val){
		 validator.set(val);
	 }
	 
	 
	 public static Validate validator(){
		 return validator.get();
	 }*/
	 
	 
	 protected void sleep(long time , TimeUnit unit) {
			sleep(unit.toMillis(time));
	}
	 
	 
	 public static void sleep(long timeInMiliSec){
		 try {
			Thread.sleep(timeInMiliSec);
		} catch (InterruptedException e) {
			LogManager.error("TestManager.sleep - InterruptedException : " + e.getMessage());
		}
	 }

}
