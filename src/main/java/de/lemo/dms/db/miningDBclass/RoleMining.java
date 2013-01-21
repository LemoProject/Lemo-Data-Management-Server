package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table quiz.*/
public class RoleMining implements IMappingClass {
	private long id;
	private String name;
	private String shortname;
	private String description;//text
	private long sortOrder;
	private Long platform;

	private Set<CourseUserMining> courseUsers = new HashSet<CourseUserMining>();	

	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof RoleMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof RoleMining))
			return true;
		return false;
	}
	
	/** standard setter for the attribute id
	 * @param id the identifier of the role
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the role
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute name
	 * @param name the full name of the role
	 */	
	public void setName(String name) {
		this.name = name;
	}
	/** standard getter for the attribute name
	 * @return the full name of the role
	 */	
	public String getName() {
		return name;
	}
	/** standard setter for the attribute shortname
	 * @param shortname the shortname of the role
	 */	
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/** standard getter for the attribute shortname
	 * @return the shortname of the role
	 */	
	public String getShortname() {
		return shortname;
	}
	/** standard setter for the attribute description
	 * @param description a description of the role
	 */	
	public void setDescription(String description) {
		this.description = description;
	}
	/** standard getter for the attribute description
	 * @return a description of the role
	 */	
	public String getDescription() {
		return description;
	}
	/** standard setter for the attribute sortorder
	 * @param sortorder the sortorder for the roles
	 */	
	public void setSortorder(long sortorder) {
		this.sortOrder = sortorder;
	}
	/** standard getter for the attribute sortorder
	 * @return the sortorder for the roles
	 */	
	public long getSortorder() {
		return sortOrder;
	}
	/** standard setter for the attribute course_user
	 * @param courseUsers a set of entries in the course_user table which relate this role with users
	 */	
	public void setCourseUsers(Set<CourseUserMining> courseUsers) {
		this.courseUsers = courseUsers;
	}
	/** standard getter for the attribute course_user
	 * @return a set of entries in the course_user table which relate this role with users
	 */	
	public Set<CourseUserMining> getCourseUsers() {
		return courseUsers;
	}
	/** standard add method for the attribute course_user
	 * @param courseUser this entry will be added to the list of course_user in this role
	 * */
	public void addCourseUser(CourseUserMining courseUser){	
		this.courseUsers.add(courseUser);	
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
