package com.upgrade.pages;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.extern.log4j.Log4j;

@Log4j
public class documentsPage extends FunnelBasePage {

	@FindBy(css = "[data-auto='getDefaultLoan']")
	private WebElement continueBtn;

	@FindBy(xpath = "//*[text()[normalize-space()='Adverse Action Notice.pdf']]")
	private WebElement DocumentText;

	@FindBy(xpath = "//tbody/tr[5]/td[3]/a[1]")
	private WebElement DocumentTextLink;

	public void documentPage() {
		DocumentText.getText();
		// click(pageText);
		// documentPage();
		// documentPagelink();
		click(DocumentTextLink);
	}

	public documentsPage(WebDriver driver) {
		super(driver);
		waitForWebElements(Arrays.asList(continueBtn));
	}

}
