package com.upgrade.pages;

import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.upgrade.pojos.Borrower;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoginInfoPage extends BasePage {

	@FindBy(name = "username")
	private WebElement email;

	@FindBy(name = "password")
	private WebElement password;

	@FindBy(linkText = "Terms of Use")
	private WebElement termsOfUse;

	@FindBy(linkText = "ESIGN Act Consent")
	private WebElement eSIGNActConsent;

	@FindBy(linkText = "Credit Profile Authorization")
	private WebElement creditProfileAuthorization;

	@FindBy(linkText = "Privacy Policy")
	private WebElement privacyPolicy;

	@FindBy(css = "[data-auto='submitPersonalInfo']")
	private WebElement checkYourRateBtn;

	@FindBy(name = "agreements")
	private WebElement agreements;

	@FindBy(css = "[data-auto='adverse-learn-more-link']")
	private WebElement pageText;

	@FindBy(xpath = "//*[text()[normalize-space()='Adverse Action Notice.pdf']]")
	private WebElement DocumentText;

	@FindBy(xpath = "//tbody/tr[5]/td[3]/a[1]")
	private WebElement DocumentTextLink;

	private String checkValue;

	public void documentPage() {
		String doc = DocumentText.getText();
		log.info(doc);
	}

	public void documentPagelink() {
		boolean check = DocumentTextLink.isDisplayed();
		if (check = true) {
			log.info("Adverse Action Notice download link exists");
		} else {
			log.info("Adverse Action Notice download link does not exist");
		}
	}

	@FindBy(css = "[data-auto='userLoanAmount']")
	private WebElement approvedLoanAmount;

	@FindBy(xpath = "//*[text()[normalize-space()='Great news, here are your offers!']]")
	private WebElement offerPageTitle;

	@FindBy(css = "[data-auto='offer-card-content-submit']")
	private WebElement defaultMonthlyPayment;

	HashMap<String, String> prev = new HashMap<>();

	@FindBy(css = "[data-auto='defaultAPR']")
	private WebElement defaultAPR;

	public void loginofferDetails() {
		String titlePage = offerPageTitle.getText();
		String approvedAmount = approvedLoanAmount.getText();

		String OfferContent = defaultMonthlyPayment.getText();
		prev.put("defaultMonthlyPayment", OfferContent);

		log.info(titlePage);
		log.info(titlePage);
		log.info(approvedAmount);
		log.info(OfferContent);
	}

	public LoginInfoPage(WebDriver driver) {
		super(driver);
		waitForWebElements(Arrays.asList(email));
	}

	// Note : Use java generics to return a different page
	public SelectOfferPage enterLoginDetails(Borrower randomPerson) {
		commonSteps(randomPerson);
		return new SelectOfferPage(driver, true);
	}

	public SelectOfferPage enterDeclineLogin(Borrower randomPerson) {
		commonSteps(randomPerson);
		click(pageText);
		documentPage();
		documentPagelink();
		click(DocumentTextLink);
		return new SelectOfferPage(driver, false);
	}

	public void commonSteps(Borrower randomPerson) {
		type(email, randomPerson.getEmail());
		type(password, randomPerson.getPassword());
		selectTermsOfUse();
		waitForPage();
		click(checkYourRateBtn);
	}

	public LoginInfoPage selectTermsOfUse() {
		javaScriptClick(agreements);
		return this;
	}
}
