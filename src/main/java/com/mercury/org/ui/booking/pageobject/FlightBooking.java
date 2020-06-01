package com.mercury.org.ui.booking.pageobject;
import java.util.Hashtable;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.mercury.org.ui.booking.resource.FlightBookingElements;
import com.mercury.org.ui.booking.resource.FlightBookingExcel;
import com.mercury.org.ui.common.Page;
public class FlightBooking extends Page{
	WebDriver driver;
	JavascriptExecutor js;
	/**
	 * Constructor to initialize POM
	 * @param driver
	 */
	public FlightBooking(WebDriver driver) {
		super(driver);
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
		PageFactory.initElements(this.driver, this);
	}
	/***************************** Flight Booking Elements *****************************/
	@FindBy(css = FlightBookingElements.RAD_ROUND_TRIP_CSS)
	@CacheLookup
	private WebElement roundTrip;
	@FindBy(css = FlightBookingElements.RAD_ONE_WAY_CSS)
	@CacheLookup
	private WebElement oneWayTrip;
	@FindBy(css = FlightBookingElements.SEL_ARRIVING_IN_CSS)
	@CacheLookup
	private WebElement arrivingIn;
	@FindBy(css = FlightBookingElements.SEL_RETURING_CSS)
	@CacheLookup
	private WebElement returing;
	@FindBy(css = FlightBookingElements.SEL_AIRLINE_CSS)
	@CacheLookup
	private WebElement airline;
	@FindBy(css = FlightBookingElements.BTN_CONTINUE_CSS)
	@CacheLookup
	private WebElement continueBtn;
	@FindBy(css = FlightBookingElements.RAD_DEPART_CSS)
	@CacheLookup
	private List<WebElement> departRadBtn;
	@FindBy(css = FlightBookingElements.RAD_RETURN_CSS)
	@CacheLookup
	private List<WebElement> returnRadBtn;
	@FindBy(css = FlightBookingElements.BTN_CONTINUE_APPLY_CSS)
	@CacheLookup
	private WebElement continueForDepartBtn;
	@FindBy(css = FlightBookingElements.TXT_BOX_FIRST_NAME_CSS)
	@CacheLookup
	private WebElement firstName;
	@FindBy(css = FlightBookingElements.TXT_BOX_LAST_NAME_CSS)
	@CacheLookup
	private WebElement lastName;
	@FindBy(css = FlightBookingElements.BTN_SECURE_PURCHASE_CSS)
	@CacheLookup
	private WebElement securePurchase;
	@FindBy(css = FlightBookingElements.BTN_BACK_TO_FLIGHTS_CSS)
	@CacheLookup
	private WebElement backToFlight;
	@FindBy(css = FlightBookingElements.BTN_BACK_TO_HOME_CSS)
	@CacheLookup
	private WebElement backToHome;
	@FindBy(css = FlightBookingElements.BTN_LOG_OUT_CSS)
	@CacheLookup
	private WebElement LogOut;
	/**********************************************************/
	/**
	 * Sets Type of flight to book
	 * example
	 * @param type
	 */
	private void setType(String type){
		if(type.equals("one"))
			oneWayTrip.click();
	}
	/**
	 * Selects Arriving In Location
	 * @param textToSelect
	 */
	private void selectArrivingIn(String textToSelect){
		Select sel = new Select(arrivingIn);
		sel.selectByVisibleText(textToSelect.trim());
	}
	/**
	 * Selects Returning 
	 * @param textToSelect
	 */
	private void selectReturning(String textToSelect){
		Select sel = new Select(returing);
		sel.selectByVisibleText(textToSelect.trim());
	}
	/**
	 * Selects Airline
	 * @param textToSelect
	 */
	private void selectAirline(String textToSelect){
		Select sel = new Select(airline);
		sel.selectByVisibleText(textToSelect.trim());
	}
	/**
	 * Sets First Name
	 * @param firstName
	 */
	private void setFirstName(String firstName){
		this.firstName.click();
		this.firstName.sendKeys(firstName);
	}
	/**
	 * Sets Last Name
	 * @param lastName
	 */
	private void setLastName(String lastName){
		this.lastName.click();
		this.lastName.sendKeys(lastName);
	}
	/**
	 * Method to book Flight Tickets
	 * @param data
	 * @throws InterruptedException 
	 */
	public void bookFlightTicket(Hashtable<String, String> data){
		setType(data.get(FlightBookingExcel.TYPE));
		selectArrivingIn(data.get(FlightBookingExcel.ARRIVING_IN));
		selectReturning(data.get(FlightBookingExcel.RETURNING));
		selectAirline(data.get(FlightBookingExcel.AIRLINE));
		continueBtn.click();
		blindWait(4);
		continueForDepartBtn.click();
		blindWait(3);
		setFirstName(data.get(FlightBookingExcel.FIRST_NAME));
		setLastName(data.get(FlightBookingExcel.LAST_NAME));
		securePurchase.click();
		blindWait(2);
	}
	/**
	 * Clicks LogOutButton
	 */
	public void logOut(){
		LogOut.click();
		blindWait(1);
	}
}