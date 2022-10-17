package utils;

import org.apache.juneau.annotation.Beanc;

public class MovieListPOJO {

	private String name;
	private String description;
	private String iso_639_1;
	
	@Beanc(properties = "name,description,iso_639_1")
	public MovieListPOJO(String name, String description, String iso_639_1) {		
		this.name = name;
		this.description = description;
		this.iso_639_1 = iso_639_1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIso_639_1() {
		return iso_639_1;
	}
	public void setIso_639_1(String iso_639_1) {
		this.iso_639_1 = iso_639_1;
	}
	
	
	
}
