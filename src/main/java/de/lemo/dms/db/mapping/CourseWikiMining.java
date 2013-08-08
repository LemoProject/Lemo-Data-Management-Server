/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CourseWikiMining.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/db/mapping/CourseWikiMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

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

import de.lemo.dms.db.mapping.abstractions.ICourseLORelation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;

/**
 * This class represents the relationship between courses and wikis.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "course_wiki")
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
	@Id
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="wiki_id")
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

	@Column(name="platform")
	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	@Transient
	public ILearningObject getLearningObject() {
		return this.wiki;
	}
}
