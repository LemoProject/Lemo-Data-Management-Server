package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

@Entity
@Table(name = "lemo_assessment")
public class Assessment implements IMapping{
	
	private long id;
	private TaskLog taskLog;
	private User grader;
	private double grade;
	private String feedback;
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof Assessment)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Assessment)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	
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
	 * @return the taskLog
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_log_id")
	public TaskLog getTaskLog() {
		return taskLog;
	}
	/**
	 * @param taskLog the taskLog to set
	 */
	public void setTaskLog(TaskLog taskLog) {
		this.taskLog = taskLog;
	}
	/**
	 * @return the grader
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="grader_id")
	public User getGrader() {
		return grader;
	}
	/**
	 * @param grader the grader to set
	 */
	public void setGrader(User grader) {
		this.grader = grader;
	}
	/**
	 * @return the grade
	 */
	@Column(name="grade")
	public double getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(double grade) {
		this.grade = grade;
	}
	/**
	 * @return the feedback
	 */
	@Lob
	@Column(name="feedback")
	public String getFeedback() {
		return feedback;
	}
	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
	

}
