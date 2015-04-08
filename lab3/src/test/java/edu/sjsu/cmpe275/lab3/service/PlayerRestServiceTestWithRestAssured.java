package edu.sjsu.cmpe275.lab3.service;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class PlayerRestServiceTestWithRestAssured {

	@Test
	public void testPlayerFetchSuccessful(){
		expect().
			body("id", equalTo("2")).
			body("firstname", equalTo("John")).
			body("lastname", equalTo("Smith")).
			body("email", equalTo("john.smith@gmail.com")).
		when().
			get("/RESTwithPersistence/players/2");
	}
}
