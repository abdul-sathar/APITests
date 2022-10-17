package com.tmdb.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.juneau.json.JsonSerializer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static Map<String,String> requestHeaders;
	public static JsonSerializer jsonSerrializer;
	
	static
	{
		jsonSerrializer = JsonSerializer.DEFAULT_READABLE;
		prop = new Properties();	
		try {
			prop.load(new FileInputStream(new File("./src/main/resources/config/config.properties")));
		} catch (IOException e) {			
			e.printStackTrace();
		}
	
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome"))
		{				
			requestHeaders = new HashMap<String, String>();		
			requestHeaders.put("content-type","application/json;charset=utf-8");
			requestHeaders.put("authorization","Bearer "+prop.getProperty("BearerToken"));			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}	
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(30,TimeUnit.SECONDS);
	}
		
	
}
	
