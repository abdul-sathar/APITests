package utils;

import org.apache.juneau.annotation.Beanc;
import java.util.List;

public class AddItemsToListPOJO {

	
	private List<ItemPOJO> items;
	
	@Beanc(properties = "items")
	public AddItemsToListPOJO(List<ItemPOJO> items) {
		super();
		this.items = items;
	}	

	public List<ItemPOJO> getItems() {
		return items;
	}

	public void setItems(List<ItemPOJO> items) {
		this.items = items;
	}

	
	
	
}
