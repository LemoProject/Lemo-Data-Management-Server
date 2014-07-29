package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assessment_sessions")
public class AssessmentSessions {
	
	private long id;
	private long membershipId;
	private long assessmentId;
	private Date timeCreated;
	private Date timeModified;
	private String type;
	private Long duration;
	private String state;
	private Long score;
	private Long maxScore;
	private String grade;
	private Date submittetdAt;
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
	 * @return the membershipId
	 */
	@Column(name="membership_id")
	public long getMembershipId() {
		return membershipId;
	}
	/**
	 * @param membershipId the membershipId to set
	 */
	public void setMembershipId(long membershipId) {
		this.membershipId = membershipId;
	}
	/**
	 * @return the assessmentId
	 */
	@Column(name="assessment_id")
	public long getAssessmentId() {
		return assessmentId;
	}
	/**
	 * @param assessmentId the assessmentId to set
	 */
	public void setAssessmentId(long assessmentId) {
		this.assessmentId = assessmentId;
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
	 * @return the duration
	 */
	@Column(name="duration")
	public Long getDuration() {
		return duration;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	/**
	 * @return the state
	 */
	@Column(name="state")
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the score
	 */
	@Column(name="score")
	public Long getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Long score) {
		this.score = score;
	}
	/**
	 * @return the maxScore
	 */
	@Column(name="max_score")
	public Long getMaxScore() {
		return maxScore;
	}
	/**
	 * @param maxScore the maxScore to set
	 */
	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
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
	/**
	 * @return the submittetdAt
	 */
	@Column(name = "submitted_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getSubmittetdAt() {
		return submittetdAt;
	}
	/**
	 * @param submittetdAt the submittetdAt to set
	 */
	public void setSubmittetdAt(Date submittetdAt) {
		this.submittetdAt = submittetdAt;
	}
	
	

	
}
