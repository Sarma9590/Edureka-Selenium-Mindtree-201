package com.mercury.org.framework.constants;

public class FrameWorkConstants {

	public FrameWorkConstants() {
		
	}
	public static final String CHROME_DRIVER = "webdriver.chrome.driver";
	public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\Drivers\\chromedriver.exe";

	public static final String IE_DRIVER = "webdriver.ie.driver";
	public static final String IE_DRIVER_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\Drivers\\IEDriverServer.exe";

	public static final String GECKO_DRIVER = "webdriver.gecko.driver";
	public static final String GECKO_DRIVER_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\Drivers\\Geckodriver.exe";

	public static final String PROPERTY_FILE_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\config.properties";

	public static final String PDF_FILE_PATH = System.getProperty("user.dir") + "\\PdfReport\\MercuryTours.pdf";
	public static final String PDF_SCREEN_SHOT_FOLDER = System.getProperty("user.dir") + "\\Screenshots\\";
	public static final String PDF_FILE_NAME = "MercuryTours.pdf";

	public static final String EXCEL_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\Excel\\MercuryTours.xlsx";

	/************** property keys *********************/

	/**
	 * Browser
	 */
	public static final String BROWSER = "browser";
	/*--------Mercury Tours Credentials---------*/
	public static final String USERNAME = "userName";
	public static final String PASSWORD = "password";

	/*--------Gmail---------*/
	public static final String GMAIL_USERNAME = "gmailUser";
	public static final String GMAIL_PASSWORD = "gmailPassword";
	public static final String GMAIL_RECEPIENTS = "recepients";

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}
}
