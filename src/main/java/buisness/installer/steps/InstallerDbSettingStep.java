package buisness.installer.steps;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.utils.AutoItAPI;

import buisness.modules.SysAid;
import buisness.modules.SysAid.DataBaseType;

public class InstallerDbSettingStep extends InstallerAbstractStep{
	
	private String dbTypeComboBoxId = "ComboBox1";
	private String dbDriverFieldId = "Edit1";
	private String dbUrlFieldId = "Edit2";
	private String userFieldId = "Edit3";
	private String passFieldId = "Edit4";
	private String checkButtonId = "2004";
	private String nextButtonId = "Button2";
	private String connectionOkMsgId = "2005"; 

	InstallerDbSettingStep() {
		super("Database Type");
	}

	/*@Override
	public void waitTo(String logInfo) {
		LogManager.info("Step: " + logInfo);
		AutoItAPI.waitWin(installerTitle , visibleText);
	}*/

	@Override
	public void waitTo() {
		waitTo("DB Connection settings");
	}
	
	public void setDB(DataBaseType dbType){
		AutoItAPI.selectFromDropDownList(installerTitle, dbTypeComboBoxId, dbType.name);
		SysAid.setDbType(dbType);
		TestManager.sleep(3000);
		AutoItAPI.activateWindow(installerTitle,visibleText); //reactivate installer page
		//only in external mode set url
	/*	AutoItAPI.setText(installerTitle, dbDriverFieldId, dbType.getDriver());
		TestManager.sleep(500);
		AutoItAPI.setText(installerTitle, dbUrlFieldId, dbType.getURL());
		TestManager.sleep(500);*/
	}
	
	public void setCredentials(String user, String pass){
		AutoItAPI.setText(installerTitle, userFieldId, user); // Set User
		TestManager.sleep(500);
		AutoItAPI.setText(installerTitle, passFieldId, pass); //Set Pass
		TestManager.sleep(500);
	}
	
	public void checkConnection(){
		AutoItAPI.clickButton(installerTitle, "", checkButtonId);
		TestManager.sleep(2000);
		AutoItAPI.verifyVisibility(installerTitle, connectionOkMsgId, true);
	}
	
	public void clickNext(){
		AutoItAPI.clickButton(installerTitle, "", nextButtonId);
		TestManager.sleep(500);
	}

}
