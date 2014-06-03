package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "assessments")
public class Assessments {
	
	private long id;
	private String type;
	private String title;
	private long timecreated;
	private long timemodified;

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
	@Column(name="created_at")
	public long getTimecreated() {
		return timecreated;
	}

	/**
	 * @param timecreated the timecreated to set
	 */
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}

	/**
	 * @return the timemodified
	 */
	@Column(name="updated_at")
	public long getTimemodified() {
		return timemodified;
	}

	/**
	 * @param timemodified the timemodified to set
	 */
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}

	@Id
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

}
