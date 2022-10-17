package com.tmdb.tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.serializer.SerializeException;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import com.google.common.util.concurrent.Uninterruptibles;
import com.tmdb.reports.TestBase;
import com.tmdb.reports.TestListeners;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.AddItemsToListPOJO;
import utils.ItemPOJO;
import utils.MovieListPOJO;
import utils.TestData;
import utils.UpdateListPOJO;

public class ListTest extends TestBase {

	public ListTest() throws FileNotFoundException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String listID;
	public WebElement listName;
	

	@Test(dataProviderClass = TestData.class, dataProvider = "getTestData")
	public void createMovieList(String listtName, String listDesc, String language) throws Exception {
		
		String jsonString = jsonSerrializer.serialize(new MovieListPOJO(listtName, listDesc, language));
		listID = RestAssured.given().headers(requestHeaders).body(jsonString).post("https://api.themoviedb.org/4/list")
				.jsonPath().get("id").toString();

		TestListeners.extentTest.get().pass("Created a new list " +listID);
		Assertions.assertThat(listID).isNotBlank();

		// this script is take a screenshot of UI 
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
	}

	

	@Test
	public void addItemsToList() throws SerializeException {

		ItemPOJO item1 = new ItemPOJO("movie", 125, "Amazing movie!");
		ItemPOJO item2 = new ItemPOJO("tv", 135, "Wow");
		List<ItemPOJO> item = new ArrayList<ItemPOJO>();
		item.add(item1);
		item.add(item2);

		String jsonString = jsonSerrializer.serialize(new AddItemsToListPOJO(item));
		String expectedStatusMessage = "Success.";
		
		Response response = RestAssured.given()
				.headers(requestHeaders)
				.pathParam("list_id", listID)
				.body(jsonString).post("https://api.themoviedb.org/4/list/{list_id}/items");
		
		String actualStatusMessage = response.jsonPath().get("status_message");		 

	
		Assertions.assertThat(actualStatusMessage).isEqualTo(expectedStatusMessage);
		TestListeners.extentTest.get().pass("Items added to the List " +listID+" are: \n"
					+response.asPrettyString());
		
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
	}

	@Test
	public void UpdateItemsToList() throws SerializeException {

		ItemPOJO item1 = new ItemPOJO("movie", 125, "Amazing movie! Updated");
		ItemPOJO item2 = new ItemPOJO("tv", 135, "Wow updated");
		List<ItemPOJO> item = new ArrayList<ItemPOJO>();
		item.add(item1);
		item.add(item2);

		String jsonString = jsonSerrializer.serialize(new AddItemsToListPOJO(item));
		
		Response response = RestAssured.given()
			.headers(requestHeaders)
			.pathParam("list_id", listID)
			.body(jsonString)
			.post("https://api.themoviedb.org/4/list/{list_id}/items");
		String statusmessage = response.jsonPath().get("status_message");
		int statuscode= response.jsonPath().get("status_code");
				
		TestListeners.extentTest.get().pass("List of Items after update  " +listID+" are: \n"
				+response.asPrettyString());
		
		Assertions.assertThat(statusmessage).isEqualTo("Success.");
		Assertions.assertThat(statuscode).isEqualTo(1);
		
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

	}

	@Test
	public void removeItemsFromList() throws SerializeException {

		ItemPOJO item1 = new ItemPOJO("movie", 125, "Amazing movie!");
		List<ItemPOJO> item = new ArrayList<ItemPOJO>();
		item.add(item1);

		String jsonString = jsonSerrializer.serialize(new AddItemsToListPOJO(item));

		Response response = RestAssured.given()
			.headers(requestHeaders)
			.pathParam("list_id", listID)
			.body(jsonString)
			.delete("https://api.themoviedb.org/4/list/{list_id}/items");
		
		String statusmessage = response.jsonPath().get("status_message");
		int statuscode= response.jsonPath().get("status_code");
		
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
		TestListeners.extentTest.get().pass("List of Items after removing a item from the list  " +listID+" are: \n"
				+response.asPrettyString());
		Assertions.assertThat(statusmessage).isEqualTo("Success.");
		Assertions.assertThat(statuscode).isEqualTo(1);
		
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
	}

	@Test
	public void clearTheList() {
	
		Response response = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
				.get("https://api.themoviedb.org/4/list/{list_id}/clear");
		String statusMessage = response.jsonPath().get("status_message");
		int statuscode = response.jsonPath().get("status_code");
		
		TestListeners.extentTest.get().pass("List of Items after removing a item from the list  " +listID+" are: \n"
				+response.asPrettyString());
		Assertions.assertThat(statusMessage).isEqualTo("Success.");
		Assertions.assertThat(statuscode).isEqualTo(1);
		
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
	}

	@Test(dataProviderClass = TestData.class, dataProvider = "getTestData")
	public void updateMovieList(String updatedDescription) throws SerializeException {

		String jsonString = jsonSerrializer.serialize(new UpdateListPOJO(updatedDescription));
		
		TestListeners.extentTest.get().pass("Json \n"+jsonString);
		
		Response response = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID).body(jsonString)
				.put("https://api.themoviedb.org/4/list/{list_id}");
		
		String statusmessage = response.jsonPath().get("status_message");
		int statuscode = response.jsonPath().get("status_code");
		
		TestListeners.extentTest.get().pass("After <b> Description </b> Update" +listID+" are: \n"
				+response.asPrettyString());
		Assertions.assertThat(statusmessage).isEqualTo("The item/record was updated successfully.");
		Assertions.assertThat(statuscode).isEqualTo(12);
		
		driver.get(prop.getProperty("url"));
		driver.findElement(By.xpath("//a[contains(@href,'" + ListTest.listID + "')]")).click();				
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
	}

	@Test
	public void deleteMovieList() throws InterruptedException {

		Response response = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
				.delete("https://api.themoviedb.org/4/list/{list_id}");

		TestListeners.extentTest.get().pass("After Deleteting List " +listID+" are: \n"
				+response.asPrettyString());
		
		String message = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
				.get("https://api.themoviedb.org/4/list/{list_id}").jsonPath().get("status_message");
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
		Assertions.assertThat(message).isEqualTo("The resource you requested could not be found.");
		
		driver.get(prop.getProperty("url"));						
		Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
		
	}
	
	@Test
	public void deleteAlreadyMovieList() throws InterruptedException {

	Response response = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
	.delete("https://api.themoviedb.org/4/list/{list_id}");

	TestListeners.extentTest.get().pass("After Deleteting List " +listID+" are: \n"
	+response.asPrettyString());

	String message = RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
	.get("https://api.themoviedb.org/4/list/{list_id}").jsonPath().get("status_message");
	int statusCode= RestAssured.given().headers(requestHeaders).pathParam("list_id", listID)
	.get("https://api.themoviedb.org/4/list/{list_id}").jsonPath().get("status_code");
	Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

	Assertions.assertThat(message).isEqualTo("The resource you requested could not be found.");
	Assertions.assertThat(statusCode).isEqualTo(34);


	driver.get(prop.getProperty("url"));
	Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);

	}

	@AfterTest
	public void tearDown(ITestContext testcontext) {
		driver.quit();	
		
	}
}
