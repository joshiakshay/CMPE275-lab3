package edu.sjsu.cmpe275.lab3.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import edu.sjsu.cmpe275.lab3.Address.entities.Address;
import edu.sjsu.cmpe275.lab3.Player.dao.PlayerDAO;
import edu.sjsu.cmpe275.lab3.Player.entities.Player;
import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author amacoder
 * 
 */
@Component
@Path("/player")
public class PlayerRestService {

	@Autowired
	private PlayerDAO playerDao;

	/************************************ CREATE ************************************/

	/**
	 * Adds a new resource (player) from the given json format (at least title
	 * and feed elements are required at the DB level)
	 * 
	 * @param player
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response createPlayer(Player player) {
		playerDao.createPlayer(player);

		return Response.status(201)
				.entity("A new Player/resource has been created").build();
	}

	/**
	 * Adds a new resource (player) from "form" (at least firstname, lastname and email
	 * elements are required at the DB level)
	 * 
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param sponsor
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response createPlayerFromForm(@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("email") String email,
			@FormParam("description") String description,
			@FormParam("street") String street,
			@FormParam("city") String city,
			@FormParam("state") String state,
			@FormParam("zip") String zip,
			@FormParam("sponsor") String sponsor1) {
		Address address = new Address(street,city, state, zip);		
		Sponsor sponsor = new Sponsor();
		sponsor.setName(sponsor1);
		Player player = new Player(firstname, lastname, email, description, address, sponsor, null);
		playerDao.createPlayer(player);

		return Response.status(201)
				.entity("A new player/resource has been created").build();
	}

//	/**
//	 * A list of resources (here players) provided in json format will be added
//	 * to the database.
//	 * 
//	 * @param players
//	 * @return
//	 */
//	@POST
//	@Path("list")
//	@Consumes({ MediaType.APPLICATION_JSON })
//	@Transactional
//	public Response createplayers(List<player> players) {
//		for (player player : players) {
//			playerDao.createplayer(player);
//		}
//
//		return Response.status(204).build();
//	}

	/************************************ READ ************************************/
	/**
	 * Returns all resources (players) from the database
	 * 
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Player> getPlayers() throws JsonGenerationException, JsonMappingException, IOException {
		
		List<Player> players = playerDao.getPlayers();		
		
		return players; 
	}
	
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findById(@PathParam("id") Long id) throws JsonGenerationException, JsonMappingException, IOException {
		
		Player playerById = playerDao.getPlayerById(id);
		
		if (playerById != null) {
			return Response
					.status(200)
					.entity(playerById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS")
					.build();
		} else {
			return Response
					.status(404)
					.entity("The player with the id " + id + " does not exist")					
					.build();
		}
	}

	/************************************ UPDATE ************************************/
	/**
	 * Updates the attributes of the player received via JSON for the given @param
	 * id
	 * 
	 * If the player does not exist yet in the database (verified by
	 * <strong>id</strong>) then the application will try to create a new
	 * player resource in the db
	 * 
	 * @param id
	 * @param player
	 * @return
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response updatePlayerById(Player player) {
		String message;
		int status;
		if (player.getId() != 0) {
			playerDao.updatePlayer(player);
			status = 200; // OK
			message = "Player has been updated";
		} else if (playerCanBeCreated(player)) {
			playerDao.createPlayer(player);
			status = 201; // Created
			message = "The player you provided has been added to the database";
		} else {
			status = 406; // Not acceptable
			message = "The information you provided is not sufficient to perform either an UPDATE or "
					+ " an INSERTION of the new player resource <br/>"
					+ " If you want to UPDATE please make sure you provide an existent <strong>id</strong> <br/>"
					+ " If you want to insert a new player please provide at least a <strong>firstname</strong>, <strong>lastname</strong> and the <strong>email</strong> for the player resource";
		}

		return Response.status(status).entity(message).build();
	}

	private boolean playerCanBeCreated(Player player) {
		return player.getFirstname() != null && player.getLastname() != null && player.getEmail() != null;
	}

	/************************************ DELETE ************************************/
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deletePlayerById(@PathParam("id") Long id) {
		if (playerDao.deletePlayerById(id) == 1) {
			return Response.status(204).build();
		} else {
			return Response
					.status(404)
					.entity("Player with the id " + id
							+ " is not present in the database").build();
		}
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deletePlayers() {
		playerDao.deletePlayers();
		return Response.status(200)
				.entity("All players have been successfully removed").build();
	}

	public void setPlayerDao(PlayerDAO playerDao) {
		this.playerDao = playerDao;
	}

}
