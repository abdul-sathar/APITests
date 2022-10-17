package utils;

import org.apache.juneau.annotation.Beanc;

public class ItemPOJO {

	private String media_type;
	private int media_id;
	private String comment;
	
	
	public ItemPOJO(String media_type, int media_id, String comment) {
		super();
		this.media_type = media_type;
		this.media_id = media_id;
		this.comment = comment;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public int getMedia_id() {
		return media_id;
	}
	public void setMedia_id(int media_id) {
		this.media_id = media_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
	
}
