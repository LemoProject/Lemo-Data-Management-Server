package de.lemo.dms.connectors.iversity.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "assessment_answers")
public class AssessmentAnswers {
	
	private long id;
	private long assessmentQuestionId;
	private boolean correct;
	private String content;	
	private long timecreated;
	private long timemodified;
	
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

	

}
