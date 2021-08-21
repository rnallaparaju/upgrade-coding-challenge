package com.upgrade.tests;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.upgrade.pages.LandingPage;
import com.upgrade.pages.SelectOfferPage;
import com.upgrade.pages.SignInPage;
import com.upgrade.pojos.Borrower;

import lombok.extern.log4j.Log4j;

@Log4j
public class LoanOffersUITest extends AbstractTest {
	private static final String url = "https://www.credify.tech";

	/*
	 * Please refer README.md for more details on Case # 1 : Validate offers after
	 * re-login
	 */
	@Test
	public void validateOffersTest() {
		Borrower borrower = getRandomTestBorrower();
		LandingPage landingPage = new LandingPage(getDriver());
		// Capture offer details in the Offers page
		setBorrowervalue(150000, 200000, "YearlyIncome", borrower);
		setBorrowervalue(1000, 10000, "AdditionalIncome", borrower);
		setBorrowervalue(5000, 10000, "DesiredLoanAmount", borrower);
		SelectOfferPage page = landingPage.gotoLandingPage(url).enterLoanDetails(borrower).enterContactDetails(borrower)
				.enterIncomeDetails(borrower).enterLoginDetails(borrower);
		page.offerDetails();
		page.setMapValues();
		Map<String, String> beforeLogout = page.getValues();
		page.clickSignOut();

		// Validate the offer details after login
		SignInPage signInPage = new SignInPage(getDriver());
		SelectOfferPage afterLogin = signInPage.gotoSignInPage(url).signIn(borrower);// .clickSignOut();
		Map<String, String> map2 = afterLogin.getValues();
		afterLogin.clickSignOut();

		if (beforeLogout.get("OfferContent").equals(map2.get("OfferContent"))) {
			log.info("Success");
		} else {

			log.info("failure");
		}
	}

	/*
	 * Please refer README.md for more details on Case # 2 : Loan rejected for low
	 * annual income
	 */

	public void validateDeclineLoanTest() {
		Borrower borrower = getRandomTestBorrower();
		LandingPage landingPage = new LandingPage(getDriver());
		// Capture offer details in the Offers page
		setBorrowervalue(100, 1000, "YearlyIncome", borrower);
		setBorrowervalue(100, 500, "AdditionalIncome", borrower);
		setBorrowervalue(5000, 10000, "DesiredLoanAmount", borrower);
		SelectOfferPage decline = landingPage.gotoLandingPage(url).enterLoanDetails(borrower)
				.enterContactDetails(borrower).enterIncomeDetails(borrower).enterDeclineLogin(borrower);
		decline.clickSignOut();

	}

	private Borrower getRandomTestBorrower() {
		Borrower borrower = new Borrower();
		Faker faker = new Faker(new Locale("en-US"));

		borrower.setFirstName(faker.name().firstName());
		borrower.setLastName(faker.name().lastName());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		borrower.setDob(simpleDateFormat.format(faker.date().birthday()));
		borrower.setCity(faker.address().city());
		borrower.setEmail(
				String.format("coding.%s@upgrade-challenge.com", generateRandomNumberFromRange(15000000, 20000000)));
		borrower.setPassword("System@987");
		borrower.setZipCode(faker.address().zipCode());
		borrower.setStreet(faker.address().streetAddress());
		borrower.setState("CA");
		borrower.setLoanPurpose("Home Improvement");
		return borrower;
	}

	public void setBorrowervalue(int r1, int r2, String name, Borrower borrower) {
		switch (name) {
		case "YearlyIncome":
			borrower.setYearlyIncome(generateRandomNumberFromRange(r1, r2));
			break;
		case "AdditionalIncome":
			borrower.setAdditionalIncome(generateRandomNumberFromRange(r1, r2));
			break;
		case "DesiredLoanAmount":
			borrower.setDesiredLoanAmount(generateRandomNumberFromRange(r1, r2));
			break;

		}

	}

	private BigDecimal generateRandomNumberFromRange(int min, int max) {
		return BigDecimal.valueOf(Math.random() * (max - min + 1) + min).setScale(0, RoundingMode.DOWN);
	}

}
