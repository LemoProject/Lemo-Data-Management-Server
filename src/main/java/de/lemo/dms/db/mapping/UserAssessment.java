package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.lemo.dms.db.mapping.abstractions.IMapping;
import de.lemo.dms.db.mapping.abstractions.ILearningUserAssociation;

/** 
 * This class represents the table task. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_user_assessment")
public class UserAssessment implements IMapping, ILearningUserAssociation{
	
	private long id;
	private Course course;
	private User user;
	private LearningObj learning;
	private double grade;
	private String feedback;
	private long timemodified;
	
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof UserAssessment)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof UserAssessment)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
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
	 * @return the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}
	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {

		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addUserAssessment(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addUserAssessment(this);
		}
	}
	
	/**
	 * @return the user
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUser(final long user, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {

		if (users.get(user) != null)
		{
			this.user = users.get(user);
			users.get(user).addTaskUser(this);
		}
		if ((this.user == null) && (oldUsers.get(user) != null))
		{
			this.user = oldUsers.get(user);
			oldUsers.get(user).addTaskUser(this);
		}
	}

	public void setLearning(final long learningId, final Map<Long, LearningObj> learningObjects,
			final Map<Long, LearningObj> oldLearningObjects) {

		if (learningObjects.get(learningId) != null)
		{
			this.learning = learningObjects.get(learningId);
			learningObjects.get(learningId).addUserAssessment(this);
		}
		if ((this.learning == null) && (oldLearningObjects.get(learningId) != null))
		{
			this.learning = oldLearningObjects.get(learningId);
			oldLearningObjects.get(learningId).addUserAssessment(this);
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
	public void setGrade(Double grade) {
		this.grade = grade;
	}
	/**
	 * @return the timemodified
	 */
	@Column(name="timemodified")
	public long getTimemodified() {
		return timemodified;
	}
	/**
	 * @param timemodified the timemodified to set
	 */
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}

	@Override
	@Transient
	public Double getFinalGrade() {
		return this.getGrade();
	}

	/**
	 * @return the learningObj
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learning_id")
	public LearningObj getLearning() {
		return learning;
	}

	/**
	 * @param learningObj the learningObj to set
	 */
	public void setLearning(LearningObj learningObj) {
		this.learning = learningObj;
	}

	/**
	 * @return the feedback
	 */
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
