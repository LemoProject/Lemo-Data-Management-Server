package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.ILog;
import de.lemo.dms.db.mapping.abstractions.IMapping;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

@Entity
@Table(name = "lemo_assessment")
public class Assessment implements IMapping, IRatedObject{
	
	private long id;
	private TaskLog submissionLog;
	private User grader;
	private double grade;
	private String feedback;
	private static Long PREFIX = 11L;
	
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
	@JoinColumn(name="submission_log_id")
	public TaskLog getTaskLog() {
		return submissionLog;
	}
	/**
	 * @param taskLog the taskLog to set
	 */
	public void setTaskLog(TaskLog taskLog) {
		this.submissionLog = taskLog;
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
	
	public void setGrader(final long grader, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {

		if (users.get(grader) != null)
		{
			this.grader = users.get(grader);
			users.get(grader).addGrader(this);
		}
		if ((this.grader == null) && (oldUsers.get(grader) != null))
		{
			this.grader = oldUsers.get(grader);
			oldUsers.get(grader).addGrader(this);
		}
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

	public int compareTo(final ILog arg0) {
		ILog s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.submissionLog.getTimestamp() > s.getTimestamp()) {
				return 1;
			}
			if (this.submissionLog.getTimestamp() < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@Transient
	public long getTimestamp() {
		return this.submissionLog.getTimestamp();
	}

	@Transient
	public User getUser() {
		return this.submissionLog.getUser();
	}

	@Transient
	public Course getCourse() {
		return this.submissionLog.getCourse();
	}

	@Override
	@Transient
	public String getTitle() {
		return this.submissionLog.getTask().getTitle();
	}

	@Override
	@Transient
	public Double getMaxGrade() {
		return this.submissionLog.getTask().getMaxGrade();
	}

	@Transient
	public Long getLearningObjectId() {
		return this.submissionLog.getTask().getId();
	}

	@Transient
	public ILearningObject getLearningObject() {
		return this.submissionLog.getTask();
	}

	@Transient
	public long getPrefix() {
		return PREFIX;
	}

	@Override
	@Transient
	public String getLOType() {
		return this.submissionLog.getTask().getType().getType();
	}
}
