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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab3.Address.entities.Address;
import edu.sjsu.cmpe275.lab3.Sponsor.dao.SponsorDAO;
import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

@Component
@Path("/sponsor")
public class SponsorRestService {

	@Autowired
	private SponsorDAO sponsorDao;

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
	public Response createSponsor(Sponsor sponsor) {
		sponsorDao.createSponsor(sponsor);

		return Response.status(201)
				.entity("A new Sponsor/resource has been created").build();
	}

	/**
	 * Adds a new resource (player) from "form" (at least firstname, lastname
	 * and email elements are required at the DB level)
	 * 
	 * @param name
	 * @param description
	 * @param address
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response createPlayerFromForm(@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("street") String street,
			@FormParam("city") String city,
			@FormParam("state") String state,
			@FormParam("zip") String zip) {
		Address address = new Address(street, city, state, zip);
		Sponsor sponsor = new Sponsor(name, description, address);
		sponsorDao.createSponsor(sponsor);

		return Response.status(201)
				.entity("A new sponsor/resource has been created").build();
	}

	// /**
	// * A list of resources (here players) provided in json format will be
	// added
	// * to the database.
	// *
	// * @param players
	// * @return
	// */
	// @POST
	// @Path("list")
	// @Consumes({ MediaType.APPLICATION_JSON })
	// @Transactional
	// public Response createplayers(List<player> players) {
	// for (player player : players) {
	// playerDao.createplayer(player);
	// }
	//
	// return Response.status(204).build();
	// }

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
	public List<Sponsor> getSponsors() throws JsonGenerationException,
			JsonMappingException, IOException {

		List<Sponsor> sponsors = sponsorDao.getSponsors();

		return sponsors;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findById(@PathParam("id") Long id)
			throws JsonGenerationException, JsonMappingException, IOException {

		Sponsor sponsorById = sponsorDao.getSponsorById(id);

		if (sponsorById != null) {
			return Response.status(200).entity(sponsorById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		} else {
			return Response
					.status(404)
					.entity("The sponsor with the id " + id + " does not exist")
					.build();
		}
	}

	/************************************ UPDATE ************************************/
	/**
	 * Updates the attributes of the player received via JSON for the given @param
	 * id
	 * 
	 * If the player does not exist yet in the database (verified by
	 * <strong>id</strong>) then the application will try to create a new player
	 * resource in the db
	 * 
	 * @param id
	 * @param player
	 * @return
	 */
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response updateSponsorById(Sponsor sponsor) {
		String message;
		int status;
		if (sponsor.getId() != 0) {
			sponsorDao.updateSponsor(sponsor);
			status = 200; // OK
			message = "Sponsor has been updated";
		} else if (sponsorCanBeCreated(sponsor)) {
			sponsorDao.createSponsor(sponsor);
			status = 201; // Created
			message = "The sponsor you provided has been added to the database";
		} else {
			status = 406; // Not acceptable
			message = "The information you provided is not sufficient to perform either an UPDATE or "
					+ " an INSERTION of the new sponsor resource <br/>"
					+ " If you want to UPDATE please make sure you provide an existent <strong>id</strong> <br/>"
					+ " If you want to insert a new sponsor please provide at least <strong>name</strong> for the sponsor resource";
		}

		return Response.status(status).entity(message).build();
	}

	private boolean sponsorCanBeCreated(Sponsor sponsor) {
		return sponsor.getName() != null;
	}

	/************************************ DELETE ************************************/
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deletePlayerById(@PathParam("id") Long id) {
		if (sponsorDao.deleteSponsorById(id) == 1) {
			return Response.status(204).build();
		} else {
			return Response
					.status(404)
					.entity("Sponsor with the id " + id
							+ " is not present in the database").build();
		}
	}

	@DELETE
	@Produces({ MediaType.TEXT_HTML })
	@Transactional
	public Response deleteSponsors() {
		sponsorDao.deleteSponsors();
		return Response.status(200)
				.entity("All sponsors have been successfully removed").build();
	}

	public void setPlayerDao(SponsorDAO sponsorDao) {
		this.sponsorDao = sponsorDao;
	}

}
