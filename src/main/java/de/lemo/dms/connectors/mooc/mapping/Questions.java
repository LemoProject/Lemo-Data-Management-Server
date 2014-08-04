package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "questions")
public class Questions {
	
	private long id;
	private long courseId;
	private Long segmentId;
	private String content;
	private long userId;
	private Date timeCreated;
	private Date timeModified;
	private String title;
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
	 * @return the content
	 */
	@Lob
	@Column(name="content")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	

}
