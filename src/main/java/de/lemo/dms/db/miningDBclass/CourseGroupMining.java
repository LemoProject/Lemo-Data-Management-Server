package de.lemo.dms.db.miningDBclass;


import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the relationship between the courses and groups.*/
public class CourseGroupMining  implements IMappingClass{

	private long id;
	private CourseMining course;
	private	GroupMining group;
	private Long platform;

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof CourseGroupMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof CourseGroupMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier for the association between course and group
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier for the association between course and group
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute course
	 * @return a course in which the group is used
	 */
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribute course
	 * @param course a course in which the group is used
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}	
	/** parameterized setter for the attribute course
	 * @param course the id of a course in which the group is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseGroup(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseGroup(this);
		}
	}
	/** parameterized setter for the attribute group
	 * @param group a group which is used in the course
	 * @param groupMining a list of new added groups, which is searched for the group with the id submitted in the group parameter
	 * @param oldGroupMining a list of groups in the miningdatabase, which is searched for the group with the id submitted in the group parameter
	 */	
	public void setGroup(long group, Map<Long, GroupMining> groupMining, Map<Long, GroupMining> oldGroupMining) {
		if(groupMining.get(group) != null)
		{
			this.group = groupMining.get(group);
			groupMining.get(group).addCourseGroup(this);
		}
		if(this.group == null && oldGroupMining.get(group) != null)
		{
			this.group = oldGroupMining.get(group);
			oldGroupMining.get(group).addCourseGroup(this);
		}
	}
	/** standard setter for the attribute group
	 * @param group a group which is used in the course
	 */	
	public void setGroup(GroupMining group) {
	   this.group = group;
	}
	/** standard getter for the attribute group
	 * @return a group which is used in the course
	 */	
	public GroupMining getGroup() {
		return group;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

}
