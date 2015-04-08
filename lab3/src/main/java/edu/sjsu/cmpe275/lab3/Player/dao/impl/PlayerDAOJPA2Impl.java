package edu.sjsu.cmpe275.lab3.Player.dao.impl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import edu.sjsu.cmpe275.lab3.Player.dao.PlayerDAO;
import edu.sjsu.cmpe275.lab3.Player.entities.Player;

public class PlayerDAOJPA2Impl implements PlayerDAO {
	@PersistenceContext(unitName="RestPersistence")
	private EntityManager entityManager;
	
	@Override
	public List<Player> getPlayers() {
		String qlString = "SELECT p FROM Player p";
		TypedQuery<Player> query = entityManager.createQuery(qlString, Player.class);		

		return query.getResultList();
	}

	@Override
	public Player getPlayerById(long id) {
		try {
			String qlString = "SELECT p FROM Player p WHERE p.id = ?1";
			TypedQuery<Player> query = entityManager.createQuery(qlString, Player.class);		
			query.setParameter(1, id);

			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public long deletePlayerById(long id) {
		Player player = entityManager.find(Player.class, id);
		entityManager.remove(player);
		return 1L;
	}

	@Override
	public long createPlayer(Player player) {
		entityManager.persist(player);
		entityManager.flush();
		return player.getId();
	}

	@Override
	public long updatePlayer(Player player) {
		entityManager.merge(player);
		return 1; 
	}

	@Override
	public void deletePlayers() {
		Query query = entityManager.createNativeQuery("TRUNCATE TABLE players");		
		query.executeUpdate();
	}

}
