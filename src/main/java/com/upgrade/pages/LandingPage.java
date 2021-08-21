package com.upgrade.pages;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.upgrade.pojos.Borrower;

import lombok.extern.log4j.Log4j;

@Log4j
public class LandingPage extends BasePage {

	@FindBy(name = "desiredAmount")
	private WebElement loanAmount;

	@FindBy(css = "[data-auto='CheckYourRate']")
	private WebElement checkYourRateBtn;

	@FindBy(css = "[data-auto='dropLoanPurpose']")
	private WebElement dropLoanPurposeBtn;

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	public ContactInfoPage enterLoanDetails(Borrower borrower) {
		type(loanAmount, borrower.getDesiredLoanAmount().toString());
		selectDropDownByTextIgnoringCase(dropLoanPurposeBtn, borrower.getLoanPurpose());
		click(checkYourRateBtn);
		return new ContactInfoPage(driver);
	}

	public LandingPage gotoLandingPage(String url) {
		String server = String.format("%s/funnel/nonDMFunnel", url);
		log.info("Navigate to - " + server);
		driver.navigate().to(server);
		waitForWebElements(Arrays.asList(loanAmount));
		return this;
	}

}
