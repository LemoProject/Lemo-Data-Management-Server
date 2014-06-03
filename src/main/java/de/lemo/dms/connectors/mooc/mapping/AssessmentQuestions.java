package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "assessment_questions")
public class AssessmentQuestions {
	
	private long id;
	private long assessmentId;
	private String type;
	private long score;
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
