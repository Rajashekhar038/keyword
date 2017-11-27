package executor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import generic.Excel;
import generic.Property;
import generic.Screenshot;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Driver implements IAutoConst
{
	public static Logger log=Logger.getLogger("Driver");
	public static int pCount=0,fCount=0,tCount=0;
	public static WebDriver driver;
	static
	{
		log.info("Set the path of Driver executable");
		System.setProperty(CHROME_KEY,CHROME_VALUE);
	}
	
	public static void executeScript(String scriptName)
	{
		log.info("Open Chrome Browser");
		driver=new ChromeDriver();
		String sITO=Property.getValue(SETTINGS_PATH,"ITO");
		long ITO = Long.parseLong(sITO);
		log.info("Set ITO:"+ITO+" Sec");
		driver.manage().timeouts().implicitlyWait(ITO,TimeUnit.SECONDS);
		String url=Property.getValue(SETTINGS_PATH,"URL");
		log.info("Enter the URL:"+url);
		driver.get(url);
		log.info("Executing Script:"+scriptName);
		Steps.executeSteps(log,driver,SCRIPT_PATH,scriptName);
		log.info("End of Script:"+scriptName);
		
	}
	
	public static void executeSuite(){
		ExtentReports extent = new ExtentReports();
		ExtentHtmlReporter htmlReport  = new ExtentHtmlReporter(EXTENT_REPORT_PATH);		
		extent.attachReporter(htmlReport);		
		int scriptCount=Excel.getRowCount(SUITE_PATH,SUITE_SHEET);
		log.info("Script count:"+scriptCount);
		for(int j=1;j<=scriptCount;j++){
			String scriptName=Excel.getCellValue(SUITE_PATH,SUITE_SHEET,j,0);
			ExtentTest test = extent.createTest(scriptName);
			String scriptStatus=Excel.getCellValue(SUITE_PATH,SUITE_SHEET,j,1);
			log.info("Script:"+scriptName+" Execute:"+scriptStatus);
			if(scriptStatus.equalsIgnoreCase("yes")){
				try{
					executeScript(scriptName);
					log.info("Script is PASSED:"+scriptName);
					pCount++;
					test.pass("Script is PASSED");
				}
				catch(Exception e){
					log.error("Script is FAILED:"+scriptName);
					fCount++;
					e.printStackTrace();
					String imgName=Screenshot.generateImageName();
					String imgPath=FAIL_PHOTO_PATH+"/"+scriptName+"_"+imgName;
					log.info("Taking ScreenShot:"+imgPath);
					Screenshot.get(driver,imgPath);
					test.fail("Script is FAILED");
					
					String aImagePath = new File(imgPath).getAbsolutePath();
					try
					{
						test.addScreenCaptureFromPath(aImagePath);
					}
					
					catch(IOException e1)
					{
						
					}
					
				}
				finally{
					driver.quit();
					log.info("Quit the Browser");
				}
			}
			else{
				log.info("NOT Executing Script:"+scriptName);
				test.skip("NOT Executing Script:");
		    }
		}	
		log.info("----------------------------------------------");
		log.info("Total PASS:"+pCount);
		log.info("Total FAIL:"+fCount);
		tCount=pCount+fCount;
		log.info("Total Scripts Executed:"+tCount);
		log.info("----------------------------------------------");
		extent.flush();
	}
	public static void main(String[] args) {
		log.info("Suite Execution Started");
		executeSuite();		
		log.info("Suite Execution Completed");
    }
}





