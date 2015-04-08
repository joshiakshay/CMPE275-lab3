package edu.sjsu.cmpe275.lab3.Sponsor.dao;

import java.util.List;

import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

public interface SponsorDAO {
	
	public List<Sponsor> getSponsors();

	
	/**
	 * Returns a podcast given its id
	 * 
	 * @param id
	 * @return
	 */
	public Sponsor getSponsorById(long id);

	public long deleteSponsorById(long id);

	public long createSponsor(Sponsor sponsor);

	public long updateSponsor(Sponsor sponsor);

	/** removes all podcasts */
	public void deleteSponsors();

	/** 
	 * Returns all podcasts from "legacy" system
	 * @return
	 */
}

