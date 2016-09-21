package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerSelectDbStep extends InstallerAbstractStep {
	
	
	private String embeddedCheckBoxOptionId = "1203";
	private String externalCheckBoxOptionId = "1204";
	private String nextButtonID = "1";

	InstallerSelectDbStep() {
		super("Use embedded MsSQL database");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
		
	}*/

	@Override
	public void waitTo() {
		waitTo("select DB");
	}
	
	
	public void selectEmbedded(){
		AutoItAPI.check(installerTitle, embeddedCheckBoxOptionId);
	}
	
	public void selectExternal(){
		AutoItAPI.check(installerTitle, externalCheckBoxOptionId);
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}
	

}
