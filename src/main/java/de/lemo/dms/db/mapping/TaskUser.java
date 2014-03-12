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
import de.lemo.dms.db.mapping.abstractions.IRatedUserAssociation;

/** 
 * This class represents the table task. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_task_user")
public class TaskUser implements IMapping, IRatedUserAssociation{
	
	private long id;
	private Course course;
	private User user;
	private Task task;
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
			courses.get(course).addTaskUser(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addTaskUser(this);
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
	
	public void setTask(final long task, final Map<Long, Task> tasks,
			final Map<Long, Task> oldTasks) {

		if (tasks.get(task) != null)
		{
			this.task = tasks.get(task);
			tasks.get(task).addTaskUser(this);
		}
		if ((this.task == null) && (oldTasks.get(task) != null))
		{
			this.task = oldTasks.get(task);
			oldTasks.get(task).addTaskUser(this);
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

	@Override
	@Transient
	public Long getLearnObjId() {
		return this.task.getId();
	}

	@Override
	@Transient
	public Double getMaxGrade() {
		return this.task.getMaxGrade();
	}

}
