/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ForumMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * This class represents the forum table.
 * @author Sebastian Schwarzrock
 */
public class ForumMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String summary;
	private long timeCreated;
	private long timeModified;
	private Long platform;
	private static final Long PREFIX = 15L;

	private Set<CourseForumMining> courseForums = new HashSet<CourseForumMining>();
	private Set<ForumLogMining> forumLogs = new HashSet<ForumLogMining>();

	public Long getPrefix()
	{
		return PREFIX;
	}
	
	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof ForumMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ForumMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the forum
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the forum
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of the forum
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of the forum
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute summary
	 * 
	 * @return a summary of the topic in the forum
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * standard setter for the attribute summary
	 * 
	 * @param summary
	 *            a summary of the topic in the forum
	 */
	public void setSummary(final String summary) {
		this.summary = summary;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the forum was created
	 */
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timecreated
	 *            the timestamp when the forum was created
	 */
	public void setTimeCreated(final long timecreated) {
		this.timeCreated = timecreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the forum was changed the last time
	 */
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the forum was changed the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard setter for the attribute course_forum
	 * 
	 * @param courseForums
	 *            a set of entries in the course_forum table which relate the forum to the courses
	 */
	public void setCourseForums(final Set<CourseForumMining> courseForums) {
		this.courseForums = courseForums;
	}

	/**
	 * standard getter for the attribute course_forum
	 * 
	 * @return a set of entries in the course_forum table which relate the forum to the courses
	 */
	public Set<CourseForumMining> getCourseForums() {
		return this.courseForums;
	}

	/**
	 * standard add method for the attribute course_forum
	 * 
	 * @param courseForum
	 *            this entry will be added to the list of course_forum in this resource
	 */
	public void addCourseForum(final CourseForumMining courseForum) {
		this.courseForums.add(courseForum);
	}

	/**
	 * standard setter for the attribute forum_log
	 * 
	 * @param forumLogs
	 *            a set of entries in the forum_log table which are related to this forum
	 */
	public void setForumLogs(final Set<ForumLogMining> forumLogs) {
		this.forumLogs = forumLogs;
	}

	/**
	 * standard getter for the attribute forum_log
	 * 
	 * @return a set of entries in the forum_log table which are related to this forum
	 */
	public Set<ForumLogMining> getForumLogs() {
		return this.forumLogs;
	}

	/**
	 * standard add method for the attribute
	 * 
	 * @param forumLog
	 *            this entry will be added to the list of forum_log in this forum
	 */
	public void addForumLog(final ForumLogMining forumLog) {
		this.forumLogs.add(forumLog);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
