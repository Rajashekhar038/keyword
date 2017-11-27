package generic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import executor.IAutoConst;

public class DeleteThis 
{
	public static void main(String[] args) throws Exception 
	
	{
	  //Create Extent Report
		ExtentReports e = new ExtentReports();		
		ExtentHtmlReporter h = new ExtentHtmlReporter("./res/Report.html");		
		e.attachReporter(h);
				
		ExtentTest test = e.createTest("ValidLogin");
		test.pass("This Test is Pass");
		
		ExtentTest test2 = e.createTest("InValidLogin");
		test2.info(" Here also we have different methods like we have in log4j");
		test2.warning("Warning message... i like log4j");
		test2.fail("This test is failed..");
		
		File f = new File("./photo/ForFailure/frame_23_Nov_17 08_15_20.png");
		String p = f.getAbsolutePath();
		test2.addScreenCaptureFromPath(p);
		e.flush();		
		
	}	

}









