package com.upgrade.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.upgrade.pojos.lead.LeadSecretRequest;
import com.upgrade.pojos.lead.LeadSecretResponse;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;

@Log4j
public class LeadSecretApiTest extends AbstractTest {

	private UUID loanAppUuid = UUID.fromString("b8096ec7-2150-405f-84f5-ae99864b3e96");
	private UUID InvalidloanAppUuid = UUID.fromString("a8096ec7-2150-405f-84f5-ae99864b3e96");

	private String url = "https://credapi.credify.tech/api/brfunnelorch/";

	private LeadSecretRequest leadSecretRequest = LeadSecretRequest.builder().loanAppUuid(loanAppUuid)
			.skipSideEffects(true).build();

	private LeadSecretRequest InvalidleadSecretRequest = LeadSecretRequest.builder().loanAppUuid(InvalidloanAppUuid)
			.skipSideEffects(true).build();

	/*
	 * Please refer README.md for more details on APT Test
	 */
	@Test
	public void validleadSecretTest() {
		apiRequest().addHeader("x-cf-corr-id", UUID.randomUUID().toString())
				.addHeader("x-cf-source-id", "coding-challenge").setContentType(ContentType.JSON)
				.setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret")).post(leadSecretRequest, 200)
				.getResponse().as(LeadSecretResponse.class);

		Assert.assertEquals(apiRequest().getResponse().getStatusCode(), 200);
		Response response = apiRequest().getResponse();
		log.info("Valid Status Code : " + apiRequest().getResponse().getStatusCode());
		int stcode = apiRequest().getResponse().getStatusCode();
		if (stcode == 200) {
			response.then().body("loanAppResumptionInfo.loanAppId", equalTo(101398314))
					.body("loanAppResumptionInfo.productType", equalTo("PERSONAL_LOAN"))
					.body("loanAppResumptionInfo.borrowerResumptionInfo.firstName", equalTo("Benjamin"));
		} else {
			log.info(" Response does not contain required values");
		}

	}

	@Test
	public void InvalidrequestUID() {
		apiRequest().addHeader("x-cf-corr-id", UUID.randomUUID().toString())
				.addHeader("x-cf-source-id", "coding-challenge").setContentType(ContentType.JSON)
				.setRequestUrl(String.format("%s%s", url, "v2/resume/byLeadSecret")).post(InvalidleadSecretRequest, 404)
				.getResponse().as(LeadSecretResponse.class);

		Assert.assertEquals(apiRequest().getResponse().getStatusCode(), 404);
		// log.info("InValid Status Code : " +
		// apiRequest().getResponse().getStatusCode());

	}
}
