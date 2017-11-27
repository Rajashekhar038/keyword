package generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import exception.ColorMisMatchException;
import exception.ElementNotPresentException;
import exception.ElementNotSelectedException;
import exception.FontMismatchException;
import exception.ListBoxNotSortedException;
import exception.OptionNotFoundException;
import exception.TextNotMatchingException;
import exception.TitleNotMatchingException;
import exception.UrlNotMatchingException;

public class WebGeneric {

	public static void enter(WebDriver driver,String keyword2,String keyword3){
		driver.findElement(By.xpath(keyword2)).sendKeys(keyword3);
	}
	
	public static void click(WebDriver driver,String keyword2){
		driver.findElement(By.xpath(keyword2)).click();
	}
	public static void verifyElementPresent(Logger log,WebDriver driver,String keyword2)
	{
		try{
			boolean displayed = driver.findElement(By.xpath(keyword2)).isDisplayed();
			if(displayed)
			{
				log.info("PASS:Element is Present:"+keyword2);
			}
			else
			{
				log.error("FAIL:Element is Not Present");
				throw new ElementNotPresentException("Element is Not Present:"+keyword2);
			}
			
			}
			catch(NoSuchElementException e){
				log.error("FAIL:Element is Not Present");
				throw new ElementNotPresentException("Element is Not Present(in src):"+keyword2);
	
			}
	}
	public static void verifyElementPresent(Logger log,WebDriver driver,String keyword2,String keyword3)
	{
		long timeOut= Long.parseLong(keyword3);
		WebDriverWait wait=new WebDriverWait(driver,timeOut);
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(keyword2)));
			log.info("PASS:Element is Present:"+keyword2);
		}
		catch(TimeoutException e)
		{
			log.error("FAIL:Element is Not Present even after:"+timeOut+" Sec");
			throw new ElementNotPresentException("Element is Not Present"+keyword2);
		
		}
		
	}
	
	public static void verifyTitle(Logger log, WebDriver driver, String keyword2,String keyword3 )
	{
	 Long timeout = Long.parseLong(keyword3);
	 log.info("Expected Title:"+keyword2);
	 WebDriverWait wait = new WebDriverWait(driver,timeout);	 
	 try
	 {
	 wait.until(ExpectedConditions.titleIs(keyword2));
	 log.info("PASS:Title is Matching");
	 }
	 
	 catch(TimeoutException e )
	 {
		 String aTitle = driver.getTitle();
		 log.info("Actual Title:"+aTitle);
		 log.error("FATAL:Title is Not Matching even after "+timeout+"sec");
		 throw new TitleNotMatchingException ("Title Not Matching");
	 }
	 
	 
	}
	
	public static void verifyURLContains(Logger log, WebDriver driver, String keyword2,String keyword3 )
	{
	 Long timeout = Long.parseLong(keyword3);
	 log.info("Expected URL fration:"+keyword2);
	 WebDriverWait wait = new WebDriverWait(driver,timeout);	 
	 try
	 {
	 wait.until(ExpectedConditions.urlContains(keyword2));
	 log.info("PASS:Actual URL contains Expected URL fraction");
	 }
	 
	 catch(TimeoutException e )
	 {	 String aURL = driver.getCurrentUrl();
		 log.info("Actual URL:"+aURL);
		 log.error("FATAL:URL does NOT contain Expected URL fraction");
		 throw new UrlNotMatchingException("URL Not Matching");
	 } 
	}
	
	public static void verifyEnabled(Logger log, WebDriver driver, String keyword2 )
	{
	 log.info("Expected URL fration:"+keyword2);
	 boolean enabled = driver.findElement(By.xpath(keyword2)).isEnabled();
	 
	 if(enabled)
	 {
		 log.info("Pass: Element is Enabled:"+keyword2);
	 }
	 
	 else 
	 {
		log.error("FAIL: Element is Disabled:"+keyword2);
		throw new InvalidElementStateException(); //built in exception
		
	 }
	 
	}
	
	
	public static void verifyElementIsSelected(Logger log, WebDriver driver, String keyword2 )
	{
	 
	 boolean selected = driver.findElement(By.xpath(keyword2)).isSelected();
	 
	 if(selected)
	 {
		 log.info("Pass: Element is Selected:"+keyword2);
	 }
	 
	 else 
	 {
		log.error("FAIL: Element is is Not Selected:"+keyword2);
		throw new ElementNotSelectedException(" Element is Not Selected"); //built in exception
		
	 }
	 
	}

	//-----------------------------------------------
	public static void veifyText(Logger log, WebDriver driver, String keyword2, String keyword3 )
	{
	 log.info("Expected Text:"+keyword3);
	 String aText=driver.findElement(By.xpath(keyword2)).getText();
	 
	 log.info("Actual Text: "+aText);	 
	 if(aText.equals(keyword3))
	 {
		 log.info("PASS:Text is Matching");
	 }
	 
	 else
	 {   log.error("FAIL:Text is Not Matchine");
		 throw new TextNotMatchingException("Text is not matching");		 
	 }	 
	}	
	
	public static void verifyColor(Logger log, WebDriver driver, String keyword2, String keyword3)
	{
	
		WebElement element = driver.findElement(By.xpath(keyword2));
		String aColor = element.getCssValue("color");
		String hColor = Color.fromString(aColor).asHex();
		log.info("Actual Color Code:"+hColor);
		log.info("Expected Color Code:"+keyword3);
		
		if(hColor.equals(keyword3))
		{
			log.info("Pass: Color is Matching for :"+keyword2);
		}
		
		else
		{
			log.error("Fail: Color is Not Matching for:"+keyword2);
			throw new ColorMisMatchException("Color Not Matching");
			
		}
	}
	
	public static void verifyFontType
	(Logger log, WebDriver driver, String keyword2, String keyword3)
	{
		WebElement element = driver.findElement(By.xpath(keyword2));
		String aFont = element.getCssValue("font-family");
		log.info("Actual Font:"+aFont);
		log.info("Expected Font:"+keyword3);
		
		if(aFont.equals(keyword3))
		{
			log.info("Pass: Font is Matching for:"+keyword2);
		}
		
		else
		{
			log.error("Fail: Font is not Matching for :"+keyword2);
			throw new FontMismatchException("Font Mismatch");
						
		}
			
	}
	
	
	public static void verifyFontSize( Logger log, WebDriver driver, String keyword2, String keyword3)
	{
		WebElement element = driver.findElement(By.xpath(keyword2));
		String aFont = element.getCssValue("font-size");
		
		log.info("Actual FontSize:"+aFont);	
		log.info("Expted Font Size:"+keyword3);
		
		if(aFont.equals(keyword3))
		{
			log.info("Pass:FontSize is Matching for:"+keyword2); 
		}
			
			else
			{
				
			}
			
	}
	
	public static void mouseHover (Logger log, WebDriver driver, String keyword2)
	{
		Actions actions = new Actions(driver);
		
		WebElement e = driver.findElement(By.xpath(keyword2));
		actions.moveToElement(e).perform();
		log.info("Hovering on Element:"+keyword2);		
		
	}
	
	public static void dragDrop( Logger log, WebDriver driver, String keyword2, String keyword3)
	{
		Actions actions = new Actions(driver);
		WebElement from = driver.findElement(By.xpath(keyword2));
		WebElement to = driver.findElement(By.xpath(keyword3));
		actions.dragAndDrop(from, to).perform();
		log.info("Drag eleement:"+keyword2);
		log.info("Drop it on to element:"+keyword3);
	}
	
	public static void doubleClick(Logger log, WebDriver driver, String keyword2)
	{
		Actions actions = new Actions(driver);
		WebElement e = driver.findElement(By.xpath(keyword2));
		actions.doubleClick(e).perform();
	}
	
	public static void rightClick(Logger log, WebDriver driver, String keyword2)
	{
		Actions actions = new Actions(driver);
		WebElement e = driver.findElement(By.xpath(keyword2));
		actions.contextClick(e).perform();
	}
	
	public static void selectOptionByIndex(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		int index = Integer.parseInt(keyword3);
		select.selectByIndex(index);
		log.info("Selecting option in ListBox using Index:"+keyword3);
	}
	
	public static void selectOptionByValue(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		select.selectByValue(keyword3);
		log.info("Selecting option in ListBox using value:"+keyword3);
	}
	
	public static void selectOptionByText(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		select.selectByVisibleText(keyword3);
		log.info("Selecting option in ListBox using Text:"+keyword3);
	}
	
	public static void deselectOptionByIndex(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
	    int index = Integer.parseInt(keyword3);
		select.deselectByIndex(index);
		
		log.info("DeSelecting option in ListBox using index:"+keyword3);
	}
	
	public static void deselectOptionByValue(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
	    select.deselectByValue(keyword3);
		
		log.info("DeSelecting option in ListBox using value:"+keyword3);
	}
	
	public static void deselectOptionByText(Logger log, WebDriver driver, String keyword2,String keyword3)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		select.deselectByVisibleText(keyword3);
		log.info("DeSelecting option in ListBox using Text:"+keyword3);
	}
	
	public static void deselectAllOptions(Logger log, WebDriver driver, String keyword2)
	{
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
	    select.deselectAll();		
		log.info("DeSelecting all the options in ListBox:");
	}
	
	public static void switchToFrameByIndex(Logger log, WebDriver driver, String keyword2)
	{
		int index = Integer.parseInt(keyword2);
		driver.switchTo().frame(index);
		log.info("Switching in to Frame by index:"+keyword2);		
	}
	
	public static void switchToFrameByIdorName(Logger log, WebDriver driver, String keyword2)
	{		
		driver.switchTo().frame(keyword2);
		log.info("Switching in to Frame by ID or Name:"+keyword2);		
	}
	
	public static void switchToFrameByAddress(Logger log, WebDriver driver, String keyword2)
	{	
		WebElement e = driver.findElement(By.xpath(keyword2));
		driver.switchTo().frame(e);
		log.info("Switching in to Frame by address:"+keyword2);		
	}
	
	public static void exitFromFrameToPage(Logger log, WebDriver driver)
	{	
		driver.switchTo().defaultContent();
		log.info("Exiting from Frame to Page");		
	}
	
	public static void exitFromFrameToParent(Logger log, WebDriver driver)
	{	
		driver.switchTo().parentFrame();
		log.info("Exiting from Frame to Parent");		
	}	
	
	public static void searchListBox(Logger log, WebDriver driver,String keyword2, String keyword3 )
	{
		boolean found = false;
		log.info("Searching "+keyword3+"in ListBox "+keyword2);
		WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		List<WebElement> allOptions = select.getOptions();
		
		for( WebElement option : allOptions)
		{
			String text =option.getText();
			log.info("Comparing with: "+text);
			
			if(text.equalsIgnoreCase(keyword3))
			{
				found = true; break;
			}
							
		}
		
		if(found)
		{
			log.info("PASS: Optioon found in ListBox");
		}
		
		else
		{
			log.error("FAIL: Optioon Not found in ListBox");
			throw new OptionNotFoundException();
		}
			
	}
	
	public static void isListBoxSorted(Logger log, WebDriver driver,String keyword2 )
	{ 	WebElement e = driver.findElement(By.xpath(keyword2));
		Select select = new Select(e);
		List<WebElement> allOptions = select.getOptions();
		ArrayList<String> allText = new ArrayList<String>();
		
		for(WebElement option : allOptions)
		{
			allText.add(option.getText());
		}
		
		ArrayList<String> copy = new ArrayList<String>(allText);
		Collections.sort(copy);
		
		if(allText.equals(copy))
		{
			log.info("PASS : ListBox is in Sorted Order");
		}
		
		else
		{
			log.error("FAIL: ListBox is NOT in sorted oreder");
			throw new ListBoxNotSortedException();
		}
	}
	
}