package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Events {

	private long id;
	private long userId;
	private long event;
	private long timestamp;
	private long segmentId;
	private long courseId;
	
	
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
	 * @return the event
	 */
	@Column(name="event")
	public long getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(long event) {
		this.event = event;
	}

	/**
	 * @return the timestamp
	 */
	@Column(name="created_at")
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the segmentId
	 */
	@Column(name="segement")
	public long getSegmentId() {
		return segmentId;
	}
	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(long segmentId) {
		this.segmentId = segmentId;
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
	
	
	
	
}
