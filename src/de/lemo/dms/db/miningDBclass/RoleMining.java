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
	private long sortorder;

	private Set<CourseUserMining> course_user = new HashSet<CourseUserMining>();	

	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof RoleMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof RoleMining))
			return true;
		return false;
	}
	
	/** standard setter for the attribut id
	 * @param id the identifier of the role
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier of the role
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut name
	 * @param name the full name of the role
	 */	
	public void setName(String name) {
		this.name = name;
	}
	/** standard getter for the attribut name
	 * @return the full name of the role
	 */	
	public String getName() {
		return name;
	}
	/** standard setter for the attribut shortname
	 * @param shortname the shortname of the role
	 */	
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/** standard getter for the attribut shortname
	 * @return the shortname of the role
	 */	
	public String getShortname() {
		return shortname;
	}
	/** standard setter for the attribut description
	 * @param description a description of the role
	 */	
	public void setDescription(String description) {
		this.description = description;
	}
	/** standard getter for the attribut description
	 * @return a description of the role
	 */	
	public String getDescription() {
		return description;
	}
	/** standard setter for the attribut sortorder
	 * @param sortorder the sortorder for the roles
	 */	
	public void setSortorder(long sortorder) {
		this.sortorder = sortorder;
	}
	/** standard getter for the attribut sortorder
	 * @return the sortorder for the roles
	 */	
	public long getSortorder() {
		return sortorder;
	}
	/** standard setter for the attribut course_user
	 * @param course_user a set of entrys in the course_user table which relate this role with users
	 */	
	public void setCourse_user(Set<CourseUserMining> course_user) {
		this.course_user = course_user;
	}
	/** standard getter for the attribut course_user
	 * @return a set of entrys in the course_user table which relate this role with users
	 */	
	public Set<CourseUserMining> getCourse_user() {
		return course_user;
	}
	/** standard add method for the attribut course_user
	 * @param course_user_add this entry will be added to the list of course_user in this role
	 * */
	public void addCourse_user(CourseUserMining course_user_add){	
		course_user.add(course_user_add);	
	}
}
