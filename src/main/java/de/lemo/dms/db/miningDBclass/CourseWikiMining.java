/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseWikiMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * This class represents the relationship between courses and wikis.
 * @author Sebastian Schwarzrock
 */
public class CourseWikiMining implements IMappingClass, ICourseLORelation{

	private long id;
	private CourseMining course;
	private WikiMining wiki;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseWikiMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseWikiMining)) {
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
	 * @return the identifier for the association between course and wiki
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and wiki
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the wiki is used in
	 */
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the wiki is used in
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of courses in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseWiki(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseWiki(this);
		}
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the wiki is used in
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * standard getter for the attribute wiki
	 * 
	 * @return the wiki which is used in the course in this entry
	 */
	public WikiMining getWiki() {
		return this.wiki;
	}

	/**
	 * standard setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the wiki which is used in the course in this entry
	 */
	public void setWiki(final WikiMining wiki) {
		this.wiki = wiki;
	}

	/**
	 * parameterized setter for the attribute wiki
	 * 
	 * @param wiki
	 *            the id of the wiki which is used in the course in this entry
	 * @param wikiMining
	 *            a list of new added wikis, which is searched for the wiki with the id submitted in the wiki parameter
	 * @param oldWikiMining
	 *            a list of wikis in the miningdatabase, which is searched for the wiki with the id submitted in the
	 *            wiki parameter
	 */
	public void setWiki(final long wiki, final Map<Long, WikiMining> wikiMining,
			final Map<Long, WikiMining> oldWikiMining) {

		if (wikiMining.get(wiki) != null)
		{
			this.wiki = wikiMining.get(wiki);
			wikiMining.get(wiki).addCourseWiki(this);
		}
		if ((this.wiki == null) && (oldWikiMining.get(wiki) != null))
		{
			this.wiki = oldWikiMining.get(wiki);
			oldWikiMining.get(wiki).addCourseWiki(this);
		}
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	public ILearningObject getLearningObject() {
		return this.wiki;
	}
}
