package edu.sjsu.cmpe275.lab3.service.integration;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Assert;
import org.junit.Test;

import edu.sjsu.cmpe275.lab3.Player.entities.Player;
import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

public class RestPersistenceServiceIT {

	@Test
	public void testGetPlayers() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8888/RESTwithPersistence/players/");

		Builder request = webTarget.request();
		request.header("Content-type", MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		List<Player> players = response
				.readEntity(new GenericType<List<Player>>() {
				});

		ObjectMapper mapper = new ObjectMapper();
		System.out.print(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(players));

		Assert.assertTrue("At least one player is present",
				players.size() > 0);
	}

	@Test
	public void testGetSponsor() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8888/RESTwithPersistence/sponsor/3");

		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		Sponsor sponsor = response.readEntity(Sponsor.class);

		ObjectMapper mapper = new ObjectMapper();
		System.out
				.print("Received sponsor from Sponsor database *************************** "
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(sponsor.getName()));

	}
	
	@Test
	public void testGetSponsors() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8888/RESTwithPersistence/sponsors/");

		Builder request = webTarget.request();
		request.header("Content-type", MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		List<Sponsor> sponsors = response
				.readEntity(new GenericType<List<Sponsor>>() {
				});

		ObjectMapper mapper = new ObjectMapper();
		System.out.print(mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(sponsors));

		Assert.assertTrue("At least one sponsor is present",
				sponsors.size() > 0);
	}

	@Test
	public void testGetPlayer() throws JsonGenerationException,
			JsonMappingException, IOException {

		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(JacksonFeature.class);

		Client client = ClientBuilder.newClient(clientConfig);

		WebTarget webTarget = client
				.target("http://localhost:8888/RESTwithPersistence/player/2");

		Builder request = webTarget.request(MediaType.APPLICATION_JSON);

		Response response = request.get();
		Assert.assertTrue(response.getStatus() == 200);

		Player player = response.readEntity(Player.class);

		ObjectMapper mapper = new ObjectMapper();
		System.out
				.print("Received player from Player database *************************** "
						+ mapper.writerWithDefaultPrettyPrinter()
								.writeValueAsString(player.getFirstname()+player.getLastname()));
	}
	
}
