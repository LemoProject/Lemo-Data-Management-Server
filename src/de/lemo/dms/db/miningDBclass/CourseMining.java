package de.lemo.dms.db.miningDBclass;

import java.util.HashSet;
import java.util.Set;

/**This class represents the table course.*/
public class CourseMining {

	private long id;
	private long startdate;	
	private long enrolstart;
	private long enrolend;
	private long timecreated;
	private long timemodified;
	private String title;
	private String shortname;
	
	private Set<CourseGroupMining> course_group = new HashSet<CourseGroupMining>();
	private Set<CourseForumMining> course_forum = new HashSet<CourseForumMining>();
	private Set<CourseWikiMining> course_wiki = new HashSet<CourseWikiMining>();
	private Set<CourseUserMining> course_user = new HashSet<CourseUserMining>();
	private Set<CourseQuizMining> course_quiz = new HashSet<CourseQuizMining>();
	private Set<CourseAssignmentMining> course_assignment = new HashSet<CourseAssignmentMining>();
	private Set<CourseResourceMining> course_resource = new HashSet<CourseResourceMining>();
	private Set<ForumLogMining> forum_log = new HashSet<ForumLogMining>();
	private Set<WikiLogMining> wiki_log = new HashSet<WikiLogMining>();	
	private Set<CourseLogMining> course_log = new HashSet<CourseLogMining>();
	private Set<QuizLogMining> quiz_log = new HashSet<QuizLogMining>();
	private Set<AssignmentLogMining> assignment_log = new HashSet<AssignmentLogMining>();
	private Set<QuizUserMining> quiz_user = new HashSet<QuizUserMining>();
	private Set<QuestionLogMining> question_log = new HashSet<QuestionLogMining>();
	private Set<ResourceLogMining> resource_log = new HashSet<ResourceLogMining>();
	private Set<ScormLogMining> scorm_log = new HashSet<ScormLogMining>();
	private Set<CourseScormMining> course_scorm = new HashSet<CourseScormMining>();	
	private Set<DegreeCourseMining> degree_course = new HashSet<DegreeCourseMining>();
	
	/** standard getter for the attribut id
	 * @return the identifier of the course
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier of the course
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut startdate
	 * @return the timestamp when the course starts
	 */		
	public long getStartdate() {
		return startdate;
	}
	/** standard setter for the attribut startdate
	 * @param startdate the timestamp when the course starts
	 */	
	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}
	/** standard getter for the attribut enrolstart
	 * @return the timestamp after that the students can enrol themselfs to the course
	 */	
	public long getEnrolstart() {
		return enrolstart;
	}
	/** standard setter for the attribut enrolstart
	 * @param enrolstart the timestamp after that the students can enrol themselfs to the course
	 */	
	public void setEnrolstart(long enrolstart) {
		this.enrolstart = enrolstart;
	}
	/** standard getter for the attribut enrolend
	 * @return the timestamp after that the students can not enrol themself any more
	 */	
	public long getEnrolend() {
		return enrolend;
	}
	/** standard setter for the attribut enrolend
	 * @param enrolend
	 */	
	public void setEnrolend(long enrolend) {
		this.enrolend = enrolend;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the course was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the course was created
	 */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the course was changes for the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the course was changes for the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard getter for the attribut course_group
	 * @return a set of entrys in the course_group table which shows the groups in this course
	 */	
	public Set<CourseGroupMining> getCourse_group() {
		return course_group;
	}
	/** standard setter for the attribut course_group
	 * @param course_group a set of entrys in the course_group table which shows the groups in this course
	 */	
	public void setCourse_group(Set<CourseGroupMining> course_group) {
		this.course_group = course_group;
	}
	/** standard add method for the attribut course_group
	 * @param course_group_add this entry of the course_group table will be added to this course
	 * */		
	public void addCourse_group(CourseGroupMining course_group_add){	
		this.course_group.add(course_group_add);	
	}
	/** standard setter for the attribut course_forum
	 * @param course_forum a set of entrys in the course_forum table which shows the forums in this course
	 */	
	public void setCourse_forum(Set<CourseForumMining> course_forum) {
		this.course_forum = course_forum;
	}
	/** standard getter for the attribut course_forum
	 * @return a set of entrys in the course_forum table which relate the course to the forums
	 */	
	public Set<CourseForumMining> getCourse_forum() {
		return course_forum;
	}
	/** standard add method for the attribut course_forum
	 * @param course_forum_add this entry of the course_forum table will be added to this course
	 * */		
	public void addCourse_forum(CourseForumMining course_forum_add){	
		course_forum.add(course_forum_add);	
	}
	/** standard setter for the attribut wiki
	 * @param course_wiki a set of entrys in the course_wiki table which shows the wikis in this course
	 */	
	public void setCourse_wiki(Set<CourseWikiMining> course_wiki) {
		this.course_wiki = course_wiki;
	}
	/** standard getter for the attribut wiki
	 * @return a set of entrys in the course_wiki table which shows the wikis in this course
	 */	
	public Set<CourseWikiMining> getCourse_wiki() {
		return course_wiki;
	}
	/** standard add method for the attribut wiki
	 * @param course_wiki_add this entry of the course_wiki table will be added to this course
	 * */		
	public void addCourse_wiki(CourseWikiMining course_wiki_add){	
		course_wiki.add(course_wiki_add);	
	}
	/** standard setter for the attribut course_user
	 * @param course_user a set of entrys in the course_user table which shows the enroled users
	 */	
	public void setCourse_user(Set<CourseUserMining> course_user) {
		this.course_user = course_user;
	}
	/** standard getter for the attribut course_user
	 * @return a set of entrys in the course_user table which shows the enroled users
	 */	
	public Set<CourseUserMining> getCourse_user() {
		return course_user;
	}
	/** standard add method for the attribut course_user
	 * @param course_user_add this entry of the course_user table will be added to this course
	 * */		
	public void addCourse_user(CourseUserMining course_user_add){	
		course_user.add(course_user_add);	
	}
	/** standard setter for the attribut course_quiz
	 * @param course_quiz a set of entrys in the course_quiz table which shows the quiz used in the course
	 */	
	public void setCourse_quiz(Set<CourseQuizMining> course_quiz) {
		this.course_quiz = course_quiz;
	}
	/** standard getter for the attribut course_quiz
	 * @return a set of entrys in the course_quiz table which shows the quiz used in the course
	 */	
	public Set<CourseQuizMining> getCourse_quiz() {
		return course_quiz;
	}
	/** standard add method for the attribut course_quiz
	 * @param course_quiz_add this entry of the course_quiz table will be added to this course
	 * */		
	public void addCourse_quiz(CourseQuizMining course_quiz_add){	
		course_quiz.add(course_quiz_add);	
	}
	/** standard setter for the attribut course_resource
	 * @param course_resource a set of entrys in the course_resource table which shows the resources in this course
	 */	
	public void setCourse_resource(Set<CourseResourceMining> course_resource) {
		this.course_resource = course_resource;
	}
	/** standard getter for the attribut course_resource
	 * @return a set of entrys in the course_resource table which shows the resources in this course
	 */	
	public Set<CourseResourceMining> getCourse_resource() {
		return course_resource;
	}
	/** standard add method for the attribut course_resource
	 * @param course_resource_add this entry of the course_resource table will be added to this course
	 * */		
	public void addCourse_resource(CourseResourceMining course_resource_add){	
		course_resource.add(course_resource_add);	
	}
	/** standard setter for the attribut course_log
	 * @param course_log a set of entrys in the course_log table which represent actions on this course
	 */	
	public void setCourse_log(Set<CourseLogMining> course_log) {
		this.course_log = course_log;
	}
	/** standard getter for the attribut course_log
	 * @return a set of entrys in the course_log table which represent actions on this course
	 */	
	public Set<CourseLogMining> getCourse_log() {
		return course_log;
	}
	/** standard add method for the attribut course_log
	 * @param course_log_add this entry of the course_log table will be added to this course
	 * */	
	public void addCourse_updates(CourseLogMining course_log_add){	
		course_log.add(course_log_add);	
	}
	/** standard setter for the attribut quiz_log
	 * @param quiz_log a set of entrys in the quiz_log table which are related to quiz in this course
	 */	
	public void setQuiz_log(Set<QuizLogMining> quiz_log) {
		this.quiz_log = quiz_log;
	}
	/** standard getter for the attribut quiz_log
	 * @return a set of entrys in the quiz_log table which are related to quiz in this course
	 */	
	public Set<QuizLogMining> getQuiz_log() {
		return quiz_log;
	}
	/** standard add method for the attribut quiz_log
	 * @param quiz_log_add this entry of the quiz_log table will be added to this course
	 * */		
	public void addQuiz_log(QuizLogMining quiz_log_add){	
		quiz_log.add(quiz_log_add);	
	}
	/** standard setter for the attribut wiki_log
	 * @param wiki_log a set of entrys in the wiki_log table which are related to wikis in this course
	 */	
	public void setWiki_log(Set<WikiLogMining> wiki_log) {
		this.wiki_log = wiki_log;
	}
	/** standard getter for the attribut wiki_log
	 * @return a set of entrys in the wiki_log table which are related to wikis in this course
	 */	
	public Set<WikiLogMining> getWiki_log() {
		return wiki_log;
	}
	/** standard add method for the attribut wiki_log
	 * @param wiki_log_add this entry of the wiki_log table will be added to this course
	 * */		
	public void addWiki_log(WikiLogMining wiki_log_add){	
		wiki_log.add(wiki_log_add);	
	}
	/** standard setter for the attribut question_log
	 * @param question_log a set of entrys in the question_log table which are related to questions in this course
	 */	
	public void setQuestion_log(Set<QuestionLogMining> question_log) {
		this.question_log = question_log;
	}
	/** standard getter for the attribut question_log
	 * @return a set of entrys in the question_log table which are related to questions in this course
	 */	
	public Set<QuestionLogMining> getQuestion_log() {
		return question_log;
	}
	/** standard add method for the attribut question_log
	 * @param question_log_add this entry of the question_log table will be added to this course
	 * */		
	public void addQuestion_log(QuestionLogMining question_log_add){	
		question_log.add(question_log_add);	
	}
	/** standard setter for the attribut resource_log
	 * @param resource_log a set of entrys in the resource_log table which are related to resources in this course
	 */	
	public void setResource_log(Set<ResourceLogMining> resource_log) {
		this.resource_log = resource_log;
	}
	/** standard getter for the attribut resource_log
	 * @return a set of entrys in the resource_log table which are related to resources in this course
	 */	
	public Set<ResourceLogMining> getResource_log() {
		return resource_log;
	}
	/** standard add method for the attribut resource_log
	 * @param resource_log_add this entry of the resource_log table will be added to this course
	 * */		
	public void addResource_log(ResourceLogMining resource_log_add){	
		resource_log.add(resource_log_add);	
	}
	/** standard setter for the attribut forum_log
	 * @param forum_log a set of entrys in the forum_log table which are related to forums in this course
	 */	
	public void setForum_log(Set<ForumLogMining> forum_log) {
		this.forum_log = forum_log;
	}
	/** standard getter for the attribut forum_log
	 * @return a set of entrys in the forum_log table which are related to forums in this course
	 */	
	public Set<ForumLogMining> getForum_log() {
		return forum_log;
	}
	/** standard add method for the attribut forum_log
	 * @param forum_log_add this entry of the forum_log table will be added to this course
	 * */		
	public void addForum_log(ForumLogMining forum_log_add){	
		forum_log.add(forum_log_add);	
	}
	/** standard setter for the attribut title
	 * @param title the title of this course
	 */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut title
	 * @return the title of this course
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut shortname
	 * @param shortname a shortname for this course
	 */	
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/** standard getter for the attribut shortname
	 * @return a shortname for this course
	 */	
	public String getShortname() {
		return shortname;
	}
	/** standard getter for the attribut quiz_user
	 * @return a set of entrys in the quiz_user table which are related to this course
	 */	
	public Set<QuizUserMining> getQuiz_user() {
		return quiz_user;
	}
	/** standard setter for the attribut quiz_user
	 * @param quiz_user a set of entrys in the quiz_user table which are related to this course
	 */	
	public void setQuiz_user(Set<QuizUserMining> quiz_user) {
		this.quiz_user = quiz_user;
	}
	/** standard add method for the attribut quiz_user
	 * @param quiz_user_add this entry of the quiz_user table will be added to this course
	 * */		
	public void addQuiz_user(QuizUserMining quiz_user_add){	
		quiz_user.add(quiz_user_add);	
	}
	/** standard setter for the attribut assignment_log
	 * @param assignment_log a set of entrys in the assignment_log table which are related to assignment in this course
	 */		
	public void setAssignment_log(Set<AssignmentLogMining> assignment_log) {
		this.assignment_log = assignment_log;
	}
	/** standard getter for the attribut assignment_log
	 * @return a set of entrys in the assignment_log table which are related to assignment in this course
	 */		
	public Set<AssignmentLogMining> getAssignment_log() {
		return assignment_log;
	}
	/** standard add method for the attribut assignment_log
	 * @param assignment_log_add this entry of the assignment_log table will be added to this course
	 * */		
	public void addAssignment_log(AssignmentLogMining assignment_log_add){	
		assignment_log.add(assignment_log_add);	
	}
	/** standard setter for the attribut course_assignment
	 * @param course_assignment a set of entrys in the course_assignment table which shows the assignments used in the course
	 */	
	public void setCourse_assignment(Set<CourseAssignmentMining> course_assignment) {
		this.course_assignment = course_assignment;
	}
	/** standard getter for the attribut course_assignment
	 * @return a set of entrys in the course_assignment table which shows the assignment used in the course
	 */	
	public Set<CourseAssignmentMining> getCourse_assignment() {
		return course_assignment;
	}
	/** standard add method for the attribut course_assignment
	 * @param course_assignment_add this entry of the course_assignment table will be added to this course
	 * */		
	public void addCourse_assignment(CourseAssignmentMining course_assignment_add){	
		course_assignment.add(course_assignment_add);	
	}
	
	/** standard setter for the attribut scorm_log
	 * @param scorm_log a set of entrys in the scorm_log table which are related to scorm packages in this course
	 */		
	public void setScorm_log(Set<ScormLogMining> scorm_log) {
		this.scorm_log = scorm_log;
	}
	/** standard getter for the attribut scorm_log
	 * @return a set of entrys in the scorm_log table which are related to scorm packages in this course
	 */		
	public Set<ScormLogMining> getScorm_log() {
		return scorm_log;
	}
	/** standard add method for the attribut scorm_log
	 * @param scorm_log_add this entry of the scorm_log table will be added to this course
	 * */		
	public void addScorm_log(ScormLogMining scorm_log_add){	
		scorm_log.add(scorm_log_add);	
	}
	/** standard setter for the attribut course_scorm
	 * @param course_scorm a set of entrys in the course_scorm table which shows the assignments used in the course
	 */	
	public void setCourse_scorm(Set<CourseScormMining> course_scorm) {
		this.course_scorm = course_scorm;
	}
	/** standard getter for the attribut course_scorm
	 * @return a set of entrys in the course_scorm table which shows the scorm used in the course
	 */	
	public Set<CourseScormMining> getCourse_scorm() {
		return course_scorm;
	}
	/** standard add method for the attribut course_scorm
	 * @param course_scorm_add this entry of the course_scorm table will be added to this course
	 * */		
	public void addCourse_scorm(CourseScormMining course_scorm_add){	
		course_scorm.add(course_scorm_add);	
	}
	/** standard setter for the attribut course_scorm
	 * @param degree_course a set of entrys in the course_scorm table which shows the assignments used in the course
	 */	
	public void setDegree_course(Set<DegreeCourseMining> degree_course) {
		this.degree_course = degree_course;
	}
	/** standard getter for the attribut course_scorm
	 * @return a set of entrys in the course_scorm table which shows the scorm used in the course
	 */	
	public Set<DegreeCourseMining> getDegree_course() {
		return degree_course;
	}
	/** standard add method for the attribut course_scorm
	 * @param degree_course_add this entry of the course_scorm table will be added to this course
	 * */		
	public void addDegree_course(DegreeCourseMining degree_course_add){	
		degree_course.add(degree_course_add);	
	}
}
