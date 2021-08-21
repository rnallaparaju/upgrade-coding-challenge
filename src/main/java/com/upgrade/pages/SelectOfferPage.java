package com.upgrade.pages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.extern.log4j.Log4j;

@Log4j
public class SelectOfferPage extends FunnelBasePage {

	@FindBy(css = "[data-auto='getDefaultLoan']")
	private WebElement continueBtn;

	@FindBy(css = "[data-auto='userLoanAmount']")
	private WebElement approvedLoanAmount;

	@FindBy(xpath = "//h2[contains(text(),\"We can't find you a loan offer yet, but you still \")]")
	private WebElement offerTitle;

	@FindBy(css = "[data-auto='adverse-learn-more-link']")
	private WebElement clickHere;

	@FindBy(xpath = "//*[text()[normalize-space()='Great news, here are your offers!']]")
	private WebElement offerPageTitle;

	@FindBy(css = "[data-auto='offer-card-content-submit']")
	private WebElement defaultMonthlyPayment;

	private Map<String, String> values = new HashMap<>();

	public void offerDetails() {
		String titlePage = offerPageTitle.getText();
		String approvedAmount = approvedLoanAmount.getText();
		String OfferContent = defaultMonthlyPayment.getText();
		setMapValues();
		log.info(titlePage);
		log.info(approvedAmount);
		log.info(approvedAmount);
	}

	public SelectOfferPage(WebDriver driver, boolean flag) {
		super(driver);

		if (flag) {
			waitForWebElements(Arrays.asList(continueBtn));
			setMapValues();
		}
	}

	public void setMapValues() {
		values.put("OfferContent", defaultMonthlyPayment.getText());
	}

	public Map<String, String> getValues() {
		return values;
	}

}
