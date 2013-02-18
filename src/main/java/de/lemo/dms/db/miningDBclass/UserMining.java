/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/UserMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the table resource. */
public class UserMining implements IMappingClass {

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

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof UserMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof UserMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the user
	 */
	@Override
	public long getId() {
		return this.id;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(final Boolean gender) {
		this.gender = gender;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the user
	 */
	public void setId(final String id, final List<IDMappingMining> idMappingMining,
			final List<IDMappingMining> oldIdMappingMining) {
		final long idN = UserMining.idForHash(id, idMappingMining, oldIdMappingMining);
		this.id = idN;
	}

	public static long idForHash(final String hash1, final List<IDMappingMining> idMappingMining,
			final List<IDMappingMining> oldIdMappingMining)
	{
		long id_n = -1;
		for (final Iterator<IDMappingMining> iter = idMappingMining.iterator(); iter.hasNext();)
		{
			final IDMappingMining loadedItem = iter.next();
			if (loadedItem.getHash() == hash1) {
				id_n = loadedItem.getId();
			}
		}
		if (id_n == -1) {
			for (final Iterator<IDMappingMining> iter = oldIdMappingMining.iterator(); iter.hasNext();)
			{
				final IDMappingMining loadedItem = iter.next();
				if (loadedItem.getHash() == hash1) {
					id_n = loadedItem.getId();
				}
			}
		}
		return id_n;
	}

	public void setId(final long id)
	{
		this.id = id;
	}

	/**
	 * standard getter for the attribute lastlogin
	 * 
	 * @return the timestamp of the last time the user has logged in
	 */
	public long getLastLogin() {
		return this.lastLogin;
	}

	/**
	 * standard setter for the attribute lastlogin
	 * 
	 * @param lastLogin
	 *            the timestamp of the last time the user has logged in
	 */
	public void setLastLogin(final long lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * standard getter for the attribute firstaccess
	 * 
	 * @return the timestamp when the user has made his first access to the LMS
	 */
	public long getFirstAccess() {
		return this.firstAccess;
	}

	/**
	 * standard setter for the attribute firstaccess
	 * 
	 * @param firstAccess
	 *            the timestamp when the user has made his first access to the LMS
	 */
	public void setFirstAccess(final long firstAccess) {
		this.firstAccess = firstAccess;
	}

	/**
	 * standard getter for the attribute lastaccess
	 * 
	 * @return the timestamp when the user has made the last access to the LMS
	 */
	public long getLastAccess() {
		return this.lastAccess;
	}

	/**
	 * standard setter for the attribute lastaccess
	 * 
	 * @param lastAccess
	 *            the timestamp when the user has made the last access to the LMS
	 */
	public void setLastAccess(final long lastAccess) {
		this.lastAccess = lastAccess;
	}

	/**
	 * standard getter for the attribute currentlogin
	 * 
	 * @return the timestamp when the user has logged into the current session
	 */
	public long getCurrentLogin() {
		return this.currentLogin;
	}

	/**
	 * standard setter for the attribute currentlogin
	 * 
	 * @param currentLogin
	 *            the timestamp when the user has logged into the current session
	 */
	public void setCurrentLogin(final long currentLogin) {
		this.currentLogin = currentLogin;
	}

	/**
	 * standard setter for the attribute course_user
	 * 
	 * @param courseUsers
	 *            a set of entries in the course_user table which relate the user to courses
	 */
	public void setCourseUsers(final Set<CourseUserMining> courseUsers) {
		this.courseUsers = courseUsers;
	}

	/**
	 * standard getter for the attribute course_user
	 * 
	 * @return a set of entries in the course_user table which relate the user to courses
	 */
	public Set<CourseUserMining> getCourseUsers() {
		return this.courseUsers;
	}

	/**
	 * standard add method for the attribute course_user
	 * 
	 * @param courseUser
	 *            this entry will be added to the list of user in this quiz
	 */
	public void addCourseUser(final CourseUserMining courseUser) {
		this.courseUsers.add(courseUser);
	}

	/**
	 * standard setter for the attribute group_user
	 * 
	 * @param groupUsers
	 *            a set of entries in the group_user table which relate the user to groups
	 */
	public void setGroupUsers(final Set<GroupUserMining> groupUsers) {
		this.groupUsers = groupUsers;
	}

	/**
	 * standard getter for the attribute group_user
	 * 
	 * @return a set of entries in the group_user table which relate the user to groups
	 */
	public Set<GroupUserMining> getGroupUsers() {
		return this.groupUsers;
	}

	/**
	 * standard add method for the attribute group_user
	 * 
	 * @param groupUser
	 *            this entry will be added to the list of group_user in this user
	 */
	public void addGroupUser(final GroupUserMining groupUser) {
		this.groupUsers.add(groupUser);
	}

	/**
	 * standard setter for the attribute forum_log
	 * 
	 * @param forumLogs
	 *            a set of entries in the forum_log table which contain actions of this user
	 */
	public void setForumLogs(final Set<ForumLogMining> forumLogs) {
		this.forumLogs = forumLogs;
	}

	/**
	 * standard getter for the attribute forum_log
	 * 
	 * @return a set of entries in the forum_log table which contain actions of this user
	 */
	public Set<ForumLogMining> getForumLogs() {
		return this.forumLogs;
	}

	/**
	 * standard add method for the attribute forum_log
	 * 
	 * @param forumLog
	 *            this entry will be added to the list of forum_log in this user
	 */
	public void addForumLog(final ForumLogMining forumLog) {
		this.forumLogs.add(forumLog);
	}

	/**
	 * standard setter for the attribute wiki_log
	 * 
	 * @param wikiLogs
	 *            a set of entries in the wiki_log table which contain actions of this user
	 */
	public void setWikiLogs(final Set<WikiLogMining> wikiLogs) {
		this.wikiLogs = wikiLogs;
	}

	/**
	 * standard getter for the attribute wiki_log
	 * 
	 * @return a set of entries in the wiki_log table which contain actions of this user
	 */
	public Set<WikiLogMining> getWikiLogs() {
		return this.wikiLogs;
	}

	/**
	 * standard add method for the attribute wiki_log
	 * 
	 * @param wikiLog
	 *            this entry will be added to the list of wiki_log in this user
	 */
	public void addWikiLog(final WikiLogMining wikiLog) {
		this.wikiLogs.add(wikiLog);
	}

	/**
	 * standard setter for the attribute course_log
	 * 
	 * @param courseLogs
	 *            a set of entries in the course_log table which contain actions of this user
	 */
	public void setCourseLogs(final Set<CourseLogMining> courseLogs) {
		this.courseLogs = courseLogs;
	}

	/**
	 * standard getter for the attribute course_log
	 * 
	 * @return a set of entries in the course_log table which contain actions of this user
	 */
	public Set<CourseLogMining> getCourseLogs() {
		return this.courseLogs;
	}

	/**
	 * standard add method for the attribute course_log
	 * 
	 * @param courseLog
	 *            this entry will be added to the list of course_log in this user
	 */
	public void addCourseLog(final CourseLogMining courseLog) {
		this.courseLogs.add(courseLog);
	}

	/**
	 * standard setter for the attribute quiz_log
	 * 
	 * @param quizLogs
	 *            a set of entries in the quiz_log table which contain actions of this user
	 */
	public void setQuizLogs(final Set<QuizLogMining> quizLogs) {
		this.quizLogs = quizLogs;
	}

	/**
	 * standard getter for the attribute quiz_log
	 * 
	 * @return a set of entries in the quiz_log table which contain actions of this user
	 */
	public Set<QuizLogMining> getQuizLogs() {
		return this.quizLogs;
	}

	/**
	 * standard add method for the attribute quiz_log
	 * 
	 * @param quizLog
	 *            this entry will be added to the list of quiz_log in this user
	 */
	public void addQuizLog(final QuizLogMining quizLog) {
		this.quizLogs.add(quizLog);
	}

	/**
	 * standard setter for the attribute question_log
	 * 
	 * @param questionLogs
	 *            a set of entries in the question_log table which contain actions of this user
	 */
	public void setQuestionLogs(final Set<QuestionLogMining> questionLogs) {
		this.questionLogs = questionLogs;
	}

	/**
	 * standard getter for the attribute question_log
	 * 
	 * @return a set of entries in the question_log table which contain actions of this user
	 */
	public Set<QuestionLogMining> getQuestionLogs() {
		return this.questionLogs;
	}

	/**
	 * standard add method for the attribute question_log
	 * 
	 * @param questionLog
	 *            this entry will be added to the list of question_log in this quiz
	 */
	public void addQuestionLog(final QuestionLogMining questionLog) {
		this.questionLogs.add(questionLog);
	}

	/**
	 * standard setter for the attribute resource_log
	 * 
	 * @param resourceLogs
	 *            a set of entries in the resource_log table which contain actions of this user
	 */
	public void setResourceLogs(final Set<ResourceLogMining> resourceLogs) {
		this.resourceLogs = resourceLogs;
	}

	/**
	 * standard getter for the attribute resource_log
	 * 
	 * @return a set of entries in the resource_log table which contain actions of this user
	 */
	public Set<ResourceLogMining> getResourceLogs() {
		return this.resourceLogs;
	}

	/**
	 * standard add method for the attribute resource_log
	 * 
	 * @param resourceLog
	 *            this entry will be added to the list of resource_log in this quiz
	 */
	public void addResourceLog(final ResourceLogMining resourceLog) {
		this.resourceLogs.add(resourceLog);
	}

	/**
	 * standard setter for the attribute quiz_user
	 * 
	 * @param quizUsers
	 *            a set of entries in the quiz_user table which relate the user to the courses
	 */
	public void setQuizUsers(final Set<QuizUserMining> quizUsers) {
		this.quizUsers = quizUsers;
	}

	/**
	 * standard getter for the attribute quiz_user
	 * 
	 * @return a set of entries in the quiz_user table which relate the user to the courses
	 */
	public Set<QuizUserMining> getQuizUsers() {
		return this.quizUsers;
	}

	/**
	 * standard add method for the attribute quiz_user
	 * 
	 * @param quizUser
	 *            this entry will be added to the list of quiz_user in this user
	 */
	public void addQuizUser(final QuizUserMining quizUser) {
		this.quizUsers.add(quizUser);
	}

	/**
	 * standard setter for the attribute assignment_log
	 * 
	 * @param assignmentLogs
	 *            a set of entries in the assignment_log table which contain actions of this user
	 */
	public void setAssignmentLogs(final Set<AssignmentLogMining> assignmentLogs) {
		this.assignmentLogs = assignmentLogs;
	}

	/**
	 * standard getter for the attribute assignment_log
	 * 
	 * @return a set of entries in the assignment_log table which contain actions of this user
	 */
	public Set<AssignmentLogMining> getAssignmentLogs() {
		return this.assignmentLogs;
	}

	/**
	 * standard add method for the attribute assignment_log
	 * 
	 * @param assignmentLog
	 *            this entry will be added to the list of assignment_log in this user
	 */
	public void addAssignmentLog(final AssignmentLogMining assignmentLog) {
		this.assignmentLogs.add(assignmentLog);
	}

	/**
	 * standard setter for the attribute scorm_log
	 * 
	 * @param scormLogs
	 *            a set of entries in the scorm_log table which contain actions of this user
	 */
	public void setScormLogs(final Set<ScormLogMining> scormLogs) {
		this.scormLogs = scormLogs;
	}

	/**
	 * standard getter for the attribute scorm_log
	 * 
	 * @return a set of entries in the scorm_log table which contain actions of this user
	 */
	public Set<ScormLogMining> getScormLogs() {
		return this.scormLogs;
	}

	/**
	 * standard add method for the attribute scorm_log
	 * 
	 * @param scormLog
	 *            this entry will be added to the list of scorm_log in this user
	 */
	public void addScormLog(final ScormLogMining scormLog) {
		this.scormLogs.add(scormLog);
	}

	public void setChatLogs(final Set<ChatLogMining> chatLogs) {
		this.chatLogs = chatLogs;
	}

	/**
	 * standard getter for the attribute scorm_log
	 * 
	 * @return a set of entries in the scorm_log table which contain actions of this user
	 */
	public Set<ChatLogMining> getChatLogs() {
		return this.chatLogs;
	}

	/**
	 * standard add method for the attribute scorm_log
	 * 
	 * @param chatLog
	 *            this entry will be added to the list of scorm_log in this user
	 */
	public void addChatLog(final ChatLogMining chatLog) {
		this.chatLogs.add(chatLog);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
