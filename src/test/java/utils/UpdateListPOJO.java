package utils;

import org.apache.juneau.annotation.Beanc;

public class UpdateListPOJO {

	private String description;


	@Beanc(properties = "description")
	public UpdateListPOJO(String description) {		
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
