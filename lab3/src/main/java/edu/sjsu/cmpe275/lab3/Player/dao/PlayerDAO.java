package edu.sjsu.cmpe275.lab3.Player.dao;

import java.util.List;

import edu.sjsu.cmpe275.lab3.Player.entities.Player;

/**
 * 
 * @author ama
 * @see <a href="http://www.codingpedia.org/ama/spring-mybatis-integration-example/">http://www.codingpedia.org/ama/spring-mybatis-integration-example/</a>
 */
public interface PlayerDAO {
	
	public List<Player> getPlayers();

	
	/**
	 * Returns a podcast given its id
	 * 
	 * @param id
	 * @return
	 */
	public Player getPlayerById(long id);

	public long deletePlayerById(long id);

	public long createPlayer(Player player);

	public long updatePlayer(Player player);

	/** removes all podcasts */
	public void deletePlayers();

	/** 
	 * Returns all podcasts from "legacy" system
	 * @return
	 */
}
