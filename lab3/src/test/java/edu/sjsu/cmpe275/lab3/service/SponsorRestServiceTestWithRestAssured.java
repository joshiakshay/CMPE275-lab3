package edu.sjsu.cmpe275.lab3.service;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class SponsorRestServiceTestWithRestAssured {

	@Test
	public void testSponsorFetchSuccessful(){
		expect().
			body("id", equalTo("3")).
			body("name", equalTo("Samsung")).
		when().
			get("/RESTwithPersistence/Sponsors/2");
	}
}
