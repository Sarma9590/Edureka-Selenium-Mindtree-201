package com.mercury.org.testexecute.base;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import com.mercury.org.framework.constants.FrameWorkConstants;
import com.mercury.org.framework.driver.Driver;
import com.mercury.org.framework.utility.Property;
import com.mercury.org.ui.booking.pageobject.FlightBooking;
import com.mercury.org.ui.register.pageobject.Register;

public class BaseClass {

	public BaseClass() {
		
	}
	WebDriver driver;
	protected static FlightBooking flightBooking;
	protected static Register register;
	protected static String userName;
	protected static String password;

	/**
	 * Driver Initialization.
	 */
	@BeforeSuite
	public void initializatingDriver() {
		this.driver = Driver.getDriverInstance();
		flightBooking = new FlightBooking(driver);
		register = new Register(driver);
	}

	/**
	 * Login Initialization.
	 */
	@BeforeClass()
	public void loginInitialization() {
		Properties prop = Property.getPropertyInstance(FrameWorkConstants.PROPERTY_FILE_PATH);
		userName = Property.getPropertyValue(prop, FrameWorkConstants.USERNAME);
		password = Property.getPropertyValue(prop, FrameWorkConstants.PASSWORD);
	}

	/**
	 * Back To Home
	 */
	@AfterClass
	public void backToHomePage() {
		register.navigateToHome();
	}

	/**
	 * Shutting Driver Instance
	 */
	@AfterSuite()
	public void shutDownDriver() {
		Driver.closeDriver();
	}
}
