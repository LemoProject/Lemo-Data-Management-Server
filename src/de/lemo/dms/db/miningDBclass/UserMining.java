package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**This class represents the table resource.*/
public class UserMining {

	private long id;
	private long lastlogin;
	private long firstaccess;
	private long lastaccess;
	private long currentlogin;
	
	private Set<CourseUserMining> course_user = new HashSet<CourseUserMining>();
	private Set<GroupUserMining> group_user = new HashSet<GroupUserMining>();
	private Set<ForumLogMining> forum_log = new HashSet<ForumLogMining>();
	private Set<WikiLogMining> wiki_log = new HashSet<WikiLogMining>();	
	private Set<CourseLogMining> course_log = new HashSet<CourseLogMining>();
	private Set<QuizLogMining> quiz_log = new HashSet<QuizLogMining>();
	private Set<ScormLogMining> scorm_log = new HashSet<ScormLogMining>();
	private Set<AssignmentLogMining> assignment_log = new HashSet<AssignmentLogMining>();
	private Set<QuestionLogMining> question_log = new HashSet<QuestionLogMining>();
	private Set<QuizUserMining> quiz_user = new HashSet<QuizUserMining>();
	private Set<ResourceLogMining> resource_log = new HashSet<ResourceLogMining>();
	private Set<ChatLogMining> chat_log = new HashSet<ChatLogMining>();
	
	
	/** standard ge tter for the attribut id
	 * @return the identifier of the user
	 */		
	public long getId() {
		return id;
	}
	
	

	/** standard setter for the attribut id
	 * @param id the identifier of the user
	 */	
	public void setId(String id,  List<IDMappingMining> id_mapping_mining, List<IDMappingMining> old_id_mapping_mining) {
		long id_n = UserMining.idForHash(id, id_mapping_mining, old_id_mapping_mining);
		/*if(id_n == -1)
		{
			id_n = largestId + 1;
			largestId = id_n;
		}*/
		this.id = id_n;
	}
	
	public static long idForHash(String hash1, List<IDMappingMining> id_mapping_mining, List<IDMappingMining> old_id_mapping_mining)
	{
		long id_n = -1;
		for(Iterator<IDMappingMining> iter = id_mapping_mining.iterator(); iter.hasNext();)
		{
			IDMappingMining loadedItem = iter.next();
			if(loadedItem.getHash() == hash1)
				id_n = loadedItem.getId();
		}		
		if(id_n == -1)
			for(Iterator<IDMappingMining> iter = old_id_mapping_mining.iterator(); iter.hasNext();)
			{
				IDMappingMining loadedItem = iter.next();
				if(loadedItem.getHash() == hash1)
					id_n = loadedItem.getId();
			}
		return id_n;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	/** standard getter for the attribut lastlogin
	 * @return the timestamp of the last time the user has logged in
	 */	
	public long getLastlogin() {
		return lastlogin;
	}
	
	/** standard setter for the attribut lastlogin
	 * @param lastlogin the timestamp of the last time the user has logged in
	 */	
	public void setLastlogin(long lastlogin) {
		this.lastlogin = lastlogin;
	}
	
	/** standard getter for the attribut firstaccess
	 * @return the timestamp when the user has made his first access to the LMS
	 */	
	public long getFirstaccess() {
		return firstaccess;
	}
	
	/** standard setter for the attribut firstaccess
	 * @param firstaccess the timestamp when the user has made his first access to the LMS
	 */	
	public void setFirstaccess(long firstaccess) {
		this.firstaccess = firstaccess;
	}
	
	/** standard getter for the attribut lastaccess
	 * @return the timestamp when the user has made the last access to the LMS
	 */	
	public long getLastaccess() {
		return lastaccess;
	}
	
	/** standard setter for the attribut lastaccess
	 * @param lastaccess the timestamp when the user has made the last access to the LMS
	 */	
	public void setLastaccess(long lastaccess) {
		this.lastaccess = lastaccess;
	}
	
	/** standard getter for the attribut currentlogin
	 * @return the timestamp when the user has logged into the current session
	 */	
	public long getCurrentlogin() {
		return currentlogin;
	}
	
	/** standard setter for the attribut currentlogin
	 * @param currentlogin the timestamp when the user has logged into the current session
	 */		
	public void setCurrentlogin(long currentlogin) {
		this.currentlogin = currentlogin;
	}
	/** standard setter for the attribut course_user
	 * @param course_user a set of entrys in the course_user table which relate the user to courses
	 */	
	public void setCourse_user(Set<CourseUserMining> course_user) {
		this.course_user = course_user;
	}
	/** standard getter for the attribut course_user
	 * @return a set of entrys in the course_user table which relate the user to courses
	 */	
	public Set<CourseUserMining> getCourse_user() {
		return course_user;
	}
	/** standard add method for the attribut course_user
	 * @param course_user_add this entry will be added to the list of user in this quiz
	 * */
	public void addCourse_user(CourseUserMining course_user_add){	
		course_user.add(course_user_add);
	}
	/** standard setter for the attribut group_user
	 * @param group_user a set of entrys in the group_user table which relate the user to groups
	 */	
	public void setGroup_user(Set<GroupUserMining> group_user) {
		this.group_user = group_user;
	}
	/** standard getter for the attribut group_user
	 * @return a set of entrys in the group_user table which relate the user to groups
	 */	
	public Set<GroupUserMining> getGroup_user() {
		return group_user;
	}
	/** standard add method for the attribut group_user
	 * @param group_user_add this entry will be added to the list of group_user in this user
	 * */
	public void addGroup_user(GroupUserMining group_user_add){	
		group_user.add(group_user_add);
	}
	/** standard setter for the attribut forum_log
	 * @param forum_log a set of entrys in the forum_log table which contain actions of this user
	 */	
	public void setForum_log(Set<ForumLogMining> forum_log) {
		this.forum_log = forum_log;
	}
	/** standard getter for the attribut forum_log
	 * @return a set of entrys in the forum_log table which contain actions of this user
	 */	
	public Set<ForumLogMining> getForum_log() {
		return forum_log;
	}
	/** standard add method for the attribut forum_log
	 * @param forum_log_add this entry will be added to the list of forum_log in this user
	 * */
	public void addForum_log(ForumLogMining forum_log_add){	
		forum_log.add(forum_log_add);
	}
	/** standard setter for the attribut wiki_log
	 * @param wiki_log a set of entrys in the wiki_log table which contain actions of this user
	 */	
	public void setWiki_log(Set<WikiLogMining> wiki_log) {
		this.wiki_log = wiki_log;
	}
	
	/** standard getter for the attribut wiki_log
	 * @return a set of entrys in the wiki_log table which contain actions of this user
	 */	
	public Set<WikiLogMining> getWiki_log() {
		return wiki_log;
	}
	/** standard add method for the attribut wiki_log
	 * @param wiki_log_add this entry will be added to the list of wiki_log in this user
	 * */
	public void addWiki_log(WikiLogMining wiki_log_add){	
		wiki_log.add(wiki_log_add);
	}
	/** standard setter for the attribut course_log
	 * @param course_log a set of entrys in the course_log table which contain actions of this user
	 */	
	public void setCourse_log(Set<CourseLogMining> course_log) {
		this.course_log = course_log;
	}
	/** standard getter for the attribut course_log
	 * @return a set of entrys in the course_log table which contain actions of this user
	 */	
	public Set<CourseLogMining> getCourse_log() {
		return course_log;
	}
	/** standard add method for the attribut course_log
	 * @param course_log_add this entry will be added to the list of course_log in this user
	 * */
	public void addCourse_log(CourseLogMining course_log_add){	
		course_log.add(course_log_add);
	}
	/** standard setter for the attribut quiz_log
	 * @param quiz_log a set of entrys in the quiz_log table which contain actions of this user
	 */	
	public void setQuiz_log(Set<QuizLogMining> quiz_log) {
		this.quiz_log = quiz_log;
	}
	/** standard getter for the attribut quiz_log
	 * @return a set of entrys in the quiz_log table which contain actions of this user
	 */	
	public Set<QuizLogMining> getQuiz_log() {
		return quiz_log;
	}
	/** standard add method for the attribut quiz_log
	 * @param quiz_log_add this entry will be added to the list of quiz_log in this user
	 * */
	public void addQuiz_log(QuizLogMining quiz_log_add){	
		quiz_log.add(quiz_log_add);
	}
	/** standard setter for the attribut question_log
	 * @param question_log a set of entrys in the question_log table which contain actions of this user
	 */	
	public void setQuestion_log(Set<QuestionLogMining> question_log) {
		this.question_log = question_log;
	}
	/** standard getter for the attribut question_log
	 * @return a set of entrys in the question_log table which contain actions of this user
	 */	
	public Set<QuestionLogMining> getQuestion_log() {
		return question_log;
	}
	/** standard add method for the attribut question_log
	 * @param question_log_add this entry will be added to the list of question_log in this quiz
	 * */
	public void addQuestion_log(QuestionLogMining question_log_add){	
		question_log.add(question_log_add);	
	}
	/** standard setter for the attribut resource_log
	 * @param resource_log a set of entrys in the resource_log table which contain actions of this user
	 */	
	public void setResource_log(Set<ResourceLogMining> resource_log) {
		this.resource_log = resource_log;
	}
	/** standard getter for the attribut resource_log
	 * @return a set of entrys in the resource_log table which contain actions of this user
	 */	
	public Set<ResourceLogMining> getResource_log() {
		return resource_log;
	}
	/** standard add method for the attribut resource_log
	 * @param resource_log_add this entry will be added to the list of resource_log in this quiz
	 * */
	public void addResource_log(ResourceLogMining resource_log_add){	
		resource_log.add(resource_log_add);	
	}
	/** standard setter for the attribut quiz_user
	 * @param quiz_user a set of entrys in the quiz_user table which relate the user to the courses
	 */	
	public void setQuiz_user(Set<QuizUserMining> quiz_user) {
		this.quiz_user = quiz_user;
	}
	/** standard getter for the attribut quiz_user
	 * @return a set of entrys in the quiz_user table which relate the user to the courses
	 */	
	public Set<QuizUserMining> getQuiz_user() {
		return quiz_user;
	}
	/** standard add method for the attribut quiz_user
	 * @param quiz_user_add this entry will be added to the list of quiz_user in this user
	 * */
	public void addQuiz_user(QuizUserMining quiz_user_add){	
		quiz_user.add(quiz_user_add);
	}
	/** standard setter for the attribut assignment_log
	 * @param assignment_log a set of entrys in the assignment_log table which contain actions of this user
	 */
	public void setAssignment_log(Set<AssignmentLogMining> assignment_log) {
		this.assignment_log = assignment_log;
	}
	/** standard getter for the attribut assignment_log
	 * @return a set of entrys in the assignment_log table which contain actions of this user
	 */		
	public Set<AssignmentLogMining> getAssignment_log() {
		return assignment_log;
	}
	/** standard add method for the attribut assignment_log
	 * @param assignment_log_add this entry will be added to the list of assignment_log in this user
	 * */
	public void addAssignment_log(AssignmentLogMining assignment_log_add){	
		assignment_log.add(assignment_log_add);
	}
	/** standard setter for the attribut scorm_log
	 * @param scorm_log a set of entrys in the scorm_log table which contain actions of this user
	 */
	public void setScorm_log(Set<ScormLogMining> scorm_log) {
		this.scorm_log = scorm_log;
	}
	/** standard getter for the attribut scorm_log
	 * @return a set of entrys in the scorm_log table which contain actions of this user
	 */		
	public Set<ScormLogMining> getScorm_log() {
		return scorm_log;
	}
	
	
	/** standard add method for the attribut scorm_log
	 * @param scorm_log_add this entry will be added to the list of scorm_log in this user
	 * */
	public void addScorm_log(ScormLogMining scorm_log_add){	
		scorm_log.add(scorm_log_add);
	}
	
	public void setChat_log(Set<ChatLogMining> chat_log) {
		this.chat_log = chat_log;
	}
	/** standard getter for the attribut scorm_log
	 * @return a set of entrys in the scorm_log table which contain actions of this user
	 */		
	public Set<ChatLogMining> getChat_log() {
		return chat_log;
	}
	
	/** standard add method for the attribut scorm_log
	 * @param chat_log_add this entry will be added to the list of scorm_log in this user
	 * */
	public void addChat_log(ChatLogMining chat_log_add){	
		chat_log.add(chat_log_add);
	}
}
