package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

/**This class represents the relationship between courses and user.*/
public class CourseUserMining {

	private long id;
	private CourseMining course;
	private	UserMining user;
	private RoleMining role;
	private long enrolstart;
	private long enrolend;

	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and user
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and user
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard setter for the attribut course
	 * @param course the course in which the user is enroled
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** standard getter for the attribut course
	 * @return the course in which the user is enroled
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of the course in which the user is enroled
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining){		
//       	System.out.println("course id: " + course);	
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_user(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_user(this);
		}
	}
	/** standard getter for the attribut enrolstart
	 * @return the timestamp when the user was enroled in the course
	 */
	public long getEnrolstart() {
		return enrolstart;
	}
	/** standard setter for the attribut enrolstart
	 * @param enrolstart the timestamp when the user was enroled in the course
	 */	
	public void setEnrolstart(long enrolstart) {
		this.enrolstart = enrolstart;
	}
	/** standard getter for the attribut enrolend
	 * @return the timestamp when the user is not enroled any more
	 */
	public long getEnrolend() {
		return enrolend;
	}
	/** standard setter for the attribut enrolend
	 * @param enrolend the timestamp when the user is not enroled any more
	 */	
	public void setEnrolend(long enrolend) {
		this.enrolend = enrolend;
	}

	/** standard getter for the attribut user
	 * @return the user who is enroled in the course
	 */
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who is enroled in the course
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut user
	 * @param user the id of the user who is enroled in the course
	 * @param userMining a list of new added users, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of users in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */		
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {				
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addCourse_user(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addCourse_user(this);
		}        
	}
	/** standard setter for the attribut role
	 * @param role the role which the user has in the course
	 */	
	public void setRole(RoleMining role) {
		this.role = role;
	}
	/** standard getter for the attribut role
	 * @return the role which the user has in the course
	 */
	public RoleMining getRole() {
		return role;
	}
	/** parameterized setter for the attribut role
	 * @param role the id of the role which the user has in the course
	 * @param roleMining a list of new added roles, which is searched for the role with the id submitted in the role parameter
	 * @param oldRoleMining a list of roles in the miningdatabase, which is searched for the role with the id submitted in the role parameter
	 */	
	public void setRole(long role, HashMap<Long, RoleMining> roleMining, HashMap<Long, RoleMining> oldRoleMining) {		
		if(roleMining.get(role) != null)
		{
			this.role = roleMining.get(role);
			roleMining.get(role).addCourse_user(this);
		}
		if(this.role == null && oldRoleMining.get(role) != null)
		{
			this.role = oldRoleMining.get(role);
			oldRoleMining.get(role).addCourse_user(this);
		}
	}
}
