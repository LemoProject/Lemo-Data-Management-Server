package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "segments")
public class Segments {
	
	private long id;
	private long courseId;
	private long userId;
	private String title;
	private String type;
	private String description;
	private Date timeCreated;
	private Date timeModified;
	private long position;
	private Long parent;
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
	 * @return the courseId
	 */
	@Column(name="course_id")
	public long getCourseId() {
		return courseId;
	}
	
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	
	/**
	 * @return the userId
	 */
	@Column(name="user_id")
	public long getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
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
	 * @return the description
	 */
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the timecreated
	 */
	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimecreated() {
		return timeCreated;
	}
	
	/**
	 * @param timecreated the timecreated to set
	 */
	public void setTimecreated(Date timecreated) {
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
	
	/**
	 * @return the position
	 */
	@Column(name="position")
	public long getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
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
	 * @return the parent
	 */
	@Column(name="parent_id")
	public Long getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	
	
	

}
