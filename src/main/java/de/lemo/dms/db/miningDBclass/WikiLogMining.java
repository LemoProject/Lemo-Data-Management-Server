/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/WikiLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the log table for the wiki object. */
public class WikiLogMining implements ILogMining, IMappingClass {

	private long id;
	private WikiMining wiki;
	private UserMining user;
	private CourseMining course;
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
		if (!(o instanceof WikiLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof WikiLogMining)) {
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
		return this.wiki == null ? null : this.wiki.getTitle();
	}

	@Override
	public Long getLearnObjId()
	{
		return this.wiki == null ? null : this.wiki.getId();
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
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the log entry
	 */
	@Override
	public void setId(final long id) {
		this.id = id;
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
	 * standard getter for the attribute
	 * 
	 * @return the time stamp the action did occur
	 */
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * standard setter for the attribute
	 * 
	 * @param timestamp
	 *            the time stamp the logged action did occur
	 */
	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return the the course in which the action on the wiki occur
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of the course in which the action on the wiki occur as long value
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of courses in the mining database, which is searched for the course with the id submitted in
	 *            the course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addWikiLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addWikiLog(this);
		}
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            the the course in which the action on the wiki occur
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute user
	 * 
	 * @return the user who interact with the resource
	 */
	@Override
	public UserMining getUser() {
		return this.user;
	}

	/**
	 * standard setter for the attribute user
	 * 
	 * @param user
	 *            the user who interact with the resource
	 */
	public void setUser(final UserMining user) {
		this.user = user;
	}

	/**
	 * parameterized setter for the attribute
	 * 
	 * @param user
	 *            the id of the user who interact with the resource
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
			userMining.get(user).addWikiLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addWikiLog(this);
		}
	}

	/**
	 * standard getter for the attribute wiki
	 * 
	 * @return the wiki with which was interacted
	 */
	public WikiMining getWiki() {
		return this.wiki;
	}

	/**
	 * standard setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the wiki with which was interacted
	 */
	public void setWiki(final WikiMining wiki) {
		this.wiki = wiki;
	}

	/**
	 * parameterized setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the id of the wiki with which was interacted
	 * @param wikiMining
	 *            a list of new added wiki, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining
	 *            a list of wiki in the mining database, which is searched for the wiki with the id submitted in the
	 *            wiki parameter
	 */
	public void setWiki(final long wiki, final Map<Long, WikiMining> wikiMining,
			final Map<Long, WikiMining> oldWikiMining) {

		if (wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addWikiLog(this);
		}
		if ((this.wiki == null) && (oldWikiMining.get(wiki) != null))
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addWikiLog(this);
		}
	}

	@Override
	public Long getPrefix() {
		return 18L;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
