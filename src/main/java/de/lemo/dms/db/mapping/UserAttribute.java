package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;


@Entity
@Table(name = "lemo_user_attribute")
public class UserAttribute implements IMapping{

	private long id;
	private User user;
	private Attribute attribute;
	private String value;
	

	
	
	/**
	 * @return the value
	 */
	@Lob
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
	 * @return the learningId
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	/**
	 * @param learningId the learningId to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the attributeId
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="attribute_id")
	public Attribute getAttribute() {
		return attribute;
	}
	/**
	 * @param attributeId the attributeId to set
	 */
	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
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
	@Override
	public boolean equals(IMapping o) {
		if (!(o instanceof UserAttribute)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof UserAttribute)) {
			return true;
		}
		return false;
	}
	
	
	
	
	
}

