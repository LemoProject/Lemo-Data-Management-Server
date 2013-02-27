/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseScormMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/** This class represents the relationship between the courses and scorm packages. */
public class CourseScormMining implements IMappingClass, ICourseRatedObjectAssociation {

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
	public long getId() {
		return this.id;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

	@Override
	public IRatedObject getRatedObject() {
		return this.scorm;
	}

	@Override
	public Long getPrefix() {
		return this.scorm.getPrefix();
	}
}