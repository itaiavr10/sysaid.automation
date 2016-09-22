package buisness.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buisness.modules.SysAidServer;

import com.core.db.AbstractDB;
import com.core.utils.SystemUtils;

public class DBconst {
	
	
	
	public enum TableContent{
		
		
		/**
		 * Verify  commands table
		 */
		 	COMMANDS(1) {
			@Override
			public String getDescription() {
				return "commands - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT COMMAND FROM COMMANDS";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("Inventory"));	
				table.add(Arrays.asList("Upgrade"));	
				return table;
			}
		},
		
		/**
		 * Verify  ci_sub_type table
		 */
		 	CI_SUB_TYPE(3) {
			@Override
			public String getDescription() {
				return "ci_sub_type - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT ID, CI_TYPE_ID, CAPTION FROM CI_SUB_TYPE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("1", "1", "Laptop"));	
				table.add(Arrays.asList("2", "1", "Smartphone"));	
				table.add(Arrays.asList("3", "1", "Printer"));	
				table.add(Arrays.asList("4", "1", "Server"));	
				table.add(Arrays.asList("5", "1", "Workstation"));	
				table.add(Arrays.asList("6", "1", "Switch"));	
				table.add(Arrays.asList("7", "1", "Router"));	
				table.add(Arrays.asList("8", "1", "DHCP Server"));	
				table.add(Arrays.asList("9", "1", "Other SNMP Device"));
				table.add(Arrays.asList("10", "1", "Other"));
				table.add(Arrays.asList("11", "1", "Tablet"));
				return table;
			}
		},
		
		/**
		 * Verify  ci_type table
		 */
		 	CI_TYPE(1) {
			@Override
			public String getDescription() {
				return "ci_type - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT CI_TYPE_NAME FROM CI_TYPE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("Asset"));
				table.add(Arrays.asList("Software Product"));
				table.add(Arrays.asList("Catalog"));
				table.add(Arrays.asList("System Software"));
				table.add(Arrays.asList("Network Component"));
				table.add(Arrays.asList("Business Process"));
				table.add(Arrays.asList("Application"));
				table.add(Arrays.asList("Physical Database"));
				table.add(Arrays.asList("Company"));
				table.add(Arrays.asList("End User"));
				table.add(Arrays.asList("Administrator"));
				return table;
			}
		},
		
		/**
		 * Verify asset_types table
		 */
			ASSET_TYPES(1) {
			@Override
			public String getDescription() {
				return "asset_types - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT COMPUTER_TYPE FROM ASSET_TYPES";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("DHCP Server"));
				table.add(Arrays.asList("Laptop"));
				table.add(Arrays.asList("Other"));
				table.add(Arrays.asList("Other SNMP Device"));
				table.add(Arrays.asList("PDA"));
				table.add(Arrays.asList("Printer"));
				table.add(Arrays.asList("Router"));
				table.add(Arrays.asList("Server"));
				table.add(Arrays.asList("Switch"));
				table.add(Arrays.asList("Tablet"));
				table.add(Arrays.asList("Workstation"));
				return table;
			}
		},
		
		
		/**
		 * Verify computer table
		 */
			COMPUTER(3) {
			@Override
			public String getDescription() {
				return "computer - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT COMPUTER_NAME, IP_ADDRESS, AGENT_VERSION FROM COMPUTER";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList(SystemUtils.OS.getComputerName(),	AbstractDB.dbIP , SysAidServer.server_ver+".102"));  // TODO :  Computer Name + RDS Version            
				return table;
			}
		},
		
		
		/**
		 * Verify discovery_service table
		 * 
		 */
			DISCOVERY_SERVICE(2) {
			@Override
			public String getDescription() {
				return "discovery_service - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT DISCOVERY_SERVICE_NAME, VERSION FROM DISCOVERY_SERVICE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("SysAid Server",SysAidServer.server_ver));  // TODO : RDS Version            
				return table;
			}
		},
		
		
		/**
		 * Verify table content - count rows grouped by report type
		 */
			REPORTS(2) {
			@Override
			public String getDescription() {
				return "count rows grouped by report type - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT REPORTTYPE, COUNT(*) as qty FROM REPORTS GROUP BY REPORTTYPE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("bars","21"));        
				table.add(Arrays.asList("customized","4"));  
				table.add(Arrays.asList("line","3"));        
				table.add(Arrays.asList("list","22"));      
				table.add(Arrays.asList("pie","1"));       
				table.add(Arrays.asList("pivot","22"));     
				return table;
			}
		},
		
		/**
		 * Verify table content - custom_columns
		 */
		CUSTOM_COLUMNS(5) {
			@Override
			public String getDescription() {
				return "custom_columns - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT ENTITY_TYPE, FIELD_CAPTION, FIELD_TYPE, ADDON_DB_NAME, ENCRYPTION FROM CUSTOM_COLUMNS";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("sr","Activities", "activity", "sr_cust_activity", "N"));      
				table.add(Arrays.asList("task","Activities", "activity", "task_cust_activity", "N"));         
				return table;
			}
		},
		
		/**
		 * Verify table content - count values grouped by list name
		 */
		CUST_VALUES(2) {
			@Override
			public String getDescription() {
				return "count values grouped by list name - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT LIST_NAME, COUNT(*) as qty FROM CUST_VALUES GROUP BY LIST_NAME";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("changeCategory","4"));         
				table.add(Arrays.asList("CIStatus","5"));             
				table.add(Arrays.asList("closureInformation","7"));     
				table.add(Arrays.asList("currencyList","2"));           
				table.add(Arrays.asList("deviceOwnership","2"));       
				table.add(Arrays.asList("deviceStatus","4"));          
				table.add(Arrays.asList("priority","5"));              
				table.add(Arrays.asList("projectCats","5"));           
				table.add(Arrays.asList("projectStatuses","7"));      
				table.add(Arrays.asList("recurrenceList","3"));        
				table.add(Arrays.asList("status","27"));              
				table.add(Arrays.asList("subTabComplexity","5"));     
				table.add(Arrays.asList("subTabImpact","5"));          
				table.add(Arrays.asList("survey","5"));                
				table.add(Arrays.asList("taskCats","8"));             
				table.add(Arrays.asList("taskStatuses","3"));          
				table.add(Arrays.asList("urgency","5"));              
				table.add(Arrays.asList("userRoles","4"));           
				return table;
			}
		},
		
		/**
		 * Verify table content - count rows grouped by problem type
		 */
		PROBLEM_TYPE(2) {
			@Override
			public String getDescription() {
				return "count rows grouped by problem type - table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT PROBLEM_TYPE, COUNT(*) as qty FROM PROBLEM_TYPE GROUP BY PROBLEM_TYPE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("Application ABC","10"));      
				table.add(Arrays.asList("Basic Software","24"));      
				table.add(Arrays.asList("Data Center","11"));         
				table.add(Arrays.asList("ERP","18"));                 
				table.add(Arrays.asList("Mobile Devices","7"));       
				table.add(Arrays.asList("Network Equipment","11"));   
				table.add(Arrays.asList("Other Equipment","19"));    
				table.add(Arrays.asList("Servers","44"));             
				table.add(Arrays.asList("Telephony / Voice","12"));   
				table.add(Arrays.asList("User Workstation","63"));    
				return table;
			}
		},
		
		
		/**
		 * Verify LIST_VIEW GROUP BY LIST_NAME
		 */
		LIST_VIEW(2) {
			@Override
			public String getDescription() {
				return "LIST_VIEW GROUP BY LIST_NAME table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT LIST_NAME, COUNT(*) as qty FROM LIST_VIEW GROUP BY LIST_NAME";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("Agreement" ,"1")); 
				table.add(Arrays.asList("Assets" ,"11")); 
				table.add(Arrays.asList("AuditLog" ,"4" ));  
				table.add(Arrays.asList("AvailabilityLog" ,"1"));   
				table.add(Arrays.asList("Catalog" ,"1"));   
				table.add(Arrays.asList("CategoryList" ,"1"));   
				table.add(Arrays.asList("ChatQueue" ,"1"));   
				table.add(Arrays.asList("ChatQueueMessages" ,"1"));   
				table.add(Arrays.asList("CIList" ,"2"));   
				table.add(Arrays.asList("CIRelationList" ,"1"));   
				table.add(Arrays.asList("CIRelationTypeList" ,"1"));   
				table.add(Arrays.asList("CITemplateList" ,"1"));   
				table.add(Arrays.asList("CITypeList" ,"1"));   
				table.add(Arrays.asList("ClosedChats" ,"1"));   
				table.add(Arrays.asList("Company" ,"2"));   
				table.add(Arrays.asList("CustomizeEntitiesColumnsList" ,"1"));   
				table.add(Arrays.asList("EndUserRemoteAccess" ,"1"));   
				table.add(Arrays.asList("Favorites" ,"1"));   
				table.add(Arrays.asList("FilterValues" ,"2"));   
				table.add(Arrays.asList("Groups" ,"2"));   
				table.add(Arrays.asList("IncidentTemplates" ,"1"));   
				table.add(Arrays.asList("Install Names" ,"1"));   
				table.add(Arrays.asList("KBList" ,"2"));   
				table.add(Arrays.asList("LinkedSysaidItemsList" ,"1"));   
				table.add(Arrays.asList("MDMPolicyList" ,"1"));   
				table.add(Arrays.asList("MDMWifiPolicyList" ,"1"));   
				table.add(Arrays.asList("Measurement" ,"1"));   
				table.add(Arrays.asList("MeasurementList" ,"1"));   
				table.add(Arrays.asList("MeasurementListResults" ,"1"));   
				table.add(Arrays.asList("MonitorAlerts" ,"2"));   
				table.add(Arrays.asList("MonitorConfiguration" ,"1"));   
				table.add(Arrays.asList("MonitorConfigurationDetailed" ,"1"));   
				table.add(Arrays.asList("MonitorEventLog" ,"1"));   
				table.add(Arrays.asList("MonitorLastPoll" ,"1"));   
				table.add(Arrays.asList("MonitorNotifications" ,"1"));   
				table.add(Arrays.asList("MonitorNotificationsProduct" ,"1"));   
				table.add(Arrays.asList("MonitorTemplate" ,"1"));   
				table.add(Arrays.asList("MonitorTemplateType" ,"1"));   
				table.add(Arrays.asList("MonitorType" ,"1"));   
				table.add(Arrays.asList("MonitorTypeNoCompany" ,"1"));   
				table.add(Arrays.asList("MonitorTypeNoTemplate" ,"1"));   
				table.add(Arrays.asList("OAuthConsumersList" ,"1"));   
				table.add(Arrays.asList("OnlineUsersHistoryList" ,"1"));   
				table.add(Arrays.asList("OnlineUsersList" ,"1"));   
				table.add(Arrays.asList("PatchAssetManageList" ,"4"));   
				table.add(Arrays.asList("PatchManageList" ,"5"));   
				table.add(Arrays.asList("PatchPolicyList" ,"1"));   
				table.add(Arrays.asList("PatchPolicyManageList" ,"1"));   
				table.add(Arrays.asList("ProblemTemplates" ,"1"));   
				table.add(Arrays.asList("Projects" ,"1"));   
				table.add(Arrays.asList("RDSList" ,"1"));   
				table.add(Arrays.asList("Reminders" ,"1"));   
				table.add(Arrays.asList("RemoteAccessActiveSessions" ,"1"));   
				table.add(Arrays.asList("RemoteAccessibleAssets" ,"1"));   
				table.add(Arrays.asList("RemoteAccessibleUsers" ,"1"));   
				table.add(Arrays.asList("RemoteAssetsPerUser" ,"1"));   
				table.add(Arrays.asList("RemoteUsersPerAsset" ,"1"));   
				table.add(Arrays.asList("RequestTemplates" ,"1"));   
				table.add(Arrays.asList("RFCNotifications" ,"1"));   
				table.add(Arrays.asList("RFCTemplates" ,"1"));   
				table.add(Arrays.asList("RulesEmails" ,"1"));   
				table.add(Arrays.asList("SecurityQuestionsList" ,"1"));   
				table.add(Arrays.asList("SelectSRs" ,"1"));   
				table.add(Arrays.asList("SelectSRsNoITIL" ,"1"));   
				table.add(Arrays.asList("Service Requests" ,"10"));  
				table.add(Arrays.asList("Service Requests Actions" ,"2"));   
				table.add(Arrays.asList("Service Requests All" ,"3"));   
				table.add(Arrays.asList("Software" ,"1"));   
				table.add(Arrays.asList("SRActivities" ,"1"));   
				table.add(Arrays.asList("SubTabs" ,"1"));   
				table.add(Arrays.asList("SubType" ,"1"));   
				table.add(Arrays.asList("Suppliers" ,"1"));   
				table.add(Arrays.asList("SurveyQuestionsList" ,"1"));   
				table.add(Arrays.asList("SystemNotifications" ,"1"));   
				table.add(Arrays.asList("TaskActivities" ,"2"));   
				table.add(Arrays.asList("TaskNotifications" ,"1"));   
				table.add(Arrays.asList("Tasks" ,"2"));   
				table.add(Arrays.asList("Users" ,"3"));   
				table.add(Arrays.asList("UsersCompany" ,"1"));   
				table.add(Arrays.asList("UsersPs" ,"1"));   
				table.add(Arrays.asList("UssNotifications" ,"1"));   
				table.add(Arrays.asList("UssNotificationsPs" ,"1"));   
				
				return table;
			}
		},
		
		/**
		 * Verify SR_SUB_TYPE table
		 */
		SR_SUB_TYPE(3) {
			@Override
			public String getDescription() {
				return "SR_SUB_TYPE table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT SR_TYPE, SR_SUB_TYPE, SUB_TYPE_NAME FROM SR_SUB_TYPE order by SR_TYPE";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("1","6","DEFAULT"));
				table.add(Arrays.asList("1","7","Printer failure"));
				table.add(Arrays.asList("4","1","Normal Change"));
				table.add(Arrays.asList("4","2","Emergency Change"));
				table.add(Arrays.asList("4","5","Standard Change"));
				table.add(Arrays.asList("4","11","Patch Approval Process"));
				table.add(Arrays.asList("4","12","Change Proposal"));
				table.add(Arrays.asList("6","3","Advanced Problem"));
				table.add(Arrays.asList("6","4","Basic Problem"));
				table.add(Arrays.asList("6","13","ITIL Problem"));
				table.add(Arrays.asList("8","8","PHONE_CALL"));
				table.add(Arrays.asList("10","9","Advanced Request"));
				table.add(Arrays.asList("10","10","Basic Request"));
				return table;
			}
		},
		
		/**
		 * Verify account table
		 */
		//ACCOUNT(3) { // Remove EXPIRATION_TIME col.
		ACCOUNT(2) {
			@Override
			public String getDescription() {
				return "account table";
			}
			
			@Override
			public String getQuery() {
				//return "SELECT ACCOUNT_ID, SERIAL_KEY, EXPIRATION_TIME FROM ACCOUNT";
				return "SELECT ACCOUNT_ID, SERIAL_KEY FROM ACCOUNT";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				//table.add(Arrays.asList("cmdb","985305C75EBE5DEV","2017-08-31 12:01:35.467")); //TODO : get from activation.xml
				table.add(Arrays.asList("cmdb","985305C75EBE5DEV")); //TODO : get from activation.xml
				return table;
			}
		},
		
		
		/**
		 * Verify sysaid_user table
		 */
		SYSAID_USER(7) {
			@Override
			public String getDescription() {
				return "sysaid_user table";
			}
			
			@Override
			public String getQuery() {
				return "SELECT ref_id, user_name, administrator, disable, locale, timezone, charset FROM SYSAID_USER";
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("1","sysaid","Y","N","en_US", "EST" , "Unicode (utf-8)"));
				return table;
			}
		},
		
		
		/**
		 * Verify table content - count rows grouped by sr_type
		 */
		SERVICE_REQ(2) {
			@Override
			public String getDescription() {
				return "count rows grouped by sr_type";
			}
			
			@Override
			public String getQuery() {
				StringBuilder sb =new StringBuilder("SELECT CASE SR_TYPE")
				.append(" WHEN 1 THEN 'Incident'")
				.append(" WHEN 2 THEN 'Incident template'")
				.append(" WHEN 4 THEN 'Change'")
				.append(" WHEN 5 THEN 'Change template'")
				.append(" WHEN 6 THEN 'Problem'")
				.append(" WHEN 7 THEN 'Problem template'")
				.append(" WHEN 10 THEN 'Request'")
				.append(" WHEN 11 THEN 'Request template'")
				.append(" END AS SR_TYPE_TEXT, COUNT(*) AS QTY")
				.append(" FROM SERVICE_REQ GROUP BY SR_TYPE");
				
				return sb.toString();
			}
			
			@Override
			public List<List<String>> getExpected() {
				List<List<String>> table = new ArrayList<List<String>>();
				table.add(Arrays.asList("Incident","1"));
				table.add(Arrays.asList("Incident template" ,"6"));
				table.add(Arrays.asList("Change template" ,"5"));
				table.add(Arrays.asList("Problem template" ,"3"));
				table.add(Arrays.asList("Request template" ,"9"));
				return table;
			}
		};
		
		public int col;
		
		private TableContent(int numOfCol){
			this.col = numOfCol;	
		}
		
		
		public abstract String getDescription();
		public abstract String getQuery();
		public abstract List<List<String>> getExpected();
	
		
		
		
	}

}
