/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ScormLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedLogObject;

/** This class represents the log table for the scorm modules. */
public class ScormLogMining implements ILogMining, IMappingClass, IRatedLogObject {

	private long id;
	private UserMining user;
	private CourseMining course;
	private ScormMining scorm;
	private Double grade;
	private String action;
	private long timestamp;
	private Long duration;
	private Long platform;

	@Override
	public int compareTo(final ILogMining arg0) {
		ILogMining s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof ScormLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ScormLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public void setDuration(final Long duration) {
		this.duration = duration;
	}

	@Override
	public String getTitle()
	{
		return this.scorm == null ? null : this.scorm.getTitle();
	}

	@Override
	public Long getLearnObjId()
	{
		return this.scorm == null ? null : this.scorm.getId();

	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the log entry
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the quiz
	 */
	@Override
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the quiz
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute quiz
	 * 
	 * @param user
	 *            the id of the user who interact with the quiz
	 * @param userMining
	 *            a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining
	 *            a list of user in the mining database, which is searched for the user with the id submitted in the
	 *            user parameter
	 */
	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addScormLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addScormLog(this);
		}
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the course in which the action takes place
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the course in which the action takes place
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the action takes place
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the mining database, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addScormLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addScormLog(this);
		}
	}

	/**
	 * standard getter for the attribute grade
	 * 
	 * @return the grade in this case of action
	 */
	@Override
	public Double getGrade() {
		return this.grade;
	}

	/**
	 * standard setter for the attribute grade
	 * 
	 * @param grade
	 *            the grade in this case of action
	 */
	public void setGrade(final Double grade) {
		this.grade = grade;
	}

	/**
	 * standard getter for the attribute timestamp
	 * 
	 * @return the timestamp the action did occur
	 */
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute timestamp
	 * 
	 * @param timestamp
	 *            the timestamp the action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute action
	 * 
	 * @return the action which occur
	 */
	@Override
	public String getAction() {
		return this.action;
	}

	/**
	 * standard setter for the attribute action
	 * 
	 * @param action
	 *            the action which occur
	 */
	public void setAction(final String action) {
		this.action = action;
	}

	/**
	 * parameterized setter for the attribute
	 * 
	 * @param id
	 *            the id of the scorm package in which the action takes place
	 * @param scormMining
	 *            a list of new added scorm packages, which is searched for the scorm package with the id submitted in
	 *            the parameter
	 * @param oldScormMining
	 *            a list of scorm packages in the mining database, which is searched for the scorm package with the id
	 *            submitted in the parameter
	 */
	public void setScorm(final long scorm, final Map<Long, ScormMining> scormMining,
			final Map<Long, ScormMining> oldScormMining) {

		if (scormMining.get(scorm) != null)
		{
			this.scorm = scormMining.get(scorm);
			scormMining.get(scorm).addScormLog(this);
		}
		if ((this.scorm == null) && (oldScormMining.get(scorm) != null))
		{
			this.scorm = oldScormMining.get(scorm);
			oldScormMining.get(scorm).addScormLog(this);
		}
	}

	/**
	 * standard setter for the attribute scorm
	 * 
	 * @param scorm
	 *            the scorm package in which the action takes place
	 */
	public void setScorm(final ScormMining scorm) {
		this.scorm = scorm;
	}

	/**
	 * standard getter for the attribute scorm
	 * 
	 * @return the scorm package in which the action takes place
	 */
	public ScormMining getScorm() {
		return this.scorm;
	}

	@Override
	public Long getPrefix() {
		return 17L;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	public Double getMaxGrade() {
		return this.scorm.getMaxGrade();
	}

	@Override
	public Double getFinalGrade() {
		return this.grade;
	}
}
