/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseForumMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the relationship between courses and forums. */
public class CourseForumMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private ForumMining forum;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseForumMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseForumMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and forum
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and forum
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the forum is used
	 */
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the forum is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the forum is used
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {
		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseForum(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseForum(this);
		}
	}

	/**
	 * standard getter for the attribute forum
	 * 
	 * @return the forum which is used in the course
	 */
	public ForumMining getForum() {
		return this.forum;
	}

	/**
	 * standard setter for the attribute forum
	 * 
	 * @param forum
	 *            the forum which is used in the course
	 */
	public void setForum(final ForumMining forum) {
		this.forum = forum;
	}

	/**
	 * parameterized setter for the attribute forum
	 * 
	 * @param forum
	 *            the id of the forum which is used in the course
	 * @param forumMining
	 *            a list of new added forums, which is searched for the forum with the id submitted in the forum
	 *            parameter
	 * @param oldForumMining
	 *            a list of forums in the miningdatabase, which is searched for the forum with the id submitted in the
	 *            forum parameter
	 */
	public void setForum(final long forum, final Map<Long, ForumMining> forumMining,
			final Map<Long, ForumMining> oldForumMining) {
		if (forumMining.get(forum) != null)
		{
			this.forum = forumMining.get(forum);
			forumMining.get(forum).addCourseForum(this);
		}
		if ((this.forum == null) && (oldForumMining.get(forum) != null))
		{
			this.forum = oldForumMining.get(forum);
			oldForumMining.get(forum).addCourseForum(this);
		}
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
