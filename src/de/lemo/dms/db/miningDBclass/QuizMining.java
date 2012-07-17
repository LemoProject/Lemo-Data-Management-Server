package de.lemo.dms.db.miningDBclass;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the table quiz.*/
@SuppressWarnings("serial")
public class QuizMining implements Serializable , IMappingClass, ILearningObject {

	private long id;
	private String type;	
	private String title;
	private double maxgrade;
	private long timeopen;
	private long timeclose;	
	
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof QuizMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuizMining))
			return true;
		return false;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private long timecreated;
	private long timemodified;

	private Set<CourseQuizMining> courseQuiz = new HashSet<CourseQuizMining>();	
	private Set<QuizQuestionMining> quizQuestion = new HashSet<QuizQuestionMining>();
	private Set<QuizLogMining> quizLog = new HashSet<QuizLogMining>();
	private Set<QuestionLogMining> questionLog = new HashSet<QuestionLogMining>();
	private Set<QuizUserMining> quizUser = new HashSet<QuizUserMining>();
	
	public boolean equals(Object obj) {
		if(!(obj instanceof QuizMining)){
			return false;			
		}
		QuizMining q = (QuizMining)obj;
		if(q.id == this.id && q.timemodified == this.timemodified){
			return true;
		}		
		return false;
	}	

	private Integer hashcodeValue = null;

	public synchronized int hashCode(){
	   if( hashcodeValue == null){
		   hashcodeValue = 42 + Long.bitCount(this.id) + Long.bitCount(this.timemodified);
		   return hashcodeValue;
	   }
	   return hashcodeValue.intValue();
	}
	/** standard getter for the attribut timestamp
	 * @return the timestamp the quiz will be accessable after by students
	 */
	public long getTimeopen() {
		return timeopen;
	}
	/** standard setter for the attribut timestamp
	 * @param timeopen the timestamp the quiz will be accessable after by students
	 * */		
	public void setTimeopen(long timeopen) {
		this.timeopen = timeopen;
	}
	/** standard getter for the attribut timeclose
	 * @return the timestamp after that the quiz will be not accessable any more by students
	 */	
	public long getTimeclose() {
		return timeclose;
	}
	/** standard setter for the attribut timeclose
	 * @param timeclose the timestamp after that the quiz will be not accessable any more by students
	 * */		
	public void setTimeclose(long timeclose) {
		this.timeclose = timeclose;
	}
	/** standard getter for the attribut timecreated
	 * @return the timestamp when the quiz was created
	 */	
	public long getTimecreated() {
		return timecreated;
	}
	/** standard setter for the attribut timecreated
	 * @param timecreated the timestamp when the quiz was created
	 * */	
	public void setTimecreated(long timecreated) {
		this.timecreated = timecreated;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the quiz was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the quiz was changed the last time
	 * */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
	/** standard setter for the attribut title
	 * @param title the title of the quiz
	 * */	
	public void setTitle(String title) {
		this.title = title;
	}
	/** standard getter for the attribut title
	 * @return the title of the quiz
	 */	
	public String getTitle() {
		return title;
	}
	/** standard setter for the attribut course_quiz
	 * @param course_quiz a set of entrys in the course_quiz table which relate the quiz to courses
	 * 	 */	
	public void setCourse_quiz(Set<CourseQuizMining> course_quiz) {
		this.courseQuiz = course_quiz;
	}
	/** standard getter for the attribut course_quiz
	 * @return a set of entrys in the course_quiz assoziation which which relate the quiz to courses
	 */	
	public Set<CourseQuizMining> getCourse_quiz() {
		return courseQuiz;
	}
	/** standard add method for the attribut course_quiz
	 * @param course_quiz_add this entry will be added to the list of course_quiz in this quiz
	 * */	
	public void addCourse_quiz(CourseQuizMining course_quiz_add){	
		courseQuiz.add(course_quiz_add);	
	}
	/** standard setter for the attribut quiz_question
	 * @param quiz_question a set of entrys in the quiz_question which relate the quiz to questions
	 * */	
	public void setQuiz_question(
			Set<QuizQuestionMining> quiz_question) {
		this.quizQuestion = quiz_question;
	}
	/** standard getter for the attribut quiz_question
	 * @return a set of entrys in the quiz_question table which which relate the quiz to questions
	 */	
	public Set<QuizQuestionMining> getQuiz_question() {
		return quizQuestion;
	}
	/** standard add method for the attribut quiz_question
	 * @param quiz_question_add this entry will be added to the list of quiz_question in this quiz
	 * */	
	public void addQuiz_question(QuizQuestionMining quiz_question_add){	
		quizQuestion.add(quiz_question_add);	
	}
	/** standard setter for the attribut quiz_log
	 * @param quiz_log a set of entrys in the quiz_log table which are related with this quiz
	 * */	
	public void setQuiz_log(Set<QuizLogMining> quiz_log) {
		this.quizLog = quiz_log;
	}
	/** standard getter for the attribut quiz_log
	 * @return a set of entrys in the quiz_log table which are related with this quiz
	 */	
	public Set<QuizLogMining> getQuiz_log() {
		return quizLog;
	}
	/** standard setter for the attribut quiz_log
	 * @param quiz_log_add this entry will be added to the list of quiz_log in this quiz
	 * */	
	public void addQuiz_log(QuizLogMining quiz_log_add){	
		quizLog.add(quiz_log_add);
	}
	/** standard setter for the attribut question_log
	 * @param question_log a set of entrys in the question_log table which are related with the questions used in this quiz
	 * */	
	public void setQuestion_log(Set<QuestionLogMining> question_log) {
		this.questionLog = question_log;
	}
	/** standard getter for the attribut question_log
	 * @return a set of entrys in the question_log table which are related with the questions used in this quiz
	 */	
	public Set<QuestionLogMining> getQuestion_log() {
		return questionLog;
	}
	/** standard add method for the attribut question_log
	 * @param question_log_add this entry will be added to the list of question_log in this quiz
	 * */	
	public void addQuestion_log(QuestionLogMining question_log_add){	
		questionLog.add(question_log_add);	
	}
	/** standard setter for the attribut qtype
	 * @param qtype type of this quiz
	 * */	
	public void setQtype(String qtype) {
		this.type = qtype;
	}
	/** standard getter for the attribut qtype
	 * @return the type of this quiz
	 */	
	public String getQtype() {
		return type;
	}
	/** standard setter for the attribut quiz
	 * @param quiz the identifier for this quiz
	 * */	
	public void setId(long quiz) {
		this.id = quiz;
	}
	
	/** standard getter for the attribut qid
	 * @return the identifier for this quiz
	 */	
	public long getId() {
		return id;
	}
	/** standard getter for the attribut maxgrade
	 * @return the maximum grade which is set for the quiz
	 */
	public double getMaxgrade() {
		return maxgrade;
	}
	/** standard setter for the attribut maxgrade
	 * @param maxgrade the maximum grade which is set for the quiz
	 * */
	public void setMaxgrade(double maxgrade) {
		this.maxgrade = maxgrade;
	}
	/** standard setter for the attribut quiz_user
	 * @param quiz_user a set of entrys in the quiz_user table which relate the quiz to user
	 * */
	public void setQuiz_user(Set<QuizUserMining> quiz_user) {
		this.quizUser = quiz_user;
	}
	/** standard getter for the attribut quiz_user
	 * @return a set of entrys in the quiz_user table which relate the quiz to user
	 */
	public Set<QuizUserMining> getQuiz_user() {
		return quizUser;
	}
	/** standard add method for the attribut quiz_user
	 * @param quiz_user_add this entry will be added to the list of quiz_user in this quiz
	 * */	
	public void addQuiz_user(QuizUserMining quiz_user_add){	
		quizUser.add(quiz_user_add);
	}	
}
