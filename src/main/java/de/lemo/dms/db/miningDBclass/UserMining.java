package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table resource.*/
public class UserMining  implements IMappingClass{

	private long id;
	private String login;
	private Boolean gender;
	private long lastLogin;
	private long firstAccess;
	private long lastAccess;
	private long currentLogin;
	private Long platform;
	
	private Set<CourseUserMining> courseUsers = new HashSet<CourseUserMining>();
	private Set<GroupUserMining> groupUsers = new HashSet<GroupUserMining>();
	private Set<ForumLogMining> forumLogs = new HashSet<ForumLogMining>();
	private Set<WikiLogMining> wikiLogs = new HashSet<WikiLogMining>();	
	private Set<CourseLogMining> courseLogs = new HashSet<CourseLogMining>();
	private Set<QuizLogMining> quizLogs = new HashSet<QuizLogMining>();
	private Set<ScormLogMining> scormLogs = new HashSet<ScormLogMining>();
	private Set<AssignmentLogMining> assignmentLogs = new HashSet<AssignmentLogMining>();
	private Set<QuestionLogMining> questionLogs = new HashSet<QuestionLogMining>();
	private Set<QuizUserMining> quizUsers = new HashSet<QuizUserMining>();
	private Set<ResourceLogMining> resourceLogs = new HashSet<ResourceLogMining>();
	private Set<ChatLogMining> chatLogs = new HashSet<ChatLogMining>();
	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof UserMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof UserMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier of the user
	 */		
	public long getId() {
		return id;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getGender() {
		return gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	/** standard setter for the attribute id
	 * @param id the identifier of the user
	 */	
	public void setId(String id,  List<IDMappingMining> idMappingMining, List<IDMappingMining> oldIdMappingMining) {
		long id_n = UserMining.idForHash(id, idMappingMining, oldIdMappingMining);
		/*if(id_n == -1)
		{
			id_n = largestId + 1;
			largestId = id_n;
		}*/
		this.id = id_n;
	}
	
	public static long idForHash(String hash1, List<IDMappingMining> idMappingMining, List<IDMappingMining> oldIdMappingMining)
	{
		long id_n = -1;
		for(Iterator<IDMappingMining> iter = idMappingMining.iterator(); iter.hasNext();)
		{
			IDMappingMining loadedItem = iter.next();
			if(loadedItem.getHash() == hash1)
				id_n = loadedItem.getId();
		}		
		if(id_n == -1)
			for(Iterator<IDMappingMining> iter = oldIdMappingMining.iterator(); iter.hasNext();)
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
	
	/** standard getter for the attribute lastlogin
	 * @return the timestamp of the last time the user has logged in
	 */	
	public long getLastlogin() {
		return lastLogin;
	}
	
	/** standard setter for the attribute lastlogin
	 * @param lastlogin the timestamp of the last time the user has logged in
	 */	
	public void setLastlogin(long lastlogin) {
		this.lastLogin = lastlogin;
	}
	
	/** standard getter for the attribute firstaccess
	 * @return the timestamp when the user has made his first access to the LMS
	 */	
	public long getFirstAccess() {
		return firstAccess;
	}
	
	/** standard setter for the attribute firstaccess
	 * @param firstAccess the timestamp when the user has made his first access to the LMS
	 */	
	public void setFirstAccess(long firstAccess) {
		this.firstAccess = firstAccess;
	}
	
	/** standard getter for the attribute lastaccess
	 * @return the timestamp when the user has made the last access to the LMS
	 */	
	public long getLastAccess() {
		return lastAccess;
	}
	
	/** standard setter for the attribute lastaccess
	 * @param lastAccess the timestamp when the user has made the last access to the LMS
	 */	
	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	/** standard getter for the attribute currentlogin
	 * @return the timestamp when the user has logged into the current session
	 */	
	public long getCurrentLogin() {
		return currentLogin;
	}
	
	/** standard setter for the attribute currentlogin
	 * @param currentLogin the timestamp when the user has logged into the current session
	 */		
	public void setCurrentLogin(long currentLogin) {
		this.currentLogin = currentLogin;
	}
	/** standard setter for the attribute course_user
	 * @param courseUsers a set of entries in the course_user table which relate the user to courses
	 */	
	public void setCourseUsers(Set<CourseUserMining> courseUsers) {
		this.courseUsers = courseUsers;
	}
	/** standard getter for the attribute course_user
	 * @return a set of entries in the course_user table which relate the user to courses
	 */	
	public Set<CourseUserMining> getCourseUsers() {
		return courseUsers;
	}
	/** standard add method for the attribute course_user
	 * @param courseUser this entry will be added to the list of user in this quiz
	 * */
	public void addCourseUser(CourseUserMining courseUser){	
		courseUsers.add(courseUser);
	}
	/** standard setter for the attribute group_user
	 * @param groupUsers a set of entries in the group_user table which relate the user to groups
	 */	
	public void setGroupUsers(Set<GroupUserMining> groupUsers) {
		this.groupUsers = groupUsers;
	}
	/** standard getter for the attribute group_user
	 * @return a set of entries in the group_user table which relate the user to groups
	 */	
	public Set<GroupUserMining> getGroupUsers() {
		return groupUsers;
	}
	/** standard add method for the attribute group_user
	 * @param groupUser this entry will be added to the list of group_user in this user
	 * */
	public void addGroupUser(GroupUserMining groupUser){	
		this.groupUsers.add(groupUser);
	}
	/** standard setter for the attribute forum_log
	 * @param forumLogs a set of entries in the forum_log table which contain actions of this user
	 */	
	public void setForumLogs(Set<ForumLogMining> forumLogs) {
		this.forumLogs = forumLogs;
	}
	/** standard getter for the attribute forum_log
	 * @return a set of entries in the forum_log table which contain actions of this user
	 */	
	public Set<ForumLogMining> getForumLogs() {
		return forumLogs;
	}
	/** standard add method for the attribute forum_log
	 * @param forumLog this entry will be added to the list of forum_log in this user
	 * */
	public void addForumLog(ForumLogMining forumLog){	
		forumLogs.add(forumLog);
	}
	/** standard setter for the attribute wiki_log
	 * @param wikiLogs a set of entries in the wiki_log table which contain actions of this user
	 */	
	public void setWikiLogs(Set<WikiLogMining> wikiLogs) {
		this.wikiLogs = wikiLogs;
	}
	
	/** standard getter for the attribute wiki_log
	 * @return a set of entries in the wiki_log table which contain actions of this user
	 */	
	public Set<WikiLogMining> getWikiLogs() {
		return wikiLogs;
	}
	/** standard add method for the attribute wiki_log
	 * @param wikiLog this entry will be added to the list of wiki_log in this user
	 * */
	public void addWikiLog(WikiLogMining wikiLog){	
		wikiLogs.add(wikiLog);
	}
	/** standard setter for the attribute course_log
	 * @param courseLogs a set of entries in the course_log table which contain actions of this user
	 */	
	public void setCourseLogs(Set<CourseLogMining> courseLogs) {
		this.courseLogs = courseLogs;
	}
	/** standard getter for the attribute course_log
	 * @return a set of entries in the course_log table which contain actions of this user
	 */	
	public Set<CourseLogMining> getCourseLogs() {
		return courseLogs;
	}
	/** standard add method for the attribute course_log
	 * @param courseLog this entry will be added to the list of course_log in this user
	 * */
	public void addCourseLog(CourseLogMining courseLog){	
		this.courseLogs.add(courseLog);
	}
	/** standard setter for the attribute quiz_log
	 * @param quizLogs a set of entries in the quiz_log table which contain actions of this user
	 */	
	public void setQuizLogs(Set<QuizLogMining> quizLogs) {
		this.quizLogs = quizLogs;
	}
	/** standard getter for the attribute quiz_log
	 * @return a set of entries in the quiz_log table which contain actions of this user
	 */	
	public Set<QuizLogMining> getQuizLogs() {
		return quizLogs;
	}
	/** standard add method for the attribute quiz_log
	 * @param quizLog this entry will be added to the list of quiz_log in this user
	 * */
	public void addQuizLog(QuizLogMining quizLog){	
		quizLogs.add(quizLog);
	}
	/** standard setter for the attribute question_log
	 * @param questionLogs a set of entries in the question_log table which contain actions of this user
	 */	
	public void setQuestionLogs(Set<QuestionLogMining> questionLogs) {
		this.questionLogs = questionLogs;
	}
	/** standard getter for the attribute question_log
	 * @return a set of entries in the question_log table which contain actions of this user
	 */	
	public Set<QuestionLogMining> getQuestionLogs() {
		return questionLogs;
	}
	/** standard add method for the attribute question_log
	 * @param questionLog this entry will be added to the list of question_log in this quiz
	 * */
	public void addQuestionLog(QuestionLogMining questionLog){	
		questionLogs.add(questionLog);	
	}
	/** standard setter for the attribute resource_log
	 * @param resourceLogs a set of entries in the resource_log table which contain actions of this user
	 */	
	public void setResourceLogs(Set<ResourceLogMining> resourceLogs) {
		this.resourceLogs = resourceLogs;
	}
	/** standard getter for the attribute resource_log
	 * @return a set of entries in the resource_log table which contain actions of this user
	 */	
	public Set<ResourceLogMining> getResourceLogs() {
		return resourceLogs;
	}
	/** standard add method for the attribute resource_log
	 * @param resourceLog this entry will be added to the list of resource_log in this quiz
	 * */
	public void addResourceLog(ResourceLogMining resourceLog){	
		resourceLogs.add(resourceLog);	
	}
	/** standard setter for the attribute quiz_user
	 * @param quizUsers a set of entries in the quiz_user table which relate the user to the courses
	 */	
	public void setQuizUsers(Set<QuizUserMining> quizUsers) {
		this.quizUsers = quizUsers;
	}
	/** standard getter for the attribute quiz_user
	 * @return a set of entries in the quiz_user table which relate the user to the courses
	 */	
	public Set<QuizUserMining> getQuizUsers() {
		return quizUsers;
	}
	/** standard add method for the attribute quiz_user
	 * @param quizUser this entry will be added to the list of quiz_user in this user
	 * */
	public void addQuizUser(QuizUserMining quizUser){	
		quizUsers.add(quizUser);
	}
	/** standard setter for the attribute assignment_log
	 * @param assignmentLogs a set of entries in the assignment_log table which contain actions of this user
	 */
	public void setAssignmentLogs(Set<AssignmentLogMining> assignmentLogs) {
		this.assignmentLogs = assignmentLogs;
	}
	/** standard getter for the attribute assignment_log
	 * @return a set of entries in the assignment_log table which contain actions of this user
	 */		
	public Set<AssignmentLogMining> getAssignmentLogs() {
		return assignmentLogs;
	}
	/** standard add method for the attribute assignment_log
	 * @param assignmentLog this entry will be added to the list of assignment_log in this user
	 * */
	public void addAssignmentLog(AssignmentLogMining assignmentLog){	
		this.assignmentLogs.add(assignmentLog);
	}
	/** standard setter for the attribute scorm_log
	 * @param scormLogs a set of entries in the scorm_log table which contain actions of this user
	 */
	public void setScormLogs(Set<ScormLogMining> scormLogs) {
		this.scormLogs = scormLogs;
	}
	/** standard getter for the attribute scorm_log
	 * @return a set of entries in the scorm_log table which contain actions of this user
	 */		
	public Set<ScormLogMining> getScormLogs() {
		return scormLogs;
	}
	
	
	/** standard add method for the attribute scorm_log
	 * @param scormLog this entry will be added to the list of scorm_log in this user
	 * */
	public void addScormLog(ScormLogMining scormLog){	
		this.scormLogs.add(scormLog);
	}
	
	public void setChatLogs(Set<ChatLogMining> chatLogs) {
		this.chatLogs = chatLogs;
	}
	/** standard getter for the attribute scorm_log
	 * @return a set of entries in the scorm_log table which contain actions of this user
	 */		
	public Set<ChatLogMining> getChatLogs() {
		return chatLogs;
	}
	
	/** standard add method for the attribute scorm_log
	 * @param chatLog this entry will be added to the list of scorm_log in this user
	 * */
	public void addChatLog(ChatLogMining chatLog){	
		chatLogs.add(chatLog);
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
