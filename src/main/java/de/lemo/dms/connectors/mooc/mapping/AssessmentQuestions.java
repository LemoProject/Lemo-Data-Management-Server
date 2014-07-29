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
@Table(name = "assessment_questions")
public class AssessmentQuestions {
	
	private long id;
	private long assessmentId;
	private String type;
	private long score;
	private Date timeCreated;
	private Date timeModified;
	private String content;
	
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
	 * @return the assessment_id
	 */
	
	@Column(name="assessment_id")
	public long getAssessmentId() {
		return assessmentId;
	}
	/**
	 * @param assessment_id the assessment_id to set
	 */
	public void setAssessmentId(long assessmentId) {
		this.assessmentId = assessmentId;
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
	 * @return the score
	 */
	@Column(name="score")
	public long getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(long score) {
		this.score = score;
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
	/**
	 * @return the description
	 */
	@Lob
	@Column(name="content")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the description to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
