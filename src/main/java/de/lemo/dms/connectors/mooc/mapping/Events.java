package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "events")
public class Events {

	private Long id;
	private Long userId;
	private int event;
	private Date timestamp;
	private Long segmentId;
	private Long courseId;
	private Long unitResourceId;
	
	
	/**
	 * @return the id
	 */
	@Id
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the event
	 */
	@Column(name="event")
	public int getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(int event) {
		this.event = event;
	}

	/**
	 * @return the timestamp
	 */
	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the segmentId
	 */
	@Column(name="segment_id")
	public Long getSegmentId() {
		return segmentId;
	}
	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(Long segmentId) {
		this.segmentId = segmentId;
	}
	/**
	 * @return the courseId
	 */
	@Column(name="course_id")
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * @return the unitResourceId
	 */
	@Column(name="unit_resource_id")
	public Long getUnitResourceId() {
		return unitResourceId;
	}
	/**
	 * @param unitResourceId the unitResourceId to set
	 */
	public void setUnitResourceId(Long unitResourceId) {
		this.unitResourceId = unitResourceId;
	}
	
	
	
	
}
