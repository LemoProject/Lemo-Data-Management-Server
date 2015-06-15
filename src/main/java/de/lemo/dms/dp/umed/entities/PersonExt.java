package de.lemo.dms.dp.umed.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lemo_personExt")
public class PersonExt{

	private long id;
	private Person person;
	private String attr;
	private String value;
	

	
	
	/**
	 * @return the value
	 */
	@Column(name="value")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the person
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person")
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person the Person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	public boolean equals(PersonExt o) {
		if ((o.getId() == this.getId()) && (o instanceof PersonExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attribute
	 */
	@Column(name="attr")
	public String getAttribute() {
		return attr;
	}
	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attr = attribute;
	}
	
	
	
	
	
}

