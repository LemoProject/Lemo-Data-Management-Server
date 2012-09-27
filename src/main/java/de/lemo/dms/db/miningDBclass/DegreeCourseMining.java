package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class DegreeCourseMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private	DegreeMining degree;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof DegreeCourseMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof DegreeCourseMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between department and resource
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between department and resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut 
	 * @return a department in which the resource is used
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut department
	 * @param course a department in which the resource is used
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut 
	 * @param course the id of a course in which the resource is used
	 * @param courseMining a list of new added departments, which is searched for the department with the id submitted in the department parameter
	 * @param oldCourseMining a list of department in the miningdatabase, which is searched for the department with the id submitted in the department parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addDegree_course(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addDegree_course(this);
		}
	}
	/** standard getter for the attribute degree
	 * @return the degree which is used in the department
	 */	
	public DegreeMining getDegree() {
		return degree;
	}
	/** standard setter for the attribute degree
	 * @param degree the degree which is used in the department
	 */	
	public void setDegree(DegreeMining degree) {
		this.degree = degree;
	}
	/** parameterized setter for the attribute degree
	 * @param degree the id of the degree which is used in the department
	 * @param degreeMining a list of new added degree, which is searched for the degree with the id submitted in the degree parameter
	 * @param oldDegreeMining a list of degree in the miningdatabase, which is searched for the degree with the id submitted in the degree parameter
	 */	
	public void setDegree(long degree, HashMap<Long, DegreeMining> degreeMining, HashMap<Long, DegreeMining> oldDegreeMining) {		
		
		if(degreeMining.get(degree) != null)
		{
			this.degree = degreeMining.get(degree);
			degreeMining.get(degree).addDegree_course(this);
		}
		if(this.degree == null && oldDegreeMining.get(degree) != null)
		{
			this.degree = oldDegreeMining.get(degree);
			oldDegreeMining.get(degree).addDegree_course(this);
		}
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
	
}
