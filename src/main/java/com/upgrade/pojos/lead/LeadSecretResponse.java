package com.upgrade.pojos.lead;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadSecretResponse {
	private LoanAppResumptionInfo loanAppResumptionInfo;
	private List<Object> offers;
	private Object selectedOffer;
	private List<Object> requiredAgreements;
	private List<String> resetOptions;
	private Object originalLoanApp;

}
