package com.mercury.org.testexecute.test;

import org.testng.annotations.Test;

import com.mercury.org.testexecute.base.BaseClass;

@Test
public class RegisterTest extends BaseClass {

	public RegisterTest() {
		
	}
	
	/**
	 * Verifies User Credentials.
	 */
	public void verifyValidCredentials() {
		register.verifyInvalidCredentials(userName);
	}
	
}
