package buisness.db;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import com.core.base.LogManager;
import com.core.base.TestManager;
import com.core.db.DBQuery;
import com.core.utils.SystemUtils;
import com.core.utils.XmlUtils;

import buisness.db.DBconst.TableContent;
import buisness.modules.SysAid;
import buisness.modules.SysAid.DataBaseType;

public class DBInstaller {

	private static String serviceName = "SQL Server (SYSAIDMSSQL)";
	private static String msSqlPath = "C:\\Program Files\\SysAidMsSql";
	public static String serverConfPath = "C:\\Program Files\\SysAidServer\\root\\WEB-INF\\conf\\serverConf.xml";

	/**
	 * Test #6 Verification - MSSQL embedded Only Typical Installation with MSSQL Embedded
	 */
	public static void verifyMsSqlEmbedded() { //TODO: Abstraction
		//Verify service installed and running
		SystemUtils.Services.verify(serviceName, true);
		// verify directory
		SystemUtils.Files.verifyExist(msSqlPath, true);

		verifyServerConfFile(); //TODO : This file always created! not only on Embedded mode!

	}

	/**
	 * Verify serverconf.xml contain correct database connection details embedded only
	 */
	private static void verifyServerConfFile() {
		//
		LogManager.debug("Verify serverconfig.xml");
		Hashtable<String, String> table = new Hashtable<String, String>();
		table.put("dbDriver", "net.sourceforge.jtds.jdbc.Driver");
		table.put("dbUrl", "jdbc:jtds:sqlserver://localhost:1450/ilient;useCursorsAlways=true");
		table.put("dbUser", "sa");
		table.put("dbType", "mssql");
		table.put("dbCPType", "TomcatCP");
		table.put("dbMultiply", "false");
		table.put("dbMainName", "ilient");
		table.put("dbPoolMaxConn", "100");
		table.put("dbPoolIdleTimeout", "360");
		table.put("dbPoolCheckoutTimeout", "100");
		table.put("dbPoolMaxCheckout", "100");

		XmlUtils.verifyNodeList(serverConfPath, table);
	}

	/**
	 * Test #252 Verification - Database (part II): Tables content
	 */
	public static void verifyTableContents() {
		LogManager.debug("Verify DB - Tables Content");
		//Step 1 Table: account
		DBQuery.verifyNumOfRows("SELECT COUNT(*) FROM ACCOUNT", 1);
		DBQuery.verifyTable(TableContent.ACCOUNT);

		//Step 2 Table: account_attributes  
		DBQuery.verifyNumOfRows("SELECT COUNT(*) FROM ACCOUNT", 1);
		DBQuery.verifyResult("SELECT ACCOUNT_ID FROM ACCOUNT_ATTRIBUTES", "cmdb");

		//Step 3 Table: sysaid_user 
		DBQuery.verifyNumOfRows("SELECT COUNT(*) FROM SYSAID_USER", 1);
		DBQuery.verifyTable(TableContent.SYSAID_USER);

		//Step 4 Table: service_req 
		DBQuery.verifyResult("SELECT COUNT(*) FROM SERVICE_REQ", "24");
		DBQuery.verifyTable(TableContent.SERVICE_REQ);

		//Step 5 Table: sr_sub_type
		DBQuery.verifyResult("SELECT COUNT(*) FROM SR_SUB_TYPE", "13");
		DBQuery.verifyTable(TableContent.SR_SUB_TYPE);

		//Step 6 Table: list_view
		DBQuery.verifyResult("SELECT COUNT(*) FROM LIST_VIEW", "124");
		DBQuery.verifyTable(TableContent.LIST_VIEW);

		//Step 7 Table: sub_tab_views 
		DBQuery.verifyResult("SELECT COUNT(*) FROM SUB_TAB_VIEWS", "35");

		//Step 8 Table: problem_type
		DBQuery.verifyResult("SELECT COUNT(*) FROM PROBLEM_TYPE", "219");
		DBQuery.verifyTable(TableContent.PROBLEM_TYPE);

		//Step 9 Table: cust_values 
		DBQuery.verifyResult("SELECT COUNT(*) FROM CUST_VALUES", "106");
		DBQuery.verifyTable(TableContent.CUST_VALUES);

		//Step 10 Table: custom_columns
		DBQuery.verifyTable(TableContent.CUSTOM_COLUMNS);

		//Step 11 Table: ui_menus 
		DBQuery.verifyResult("SELECT COUNT(*) FROM UI_MENUS", "204");

		//Step 12 Table: reports 
		DBQuery.verifyResult("SELECT COUNT(*) FROM REPORTS", "73");
		DBQuery.verifyTable(TableContent.REPORTS);

		//Step 13 Table: discovery_service
		DBQuery.verifyTable(TableContent.DISCOVERY_SERVICE);

		/*//Step 14 DISCOVERT_SERVICE 
		DBQuery.verifyTable(TableContent.COMPUTER);*/

		//Step 15 Table: faq
		DBQuery.verifyResult("SELECT COUNT(*) FROM FAQ", "1");

		//Step 16 Table: agreement 
		DBQuery.verifyResult("SELECT COUNT(*) FROM AGREEMENT", "1");
		DBQuery.verifyResult("SELECT title FROM AGREEMENT", "DEFAULT SLA");

		//Step 17 Table: asset_types
		DBQuery.verifyTable(TableContent.ASSET_TYPES);

		//Step 18 Table: ci_type 
		DBQuery.verifyTable(TableContent.CI_TYPE);

		//Step 19 Table: ci_sub_type 
		DBQuery.verifyTable(TableContent.CI_SUB_TYPE);

		//Step 20 Table: commands 
		DBQuery.verifyTable(TableContent.COMMANDS);

		//Step 21 Table: reminders
		DBQuery.verifyResult("SELECT COUNT(*) FROM REMINDERS", "2");

		//Step 22 Table: schedule_task
		DBQuery.verifyResult("SELECT COUNT(*) FROM SCHEDULE_TASK", "2");

		//Step 14 DISCOVERT_SERVICE 
		DBQuery.verifyTableRetry(TableContent.COMPUTER, 60 * 1000, 3 * 1000);

	}

	/**
	 * Test #11 Verification - Database (part I): DB Tables Count
	 */
	public static void verifyTablesCount() {//TODO : Should be according to DBType
		LogManager.debug("Verify DB - Tables Count");
		DataBaseType current = SysAid.getDbType();
		switch (current) {
		case MsSQL:
			verifyTablesCountMsSQL();
			break;
		case MySQL:
			verifyTablesCountMySQL();
			break;
		default:
			LogManager.error("Unsupported table verification on DB: " + current);
		}

	}

	private static void verifyTablesCountMySQL() {
		// Count all tables in database
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient'", "265");
		//Tables (prefix = account)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'ACCOUNT%'", "2");
		//Tables (prefix = sysaid_user)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'SYSAID_USER%'", "8");
		//Tables (prefix = service_req)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'SERVICE_REQ%'", "7");
		//Tables (prefix = qrtz)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'QRTZ%'", "11");
		//Tables (prefix = addon)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'ADDON%'", "8");
		//Tables (prefix = asset)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'ASSET%'", "12");
		//Tables (prefix = company)
		DBQuery.verifyResult(" SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'COMPANY%'", "4");
		//Tables (verifyResult = computer)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'COMPUTER%'", "24");
		//Tables (prefix = ci)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'CI%'", "10");
		//Tables (prefix = project)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'PROJECT%'", "6");
		//Tables (prefix = task)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'TASK%'", "7");
		//Tables (prefix = reports)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'REPORTS%'", "18");
		//Tables (prefix = reminders)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'REMINDERS%'", "1");
		//Tables (prefix = list_view)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'LIST_VIEW%'", "1");
		//Tables (prefix = problem_type)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'PROBLEM_TYPE%'", "1");
		//Tables (prefix = sr_sub_type)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'ilient' AND TABLE_NAME LIKE 'SR_SUB_TYPE%'", "1");

		//Tables and Rows (optional) - TODO!
	}

	private static void verifyTablesCountMsSQL() {
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", "265");
		//Tables (prefix = account)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'ACCOUNT%'", "2");
		//Tables (prefix = sysaid_user)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'SYSAID_USER%'", "8");
		//Tables (prefix = service_req)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'SERVICE_REQ%'", "7");
		//Tables (prefix = qrtz)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'QRTZ%'", "11");
		//Tables (prefix = addon)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'ADDON%'", "8");
		//Tables (prefix = asset)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'ASSET%'", "12");
		//Tables (prefix = company)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'COMPANY%'", "4");
		//Tables (verifyResult = computer)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'COMPUTER%'", "24");
		//Tables (prefix = ci)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'CI%'", "10");
		//Tables (prefix = project)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'PROJECT%'", "6");
		//Tables (prefix = task)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'TASK%'", "7");
		//Tables (prefix = reports)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'REPORTS%'", "18");
		//Tables (prefix = reminders)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'REMINDERS%'", "1");
		//Tables (prefix = list_view)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'LIST_VIEW%'", "1");
		//Tables (prefix = problem_type)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'PROBLEM_TYPE%'", "1");
		//Tables (prefix = sr_sub_type)
		DBQuery.verifyResult("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'SR_SUB_TYPE%'", "1");

		//Tables and Rows (optional) - TODO!
	}

}
