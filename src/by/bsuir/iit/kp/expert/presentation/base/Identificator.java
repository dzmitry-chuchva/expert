package by.bsuir.iit.kp.expert.presentation.base;


public class Identificator {

	private String id;
	private String description;
	
	public Identificator() {
		
	}
	
	public Identificator(String id) {
		setId(id);
	}
	
	public Identificator(String id, String description) {
		setId(id);
		setDescription(description);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void reset() {
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (id != null) {
			buffer.append(id);
		}
		
		if (description != null) {
			if (id != null) {
				buffer.append(" ");
			}
			buffer.append(description);
		}
		
		return buffer.toString();
	}

}
