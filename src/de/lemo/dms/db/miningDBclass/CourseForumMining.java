package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

/**This class represents the relationship between courses and forums.*/
public class CourseForumMining {

	private long id;
	private CourseMining course;
	private	ForumMining forum;

	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between course and forum
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between course and forum
	 */	
	public void setId(long id) {
		this.id = id;
	}
	
	/** standard getter for the attribut course
	 * @return a course in which the forum is used
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course a course in which the forum is used
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}	
	/** parameterized setter for the attribut course
	 * @param course the id of a course in which the forum is used
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter	 
	 */		
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourse_forum(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourse_forum(this);
		}
	}	
	
	/** standard getter for the attribut forum
	 * @return the forum which is used in the course
	 */		
	public ForumMining getForum() {
		return forum;
	}
	/** standard setter for the attribut forum
	 * @param forum the forum which is used in the course
	 */		
	public void setForum(ForumMining forum) {
		this.forum = forum;
	}	
	/** parameterized setter for the attribut forum
	 * @param forum the id of the forum which is used in the course
	 * @param forumMining a list of new added forums, which is searched for the forum with the id submitted in the forum parameter
	 * @param oldForumMining a list of forums in the miningdatabase, which is searched for the forum with the id submitted in the forum parameter	 
	 */		
	public void setForum(long forum, HashMap<Long, ForumMining> forumMining, HashMap<Long, ForumMining> oldForumMining) {
		if(forumMining.get(forum) != null)
		{
			this.forum = forumMining.get(forum);
			forumMining.get(forum).addCourse_forum(this);
		}
		if(this.forum == null && oldForumMining.get(forum) != null)
		{
			this.forum = oldForumMining.get(forum);
			oldForumMining.get(forum).addCourse_forum(this);
		}
	}	
}
