package com.tmdb.reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {

	private static ExtentReports extentReport;
	
	public static ExtentTest test;
	
	public  static ExtentReports createInstance() {
		
		String fileName= getReportName();
		String directory =  System.getProperty("user.dir")+"/reports/";
		new File(directory).mkdirs();
		String path = directory + fileName;
		extentReport = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(path);
		extentReport.attachReporter(spark);
		spark.config().setDocumentTitle("API Tests Report");
		spark.config().setTheme(Theme.STANDARD);
		spark.config().setReportName("TMDB API Tests Report");
		
		
		return extentReport;
 
	}
	
	 public static String getReportName() {
		  
		  Date d = new Date();
		  String filename = "Automation Report_"+d.toString().replace(":", "_").replace(" ", "_")+".html";
		  return filename;
		  
	  }
	
}
