/**
 * File ./src/main/java/de/lemo/dms/db/mapping/CourseScormMining.java
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
 * File ./main/java/de/lemo/dms/db/mapping/CourseScormMining.java
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
import de.lemo.dms.db.mapping.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.mapping.abstractions.ILearningObject;
import de.lemo.dms.db.mapping.abstractions.IMappingClass;
import de.lemo.dms.db.mapping.abstractions.IRatedObject;

/** This class represents the relationship between the courses and scorm packages. */
@Entity
@Table(name = "course_scorm")
public class CourseScormMining implements IMappingClass, ICourseLORelation, ICourseRatedObjectAssociation {

	private long id;
	private CourseMining course;
	private ScormMining scorm;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseScormMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseScormMining)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	/**
	 * standard getter for the attribute course
	 * 
	 * @return a course in which the quiz is used
	 */
	@Override
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribute course
	 * 
	 * @param course
	 *            a course in which the quiz is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the quiz is used
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
			courseMining.get(course).addCourseScorm(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseScorm(this);
		}
	}

	/**
	 * standard getter for the attribute scorm
	 * 
	 * @return the scorm which is used in the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="scorm_id")
	public ScormMining getScorm() {
		return this.scorm;
	}

	/**
	 * standard setter for the attribute scorm
	 * 
	 * @param scorm
	 *            the scorm which is used in the course
	 */
	public void setScorm(final ScormMining scorm) {
		this.scorm = scorm;
	}

	/**
	 * parameterized setter for the attribute assignment
	 * 
	 * @param id
	 *            the id of the quiz in which the action takes place
	 * @param scormMining
	 *            a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other
	 *            parameters
	 * @param oldScormMining
	 *            a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted
	 *            in the other parameters
	 */
	public void setScorm(final long scorm, final Map<Long, ScormMining> scormMining,
			final Map<Long, ScormMining> oldScormMining) {
		if (scormMining.get(scorm) != null)
		{
			this.scorm = scormMining.get(scorm);
			scormMining.get(scorm).addCourseScorm(this);
		}
		if ((this.scorm == null) && (oldScormMining.get(scorm) != null))
		{
			this.scorm = oldScormMining.get(scorm);
			oldScormMining.get(scorm).addCourseScorm(this);
		}
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between course and scorm
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between course and scorm
	 */
	@Override
	@Id
	public long getId() {
		return this.id;
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
	public IRatedObject getRatedObject() {
		return this.scorm;
	}

	@Override
	@Transient
	public Long getPrefix() {
		return this.scorm.getPrefix();
	}

	@Override
	@Transient
	public ILearningObject getLearningObject() {
		return this.scorm;
	}
}