package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "assessment_answers")
public class AssessmentAnswers {
	
	private long id;
	private long assessmentQuestionId;
	private boolean correct;
	private String content;	
	private Date timeCreated;
	private Date timeModified;
	
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
	 * @return the assessmentQuestionId
	 */
	@Column(name="assessment_question_id")
	public long getAssessmentQuestionId() {
		return assessmentQuestionId;
	}
	
	/**
	 * @param assessmentQuestionId the assessmentQuestionId to set
	 */
	public void setAssessmentQuestionId(long assessmentQuestionId) {
		this.assessmentQuestionId = assessmentQuestionId;
	}
	
	/**
	 * @return the correct
	 */
	@Column(name="correct")
	public boolean isCorrect() {
		return correct;
	}
	
	/**
	 * @param correct the correct to set
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	/**
	 * @return the content
	 */
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

	

}
