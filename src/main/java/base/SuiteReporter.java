package base;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import utils.ConfigProperties;
import utils.MailUtils;
import utils.SystemUtils;

public class SuiteReporter {

	private Logger log = null;
	private ArrayList<String> logTable;
	private boolean testFailed;
	private String suiteName;

	SuiteReporter(String suiteName) {
		this.logTable = new ArrayList<String>();
		this.logTable.add("<table border=\"4\" style=\"width:100%\">");
		this.suiteName = suiteName;
		addTableRow(ReportStepType.BOLD_Type, "XML Suite: " + suiteName);
		this.testFailed = false;
		init();
	}

	private void init() {
		
		String propertiesPath = SystemUtils.Files.getResourcesDirectoryPath() + "\\conf\\log4j.properties"; //TODO : Should be according to os 
		if (!new File(propertiesPath).exists())
			System.err.println("log4j.properties is missing under : " + propertiesPath);
		PropertyConfigurator.configure(propertiesPath);
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		Class<?> cls = null;
		try {
			cls = Class.forName(ste[1].getClassName());
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
		log = Logger.getLogger(cls);
		log.info("XML Suite: " + this.suiteName);
	}

	public void bold(String msg) {//TODO
		addTableRow(ReportStepType.BOLD_Type, msg);
		log.info(msg);
	}

	public void pass(String msg) {//TODO
		addTableRow(ReportStepType.Pass_Type, msg);
		log.info(msg);
	}
	
	public void debug(String msg) {
		//addTableRow(ReportStepType.Pass_Type, msg);
		log.debug(msg);
	}

	public void info(String msg) {
		addTableRow(ReportStepType.Info_Type, msg);
		log.info(msg);
	}

	public void warn(String msg) {
		addTableRow(ReportStepType.Warning_Type, msg);
		log.warn(msg);
	}

	public void error(String msg) {
		addTableRow(ReportStepType.Fail_Type, msg);
		log.error(msg);
		this.testFailed = true;
	}

	private void addTableRow(ReportStepType reportStepType, String msg) {
		logTable.add("<tr bgcolor=\" " + reportStepType.getColor() + "\">");
		logTable.add("<td>" + msg + "<td>");
		logTable.add("<td>" + reportStepType.name + "<td>");
		logTable.add("</tr>");
	}
	
	 public  void sendResult(){
	        logTable.add("</table>");
	        String[] recipients = (ConfigProperties.getValue("mailRecipients")).split(";");
	        String[] bccRecipients = new String[]{};
	        String subject = "Test Resuls";
	        if (testFailed)
	            subject += " - Failed";
	        else
	            subject += " - Passed";
	        new MailUtils().sendMail(recipients, bccRecipients, subject, logTable.toString().replace(",","").replace("[","").replace("]",""));
	        //deleteScreenShots();
	    }



	private enum ReportStepType {
		Info_Type("Info") {
			@Override
			public String getColor() {
				return "#EEEEEE";
			}
		},
		BOLD_Type("Bold") {
			@Override
			public String getColor() {
				return "#888888";
			}
		},
		Pass_Type("Passed") {
			@Override
			public String getColor() {
				return "#00EE00";
			}
		},
		Fail_Type("Failed") {
			@Override
			public String getColor() {
				return "#FF0000";
			}
		},
		Warning_Type("Warning") {
			@Override
			public String getColor() {
				return "#FFFF00";
			}
		}

		;
		public String name;

		private ReportStepType(String name) {
			this.name = name;
		}

		public abstract String getColor();

	}

}
