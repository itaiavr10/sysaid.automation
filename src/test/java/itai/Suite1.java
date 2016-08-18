package itai;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import utils.SystemUtils;
import utils.XmlUtils;
import base.AbstractSuite;

public class Suite1 extends AbstractSuite{
	
	@Test
	public void A(){
		//XmlUtils.validteNodeValue("C:\\AgentConfigurationFile.xml", "FirstTime", "Y", 120000, 5000);
		SystemUtils.scanFile("C:\\SysAidAgentLog.txt", "Error","Exception");
		
	}

}
