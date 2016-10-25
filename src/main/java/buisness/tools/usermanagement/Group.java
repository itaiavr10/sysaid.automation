package buisness.tools.usermanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.core.base.LogManager;
import com.core.db.DbFactory;

public class Group {

	private String id = "cmdb";
	private String name;
	private GroupType type;
	private int supportLevel;

	private static HashMap<String, Group> groups;

	static {
		groups = new HashMap<String, Group>();
		groups.put("Support", new Group("Support", GroupType.Administrators, 0));
	}

	/**
	 * 
	 * @param name  - MUST be unique
	 * @param type
	 * @param supportLevel
	 */
	private Group(String name, GroupType type, int supportLevel) {
		this.name = name;
		this.type = type;
		this.supportLevel = supportLevel;
	}

	/**
	 * Create a new Group via DB Insert Query And add to group list
	 * 
	 * @param name
	 *            - should be unique
	 * @param type
	 * @param supportLevel
	 */
	public static void createNewGroup(String name, GroupType type, int supportLevel) {
		new Group(name, type, supportLevel).addAsNew();

	}

	private void addAsNew() {
		LogManager.debug("Add New Group: " + this.name);
		try {
			if (groups.containsKey(this.name))
				throw new Exception("Group with name= " + this.name + " - already exists! MUST Be unique name");
			boolean pass = DbFactory.get().execUpdate(this.getQuery());
			if (pass)
				groups.put(this.name, this);
		} catch (Exception e) {
			LogManager.error("Add New Group - Error: " + e.getMessage());
		}
	}

	private String getQuery() {
		StringBuilder sb = new StringBuilder("INSERT INTO user_groups(account_id, group_name, group_type, support_level)");
		sb.append(String.format("VALUES('cmdb', '%s', '%d', %d)", this.name, this.type.id, this.supportLevel));
		return sb.toString();
	}
	
	public static List<Group> getAll(){
		return new ArrayList<Group>(groups.values());
	}
	
	public static int getTotal(){
		return groups.size();
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type.toString();
	}
	
	public String getSupportLevel() {
		return Integer.toString(supportLevel);
	}
	
	public enum GroupType{
		Administrators(1,"Administrators"),
		EndUser(2,"End users")
		;
		private int id;
		private String name;
		
		private GroupType(int id , String name){
			this.id = id;
			this.name = name;
		}
		
		public String toString(){
			return this.name;
		}
		
	}


}
