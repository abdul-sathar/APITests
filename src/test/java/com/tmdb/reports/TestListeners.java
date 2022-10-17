package com.tmdb.reports;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.util.concurrent.Uninterruptibles;
import com.tmdb.tests.ListTest;

import utils.FrameworkFileUtils;

public class TestListeners extends TestBase implements ITestListener {

	public TestListeners() throws  IOException {
		super(); 
	}

	 static ExtentReports extentReport = ExtentManager.createInstance();
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	public void onTestStart(ITestResult result) {
		ExtentTest test = extentReport.createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
		extentTest.set(test);
	}

	public void onTestSuccess(ITestResult result) {

		//String path = takeScreenshot(driver, result.getMethod().getMethodName());		
		//extentTest.get().pass("<b><font color=Green>Screenshot</font></b>",	MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		extentTest.get().pass("<b><font color=Green>Screenshot</font></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)).build());
		String logText = "<b>Test Method '" + result.getMethod().getMethodName() + "' passed </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, m);

		
	}

	public void onTestFailure(ITestResult result) {

		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		extentTest.get()
				.fail("<details><summary><b><font color=red>"
						+ "Exception Occured, click to see details:</font></b></summary>"
						+ exceptionMessage.replaceAll("<br>", "</details> \n"));

		// WebDriver driver = ((TestClassUsingListeners)result.getInstance()).driver;
		//String path = takeScreenshot(driver, result.getMethod().getMethodName());
		extentTest.get().fail("<b><font color=Red>Screenshot</font></b>", MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)).build());
		
		//extentTest.get().fail("<b><font color=Red>Screenshot</font></b>",MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		
		String logText = "<b>Test Method '" + result.getMethod().getMethodName() + "' failed </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, m);

	}

	public void onTestSkipped(ITestResult result) {
		String logText = "<b>Test Method '" + result.getMethod().getMethodName() + "' skipped </b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
		extentTest.get().log(Status.SKIP, m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println("Failure of test cases and its details are : " + result.getName());
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}
	
	public void onFinish(ITestContext context) {
		
		if (extentReport != null) {
			extentReport.flush();			
			try {
				Desktop.getDesktop().browse(FrameworkFileUtils.getTheNewestFile(System.getProperty("user.dir")+"/reports", "html"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		driver.quit();

	}

	public String takeScreenshot(WebDriver driver, String methodname) {
		String filename = getFileName(methodname);
		String directory = System.getProperty("user.dir") + "/screenshot/";
		new File(directory).mkdirs();
		String path = directory + filename;

		try {
			if(Objects.isNull(driver)) {
				System.out.println("Driver is found null at takeScreenShot method in TestListeners class");
				System.exit(0);
			}
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	public String getFileName(String methodname) {

		Date d = new Date();
		return methodname + d.toString().replace(":", "_").replace(" ", "_") + ".png";
		//return filename;

	}

}
