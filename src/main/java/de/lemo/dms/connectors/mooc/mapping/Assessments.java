package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "assessments")
public class Assessments {
	
	private long id;
	private String type;
	private String title;
	private Date timeCreated;
	private Date timeModified;

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
	 * @return the timecreated
	 */
	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeCreated() {
		return timeCreated;
	}

	/**
	 * @param timecreated the timecreated to set
	 */
	public void setTimeCreated(Date timecreated) {
		this.timeCreated = timecreated;
	}

	/**
	 * @return the timemodified
	 */
	@Column(name = "updated_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeModified() {
		return timeModified;
	}

	/**
	 * @param timemodified the timemodified to set
	 */
	public void setTimeModified(Date timemodified) {
		this.timeModified = timemodified;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

}
