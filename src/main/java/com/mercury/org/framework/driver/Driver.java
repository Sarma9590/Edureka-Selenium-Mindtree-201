package com.mercury.org.framework.driver;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import com.mercury.org.framework.constants.FrameWorkConstants;
import com.mercury.org.framework.utility.Property;
import org.openqa.selenium.TakesScreenshot;

public class Driver {

	public static WebDriver driver = null;
	public static String homeUrl = "http://newtours.demoaut.com/mercurywelcome.php";
	
	public Driver(String browser) {
			if (browser.equalsIgnoreCase("chrome"))
				driver = initialiseChrome();
			else if (browser.equalsIgnoreCase("ie"))
				driver = initialiseIE();
			else
				driver = initialistFireFox();
			initialise();
	}

	/**
	 *  Instantiates Driver based on the configure.properties files
	 * @return
	 */
	public static WebDriver getDriverInstance() {
		if(driver == null){
		Properties prop = Property.getPropertyInstance(FrameWorkConstants.PROPERTY_FILE_PATH);
		String browser = Property.getPropertyValue(prop, FrameWorkConstants.BROWSER);
		if (browser.equalsIgnoreCase("chrome"))
			driver = initialiseChrome();
		else if (browser.equalsIgnoreCase("ie"))
			driver = initialiseIE();
		else
			driver = initialistFireFox();
		initialise();
		}
		return driver;
	}

	
	/**
	 *  Initialize driver
	 */
	private static void initialise(){
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(homeUrl);
	}
	
	
	/**
	 *  Initialize Fire Fox
	 * @return
	 */
	private static WebDriver initialistFireFox() {
		System.setProperty(FrameWorkConstants.GECKO_DRIVER, FrameWorkConstants.GECKO_DRIVER_PATH);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}
	
	/** Initialize Chrome
	 * @return
	 */
	private static WebDriver initialiseChrome() {
		System.setProperty(FrameWorkConstants.CHROME_DRIVER, FrameWorkConstants.CHROME_DRIVER_PATH);
		WebDriver driver = new ChromeDriver();
		return driver;
	}

	/** Initialize IE
	 * @return
	 */
	
	private static WebDriver initialiseIE() {
		System.setProperty(FrameWorkConstants.IE_DRIVER, FrameWorkConstants.IE_DRIVER_PATH);
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

	/**
	 * This function will take screenshot
	 * 
	 * @param webdriver
	 * @param fileWithPath
	 * @throws Exception
	 */
	public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);

	}
	
	/**
	 *  Close the driver 
	 */
	public static void closeDriver(){
		driver.quit();
	}
	
}