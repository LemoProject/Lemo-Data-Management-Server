package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assets")
public class Assets {
	
	private long id;
	private String title;
	private String type;
	private Date timeCreated;
	private Date timeModified;
	
	
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
	 * @return the timeCreated
	 */
	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeCreated() {
		return timeCreated;
	}
	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	/**
	 * @return the timeModified
	 */
	@Column(name = "updated_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeModified() {
		return timeModified;
	}
	/**
	 * @param timeModified the timeModified to set
	 */
	public void setTimeModified(Date timeModified) {
		this.timeModified = timeModified;
	}
	
	
	

}
