package de.lemo.dms.db.miningDBclass;

//import java.io.Serializable;
import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between the courses and assignments.*/
public class CourseAssignmentMining  implements IMappingClass{

//public class Course_assignment_mining implements Serializable{

//	private static final long serialVersionUID = 1L;
	private long id;	
	private CourseMining course;
	private	AssignmentMining assignment;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof CourseAssignmentMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseAssignmentMining))
			return true;
		return false;
	}
	
	
//	public boolean equals(Object obj) {
//		if(!(obj instanceof Course_assignment_mining)){
//			return false;			
//		}
//		Course_quiz_mining a = (Course_assignment_mining)obj;
//		if(a.assignment == this.assignment && a.course == this.course){
//			return true;
//		}		
//		return false;
//	}	

//	private Integer hashcodeValue = null;

//	public synchronized int hashCode(){
//	   if( hashcodeValue == null){
//		   if(assignment != null && course != null){
//			   hashcodeValue = 42 + Long.bitCount(this.assignment.getQid()) +
//			   Long.bitCount(this.course.getId() +
//				this.assignment.getQtype().length());
//		   }
//		   else{
//			   hashcodeValue = 42 * Long.bitCount(System.currentTimeMillis());  
//		   }
//		   return hashcodeValue;
//	   }
//	   return hashcodeValue.intValue();
//	}
	
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
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
        
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_assignment(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_assignment(this);
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
	public void setAssignment(long assignment, HashMap<Long, AssignmentMining> assignmentMining, HashMap<Long, AssignmentMining> oldAssignmentMining) {		
       
		if(assignmentMining.get(assignment) != null)
		{
			this.assignment = assignmentMining.get(assignment);
			assignmentMining.get(assignment).addCourse_assignment(this);
		}
		if(this.assignment == null && oldAssignmentMining.get(assignment) != null)
		{
			this.assignment = oldAssignmentMining.get(assignment);
			oldAssignmentMining.get(assignment).addCourse_assignment(this);
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
}