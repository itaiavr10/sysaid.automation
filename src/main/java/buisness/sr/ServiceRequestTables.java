package buisness.sr;

import java.util.ArrayList;
import java.util.List;

import buisness.sr.AbstractSR.Category;
import buisness.sr.AbstractSR.PriorityType;

public class ServiceRequestTables {
	
	private static ServiceRequestTables srTables;
	
	private List<AbstractSR> allTable;
	private List<IncidentSR> incidentTable;
	private List<RequestSR> requestTable;
	
	public static ServiceRequestTables get(){
		if(srTables == null)
			srTables = new ServiceRequestTables();
		return srTables;
	}
	
	private ServiceRequestTables(){
		initTables();
		initAllTable();
	}

	private void initAllTable() {
		for (AbstractSR sr : incidentTable) {
			allTable.add(sr);
		}
		for (AbstractSR sr : requestTable) {
			allTable.add(sr);
		}
		
	}

	private void initTables() {
		allTable = new ArrayList<AbstractSR>();
		initIncidents();
		initRequests();
		
	}

	private void initRequests() {
		RequestSR  req1 = new RequestSR(Category.BASIC_SOFTWARE, "Other", "How to?", "Welcome to SysAid!", "description").setID("224");
		requestTable = new ArrayList<RequestSR>();		
		requestTable.add(req1);
	}

	private void initIncidents() {
		IncidentSR welcome = new IncidentSR(Category.BASIC_SOFTWARE, "Other", "How to?", "Welcome to SysAid!", "description" , PriorityType.Normal,"").setID("6");
		incidentTable = new ArrayList<IncidentSR>();
		incidentTable.add(welcome);
	}
	
	
	public List<IncidentSR> incidents(){
		return this.incidentTable;
	}
	
	public void addIncident(IncidentSR newIncident){
		incidentTable.add(newIncident);
		allTable.add(newIncident);
	}
	
	public void addRequest(RequestSR newRequest){
		requestTable.add(newRequest);
		allTable.add(newRequest);
	}

}
