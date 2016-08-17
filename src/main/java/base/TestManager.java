package base;

import java.util.concurrent.TimeUnit;

public class TestManager {
	
	private final static ThreadLocal<Validate> validator = new ThreadLocal<Validate>();
	
	 public static void setValidator(Validate val){
		 validator.set(val);
	 }
	 
	 
	 public static Validate validator(){
		 return validator.get();
	 }
	 
	 
	 protected void sleep(long time , TimeUnit unit) {
			sleep(unit.toMillis(time));
	}
	 
	 
	 public static void sleep(long timeInMiliSec){
		 try {
			Thread.sleep(timeInMiliSec);
		} catch (InterruptedException e) {
			validator().soft(false, "TestManager.sleep - InterruptedException : " + e.getMessage());
		}
	 }

}
