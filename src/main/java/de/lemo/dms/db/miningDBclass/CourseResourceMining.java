package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between courses and resources.*/
public class CourseResourceMining implements IMappingClass {

	private long id;
	private CourseMining course;
	private	ResourceMining resource;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof CourseResourceMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseResourceMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and resource
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and resource
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut 
	 * @return a course in which the resource is used
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course a course in which the resource is used
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut 
	 * @param course the id of a course in which the resource is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {	
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_resource(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_resource(this);
		}
	}
	/** standard getter for the attribut resource
	 * @return the resource which is used in the course
	 */	
	public ResourceMining getResource() {
		return resource;
	}
	/** standard setter for the attribut resource
	 * @param resource the resource which is used in the course
	 */	
	public void setResource(ResourceMining resource) {
		this.resource = resource;
	}
	/** parameterized setter for the attribut resource
	 * @param resource the id of the resource which is used in the course
	 * @param resourceMining a list of new added resources, which is searched for the resource with the id submitted in the resource parameter
	 * @param oldResourceMining a list of resource in the miningdatabase, which is searched for the resource with the id submitted in the resource parameter
	 */	
	public void setResource(long resource, HashMap<Long, ResourceMining> resourceMining, HashMap<Long, ResourceMining> oldResourceMining) {		
        if(resourceMining.get(resource) != null)
		{
			this.resource = resourceMining.get(resource);
			resourceMining.get(resource).addCourse_resource(this);
		}
        if(this.resource == null && oldResourceMining.get(resource) != null)
		{
			this.resource = oldResourceMining.get(resource);
			oldResourceMining.get(resource).addCourse_resource(this);
		}		
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
