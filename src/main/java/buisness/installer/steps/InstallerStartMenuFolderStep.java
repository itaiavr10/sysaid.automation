package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.utils.AutoItAPI;

public class InstallerStartMenuFolderStep extends InstallerAbstractStep{

	InstallerStartMenuFolderStep() {
		super("add program icons to the Program Folder");
		// TODO Auto-generated constructor stub
	}

	private String nextButtonID = "1";
	
	@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
		
	}
	
	@Override
	public void waitTo() {
		waitTo("start menu folder");
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, visibleText, nextButtonID);
	}

}
