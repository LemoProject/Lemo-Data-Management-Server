package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the forum table.*/
public class ForumMining implements IMappingClass, ILearningObject {

	private long id;
	private String title;
	private String summary;
	private long timeCreated;
	private long timeModified;
	private Long platform;
	
	private Set<CourseForumMining> courseForums = new HashSet<CourseForumMining>();
	private Set<ForumLogMining> forumLogs = new HashSet<ForumLogMining>();

	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof ForumMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof ForumMining))
			return true;
		return false;
	}

	/** standard getter for the attribute id
	 * @return the identifier of the forum
	 */	
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier of the forum
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute title
	 * @return the title of the forum
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribute title
	 * @param title the title of the forum
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribute summary
	 * @return a summary of the topic in the forum
	 */	
	public String getSummary() {
		return summary;
	}
	/** standard setter for the attribute summary
	 * @param summary a summary of the topic in the forum
	 */	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/** standard getter for the attribute timecreated
	 * @return the timestamp when the forum was created
	 */	
	public long getTimeCreated() {
		return timeCreated;
	}
	/** standard setter for the attribute timecreated
	 * @param timecreated the timestamp when the forum was created
	 */	
	public void setTimeCreated(long timecreated) {
		this.timeCreated = timecreated;
	}

	/** standard getter for the attribute timemodified
	 * @return the timestamp when the forum was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	/** standard setter for the attribute timemodified
	 * @param timeModified the timestamp when the forum was changed the last time
	 */	
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	/** standard setter for the attribute course_forum
	 * @param courseForums a set of entries in the course_forum table which relate the forum to the courses
	 */	
	public void setCourseForums(Set<CourseForumMining> courseForums) {
		this.courseForums = courseForums;
	}
	/** standard getter for the attribute course_forum
	 * @return a set of entries in the course_forum table which relate the forum to the courses
	 */	
	public Set<CourseForumMining> getCourseForums() {
		return courseForums;
	}
	/** standard add method for the attribute course_forum
	 * @param courseForum this entry will be added to the list of course_forum in this resource
	 * */	
	public void addCourseForum(CourseForumMining courseForum){	
		this.courseForums.add(courseForum);	
	}
	/** standard setter for the attribute forum_log
	 * @param forumLogs a set of entries in the forum_log table which are related to this forum
	 */	
	public void setForumLogs(Set<ForumLogMining> forumLogs) {
		this.forumLogs = forumLogs;
	}
	/** standard getter for the attribute forum_log
	 * @return a set of entries in the forum_log table which are related to this forum
	 */	
	public Set<ForumLogMining> getForumLogs() {
		return forumLogs;
	}
	/** standard add method for the attribute
	 * @param forumLog this entry will be added to the list of forum_log in this forum
	 * */
	public void addForumLog(ForumLogMining forumLog){	
		this.forumLogs.add(forumLog);	
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
