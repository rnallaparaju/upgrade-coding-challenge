package com.upgrade.pages;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.upgrade.pojos.Borrower;

import lombok.extern.log4j.Log4j;

@Log4j
public class SignInPage extends BasePage {

	@FindBy(css = "[data-auto='username']")
	private WebElement username;

	@FindBy(css = "[data-auto='password']")
	private WebElement password;

	@FindBy(css = "[data-auto='login']")
	private WebElement submitBtn;

	@FindBy(css = "[data-auto='userLoanAmount']")
	private WebElement approvedLoanAmount;

	@FindBy(xpath = "//*[text()[normalize-space()='Great news, here are your offers!']]")
	private WebElement offerPageTitle;

	@FindBy(css = "[data-auto='offer-card-content-submit']")
	private WebElement offerDetails;

	public SignInPage(WebDriver driver) {
		super(driver);
	}

	public void offerconfirmation() {
		String titlePage = offerPageTitle.getText();
		String approvedAmount = approvedLoanAmount.getText();
		String Details = offerDetails.getText();
		// System.out.println("Title Page " + titlePage);
		// System.out.println("Approved Amount " + approvedAmount);
		// System.out.println("offerDetails " + Details);
	}

	public SelectOfferPage signIn(Borrower borrower) {
		type(username, borrower.getEmail());
		type(password, borrower.getPassword());
		click(submitBtn);
		offerconfirmation();
		waitForPage();
		return new SelectOfferPage(driver, true);
	}

	public SignInPage gotoSignInPage(String url) {
		String server = String.format("%s/portal/login", url);
		log.info("Navigate to - " + server);
		driver.get(server);
		waitForWebElements(Arrays.asList(username));
		return this;
	}
}