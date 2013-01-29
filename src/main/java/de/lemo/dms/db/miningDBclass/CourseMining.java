/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**
 * This class represents the table course.
 * @author Sebastian Schwarzrock
 */
public class CourseMining implements IMappingClass, ILearningObject {

	private long id;
	private long startDate;
	private long enrolStart;
	private long enrolEnd;
	private long timeCreated;
	private long timeModified;
	private String title;
	private String shortname;
	private Long platform;

	private Set<CourseGroupMining> courseGroupSet = new HashSet<CourseGroupMining>();
	private Set<ChatMining> chats = new HashSet<ChatMining>();
	private Set<ChatLogMining> chatLogs = new HashSet<ChatLogMining>();
	private Set<CourseForumMining> courseForums = new HashSet<CourseForumMining>();
	private Set<CourseWikiMining> courseWikis = new HashSet<CourseWikiMining>();
	private Set<CourseUserMining> courseUsers = new HashSet<CourseUserMining>();
	private Set<CourseQuizMining> courseQuizzes = new HashSet<CourseQuizMining>();
	private Set<CourseAssignmentMining> courseAssignments = new HashSet<CourseAssignmentMining>();
	private Set<CourseResourceMining> courseResources = new HashSet<CourseResourceMining>();
	private Set<ForumLogMining> forumLogs = new HashSet<ForumLogMining>();
	private Set<WikiLogMining> wikiLogs = new HashSet<WikiLogMining>();
	private Set<CourseLogMining> courseLogs = new HashSet<CourseLogMining>();
	private Set<QuizLogMining> quizLogs = new HashSet<QuizLogMining>();
	private Set<AssignmentLogMining> assignmentLogs = new HashSet<AssignmentLogMining>();
	private Set<QuizUserMining> quizUsers = new HashSet<QuizUserMining>();
	private Set<QuestionLogMining> questionLogs = new HashSet<QuestionLogMining>();
	private Set<ResourceLogMining> resourceLogs = new HashSet<ResourceLogMining>();
	private Set<ScormLogMining> scormLogs = new HashSet<ScormLogMining>();
	private Set<CourseScormMining> courseScorms = new HashSet<CourseScormMining>();
	private Set<LevelCourseMining> levelCourses = new HashSet<LevelCourseMining>();

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof CourseMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseMining)) {
			return true;
		}
		return false;
	}

	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier of the course
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier of the course
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute startdate
	 * 
	 * @return the timestamp when the course starts
	 */
	public long getStartDate() {
		return this.startDate;
	}

	/**
	 * standard setter for the attribute startdate
	 * 
	 * @param startDate
	 *            the timestamp when the course starts
	 */
	public void setStartDate(final long startDate) {
		this.startDate = startDate;
	}

	/**
	 * standard getter for the attribute enrolstart
	 * 
	 * @return the timestamp after that the students can enrol themselfs to the course
	 */
	public long getEnrolStart() {
		return this.enrolStart;
	}

	/**
	 * standard setter for the attribute enrolstart
	 * 
	 * @param enrolStart
	 *            the timestamp after that the students can enrol themselfs to the course
	 */
	public void setEnrolStart(final long enrolStart) {
		this.enrolStart = enrolStart;
	}

	/**
	 * standard getter for the attribute enrolend
	 * 
	 * @return the timestamp after that the students can not enrol themself any more
	 */
	public long getEnrolEnd() {
		return this.enrolEnd;
	}

	/**
	 * standard setter for the attribute enrolend
	 * 
	 * @param enrolEnd
	 */
	public void setEnrolEnd(final long enrolEnd) {
		this.enrolEnd = enrolEnd;
	}

	/**
	 * standard getter for the attribute timecreated
	 * 
	 * @return the timestamp when the course was created
	 */
	public long getTimeCreated() {
		return this.timeCreated;
	}

	/**
	 * standard setter for the attribute timecreated
	 * 
	 * @param timeCreated
	 *            the timestamp when the course was created
	 */
	public void setTimeCreated(final long timeCreated) {
		this.timeCreated = timeCreated;
	}

	/**
	 * standard getter for the attribute timemodified
	 * 
	 * @return the timestamp when the course was changes for the last time
	 */
	public long getTimeModified() {
		return this.timeModified;
	}

	/**
	 * standard setter for the attribute timemodified
	 * 
	 * @param timeModified
	 *            the timestamp when the course was changes for the last time
	 */
	public void setTimeModified(final long timeModified) {
		this.timeModified = timeModified;
	}

	/**
	 * standard getter for the attribute course_group
	 * 
	 * @return a set of entries in the course_group table which shows the groups in this course
	 */
	public Set<CourseGroupMining> getCourseGroups() {
		return this.courseGroupSet;
	}

	/**
	 * standard setter for the attribute course_group
	 * 
	 * @param courseGroup
	 *            a set of entries in the course_group table which shows the groups in this course
	 */
	public void setCourseGroups(final Set<CourseGroupMining> courseGroup) {
		this.courseGroupSet = courseGroup;
	}

	/**
	 * standard add method for the attribute course_group
	 * 
	 * @param courseGroup
	 *            this entry of the course_group table will be added to this course
	 */
	public void addCourseGroup(final CourseGroupMining courseGroup) {
		this.courseGroupSet.add(courseGroup);
	}

	/**
	 * standard setter for the attribute course_forum
	 * 
	 * @param courseForum
	 *            a set of entries in the course_forum table which shows the forums in this course
	 */
	public void setCourseForums(final Set<CourseForumMining> courseForum) {
		this.courseForums = courseForum;
	}

	/**
	 * standard getter for the attribute course_forum
	 * 
	 * @return a set of entries in the course_forum table which relate the course to the forums
	 */
	public Set<CourseForumMining> getCourseForums() {
		return this.courseForums;
	}

	/**
	 * standard add method for the attribute course_forum
	 * 
	 * @param courseForum
	 *            this entry of the course_forum table will be added to this course
	 */
	public void addCourseForum(final CourseForumMining courseForum) {
		this.courseForums.add(courseForum);
	}

	/**
	 * standard setter for the attribute wiki
	 * 
	 * @param courseWiki
	 *            a set of entries in the course_wiki table which shows the wikis in this course
	 */
	public void setCourseWikis(final Set<CourseWikiMining> courseWiki) {
		this.courseWikis = courseWiki;
	}

	/**
	 * standard getter for the attribute wiki
	 * 
	 * @return a set of entries in the course_wiki table which shows the wikis in this course
	 */
	public Set<CourseWikiMining> getCourseWikis() {
		return this.courseWikis;
	}

	/**
	 * standard add method for the attribute wiki
	 * 
	 * @param courseWiki
	 *            this entry of the course_wiki table will be added to this course
	 */
	public void addCourseWiki(final CourseWikiMining courseWiki) {
		this.courseWikis.add(courseWiki);
	}

	/**
	 * standard setter for the attribute course_user
	 * 
	 * @param courseUser
	 *            a set of entries in the course_user table which shows the enroled users
	 */
	public void setCourseUsers(final Set<CourseUserMining> courseUser) {
		this.courseUsers = courseUser;
	}

	/**
	 * standard getter for the attribute course_user
	 * 
	 * @return a set of entries in the course_user table which shows the enroled users
	 */
	public Set<CourseUserMining> getCourseUsers() {
		return this.courseUsers;
	}

	/**
	 * standard add method for the attribute course_user
	 * 
	 * @param courseUser
	 *            this entry of the course_user table will be added to this course
	 */
	public void addCourseUser(final CourseUserMining courseUser) {
		this.courseUsers.add(courseUser);
	}

	/**
	 * standard setter for the attribute course_quiz
	 * 
	 * @param courseQuiz
	 *            a set of entries in the course_quiz table which shows the quiz used in the course
	 */
	public void setCourseQuizzes(final Set<CourseQuizMining> courseQuiz) {
		this.courseQuizzes = courseQuiz;
	}

	/**
	 * standard getter for the attribute course_quiz
	 * 
	 * @return a set of entries in the course_quiz table which shows the quiz used in the course
	 */
	public Set<CourseQuizMining> getCourseQuizzes() {
		return this.courseQuizzes;
	}

	/**
	 * standard add method for the attribute course_quiz
	 * 
	 * @param courseQuiz
	 *            this entry of the course_quiz table will be added to this course
	 */
	public void addCourseQuiz(final CourseQuizMining courseQuiz) {
		this.courseQuizzes.add(courseQuiz);
	}

	/**
	 * standard setter for the attribute course_resource
	 * 
	 * @param courseResource
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setCourseResources(final Set<CourseResourceMining> courseResource) {
		this.courseResources = courseResource;
	}

	/**
	 * standard getter for the attribute course_resource
	 * 
	 * @return a set of entries in the course_resource table which shows the resources in this course
	 */
	public Set<CourseResourceMining> getCourseResources() {
		return this.courseResources;
	}

	/**
	 * standard add method for the attribute course_resource
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addCourseResource(final CourseResourceMining courseResource) {
		this.courseResources.add(courseResource);
	}

	/**
	 * standard setter for the attribute course_log
	 * 
	 * @param courseLog
	 *            a set of entries in the course_log table which represent actions on this course
	 */
	public void setCourseLogs(final Set<CourseLogMining> courseLog) {
		this.courseLogs = courseLog;
	}

	/**
	 * standard getter for the attribute course_log
	 * 
	 * @return a set of entries in the course_log table which represent actions on this course
	 */
	public Set<CourseLogMining> getCourseLogs() {
		return this.courseLogs;
	}

	/**
	 * standard add method for the attribute course_log
	 * 
	 * @param courseLog
	 *            this entry of the course_log table will be added to this course
	 */
	public void addCourseUpdates(final CourseLogMining courseLog) {
		this.courseLogs.add(courseLog);
	}

	/**
	 * standard setter for the attribute quiz_log
	 * 
	 * @param quizLog
	 *            a set of entries in the quiz_log table which are related to quiz in this course
	 */
	public void setQuizLogs(final Set<QuizLogMining> quizLog) {
		this.quizLogs = quizLog;
	}

	/**
	 * standard getter for the attribute quiz_log
	 * 
	 * @return a set of entries in the quiz_log table which are related to quiz in this course
	 */
	public Set<QuizLogMining> getQuizLogs() {
		return this.quizLogs;
	}

	/**
	 * standard add method for the attribute quiz_log
	 * 
	 * @param quizLog
	 *            this entry of the quiz_log table will be added to this course
	 */
	public void addQuizLog(final QuizLogMining quizLog) {
		this.quizLogs.add(quizLog);
	}

	/**
	 * standard setter for the attribute wiki_log
	 * 
	 * @param wikiLog
	 *            a set of entries in the wiki_log table which are related to wikis in this course
	 */
	public void setWikiLogs(final Set<WikiLogMining> wikiLog) {
		this.wikiLogs = wikiLog;
	}

	/**
	 * standard getter for the attribute wiki_log
	 * 
	 * @return a set of entries in the wiki_log table which are related to wikis in this course
	 */
	public Set<WikiLogMining> getWikiLogs() {
		return this.wikiLogs;
	}

	/**
	 * standard add method for the attribute wiki_log
	 * 
	 * @param wikiLog
	 *            this entry of the wiki_log table will be added to this course
	 */
	public void addWikiLog(final WikiLogMining wikiLog) {
		this.wikiLogs.add(wikiLog);
	}

	/**
	 * standard setter for the attribute question_log
	 * 
	 * @param questionLog
	 *            a set of entries in the question_log table which are related to questions in this course
	 */
	public void setQuestionLogs(final Set<QuestionLogMining> questionLog) {
		this.questionLogs = questionLog;
	}

	/**
	 * standard getter for the attribute question_log
	 * 
	 * @return a set of entries in the question_log table which are related to questions in this course
	 */
	public Set<QuestionLogMining> getQuestionLogs() {
		return this.questionLogs;
	}

	/**
	 * standard add method for the attribute question_log
	 * 
	 * @param questionLog
	 *            this entry of the question_log table will be added to this course
	 */
	public void addQuestionLog(final QuestionLogMining questionLog) {
		this.questionLogs.add(questionLog);
	}

	/**
	 * standard setter for the attribute resource_log
	 * 
	 * @param resourceLog
	 *            a set of entries in the resource_log table which are related to resources in this course
	 */
	public void setResourceLogs(final Set<ResourceLogMining> resourceLog) {
		this.resourceLogs = resourceLog;
	}

	/**
	 * standard getter for the attribute resource_log
	 * 
	 * @return a set of entries in the resource_log table which are related to resources in this course
	 */
	public Set<ResourceLogMining> getResourceLogs() {
		return this.resourceLogs;
	}

	/**
	 * standard add method for the attribute resource_log
	 * 
	 * @param resourceLog
	 *            this entry of the resource_log table will be added to this course
	 */
	public void addResourceLog(final ResourceLogMining resourceLog) {
		this.resourceLogs.add(resourceLog);
	}

	/**
	 * standard setter for the attribute forum_log
	 * 
	 * @param forumLog
	 *            a set of entries in the forum_log table which are related to forums in this course
	 */
	public void setForumLogs(final Set<ForumLogMining> forumLog) {
		this.forumLogs = forumLog;
	}

	/**
	 * standard getter for the attribute forum_log
	 * 
	 * @return a set of entries in the forum_log table which are related to forums in this course
	 */
	public Set<ForumLogMining> getForumLogs() {
		return this.forumLogs;
	}

	/**
	 * standard add method for the attribute forum_log
	 * 
	 * @param forumLog
	 *            this entry of the forum_log table will be added to this course
	 */
	public void addForumLog(final ForumLogMining forumLog) {
		this.forumLogs.add(forumLog);
	}

	/**
	 * standard setter for the attribute title
	 * 
	 * @param title
	 *            the title of this course
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * standard getter for the attribute title
	 * 
	 * @return the title of this course
	 */
	@Override
	public String getTitle() {
		return this.title;
	}

	/**
	 * standard setter for the attribute shortname
	 * 
	 * @param shortname
	 *            a shortname for this course
	 */
	public void setShortname(final String shortname) {
		this.shortname = shortname;
	}

	/**
	 * standard getter for the attribute shortname
	 * 
	 * @return a shortname for this course
	 */
	public String getShortname() {
		return this.shortname;
	}

	/**
	 * standard getter for the attribute quiz_user
	 * 
	 * @return a set of entries in the quiz_user table which are related to this course
	 */
	public Set<QuizUserMining> getQuizUsers() {
		return this.quizUsers;
	}

	/**
	 * standard setter for the attribute quiz_user
	 * 
	 * @param quizUser
	 *            a set of entries in the quiz_user table which are related to this course
	 */
	public void setQuizUsers(final Set<QuizUserMining> quizUser) {
		this.quizUsers = quizUser;
	}

	/**
	 * standard add method for the attribute quiz_user
	 * 
	 * @param quizUser
	 *            this entry of the quiz_user table will be added to this course
	 */
	public void addQuizUser(final QuizUserMining quizUser) {
		this.quizUsers.add(quizUser);
	}

	/**
	 * standard setter for the attribute assignment_log
	 * 
	 * @param assignmentLog
	 *            a set of entries in the assignment_log table which are related to assignment in this course
	 */
	public void setAssignmentLogs(final Set<AssignmentLogMining> assignmentLog) {
		this.assignmentLogs = assignmentLog;
	}

	/**
	 * standard getter for the attribute assignment_log
	 * 
	 * @return a set of entries in the assignment_log table which are related to assignment in this course
	 */
	public Set<AssignmentLogMining> getAssignmentLogs() {
		return this.assignmentLogs;
	}

	/**
	 * standard add method for the attribute assignment_log
	 * 
	 * @param assignmentLog
	 *            this entry of the assignment_log table will be added to this course
	 */
	public void addAssignmentLog(final AssignmentLogMining assignmentLog) {
		this.assignmentLogs.add(assignmentLog);
	}

	/**
	 * standard setter for the attribute course_assignment
	 * 
	 * @param courseAssignment
	 *            a set of entries in the course_assignment table which shows the assignments used in the course
	 */
	public void setCourseAssignments(final Set<CourseAssignmentMining> courseAssignment) {
		this.courseAssignments = courseAssignment;
	}

	/**
	 * standard getter for the attribute course_assignment
	 * 
	 * @return a set of entries in the course_assignment table which shows the assignment used in the course
	 */
	public Set<CourseAssignmentMining> getCourseAssignments() {
		return this.courseAssignments;
	}

	/**
	 * standard add method for the attribute course_assignment
	 * 
	 * @param courseAssignment
	 *            this entry of the course_assignment table will be added to this course
	 */
	public void addCourseAssignment(final CourseAssignmentMining courseAssignment) {
		this.courseAssignments.add(courseAssignment);
	}

	public void addChat(final ChatMining chat) {
		this.chats.add(chat);
	}

	public void addChatLog(final ChatLogMining chatLog) {
		this.chatLogs.add(chatLog);
	}

	public Set<ChatMining> getChats() {
		return this.chats;
	}

	public void setChats(final Set<ChatMining> chat) {
		this.chats = chat;
	}

	public Set<ChatLogMining> getChatLogs() {
		return this.chatLogs;
	}

	public void setChatLogs(final Set<ChatLogMining> chatLog) {
		this.chatLogs = chatLog;
	}

	/**
	 * standard setter for the attribute scorm_log
	 * 
	 * @param scormLog
	 *            a set of entries in the scorm_log table which are related to scorm packages in this course
	 */
	public void setScormLogs(final Set<ScormLogMining> scormLog) {
		this.scormLogs = scormLog;
	}

	/**
	 * standard getter for the attribute scorm_log
	 * 
	 * @return a set of entries in the scorm_log table which are related to scorm packages in this course
	 */
	public Set<ScormLogMining> getScormLogs() {
		return this.scormLogs;
	}

	/**
	 * standard add method for the attribute scorm_log
	 * 
	 * @param scormLog
	 *            this entry of the scorm_log table will be added to this course
	 */
	public void addScormLog(final ScormLogMining scormLog) {
		this.scormLogs.add(scormLog);
	}

	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param courseScorm
	 *            a set of entries in the course_scorm table which shows the assignments used in the course
	 */
	public void setCourseScorms(final Set<CourseScormMining> courseScorm) {
		this.courseScorms = courseScorm;
	}

	/**
	 * standard getter for the attribute course_scorm
	 * 
	 * @return a set of entries in the course_scorm table which shows the scorm used in the course
	 */
	public Set<CourseScormMining> getCourseScorms() {
		return this.courseScorms;
	}

	/**
	 * standard add method for the attribute course_scorm
	 * 
	 * @param courseScorm
	 *            this entry of the course_scorm table will be added to this course
	 */
	public void addCourseScorm(final CourseScormMining courseScorm) {
		this.courseScorms.add(courseScorm);
	}

	/**
	 * standard setter for the attribute course_scorm
	 * 
	 * @param degree_course
	 *            a set of entries in the course_scorm table which shows the assignments used in the course
	 */
	public void setLevelCourses(final Set<LevelCourseMining> levelCourse) {
		this.levelCourses = levelCourse;
	}

	/**
	 * standard getter for the attribute course_scorm
	 * 
	 * @return a set of entries in the course_scorm table which shows the scorm used in the course
	 */
	public Set<LevelCourseMining> getLevelCourses() {
		return this.levelCourses;
	}

	/**
	 * standard add method for the attribute course_scorm
	 * 
	 * @param degree_course_add
	 *            this entry of the course_scorm table will be added to this course
	 */
	public void addLevelCourse(final LevelCourseMining levelCourse) {
		this.levelCourses.add(levelCourse);
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}
}
