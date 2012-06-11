package de.lemo.dms.db.miningDBclass;


import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

/**This class represents the log table for the question object.*/
public class QuestionLogMining implements ILogMining{

	private long id;
	private	QuestionMining question;
	private	UserMining user;
	private CourseMining course;
	private	QuizMining quiz;
	private String type;
	private double penalty;
	private double rawgrade;
	private double finalgrade;
	private String answers;
	private long timestamp;
	private String action;
	private long duration;
	
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String getTitle()
	{
		return this.question.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.question.getId();
	}

	/** standard getter for the attribut id
	 * @return the identifier for the log entry
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard setter for the attribut course
	 * @param course the course in which the action takes place
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** standard getter for the attribut course
	 * @return the course in which the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of the course in which the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addQuestion_log(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuestion_log(this);
		}
	}
	/** standard getter for the attribut type
	 * @return the type of the question
	 */	
	public String getType() {
		return type;
	}
	/** standard setter for the attribut type
	 * @param type the type of the question
	 */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribut answers
	 * @return the answers which are submittet with this action
	 */	
	public String getAnswers() {
		return answers;
	}
	/** standard setter for the attribut answers
	 * @param answers the answers which are submittet with this action
	 */	
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	/** standard getter for the attribut timestamp
	 * @return the timestamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribut timestamp
	 * @param timestamp the timestamp the action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/** standard getter for the attribut penalty
	 * @return the penalty on the grade in this action
	 */	
	public double getPenalty() {
		return penalty;
	}
	/** standard setter for the attribut penalty
	 * @param penalty the penalty on the grade in this action
	 */	
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	/** standard getter for the attribut raw_grade
	 * @return the raw grade in this action(grade without penalty)
	 */	
	public double getRawgrade() {
		return rawgrade;
	}
	/** standard setter for the attribut raw_grade
	 * @param rawgrade the raw grade in this action(grade without penalty)
	 */	
	public void setRawgrade(double rawgrade) {
		this.rawgrade = rawgrade;
	}
	/** standard getter for the attribut finalgrade
	 * @return the final grade in this action(with penalty)
	 */	
	public double getFinalgrade() {
		return finalgrade;
	}
	/** standard setter for the attribut finalgrade
	 * @param finalgrade the final grade in this action(with penalty)
	 */	
	public void setFinalgrade(double finalgrade) {
		this.finalgrade = finalgrade;
	}
	/** standard getter for the attribut question
	 * @return the question with which is interacted
	 */	
	public QuestionMining getQuestion() {
		return question;
	}
	/** standard setter for the attribut question
	 * @param question the question with which is interacted
	 */	
	public void setQuestion(QuestionMining question) {
		this.question = question;
	}
	/** parameterized setter for the attribut question
	 * @param question the id of the course in wich the action takes place
	 * @param questionMining a list of new added questions, which is searched for the question with the id submitted in the question parameter
	 * @param oldQuestionMining a list of questions in the miningdatabase, which is searched for the question with the id submitted in the question parameter
	 */	
	public void setQuestion(long question, HashMap<Long, QuestionMining> questionMining, HashMap<Long, QuestionMining> oldQuestionMining) {		
        
		if(questionMining.get(question) != null)
		{
			this.question = questionMining.get(question);
			questionMining.get(question).addQuestion_log(this);
		}
		if(this.question == null && oldQuestionMining.get(question) != null)
		{
			this.question = oldQuestionMining.get(question);
			oldQuestionMining.get(question).addQuestion_log(this);
		}
	}
	/** standard getter for the attribut quiz
	 * @return the quiz in which the action takes place
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribut quiz
	 * @param quiz the quiz in which the action takes place
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribut quiz
	 * @param quiz the id of the quiz in wich the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the id submitted in the quiz parameter
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the id submitted in the quiz parameter
	 */	
	public void setQuiz(long quiz, HashMap<Long, QuizMining> quizMining, HashMap<Long, QuizMining> oldQuizMining) {		
		
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuestion_log(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuestion_log(this);
		}
	}
	/** standard getter for the attribut user
	 * @return the user who interact with the question
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who interact with the question
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut user
	 * @param user the user who interact with the question
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {				
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addQuestion_log(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuestion_log(this);
		}
	}
	/** standard setter for the attribut action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	/** standard getter for the attribut action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}	
}
