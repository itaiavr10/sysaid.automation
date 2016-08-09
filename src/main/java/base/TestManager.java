package base;

public class TestManager {
	
	private final static ThreadLocal<Validate> validator = new ThreadLocal<Validate>();
	
	 public static void setValidator(Validate val){
		 validator.set(val);
	 }
	 
	 
	 public static Validate validator(){
		 return validator.get();
	 }

}
