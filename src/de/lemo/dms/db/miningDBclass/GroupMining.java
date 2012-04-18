package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

/**This class represents the table group.*/
public class GroupMining {

	private long id;
	private long timecreated;
	private long timemodified;

	private Set<CourseGroupMining> course_group = new HashSet<CourseGroupMining>();
	private Set<GroupUserMining> group_user = new HashSet<GroupUserMining>();

	/** standard getter for the attribut id
	 * @return the identifier of the group
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the group
	 */
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the group was created
	 */
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the group was created
	 */
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the group was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the group was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut course_groups
	 * @param course_group a set of entrys in the course_group table which relate the group to the courses
	 */	
	public void setCourse_group(Set<CourseGroupMining> course_group) {
		this.course_group = course_group;
	}
	/** standard getter for the attribut course_groups
	 * @return a set of entrys in the course_group table which relate the group to the courses
	 */	
	public Set<CourseGroupMining> getCourse_group() {
		return course_group;
	}
	/** standard add method for the attribut course_group
	 * @param course_group_add this entry will be added to the list of course_group in this group
	 * */		
	public void addCourse_group(CourseGroupMining course_group_add){	
		course_group.add(course_group_add);
	}
	/** standard setter for the attribut group_enrol
	 * @param group_user a set of entrys in the group_user table which relate the group to the users
	 */	
	public void setGroup_user(Set<GroupUserMining> group_user) {
		this.group_user = group_user;
	}
	/** standard getter for the attribut group_user
	 * @return a set of entrys in the group_user table which relate the group to the users
	 */	
	public Set<GroupUserMining> getGroup_user() {
		return group_user;
	}
	/** standard add method for the attribut group_user
	 * @param group_user_add this entry will be added to the list of group_user in this group
	 * */	
	public void addGroup_user(GroupUserMining group_user_add){	
		group_user.add(group_user_add);
	}
}
