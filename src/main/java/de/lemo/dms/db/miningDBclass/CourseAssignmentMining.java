package de.lemo.dms.db.miningDBclass;

//import java.io.Serializable;
import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseRatedObjectAssociation;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;
import de.lemo.dms.db.miningDBclass.abstractions.IRatedObject;

/**This class represents the relationship between the courses and assignments.*/
public class CourseAssignmentMining  implements IMappingClass, ICourseRatedObjectAssociation{

//public class Course_assignment_mining implements Serializable{

//	private static final long serialVersionUID = 1L;
	private long id;	
	private CourseMining course;
	private	AssignmentMining assignment;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof CourseAssignmentMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseAssignmentMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut course
	 * @return a course in which the quiz is used
	 */		
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course a course in which the quiz is used
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of a course in which the quiz is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
        
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseAssignment(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseAssignment(this);
		}
	}
	
	/** standard getter for the attribut assignment
	 * @return the quiz which is used in the course
	 */	
	public AssignmentMining getAssignment() {
		return assignment;
	}
	/** standard setter for the attribut assignment
	 * @param assignment the assignment which is used in the course
	 */	
	public void setAssignment(AssignmentMining assignment) {
		this.assignment = assignment;
	}
	/** parameterized setter for the attribut assignment
	 * @param id the id of the quiz in which the action takes place
	 * @param assignmentMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldAssignmentMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setAssignment(long assignment, Map<Long, AssignmentMining> assignmentMining, Map<Long, AssignmentMining> oldAssignmentMining) {		
       
		if(assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addCourseAssignment(this);
		}
		if(this.assignment == null && oldAssignmentMining.get(assignment) != null)
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addCourseAssignment(this);
		}
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and assignment
	 */		
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and assignment
	 */	
	public long getId() {
		return id;
	}


	public Long getPlatform() {
		return platform;
	}


	public void setPlatform(Long platform) {
		this.platform = platform;
	}


	@Override
	public IRatedObject getRatedObject() {
		// TODO Auto-generated method stub
		return this.assignment;
	}
}