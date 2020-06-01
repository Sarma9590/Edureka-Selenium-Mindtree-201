package com.mercury.org.ui.register.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.mercury.org.ui.common.Page;
import com.mercury.org.ui.register.resource.RegisterPageElements;

/**
 * 
 *
 */
public class Register extends Page{

	WebDriver driver;
	JavascriptExecutor js;

	/**
	 * Constructor to initialize POM
	 * 
	 * @param driver
	 */
	public Register(WebDriver driver) {
		super(driver);
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;
		PageFactory.initElements(this.driver, this);
	}

	/***************************** Home Page Elements *****************************/

	@FindBy(css = RegisterPageElements.LNK_FLIGHTS_CSS)
	@CacheLookup
	private WebElement flightsLink;
	
	@FindBy(css = RegisterPageElements.TXT_BOX_USER_NAME_CSS)
	private WebElement userNameTxtBox;

	@FindBy(css = RegisterPageElements.TXT_BOX_PASSWORD_CSS)
	private WebElement passwordTxtBox;

	@FindBy(css = RegisterPageElements.BTN_SUBMIT_CSS)
	private WebElement submitBtn;

	@FindBy(css = RegisterPageElements.LNK_HOME_CSS)
	private WebElement homeLink;

	@FindBy(css = RegisterPageElements.TXT_MERCURY_HEADER_CSS)
	private WebElement mercuryHeader;

	/**********************************************************/
	
	/**
	 * clikc submit button
	 */
	private void clickSubmit() {
		submitBtn.click();
	}

	public void clickFlightLnk(){
		flightsLink.click();
	}
	/**
	 * Sets user name
	 * 
	 * @param userName
	 */
	private void setUserName(String userName) {
		userNameTxtBox.click();
		userNameTxtBox.sendKeys(userName);
	}

	/**
	 * Sets Password
	 * 
	 * @param password
	 */
	private void setPassword(String password) {
		passwordTxtBox.click();
		passwordTxtBox.sendKeys(password);
	}

	/**
	 * Login 
	 * @param userName
	 * @param password
	 */
	public void logIn(String userName, String password) {
		setUserName(userName);
		setPassword(password);
		clickSubmit();
		blindWait(2);
	}

	/**
	 * Navigate to Home
	 * 
	 */
	public void navigateToHome() {
		homeLink.click();
		blindWait(2);
	}

	/**
	 * Verify Invalid Credentials
	 * @param validUserName
	 */
	public void verifyInvalidCredentials(String validUserName) {
		logIn(validUserName, System.currentTimeMillis() + "");
		String text = mercuryHeader.getText();
		Assert.assertEquals(text, RegisterPageElements.HEADER_TXT);
	}

}
