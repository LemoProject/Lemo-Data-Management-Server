package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
@Table(name = "memberships")
public class Memberships {
	
	private long id;
	private long userId;
	private long courseId;
	private String role;
	private Date timeCreated;
	private Date timeModified;
	private String grade;
	
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
	 * @return the role
	 */
	@Lob
	@Column(name="role")
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
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
	 * @return the grade
	 */
	@Column(name="grade")
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@Transient
	public Double getNumericGrade()
	{
		if(this.grade == null)
			return 0d;
		if(this.grade.equals("A"))
			return 1d;
		if(this.grade.equals("B"))
			return 2d;
		if(this.grade.equals("C"))
			return 3d;
		if(this.grade.equals("D"))
			return 4d;
		if(this.grade.equals("E"))
			return 5d;
		if(this.grade.equals("F"))
			return 6d;
		return null;
	}
	

}
