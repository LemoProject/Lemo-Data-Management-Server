package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the forum table.*/
public class ForumMining implements IMappingClass {

	private long id;
	private String title;
	private String summary;
	private long timecreated;
	private long timemodified;
	
	private Set<CourseForumMining> course_forum = new HashSet<CourseForumMining>();
	private Set<ForumLogMining> forum_log = new HashSet<ForumLogMining>();

	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof ForumMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ForumMining))
			return true;
		return false;
	}

	/** standard getter for the attribut id
	 * @return the identifier of the forum
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the forum
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut title
	 * @return the title of the forum
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut title
	 * @param title the title of the forum
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut summary
	 * @return a summary of the topic in the forum
	 */	
	public String getSummary() {
		return summary;
	}
	/** standard setter for the attribut summary
	 * @param summary a summary of the topic in the forum
	 */	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the forum was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the forum was created
	 */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
//	public void setTimecreated(Long timecreated) {
//		if(timecreated != null){
//			this.timecreated = timecreated.longValue();
//		}
//	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the forum was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the forum was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut course_forum
	 * @param course_forum a set of entrys in the course_forum table which relate the forum to the courses
	 */	
	public void setCourse_forum(Set<CourseForumMining> course_forum) {
		this.course_forum = course_forum;
	}
	/** standard getter for the attribut course_forum
	 * @return a set of entrys in the course_forum table which relate the forum to the courses
	 */	
	public Set<CourseForumMining> getCourse_forum() {
		return course_forum;
	}
	/** standard add method for the attribut course_forum
	 * @param course_forum_add this entry will be added to the list of course_forum in this resource
	 * */	
	public void addCourse_forum(CourseForumMining course_forum_add){	
		course_forum.add(course_forum_add);	
	}
	/** standard setter for the attribut forum_log
	 * @param forum_log a set of entrys in the forum_log table which are related to this forum
	 */	
	public void setForum_log(Set<ForumLogMining> forum_log) {
		this.forum_log = forum_log;
	}
	/** standard getter for the attribut forum_log
	 * @return a set of entrys in the forum_log table which are related to this forum
	 */	
	public Set<ForumLogMining> getForum_log() {
		return forum_log;
	}
	/** standard add method for the attribut
	 * @param forum_log_add this entry will be added to the list of forum_log in this forum
	 * */
	public void addForum_log(ForumLogMining forum_log_add){	
		forum_log.add(forum_log_add);	
	}
}
