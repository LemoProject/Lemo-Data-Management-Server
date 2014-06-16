package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "segments")
public class Segments {
	
	private long id;
	private long courseId;
	private long userId;
	private String title;
	private String type;
	private String description;
	private long timecreated;
	private long timemodified;
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
	@Column(name="parent")
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
