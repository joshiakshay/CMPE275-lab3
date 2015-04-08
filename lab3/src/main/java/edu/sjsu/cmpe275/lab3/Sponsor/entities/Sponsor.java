package edu.sjsu.cmpe275.lab3.Sponsor.entities;

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
@Table(name="Sponsors")
public class Sponsor implements Serializable {

	private static final long serialVersionUID = -8039686696076337053L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;

	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="address")
	private Address address;
		
	public Sponsor(){}
	
	public Sponsor(String name, String description, Address address) {
		
		this.name = name;
		this.description = description;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
