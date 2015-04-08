package edu.sjsu.cmpe275.lab3.Player.entities;
import java.util.List;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import edu.sjsu.cmpe275.lab3.Address.entities.Address;
import edu.sjsu.cmpe275.lab3.Sponsor.entities.Sponsor;

import java.io.Serializable;
/**
 * Podcast entity 
 * 
 * @author ama
 *
 */
@SuppressWarnings("restriction")
@XmlRootElement
@Entity
@Table(name="players")
public class Player implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;

	@Column(name="firstname")
	private String firstname;
		
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="description")
	private String description;
	
	@Column(name="address")
	private Address address;
	
	@Column(name="sponsor")
	private Sponsor sponsor;
	
	@Column(name="opponents")
	private List<Player> opponents;
		
	public Player(){}
	
	public Player(String firstname, String lastname, String email, String description, Address address, Sponsor sponsor, List<Player> opponents) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.address = address;
		this.sponsor = sponsor;
		this.opponents = opponents;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address.getStreet()+", "+address.getCity()+", "+address.getState()+" - "+address.getZip();
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getSponsor() {
		return sponsor.toString();
	}

	public void setSponsor(Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public List<Player> getOpponents() {
		return opponents;
	}

	public void setOpponents(List<Player> opponents) {
		this.opponents = opponents;
	}
}