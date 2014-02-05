package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

/**
 * Bean class for the platforms 
 *
 */
@Entity
@Table(name = "platform")
public class Platform implements IMapping {
	
	private long id;
	private String title;
	private String type;
	private Long prefix;
	
	public Platform(final Long id, final String title, final String type, final Long prefix)
	{
		this.id = id;
		this.title = title;
		this.type = type;
		this.prefix = prefix;
	}
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof Platform)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Platform)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
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
	/**
	 * @return the title
	 */
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the type
	 */
	@Lob
	@Column(name="type")
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the prefix
	 */
	@Column(name="prefix")
	public Long getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(Long prefix) {
		this.prefix = prefix;
	}
	
	

}
