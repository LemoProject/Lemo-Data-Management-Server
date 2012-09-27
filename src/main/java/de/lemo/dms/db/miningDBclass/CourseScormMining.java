package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between the courses and scorm packages.*/
public class CourseScormMining  implements IMappingClass{


//public class Course_assignment_mining implements Serializable{

//	private static final long serialVersionUID = 1L;
	private long id;	
	private CourseMining course;
	private	ScormMining scorm;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof CourseScormMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseScormMining))
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
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {	
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_scorm(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_scorm(this);
		}
	}
	/** standard getter for the attribut scorm
	 * @return the scorm which is used in the course
	 */	
	public ScormMining getScorm() {
		return scorm;
	}
	/** standard setter for the attribut scorm
	 * @param scorm the scorm which is used in the course
	 */	
	public void setScorm(ScormMining scorm) {
		this.scorm = scorm;
	}
	/** parameterized setter for the attribut assignment
	 * @param id the id of the quiz in which the action takes place
	 * @param scormMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldScormMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setScorm(long scorm, HashMap<Long, ScormMining> scormMining, HashMap<Long, ScormMining> oldScormMining) {		
		if(scormMining.get(scorm) != null)
		{
			this.scorm = scormMining.get(scorm);
			scormMining.get(scorm).addCourse_scorm(this);
		}
		if(this.scorm == null && oldScormMining.get(scorm) != null)
		{
			this.scorm = oldScormMining.get(scorm);
			oldScormMining.get(scorm).addCourse_scorm(this);
		}
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and scorm
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and scorm
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
}