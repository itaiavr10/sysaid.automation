package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

 public class InstallerSetupTypeStep extends InstallerAbstractStep {
	
	
	private String typicalOptionID = "504"; 
	private String customizedOptionID = "503"; 
	private String nextButtonID = "Button3"; 
	
	InstallerSetupTypeStep(){
		
	}
	
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin("InstallShield Wizard" , "Typical");
	}
	
	/**
	 * Wait for page to be ready
	 */
	public void waitTo(){
		waitTo("Setup Type");
	}
	
	
	public void selectType(SetupType type){
		switch (type) {
		case Typical:
			AutoItAPI.check("InstallShield Wizard", typicalOptionID); 
			break;
		case Customized:
			AutoItAPI.check("InstallShield Wizard", customizedOptionID); 
			break;
		}
		TestManager.sleep(1000);
		
	}
	
	public void clickNext(){
		AutoItAPI.clickButton("InstallShield Wizard", "Typical", nextButtonID);
	}
	
	
	
	public enum SetupType{
		Typical,Customized;
	}



	

}
