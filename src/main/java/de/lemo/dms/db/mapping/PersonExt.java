package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "umed_personExt")
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
	
	public void setPerson(Long id, Map<Long, Person> persons, Map<Long, Person> oldPersons) {
		
		if (persons.get(id) != null)
		{
			this.person = persons.get(id);
			persons.get(id).addPersonExtension(this);
		}
		if ((this.person == null) && (oldPersons.get(id) != null))
		{
			this.person = oldPersons.get(id);
			oldPersons.get(id).addPersonExtension(this);
		}
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

