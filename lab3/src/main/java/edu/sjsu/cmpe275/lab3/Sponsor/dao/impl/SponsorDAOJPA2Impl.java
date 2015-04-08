package edu.sjsu.cmpe275.lab3.Sponsor.dao.impl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import edu.sjsu.cmpe275.lab3.Sponsor.dao.SponsorDAO;
import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

public class SponsorDAOJPA2Impl implements SponsorDAO {
	@PersistenceContext(unitName="RestPersistence")
	private EntityManager entityManager;
	
	@Override
	public List<Sponsor> getSponsors() {
		String qlString = "SELECT s FROM Sponsor s";
		TypedQuery<Sponsor> query = entityManager.createQuery(qlString, Sponsor.class);		

		return query.getResultList();
	}

	@Override
	public Sponsor getSponsorById(long id) {
		try {
			String qlString = "SELECT s FROM Sponsor s WHERE s.id = ?1";
			TypedQuery<Sponsor> query = entityManager.createQuery(qlString, Sponsor.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public long deleteSponsorById(long id) {
		Sponsor sponsor = entityManager.find(Sponsor.class, id);
		entityManager.remove(sponsor);
		return 1L;
	}

	@Override
	public long createSponsor(Sponsor sponsor) {
		entityManager.persist(sponsor);
		entityManager.flush();
		return sponsor.getId();
	}

	@Override
	public long updateSponsor(Sponsor sponsor) {
		entityManager.merge(sponsor);
		return 1; 
	}

	@Override
	public void deleteSponsors() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE players");		
		query.executeUpdate();
	}

}
