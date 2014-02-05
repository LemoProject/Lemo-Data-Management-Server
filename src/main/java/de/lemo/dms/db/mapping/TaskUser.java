package de.lemo.dms.db.mapping;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

/** 
 * This class represents the table task. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_task_user")
public class TaskUser implements IMapping{
	
	private long id;
	private Course course;
	private User user;
	private Task task;
	private long platform;
	private double grade;
	private long timemodified;
	
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof Task)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Task)) {
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
	/**
	 * @return the task
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	public Task getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}
	/**
	 * @return the platform
	 */
	public long getPlatform() {
		return platform;
	}
	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(long platform) {
		this.platform = platform;
	}
	/**
	 * @return the grade
	 */
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
	 * @return the timemodified
	 */
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
