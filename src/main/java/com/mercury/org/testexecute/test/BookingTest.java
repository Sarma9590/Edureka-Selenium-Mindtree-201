package com.mercury.org.testexecute.test;

import java.util.Hashtable;
import org.testng.annotations.Test;

import com.mercury.org.framework.dataprovider.ExcelDataProvider;
import com.mercury.org.testexecute.base.BaseClass;


@Test(dataProvider = ExcelDataProvider.DATA_PROVIDER, dataProviderClass = ExcelDataProvider.class)
public class BookingTest extends BaseClass {
	
	public BookingTest() {
		
	}
	/**
	 * Books Flight Ticket Based on the data present in Excel.
	 * @param data
	 */
	public void bookingTest(Hashtable<String, String> data) {
		register.logIn(userName, password);
		flightBooking.bookFlightTicket(data);
		flightBooking.logOut();
	}

}
