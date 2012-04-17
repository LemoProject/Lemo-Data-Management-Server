package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**This class represents the relationship between the courses and groups.*/
public class CourseGroupMining {

	private long id;
	private CourseMining course;
	private	GroupMining group;

	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and group
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and group
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut course
	 * @return a course in which the group is used
	 */
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course a course in which the group is used
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}	
	/** parameterized setter for the attribut course
	 * @param course the id of a course in which the group is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_group(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_group(this);
		}
	}
	/** parameterized setter for the attribut group
	 * @param group a group which is used in the course
	 * @param groupMining a list of new added groups, which is searched for the group with the id submitted in the group parameter
	 * @param oldGroupMining a list of groups in the miningdatabase, which is searched for the group with the id submitted in the group parameter
	 */	
	public void setGroup(long group, HashMap<Long, GroupMining> groupMining, HashMap<Long, GroupMining> oldGroupMining) {
		if(groupMining.get(group) != null)
		{
			this.group = groupMining.get(group);
			groupMining.get(group).addCourse_group(this);
		}
		if(this.group == null && oldGroupMining.get(group) != null)
		{
			this.group = oldGroupMining.get(group);
			oldGroupMining.get(group).addCourse_group(this);
		}
	}
	/** standard setter for the attribut group
	 * @param group a group which is used in the course
	 */	
	public void setGroup(GroupMining group) {
	   this.group = group;
	}
	/** standard getter for the attribut group
	 * @return a group which is used in the course
	 */	
	public GroupMining getGroup() {
		return group;
	}

}
