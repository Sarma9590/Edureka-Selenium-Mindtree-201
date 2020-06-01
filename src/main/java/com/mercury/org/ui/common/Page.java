package com.mercury.org.ui.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

	WebDriver driver;
	
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Blind Wait
	 * @param timeInSec
	 */
	public void blindWait(int timeInSeconds){
			try {
				Thread.sleep(timeInSeconds*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * @param locatorElement
	 * @param timeOutInSec
	 * @return
	 */
	public boolean waitUntilWebElementHidden(WebElement locatorElement, int timeOutInSec){
		boolean isHidden = false;
		isHidden = (new WebDriverWait(this.driver, timeOutInSec))
					.until(ExpectedConditions.invisibilityOf(locatorElement));
		return isHidden;
	}
	
	/**
	 * Fluent Wait
	 * @param locatorElement
	 * @param timeOutInSec
	 * @param pollingSec
	 */
	public void waitForTheElementToBeVisible(WebElement locatorElement, int timeOutInSec, int pollingSec){
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver).withTimeout(timeOutInSec, TimeUnit.SECONDS)
				.pollingEvery(pollingSec, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class, TimeoutException.class)
				.ignoring(StaleElementReferenceException.class);
		try {
			fluentWait.until(ExpectedConditions.visibilityOf(locatorElement));
		} catch (Exception e) {
			System.out.println("Page Taking longer time to load....");
			System.out.println("Element Not found trying again - " + locatorElement.toString().substring(70));
			e.printStackTrace();
		}
	}
}
