/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseAssignmentMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;
import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/** 
 * This class represents the relationship between the courses and assignments. 
 * @author Sebastian Schwarzrock
 */
public class CourseAssignmentMining implements ICourseLORelation, IMappingClass, ICourseRatedObjectAssociation {

	private long id;
	private CourseMining course;
	private AssignmentMining assignment;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseAssignmentMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseAssignmentMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribut course
	 * 
	 * @return a course in which the quiz is used
	 */
	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	/**
	 * standard setter for the attribut course
	 * 
	 * @param course
	 *            a course in which the quiz is used
	 */
	public void setCourse(final CourseMining course) {
		this.course = course;
	}

	/**
	 * parameterized setter for the attribut course
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
			courseMining.get(course).addCourseAssignment(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseAssignment(this);
		}
	}

	/**
	 * standard getter for the attribut assignment
	 * 
	 * @return the quiz which is used in the course
	 */
	public AssignmentMining getAssignment() {
		return this.assignment;
	}

	/**
	 * standard setter for the attribut assignment
	 * 
	 * @param assignment
	 *            the assignment which is used in the course
	 */
	public void setAssignment(final AssignmentMining assignment) {
		this.assignment = assignment;
	}

	/**
	 * parameterized setter for the attribut assignment
	 * 
	 * @param id
	 *            the id of the quiz in which the action takes place
	 * @param assignmentMining
	 *            a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other
	 *            parameters
	 * @param oldAssignmentMining
	 *            a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted
	 *            in the other parameters
	 */
	public void setAssignment(final long assignment, final Map<Long, AssignmentMining> assignmentMining,
			final Map<Long, AssignmentMining> oldAssignmentMining) {

		if (assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addCourseAssignment(this);
		}
		if ((this.assignment == null) && (oldAssignmentMining.get(assignment) != null))
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addCourseAssignment(this);
		}
	}

	/**
	 * standard setter for the attribut id
	 * 
	 * @param id
	 *            the identifier for the assoziation between course and assignment
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribut id
	 * 
	 * @return the identifier for the assoziation between course and assignment
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
		return this.assignment;
	}

	@Override
	public Long getPrefix() {
		return this.assignment.getPrefix();
	}

	@Override
	public ILearningObject getLearningObject() {
		return this.getAssignment();
	}


}