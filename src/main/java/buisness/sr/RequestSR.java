package buisness.sr;

public class RequestSR extends AbstractSR{

	public RequestSR(Category category, String sub_category, String third_category, String title, String description) {
		super(category, sub_category, third_category, title, description);
	}
	
	
	@Override
	public void addToTable() {
		ServiceRequestTables.get().addRequest(this);
	}
	
	@Override
	public String getSuffix() {
		return "sr?type=10";
	}

}
