package utils;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.github.javafaker.Faker;

public class TestData {

	 Faker faker;
	 
	 
	@DataProvider(name = "getTestData")
	public Object[][] getData(Method method)
	{
		 faker = new Faker();
		 String listname = faker.ancient().hero();
		 String description = faker.ancient().primordial();
		if(method.getName().equalsIgnoreCase("createMovieList"))
		{
		return new Object[][] {
			{listname,description,"en"}			
		};
		}
		else if(method.getName().equalsIgnoreCase("updateMovieList")) 
		{
			return new Object[][] {
				
				{description+" - Updatedd"}
				
			};
		}
		else
			return null;
		
	}
}
