package buisness.modules;

import java.util.ArrayList;
import java.util.List;

public class IncidentTable {
	
	private List<Incident> table;
	
	public IncidentTable(){
		table = new ArrayList<Incident>();
	}
	
	public void add(Incident newIncident){
		table.add(newIncident);
	}
	
	public List<Incident> getList(){
		return table;
	}
	
	
	public int total(){
		return table.size();
	}

}
