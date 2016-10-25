package buisness.tools.usermanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.core.base.LogManager;
import com.core.db.DbFactory;

public class Company {
	
	
	private String name;
	private String id = "cmdb";
	private String address;
	private String city;
	private String zip;
	private String country;
	private String phone;
	private String fax;
	private String expirationDate;
	private int version = 1;
	
	
	private static HashMap<String, Company> companies;
	
	static{
		companies = new HashMap<String, Company>();
	}
	
	
	/**
	 * 
	 * @param name - SHOULD BE Unique!!
	 * @param address
	 * @param zip
	 * @param country
	 * @param phone
	 * @param fax
	 */
	private Company(String name, String address, String city,String zip, String country, String phone, String fax) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.country = country;
		this.phone = phone;
		this.fax = fax;
		
	}
	
	/**
	 * Create a new Company via DB Insert Query
	 * And add to company list
	 * @param name - SHOULD BE Unique!!
	 * @param address
	 * @param city
	 * @param zip
	 * @param country
	 * @param phone
	 * @param fax
	 */
	public static void createNewCompany(String name, String address, String city,String zip, String country, String phone, String fax){
		new Company(name, address, city , zip, country, phone, fax).addAsNew();
		
	}
	
	private void addAsNew(){
		LogManager.debug("Add New Company: " + this.name);
		try {
			if(companies.containsKey(this.name))
				throw new Exception("Company with name= " + this.name + " - already exists! MUST Be unique name");
			boolean pass = DbFactory.get().execUpdate(this.getQuery());
			if(pass)
				companies.put(this.name, this);
		} catch (Exception e) {
			LogManager.error("Add New Company - Error: " + e.getMessage());
		}
	}
	
	private String getQuery() {
		//String q = "INSERT INTO company(account_id, company_name, address, city, zip, country, phone, fax, version)" + "VALUES('cmdb', 'SysAid', 'Auto1', 'City', '70100', 'Israel', '972-3-5333675', '972-3-7617205', 1)";
		StringBuilder sb = new StringBuilder("INSERT INTO company(account_id, company_name, address, city, zip, country, phone, fax, version)");
		sb.append(String.format("VALUES('cmdb', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d)" , 
				this.name,this.address,this.city,this.zip,this.country,this.phone,this.fax,this.version));
		return sb.toString();
	}
	
	
	public static List<Company> getAll(){
		return new ArrayList<Company>(companies.values());
	}
	
	public static int getTotal(){
		return companies.size();
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}

	public String getPhone() {
		return phone;
	}

	public String getFax() {
		return fax;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public int getVersion() {
		return version;
	}

}
